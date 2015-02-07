package com.example.inventory;

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
	private Button viewTest;
	private Button deleteButton;
	private Button cancelButton;
	private Button deleteInPopup;
	private PopupWindow popupWindow;
	private EditText deleteItemName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		addButton = (Button) findViewById(R.id.addButton);
		viewButton = (Button) findViewById(R.id.viewButton);
		viewTest = (Button) findViewById(R.id.viewTest);
		deleteButton = (Button) findViewById(R.id.deleteButton);

		addButton.setOnClickListener(this);
		viewButton.setOnClickListener(this);
		viewTest.setOnClickListener(this);
		deleteButton.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addButton:
			Log.d("add buton", "add button");
			Intent intent1 = new Intent(getApplicationContext(),
					AddItemActivity.class);
			startActivity(intent1);
			break;
		case R.id.viewButton:
			Log.d("view buton", "view button");
			Intent intent2 = new Intent(getApplicationContext(),
					AndroidDatabaseManager.class);
			// Intent intent2 = new Intent(getApplicationContext(),
			// ViewItemActivity.class);
			startActivity(intent2);
			break;
		case R.id.viewTest:
			Log.d("2nd view buton", "2nd view button");
			Intent intent3 = new Intent(getApplicationContext(),
			ViewItemActivity.class);
			startActivity(intent3);
			break;
		case R.id.deleteButton:
			Log.d("deleteButton", "delete button");
			startPopup();
			break;
		case R.id.backButton:
			Log.d("cancelButton", "cancel button");
			popupWindow.dismiss();
			break;
		case R.id.deleteInPopup:
			Log.d("deleteInPopup", "deleteInPopup");
			final RunDatabaseHelper itemsDb = new RunDatabaseHelper(this, null,
					null, 1);
			Items anItem = new Items(deleteItemName.getText().toString(), 7);
			if (itemsDb.deleteItem(anItem)) {
				Toast.makeText(getApplicationContext(), "Delete Successful.",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(), "Item does not exist.",
						Toast.LENGTH_LONG).show();
			}
			deleteItemName.setText("");
			break;
		default:
			throw new RuntimeException("Invalid button");
		}
	}

	private void startPopup() {
		try {
			// We need to get the instance of the LayoutInflater
			LayoutInflater inflater = (LayoutInflater) MainActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.delete_popup,
					(ViewGroup) findViewById(R.id.popup_element));
			popupWindow = new PopupWindow(layout, 600, 650, true);
			popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

			cancelButton = (Button) layout.findViewById(R.id.backButton);
			deleteInPopup = (Button) layout.findViewById(R.id.deleteInPopup);
			deleteItemName = (EditText) layout
					.findViewById(R.id.deleteItemName);

			cancelButton.setOnClickListener(this);
			deleteInPopup.setOnClickListener(this);

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
