package com.inventory;

import com.inventory.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.PopupWindow;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private Button addButton;
	private Button viewButton;
	private Button deleteButton;
	private Button updateButton;

	private Button cancelButton;
	private Button deleteInPopup;
	private Button updateInPopup;
	private PopupWindow popupWindow;
	private EditText editThisItem;
	private EditText updateItemAmount;
	private EditText updateItemName;
	
	Items anItem;
	RunDatabaseHelper itemsDb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		addButton = (Button) findViewById(R.id.addButton);
		viewButton = (Button) findViewById(R.id.viewTest);
		deleteButton = (Button) findViewById(R.id.deleteButton);
		updateButton = (Button) findViewById(R.id.updateButton);

		addButton.setOnClickListener(this);
		viewButton.setOnClickListener(this);
		deleteButton.setOnClickListener(this);
		updateButton.setOnClickListener(this);
		
		anItem = new Items();
		itemsDb = new RunDatabaseHelper(this, null,null, 1);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addButton:
			Log.d("add buton", "add button");
			Intent intent1 = new Intent(getApplicationContext(),
					AddItemActivity.class);
			startActivity(intent1);
			break;
		case R.id.viewTest:
			Log.d("view buton", "view button");
			Intent intent3 = new Intent(getApplicationContext(),
					ViewItemActivity.class);
			startActivity(intent3);
			break;
		case R.id.deleteButton:
			Log.d("deleteButton", "delete button");
			startPopup(R.layout.delete_popup);
			break;
		case R.id.backButton:
			Log.d("cancelButton", "cancel button");
			popupWindow.dismiss();
			break;
		case R.id.deleteInPopup:
			Log.d("deleteInPopup", "deleteInPopup");
			//final RunDatabaseHelper itemsDb = new RunDatabaseHelper(this, null,
				//	null, 1);
			anItem = new Items(editThisItem.getText().toString(), 7);
			if (itemsDb.deleteItem(anItem)) {
				Toast.makeText(getApplicationContext(), "Delete Successful.",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "Item does not exist.",
						Toast.LENGTH_SHORT).show();
			}
			editThisItem.setText("");
			break;
		case R.id.updateButton:
			Log.d("updateButton", "In updateButton");
			startPopup(R.layout.update_popup);
			break;
		case R.id.updateInPopup:
			anItem = new Items(updateItemName.getText().toString(),
					Integer.parseInt(updateItemAmount.getText().toString()));
			if(itemsDb.updateItem(anItem) > 0){
				Toast.makeText(getApplicationContext(), "Update Successful.",
						Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getApplicationContext(), "Item Does Not Exist.",
						Toast.LENGTH_SHORT).show();
			}
			updateItemName.setText("");
			updateItemAmount.setText("");
			break;
		default:
			throw new RuntimeException("Invalid button");
		}
	}

	private void startPopup(int layoutType) {
		try {
			// We need to get the instance of the LayoutInflater
			LayoutInflater inflater = (LayoutInflater) MainActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(layoutType,
					(ViewGroup) findViewById(R.id.popup_element));
			popupWindow = new PopupWindow(layout, 600, 650, true);
			popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

			editThisItem = (EditText) layout.findViewById(R.id.deleteItemName);
			updateItemAmount = (EditText) layout.findViewById(R.id.updateItemAmount);
			updateItemName = (EditText) layout.findViewById(R.id.updateItemName);
			
			cancelButton = (Button) layout.findViewById(R.id.backButton);
			cancelButton.setOnClickListener(this);
			if (layoutType == R.layout.delete_popup) {
				deleteInPopup = (Button) layout.findViewById(R.id.deleteInPopup);
				deleteInPopup.setOnClickListener(this);
			}else if(layoutType == R.layout.update_popup){
				updateInPopup = (Button) layout.findViewById(R.id.updateInPopup);
				updateInPopup.setOnClickListener(this);
			}

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
