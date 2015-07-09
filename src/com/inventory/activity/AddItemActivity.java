package com.inventory.activity;

import com.inventory.R;
import com.inventory.data.Items;
import com.inventory.provider.InventoryDatabaseHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.inventory.utils.*;

public class AddItemActivity extends Activity {

	private Button addComplete;
	private EditText itemName;
	private EditText itemQuantity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		final InventoryDatabaseHelper itemsDb = new InventoryDatabaseHelper(this, null,
				null, 1);
		addComplete = (Button) findViewById(R.id.addComplete);
		itemName = (EditText) findViewById(R.id.newItemName);
		itemQuantity = (EditText) findViewById(R.id.newItemAmount);
		addComplete.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (!itemName.getText().toString().isEmpty()
						&& !itemQuantity.getText().toString().isEmpty()) {
					Items anItem = new Items(itemName.getText().toString(),
							Integer.parseInt(itemQuantity.getText().toString()));
					if (itemsDb.insertItem(anItem)) {
						Utils.showToast(getApplicationContext(),"Add Successful.");
					} else {
						Utils.showToast(getApplicationContext(),"Add failed. Please try again.");
					}
					itemName.setText("");
					itemQuantity.setText("");
				}else{
					Utils.showToast(getApplicationContext(), "Missing item name or quantity.");
							
				}
			}
		});
	}
}
