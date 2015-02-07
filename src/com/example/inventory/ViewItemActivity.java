package com.example.inventory;

import android.app.Activity;
import android.os.Bundle;

public class ViewItemActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);
		final RunDatabaseHelper itemsDb = new RunDatabaseHelper(this,null,null,1);
		itemsDb.getAllItems();	
	}
}
