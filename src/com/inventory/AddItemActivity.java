package com.inventory;

import com.inventory.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddItemActivity extends Activity {

	private Button addComplete;
	private EditText itemName;
	private EditText itemQuantity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		final RunDatabaseHelper itemsDb = new RunDatabaseHelper(this, null,
				null, 1);
		addComplete = (Button) findViewById(R.id.addComplete);
		itemName = (EditText) findViewById(R.id.newItemName);
		itemQuantity = (EditText) findViewById(R.id.newItemAmount);
		addComplete.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// itemName.setVisibility(View.INVISIBLE);
				// itemQuantity.setVisibility(View.INVISIBLE);
				// if(!itemName.getText().toString().equals("")){
				// Integer.parseInt(itemQuantity.getText().toString()) != " "
				Log.d("before new item obj", "no");
				if (!itemName.getText().toString().isEmpty()
						&& !itemQuantity.getText().toString().isEmpty()) {
					Items anItem = new Items(itemName.getText().toString(),
							Integer.parseInt(itemQuantity.getText().toString()));
					if (itemsDb.insertItem(anItem)) {
						Toast.makeText(getApplicationContext(),
								"Add Successful.", Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(getApplicationContext(),
								"Add failed. Please try again.",
								Toast.LENGTH_LONG).show();
					}
					itemName.setText("");
					itemQuantity.setText("");
				}else{
					Toast.makeText(getApplicationContext(),
							"Missing item name or quantity.", Toast.LENGTH_LONG).show();
				}
				

				// }

			}
		});
	}
}
