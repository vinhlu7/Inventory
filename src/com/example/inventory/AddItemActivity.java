package com.example.inventory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddItemActivity extends Activity{
	
	private Button addComplete;
	private EditText itemName;
	private EditText itemQuantity;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        final RunDatabaseHelper itemsDB = new RunDatabaseHelper(this);
        addComplete = (Button) findViewById(R.id.addComplete);
        itemName = (EditText) findViewById(R.id.newItemName);
        itemQuantity = (EditText) findViewById(R.id.newItemAmount);
        addComplete.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v) {
        		//itemName.setVisibility(View.INVISIBLE);
        		//itemQuantity.setVisibility(View.INVISIBLE);
        		//if(!itemName.getText().toString().equals("")){
        			//Integer.parseInt(itemQuantity.getText().toString()) != " "
        			itemsDB.insertItem(itemName.getText().toString(),Integer.parseInt(itemQuantity.getText().toString()));
        		//}
        		
        	}
        });
    }
}
