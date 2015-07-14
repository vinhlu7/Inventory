package com.inventory.provider;

import java.util.LinkedList;
import java.util.List;

import com.inventory.data.Items;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class InventoryDatabaseHelper extends SQLiteOpenHelper {

	private ContentResolver myCr;

	private static final String DB_NAME = "items.sqlite";
	private static final int VERSION = 1;

	public static final String TABLE_ITEMS = "items";
	public static final String TABLE2 = "fts_table";
	public static final String COLUMN_ITEMS_ID = "_id";
	private static final String COLUMN_ITEMS_NAME = "name";
	private static final String COLUMN_ITEMS_QUANTITY = "quantity";
	private static final String COLUMN_ITEMS_LAST_MOD = "update_time";
	private static final String[] COLUMNS = { COLUMN_ITEMS_ID,
			COLUMN_ITEMS_NAME, COLUMN_ITEMS_QUANTITY, COLUMN_ITEMS_LAST_MOD};

	public InventoryDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, DB_NAME, factory, VERSION);
		myCr = context.getContentResolver();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create the "run" table
		db.execSQL("CREATE TABLE items (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"name text, quantity integer, update_time text );");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d("onUpgrade()", "drop db");
		db.execSQL("DROP TABLE IF EXISTS items");
		db.execSQL("DROP TABLE IF EXISTS fts_table");
		this.onCreate(db);
	}

	public boolean insertItem(Items item) {
		boolean success = false;;
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_ITEMS_NAME, item.getItemName());
		cv.put(COLUMN_ITEMS_QUANTITY, item.getItemQuantity());
		cv.put(COLUMN_ITEMS_LAST_MOD, item.getTime());

		myCr.insert(MyContentProvider.CONTENT_URI, cv);
		Log.d("insertItem()", item.toString());
		success = true;

		return success;
	}

	public Items getItem(int id) {

		Cursor cursor = myCr.query(MyContentProvider.CONTENT_URI, COLUMNS,
				COLUMN_ITEMS_ID + " =?", new String[] { String.valueOf(id) },
				null);

		if (cursor != null)
			cursor.moveToFirst();

		Items item = new Items();
		item.setId(Integer.parseInt(cursor.getString(0)));
		item.setItemName(cursor.getString(1));
		item.setItemQuantity(Integer.parseInt(cursor.getString(2)));

		Log.d("getItem(" + id + ")", item.toString());

		return item;
	}

	public int getId(Items item) {

		Cursor cursor = myCr.query(MyContentProvider.CONTENT_URI, COLUMNS,
				" name = ?", new String[] { item.getItemName() }, null);

		if (cursor != null && cursor.getCount() != 0) {
			cursor.moveToFirst();

			Items itemFound = new Items();
			itemFound.setId(Integer.parseInt(cursor.getString(0)));

			Log.d("getId()", item.toString());

			return itemFound.getId();
		} else {
			return 0;
		}
	}

	public List<Items> getAllItems() {
		List<Items> itemsList = new LinkedList<Items>();

		Cursor cursor = myCr.query(MyContentProvider.CONTENT_URI, COLUMNS,
				null, null, null);

		Items itemInstant = null;
		if (cursor.moveToFirst()) {
			do {
				itemInstant = new Items();
				itemInstant.setId(Integer.parseInt(cursor.getString(0)));
				itemInstant.setItemName(cursor.getString(1));
				itemInstant.setItemQuantity(Integer.parseInt(cursor
						.getString(2)));
				itemsList.add(itemInstant);
			} while (cursor.moveToNext());
		}

		Log.d("getAllItems()", itemInstant.toString());
		return itemsList;
	}

	public int updateItem(Items item) {
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_ITEMS_NAME, item.getItemName());
		cv.put(COLUMN_ITEMS_QUANTITY, item.getItemQuantity());
		cv.put(COLUMN_ITEMS_LAST_MOD, item.getTime());
		Uri uri = Uri.parse(MyContentProvider.CONTENT_URI + "/" + getId(item));

		int updatedRows = myCr.update(uri, cv, COLUMN_ITEMS_ID + " =?",
				new String[] { String.valueOf(getId(item)) });
		return updatedRows;
	}

	public boolean deleteItem(Items item) {
		boolean deleted = false;

		if (getId(item) > 0) {
			myCr.delete(MyContentProvider.CONTENT_URI,
					COLUMN_ITEMS_ID + " = ?",
					new String[] { String.valueOf(getId(item)) });
			deleted = true;
		}
		return deleted;
	}

	
}
