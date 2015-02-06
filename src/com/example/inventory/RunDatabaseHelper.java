package com.example.inventory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RunDatabaseHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "items.sqlite";
	private static final int VERSION = 1;

	private static final String TABLE_ITEMS = "items";
	private static final String COLUMN_ITEMS_ID = "_id";
	private static final String COLUMN_ITEMS_NAME = "name";
	private static final String COLUMN_ITEMS_QUANTITY = "quantity";
	private static final String[] COLUMNS = { COLUMN_ITEMS_ID,
			COLUMN_ITEMS_NAME, COLUMN_ITEMS_QUANTITY };

	public RunDatabaseHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create the "run" table
		db.execSQL("CREATE TABLE items (_id INTEGER PRIMARY KEY AUTOINCREMENT, name text, quantity integer);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d("drop db", "no");
		db.execSQL("DROP TABLE IF EXISTS items");
		this.onCreate(db);
	}

	public boolean insertItem(Items item) {
		boolean success = false;
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_ITEMS_NAME, item.getItemName());
		cv.put(COLUMN_ITEMS_QUANTITY, item.getItemQuantity());
		if (db.insert(TABLE_ITEMS, null, cv) > 0) {
			db.close();
			Log.d("insertItem()", item.toString());
			success = true;
		}
		return success;
	}

	public Items getItem(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_ITEMS, COLUMNS, " id = ?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		// 3. if we got results get the first one
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
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_ITEMS, COLUMNS, " name = ?",
				new String[] { item.getItemName() }, null, null, null, null);

		if (cursor != null) {
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

		String query = "SELECT * FROM " + TABLE_ITEMS;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

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
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_ITEMS_NAME, item.getItemName());
		cv.put(COLUMN_ITEMS_QUANTITY, item.getItemQuantity());

		int updatedRows = db.update(
				TABLE_ITEMS, // returns # of rows affected
				cv, COLUMN_ITEMS_ID + " = ?",
				new String[] { String.valueOf(item.getId()) });

		db.close();
		return updatedRows;
	}

	public boolean deleteItem(Items item) {
		boolean deleted = false;
		SQLiteDatabase db = this.getWritableDatabase();
		if (getId(item) > 0) {
			db.delete(TABLE_ITEMS, COLUMN_ITEMS_ID + " = ?",
					new String[] { String.valueOf(getId(item)) });
			deleted = true;
			db.close();
			Log.d("deleteItem", item.toString());
		}
		return deleted;
	}

	public ArrayList<Cursor> getData(String Query) {
		// get writable database
		SQLiteDatabase sqlDB = this.getWritableDatabase();
		String[] columns = new String[] { "mesage" };
		// an array list of cursor to save two cursors one has results from the
		// query
		// other cursor stores error message if any errors are triggered
		ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
		MatrixCursor Cursor2 = new MatrixCursor(columns);
		alc.add(null);
		alc.add(null);

		try {
			String maxQuery = Query;
			// execute the query results will be save in Cursor c
			Cursor c = sqlDB.rawQuery(maxQuery, null);

			// add value to cursor2
			Cursor2.addRow(new Object[] { "Success" });

			alc.set(1, Cursor2);
			if (null != c && c.getCount() > 0) {

				alc.set(0, c);
				c.moveToFirst();

				return alc;
			}
			return alc;
		} catch (SQLException sqlEx) {
			Log.d("printing exception", sqlEx.getMessage());
			// if any exceptions are triggered save the error message to cursor
			// an return the arraylist
			Cursor2.addRow(new Object[] { "" + sqlEx.getMessage() });
			alc.set(1, Cursor2);
			return alc;
		} catch (Exception ex) {

			Log.d("printing exception", ex.getMessage());

			// if any exceptions are triggered save the error message to cursor
			// an return the arraylist
			Cursor2.addRow(new Object[] { "" + ex.getMessage() });
			alc.set(1, Cursor2);
			return alc;
		}

	}
}
