package com.example.inventory;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ViewItemActivity extends FragmentActivity implements
		LoaderManager.LoaderCallbacks<Cursor> {
	TextView resultView = null;
	CursorLoader cursorLoader;
	
	private static final String AUTHORITY = "com.example.inventory.MyContentProvider";
	private static final String TABLE_ITEMS = "items";
	private static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE_ITEMS);
	private static final String COLUMN_ITEMS_ID = "_id";
	private static final String COLUMN_ITEMS_NAME = "name";
	private static final String COLUMN_ITEMS_QUANTITY = "quantity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);
		resultView = (TextView) findViewById(R.id.res);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		getSupportLoaderManager().initLoader(1, null, this);
		return true;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		cursorLoader = new CursorLoader(
				this,
				Uri.parse("content://" + AUTHORITY
						+ "/" + TABLE_ITEMS),
				null, null, null, null);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		cursor.moveToFirst();
		StringBuilder res = new StringBuilder();
		while (!cursor.isAfterLast()) {
			res.append("\n" + cursor.getString(cursor.getColumnIndex(COLUMN_ITEMS_ID))
					+ "-" + cursor.getString(cursor.getColumnIndex(COLUMN_ITEMS_NAME))
					+ ":" + cursor.getString(cursor.getColumnIndex(COLUMN_ITEMS_QUANTITY)));
			cursor.moveToNext();
		}
		resultView.setText(res);
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