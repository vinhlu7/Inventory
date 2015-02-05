package com.example.inventory;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RunDatabaseHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "items.sqlite";
	private static final int VERSION = 1;

	private static final String TABLE_ITEMS = "items";
	private static final String COLUMN_ITEMS_ID = "id";
	private static final String COLUMN_ITEMS_NAME = "name";
	private static final String COLUMN_ITEMS_QUANTITY = "quantity";
	private static final String[] COLUMNS = {COLUMN_ITEMS_ID,COLUMN_ITEMS_NAME,COLUMN_ITEMS_QUANTITY};

	public RunDatabaseHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create the "run" table
		db.execSQL("CREATE TABLE items (_id integer primary key autoincrement, name text, quantity integer);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS items");
		onCreate(db);
	}

	public void insertItem(Items item) {
		Log.d("insertItem", item.toString());
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_ITEMS_NAME, item.getItemName());
		cv.put(COLUMN_ITEMS_QUANTITY, item.getItemQuantity());
		db.insert(TABLE_ITEMS, null, cv);
		db.close();
	}
	
	public Items getItem(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = 
				db.query(TABLE_ITEMS,
						COLUMNS,
						" id = ?",
						new String[]{ String.valueOf(id)},
						null,
						null,
						null,
						null);
		
		// 3. if we got results get the first one
	    if (cursor != null)
	        cursor.moveToFirst();
	    
	    Items item = new Items();
	    item.setId(Integer.parseInt(cursor.getString(0)));
	    item.setItemName(cursor.getString(1));
	    item.setItemQuantity(Integer.parseInt(cursor.getString(2)));
	    
	    Log.d("getItem("+id+")", item.toString());
	    
	    return item;
	}
	
	public List<Items> getAllItems(){
		List<Items> itemsList = new LinkedList<Items>();
		
		String query = "SELECT * FROM " + TABLE_ITEMS;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		Items itemInstant = null;
		if(cursor.moveToFirst()){
			do{
				itemInstant = new Items();
				itemInstant.setId(Integer.parseInt(cursor.getString(0)));
			    itemInstant.setItemName(cursor.getString(1));
			    itemInstant.setItemQuantity(Integer.parseInt(cursor.getString(2)));
				itemsList.add(itemInstant);
			}while(cursor.moveToNext());
		}
		
		
		
	}
}
