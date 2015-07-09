package com.inventory.activity;

import com.inventory.R;
import com.inventory.data.Items;
import com.inventory.provider.InventoryDatabaseHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import com.inventory.utils.*;

public class InventoryActivity extends Activity implements OnClickListener {

	private Button addButton;
	private Button viewButton;
	private Button deleteButton;
	private Button updateButton;

	private Button deleteInPopup;
	private Button updateInPopup;
	private PopupWindow popupWindow;
	private EditText editThisItem;
	private EditText updateItemAmount;
	private EditText updateItemName;
	private FrameLayout layout_MainMenu;

	Items anItem;
	InventoryDatabaseHelper itemsDb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		addButton = (Button) findViewById(R.id.addButton);
		viewButton = (Button) findViewById(R.id.viewTest);
		deleteButton = (Button) findViewById(R.id.deleteButton);
		updateButton = (Button) findViewById(R.id.updateButton);
		layout_MainMenu = (FrameLayout) findViewById(R.id.mainmenu);
		layout_MainMenu.getForeground().setAlpha(0);

		addButton.setOnClickListener(this);
		viewButton.setOnClickListener(this);
		deleteButton.setOnClickListener(this);
		updateButton.setOnClickListener(this);

		anItem = new Items();
		itemsDb = new InventoryDatabaseHelper(this, null, null, 1);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addButton:
			Log.d("add buton", "add button");
			Intent addIntent = new Intent(getApplicationContext(),
					AddItemActivity.class);
			startActivity(addIntent);
			break;
		case R.id.viewTest:
			Log.d("view buton", "view button");
			Intent viewIntent = new Intent(getApplicationContext(),
					ViewItemActivity.class);
			startActivity(viewIntent);
			break;
		case R.id.deleteButton:
			Log.d("deleteButton", "delete button");
			startPopup(R.layout.delete_popup);
			break;

		case R.id.deleteInPopup:
			Log.d("deleteInPopup", "deleteInPopup");
			anItem = new Items(editThisItem.getText().toString(), 7);
			if (!editThisItem.getText().toString().isEmpty()) {
				if (itemsDb.deleteItem(anItem)) {
					Utils.showToast(this,"Delete Successful.");
				} else {
					Utils.showToast(this,"Item does not exist.");
				}
			} else {
				Utils.showToast(this,"Missing item name.");
			}
			editThisItem.setText("");
			break;
		case R.id.updateButton:
			Log.d("InventoryActivity", "In updateButton");
			startPopup(R.layout.update_popup);
			break;
		case R.id.updateInPopup:
			if (!updateItemName.getText().toString().isEmpty()
					&& !updateItemAmount.getText().toString().isEmpty()) {
				anItem = new Items(updateItemName.getText().toString(),
						Integer.parseInt(updateItemAmount.getText().toString()));
				if (itemsDb.updateItem(anItem) > 0) {
					Utils.showToast(this,"Update successful.");
				} else {
					Utils.showToast(this,"Item does not exist.");
				}
				updateItemName.setText("");
				updateItemAmount.setText("");
			} else {
				Utils.showToast(this,"Missing item name or quantity.");
			}
			break;
		default:
			throw new RuntimeException("Invalid button");
		}
	}

	private void startPopup(int layoutType) {
		try {
			layout_MainMenu.getForeground().setAlpha(220);
			// We need to get the instance of the LayoutInflater
			LayoutInflater inflater = (LayoutInflater) InventoryActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(layoutType,
					(ViewGroup) findViewById(R.id.popup_element));
			popupWindow = new PopupWindow(layout, 600, 650, true);
			popupWindow.setOutsideTouchable(true);
			popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
			popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

			editThisItem = (EditText) layout.findViewById(R.id.deleteItemName);
			updateItemAmount = (EditText) layout
					.findViewById(R.id.updateItemAmount);
			updateItemName = (EditText) layout
					.findViewById(R.id.updateItemName);

			if (layoutType == R.layout.delete_popup) {
				deleteInPopup = (Button) layout
						.findViewById(R.id.deleteInPopup);
				deleteInPopup.setOnClickListener(this);
			} else if (layoutType == R.layout.update_popup) {
				updateInPopup = (Button) layout
						.findViewById(R.id.updateInPopup);
				updateInPopup.setOnClickListener(this);
			}

			popupWindow
					.setOnDismissListener(new PopupWindow.OnDismissListener() {
						@Override
						public void onDismiss() {
							layout_MainMenu.getForeground().setAlpha(0);
						}
					});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
