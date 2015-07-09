package com.inventory.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.inventory.R;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ViewItemActivity extends ListActivity implements
		LoaderManager.LoaderCallbacks<Cursor> {
	TextView resultView = null;
	CursorLoader cursorLoader;

	private static final String AUTHORITY = "com.inventory.MyContentProvider";
	private static final String TABLE_ITEMS = "items";

	ListView listView;

	ArrayList<HashMap<String, String>> allItemsList;
	HashMap<String, String> map;
	SimpleAdapter simpleAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);
		
		listView = (ListView) findViewById(android.R.id.list);
		allItemsList = new ArrayList<HashMap<String, String>>();
		map = new HashMap<String, String>();
		getLoaderManager().initLoader(0, null, this);
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
		while (!cursor.isAfterLast()) {
			map = new HashMap<String, String>();
			map.put("name", cursor.getString(1));
			map.put("quantity", cursor.getString(2));
			map.put("update_time", cursor.getString(3));
			allItemsList.add(map);
			cursor.moveToNext();
		}
		simpleAdapter = new SimpleAdapter(this, allItemsList, R.layout.view_item,
				new String[] { "name", "quantity", "update_time" }, new int[] {
						R.id.textViewName, R.id.textViewQuantity, R.id.textViewUpdate});
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