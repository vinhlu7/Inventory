package com.inventory.provider;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class MyContentProvider extends ContentProvider {

	private InventoryDatabaseHelper myDB;

	private static final String AUTHORITY = "com.inventory.MyContentProvider";
	private static final String TABLE_ITEMS = "items";
	public static final String COLUMN_ITEMS_ID = "_id";
	
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE_ITEMS);
	public static final Uri COLUMN_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE_ITEMS + "/#" + COLUMN_ITEMS_ID);

	public static final int ALL_ITEMS = 1;
	public static final int ITEM_ID = 2;

	private static final UriMatcher sURIMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);

	static {
		sURIMatcher.addURI(AUTHORITY, TABLE_ITEMS, ALL_ITEMS);
		sURIMatcher.addURI(AUTHORITY, TABLE_ITEMS + "/#", ITEM_ID);
	}

	@Override
	public boolean onCreate() {
		myDB = new InventoryDatabaseHelper(getContext(),null,null,1);
		return false;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {	

		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = myDB.getWritableDatabase();
		int rowsDeleted = 0;

		switch (uriType) {
		    case ALL_ITEMS:
		      rowsDeleted = sqlDB.delete(InventoryDatabaseHelper.TABLE_ITEMS,
	              selection,
		        selectionArgs);
		        break;
		      
		    case ITEM_ID:
		      String id = uri.getLastPathSegment();
		      if (TextUtils.isEmpty(selection)) {
		        rowsDeleted = sqlDB.delete(InventoryDatabaseHelper.TABLE_ITEMS,
		        		InventoryDatabaseHelper.COLUMN_ITEMS_ID + "=" + id, 
		            null);
		      } else {
		        rowsDeleted = sqlDB.delete(InventoryDatabaseHelper.TABLE_ITEMS,
		        		InventoryDatabaseHelper.COLUMN_ITEMS_ID + "=" + id 
		            + " and " + selection,
		            selectionArgs);
		      }
		      break;
		    default:
		      throw new IllegalArgumentException("Unknown URI: " + uri);
		    }
		    getContext().getContentResolver().notifyChange(uri, null);
		    return rowsDeleted;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriType = sURIMatcher.match(uri);

		SQLiteDatabase sqlDB = myDB.getWritableDatabase();

		long id = 0;
		switch (uriType) {
		case ALL_ITEMS:
			id = sqlDB.insert(InventoryDatabaseHelper.TABLE_ITEMS, null, values);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(TABLE_ITEMS + "/" + id);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(InventoryDatabaseHelper.TABLE_ITEMS);

		int uriType = sURIMatcher.match(uri);

		switch (uriType) {
		case ITEM_ID:
			queryBuilder.appendWhere(InventoryDatabaseHelper.COLUMN_ITEMS_ID + "="
					+ uri.getLastPathSegment());
			break;
		case ALL_ITEMS:
			break;
		default:
			throw new IllegalArgumentException("Unknown URI");
		}

		Cursor cursor = queryBuilder.query(myDB.getReadableDatabase(),
				projection, selection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
		      String[] selectionArgs) {

		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = myDB.getWritableDatabase();
		int rowsUpdated = 0;

		switch (uriType) {
		  case ALL_ITEMS:
		    rowsUpdated = sqlDB.update(InventoryDatabaseHelper.TABLE_ITEMS, 
		        values, 
		        selection,
		        selectionArgs);
		    break;
		  case ITEM_ID:
		    String id = uri.getLastPathSegment();
		    if (TextUtils.isEmpty(selection)) {
		      rowsUpdated = 
			sqlDB.update(InventoryDatabaseHelper.TABLE_ITEMS, 
		          values,
		          InventoryDatabaseHelper.COLUMN_ITEMS_ID + "=" + id, 
		          null);
		    } else {
		      rowsUpdated = 
			sqlDB.update(InventoryDatabaseHelper.TABLE_ITEMS, 
		          values,
		          InventoryDatabaseHelper.COLUMN_ITEMS_ID + "=" + id
		          + " and " 
		          + selection,
		          selectionArgs);
		    }
		    break;
		  default:
		    throw new IllegalArgumentException("Unknown URI: " + uri);
		  }
		getContext().getContentResolver().notifyChange(uri, null);
	      return rowsUpdated;
}

}
