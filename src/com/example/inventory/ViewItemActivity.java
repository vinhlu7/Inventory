package com.example.inventory;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ViewItemActivity extends ListActivity implements
		LoaderManager.LoaderCallbacks<Cursor> {
	TextView resultView = null;
	CursorLoader cursorLoader;

	private static final String AUTHORITY = "com.example.inventory.MyContentProvider";
	private static final String TABLE_ITEMS = "items";

	//ArrayList<String> list = new ArrayList<String>();
	//ArrayAdapter<String> adapter;
	TextView nameHeader;
	TextView quantityHeader;
	ListView listView;

	ArrayList<HashMap<String, String>> allItemsList;
	HashMap<String, String> map;
	SimpleAdapter simpleAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);

		//adapter = new ArrayAdapter<String>(this,
			//	android.R.layout.simple_list_item_1, list);

		nameHeader = (TextView) findViewById(R.id.header);
		quantityHeader = (TextView) findViewById(R.id.headerQuantity);
		quantityHeader.setText("QUANTITY");
		quantityHeader.setTypeface(null, Typeface.BOLD);
		quantityHeader.setTextColor(Color.BLACK);
		
		nameHeader.setText("NAME");
		nameHeader.setTypeface(null, Typeface.BOLD);
		nameHeader.setTextColor(Color.BLACK);
		// textView.setWidth(10);
		// listView = getListView();
		// listView.addHeaderView(textView);
		listView = (ListView) findViewById(android.R.id.list);
		allItemsList = new ArrayList<HashMap<String, String>>();
		map = new HashMap<String, String>();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		getLoaderManager().initLoader(0, null, this);
		return true;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		cursorLoader = new CursorLoader(this, Uri.parse("content://"
				+ AUTHORITY + "/" + TABLE_ITEMS), null, null, null, null);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		cursor.moveToFirst();
		// StringBuilder res = new StringBuilder();
		while (!cursor.isAfterLast()) {
			// res.append("\n" +
			// cursor.getString(cursor.getColumnIndex(COLUMN_ITEMS_ID))
			// + "-" +
			// cursor.getString(cursor.getColumnIndex(COLUMN_ITEMS_NAME))
			// + ":" +
			// cursor.getString(cursor.getColumnIndex(COLUMN_ITEMS_QUANTITY)));
			// list.add(cursor.getString(1) + "\t" + "\t" + "\t" + "\t" + "\t" +
			// cursor.getString(2));
			map = new HashMap<String, String>();
			map.put("name", cursor.getString(1));
			map.put("quantity", cursor.getString(2));
			allItemsList.add(map);
			//adapter.notifyDataSetChanged();
			cursor.moveToNext();
		}
		// resultView.setText(res);
		// setListAdapter(adapter);
		simpleAdapter = new SimpleAdapter(this, allItemsList, R.layout.view_item,
				new String[] { "name", "quantity" }, new int[] {
						R.id.textViewName, R.id.textViewQuantity });
		listView.setAdapter(simpleAdapter);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}