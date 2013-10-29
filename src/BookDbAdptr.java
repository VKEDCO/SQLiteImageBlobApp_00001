package org.vkedco.mobappdev.sqlite_image_blob_app_00001;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/*
 * ***************************************************
 * BookDbAdptr.java
 * Class responsible for programmatically creating, maintaining, and quering
 * book_info.db 
 * 
 * Bugs to vladimir dot kulyukin at gmail dot com
 * ***************************************************
 */

public class BookDbAdptr {
	
	public static final String ADPTR_LOGTAG = BookDbAdptr.class.getSimpleName() + "_TAG";
	public static final String QUERY_LOGTAG = "QUERY_TAG";
	
	public static final String DB_NAME            		= "book_info.db";
	public static final int    DB_VERSION         		= 1;
	public static final String BOOK_TITLE_TABLE   		= "book_title";
	public static final String BOOK_AUTHOR_TABLE  		= "book_author";
	public static final String BOOK_COVER_IMAGE_TABLE 	= "book_cover_image";
	public static final String BOOK_ORDER_TABLE			= "book_order";

	// ********** book_title constants ******************
	// constants for book_title table column names
	public static final String BOOK_ID_COL_NAME         = "ID";
	public static final String BOOK_TITLE_COL_NAME      = "Title";
	public static final String BOOK_AUTHOR_COL_NAME 	= "BookAuthor";
	public static final String BOOK_TRANSLATOR_COL_NAME = "Translator";
	public static final String BOOK_ISBN_COL_NAME		= "ISBN";
	public static final String BOOK_PRICE_COL_NAME	    = "Price";
	// constants for book_title table column numbers
	public static final int BOOK_ID_COL_NUM         	= 0;
	public static final int BOOK_TITLE_COL_NUM      	= 1;
	public static final int BOOK_AUTHOR_COL_NUM     	= 2;
	public static final int BOOK_TRANSLATOR_COL_NUM		= 3;
	public static final int BOOK_ISBN_COL_NUM			= 4;
	public static final int BOOK_PRICE_COL_NUM			= 5;
	
	// ********** book_author table constants ******************
	// constants for book_author table column names
	public static final String AUTHOR_ID_COL_NAME 	  	  	= "ID";
	public static final String AUTHOR_NAME_COL_NAME   	  	= "Name";
	public static final String AUTHOR_BIRTH_YEAR_COL_NAME 	= "BirthYear";
	public static final String AUTHOR_DEATH_YEAR_COL_NAME 	= "DeathYear";
	public static final String AUTHOR_COUNTRY_COL_NAME    	= "Country";
	// constants for book_author table column numbers
	public static final int AUTHOR_ID_COL_NUM 		  		= 0;
	public static final int AUTHOR_NAME_COL_NUM       		= 1;
	public static final int AUTHOR_BIRTH_YEAR_COL_NUM 		= 2;
	public static final int AUTHOR_DEATH_YEAR_COL_NUM 		= 3;
	public static final int AUTHOR_COUNTRY_COL_NUM    		= 4;
	
	// ********** book_cover_image table constants ******************
	// constants for book_cover_image column names
	public static final String COVER_IMAGE_ID_COL_NAME		= "ID";
	public static final String COVER_IMAGE_ISBN_COL_NAME	= "ISBN";
	public static final String COVER_IMAGE_IMG_COL_NAME		= "IMG";
	// constants for book cover image column numbers
	public static final int COVER_IMAGE_ID_COL_NUM			= 0;
	public static final int COVER_IMAGE_ISBN_COL_NUM		= 1;
	public static final int COVER_IMAGE_IMG_COL_NUM			= 2;
	
	// ********** book_order table constants ******************
	// constants for book_order table column names
	public static final String BOOK_ORDER_ID_COL_NAME		= "ID";
	public static final String BOOK_ORDER_ISBN_COL_NAME		= "ISBN";
	public static final String BOOK_ORDER_CFN_COL_NAME		= "FirstName";
	public static final String BOOK_ORDER_CLN_COL_NAME		= "LastName";
	public static final String BOOK_ORDER_PRICE_COL_NAME	= "Price";
	// constants for book_order table column numbers
	public static final int BOOK_ORDER_ID_COL_NUM			= 0;
	public static final int BOOK_ORDER_ISBN_COL_NUM			= 1;
	public static final int BOOK_ORDER_CFN_COL_NUM			= 2;
	public static final int BOOK_ORDER_CLN_COL_NUM			= 3;
	public static final int BOOK_ORDER_PRICE_COL_NUM		= 4;
	
	
	private SQLiteDatabase   	 mDb = null;
	private BookInfoDBOpenHelper mDbHelper = null;
	
	// BookInfoDBOpenHelper class creates the table in the database
	private static class BookInfoDBOpenHelper extends SQLiteOpenHelper {
		static final String HELPER_LOGTAG = BookInfoDBOpenHelper.class.getSimpleName() + "_TAG";
		
		public BookInfoDBOpenHelper(Context context, String name, 
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}
		
		// table creation string constant
		static final String BOOK_TITLE_TABLE_CREATE =
			"create table " + BOOK_TITLE_TABLE + 
			" (" + 
			BOOK_ID_COL_NAME         + " integer primary key autoincrement, " + 
			BOOK_TITLE_COL_NAME      + " text not null, " + 
			BOOK_AUTHOR_COL_NAME     + " text not null, " +
			BOOK_TRANSLATOR_COL_NAME + " text not null, " + 
			BOOK_ISBN_COL_NAME		 + " text not null, " +
			BOOK_PRICE_COL_NAME      + " float not null " + 
			");";
		
		static final String BOOK_AUTHOR_TABLE_CREATE =
			"create table " + BOOK_AUTHOR_TABLE + 
			" (" + 
			AUTHOR_ID_COL_NAME    		+ " integer primary key autoincrement, " + 
			AUTHOR_NAME_COL_NAME  		+ " text not null, " + 
			AUTHOR_BIRTH_YEAR_COL_NAME 	+ " integer not null, " +
			AUTHOR_DEATH_YEAR_COL_NAME 	+ " integer not null, " +
			AUTHOR_COUNTRY_COL_NAME 	+ " text not null " + 
			");";
		
		static final String BOOK_COVER_IMG_TABLE_CREATE = 
			"create table " + BOOK_COVER_IMAGE_TABLE + 
			" (" + 
			//COVER_IMAGE_ID_COL_NAME   + " integer primary key autoincrement, " + 
			COVER_IMAGE_ISBN_COL_NAME + " text primary key not null, " +
			COVER_IMAGE_IMG_COL_NAME  + " blob not null " +
			");";
		
		static final String BOOK_ORDER_TABLE_CREATE =
			"create table " + BOOK_ORDER_TABLE + 
			" (" +
			BOOK_ORDER_ID_COL_NAME    + " integer primary key autoincrement, " +
			BOOK_ORDER_ISBN_COL_NAME  + " text not null, " +
			BOOK_ORDER_CFN_COL_NAME   + " text not null, " + 
			BOOK_ORDER_CLN_COL_NAME   + " text not null, " + 
			BOOK_ORDER_PRICE_COL_NAME + " float not null " + 
			");";
			
		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d(HELPER_LOGTAG, BOOK_TITLE_TABLE_CREATE);
			db.execSQL(BOOK_TITLE_TABLE_CREATE);
			
			Log.d(HELPER_LOGTAG, BOOK_AUTHOR_TABLE_CREATE);
			db.execSQL(BOOK_AUTHOR_TABLE_CREATE);
			
			Log.d(HELPER_LOGTAG, BOOK_COVER_IMG_TABLE_CREATE);
			db.execSQL(BOOK_COVER_IMG_TABLE_CREATE);
			
			Log.d(HELPER_LOGTAG, BOOK_ORDER_TABLE_CREATE);
			db.execSQL(BOOK_ORDER_TABLE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, 
				int newVersion) {
			Log.d(ADPTR_LOGTAG, "Upgrading from version " +
					oldVersion + " to " +
					newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + BOOK_TITLE_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + BOOK_AUTHOR_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + BOOK_COVER_IMAGE_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + BOOK_ORDER_TABLE);
			onCreate(db);
		}
	} // end of BookInfoDBOpenHelper class
	
	// initialize the mContext and the helper objects
	public BookDbAdptr(Context context) {
		mDbHelper = new BookInfoDBOpenHelper(context, DB_NAME, null, DB_VERSION);
	}
	
	// open either writeable or, if that is impossible,
	// readable database
	public void open() throws SQLiteException {
		try {
			mDb = mDbHelper.getWritableDatabase();
			Log.d(ADPTR_LOGTAG, "WRITEABLE DB CREATED");
		}
		catch ( SQLiteException ex ) {
			Log.d(ADPTR_LOGTAG, "READABLE DB CREATED");
			mDb = mDbHelper.getReadableDatabase();
		}
	}
	
	public SQLiteDatabase getReadableDatabase() {
		try {
			return mDbHelper.getReadableDatabase();
		}
		catch ( SQLiteException ex ) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public void close() {
		mDb.close();
	}
	
	// Strongly typed insertion method but inserts multiple books with the same ISBN.
	public long insertBookTitle(BookTitle book) {
		ContentValues newBook = new ContentValues();
		newBook.put(BOOK_TITLE_COL_NAME, book.getTitle());
		newBook.put(BOOK_AUTHOR_COL_NAME, book.getAuthor());
		newBook.put(BOOK_ISBN_COL_NAME, book.getISBN());
		newBook.put(BOOK_TRANSLATOR_COL_NAME, book.getTranslator());
		newBook.put(BOOK_PRICE_COL_NAME, book.getPrice());
		long insertedRowIndex = 
			mDb.insertWithOnConflict(BOOK_TITLE_TABLE, null, newBook, SQLiteDatabase.CONFLICT_REPLACE);
		Log.d(ADPTR_LOGTAG, "Inserted book record " + insertedRowIndex);
		return insertedRowIndex;
	}
	
	// Strongly typed insertion method. Insert the book if and only if it is not already
	// in the database.
	public long insertUniqueBookTitle(BookTitle book) {
		ContentValues newBook = new ContentValues();
		newBook.put(BOOK_TITLE_COL_NAME, book.getTitle());
		newBook.put(BOOK_AUTHOR_COL_NAME, book.getAuthor());
		newBook.put(BOOK_ISBN_COL_NAME, book.getISBN());
		newBook.put(BOOK_TRANSLATOR_COL_NAME, book.getTranslator());
		newBook.put(BOOK_PRICE_COL_NAME, book.getPrice());
		//long insertedRowIndex = mDb.insert(BOOK_TABLE, null, newBook);
		Cursor rslt = mDb.query(BOOK_TITLE_TABLE, 
				new String[] { BOOK_ISBN_COL_NAME }, 
				BOOK_ISBN_COL_NAME + "=" + book.getISBN(), 
				null, null, null, null);
		long insertedRowIndex = -1;
		if ((rslt.getCount() == 0 || !rslt.moveToFirst()) ) {
			insertedRowIndex =  mDb.insertWithOnConflict(BOOK_TITLE_TABLE, 
					null, 
					newBook, 
					SQLiteDatabase.CONFLICT_REPLACE);	
		}		
		
		// close the cursor and make it completely invalid
		rslt.close();
		Log.d(ADPTR_LOGTAG, "Inserted book record " + insertedRowIndex);
		return insertedRowIndex;
	}
	
	public long insertUniqueBookAuthor(BookAuthor author) {
		ContentValues newBook = new ContentValues();
		newBook.put(BookDbAdptr.AUTHOR_NAME_COL_NAME, author.getName());
		newBook.put(BookDbAdptr.AUTHOR_BIRTH_YEAR_COL_NAME, author.getBirthYear());
		newBook.put(BookDbAdptr.AUTHOR_DEATH_YEAR_COL_NAME, author.getDeathYear());
		newBook.put(BookDbAdptr.AUTHOR_COUNTRY_COL_NAME, author.getCountry());
		Cursor rslt = 
			mDb.query(BOOK_AUTHOR_TABLE, 
					new String[] { AUTHOR_NAME_COL_NAME }, 
					AUTHOR_NAME_COL_NAME + "=" + "\"" + author.getName() + "\"", 
					null, null, null, null);
		long insertedRowIndex = -1;
		if ((rslt.getCount() == 0 || !rslt.moveToFirst()) ) {
			insertedRowIndex = 
				mDb.insertWithOnConflict(BOOK_AUTHOR_TABLE, 
						null, 
						newBook, 
						SQLiteDatabase.CONFLICT_REPLACE);	
		}		
		
		// close the cursor and make it completely invalid
		rslt.close();
		Log.d(ADPTR_LOGTAG, "Inserted author record " + insertedRowIndex);
		return insertedRowIndex;
	}
	
	public long insertUniqueBookCoverImage(BookCoverImage bci) {
		long insertedRowIndex = -1;
		byte[] bitmapbytes = bci.getCoverRef();
		if ( bitmapbytes != null ) {
			Log.d(ADPTR_LOGTAG, "bitmapbytes length = " + bitmapbytes.length);
		
			ContentValues newBookCoverRef = new ContentValues();
			newBookCoverRef.put(BookDbAdptr.COVER_IMAGE_ISBN_COL_NAME, bci.getISBN());
			newBookCoverRef.put(BookDbAdptr.COVER_IMAGE_IMG_COL_NAME, bitmapbytes);
		
			Cursor rslt = 
				mDb.query(BOOK_COVER_IMAGE_TABLE, 
					new String[] { COVER_IMAGE_ISBN_COL_NAME }, 
					COVER_IMAGE_ISBN_COL_NAME + "=" + "\"" + bci.getISBN() + "\"", 
					null, null, null, null);

			if ((rslt.getCount() == 0 || !rslt.moveToFirst()) ) {
				insertedRowIndex = 
					mDb.insertWithOnConflict(BOOK_COVER_IMAGE_TABLE, 
						null, 
						newBookCoverRef, 
						SQLiteDatabase.CONFLICT_REPLACE);	
			}
			rslt.close();
		}
		
		// close the cursor and make it completely invalid
		Log.d(ADPTR_LOGTAG, "Inserted book cover image record " + insertedRowIndex);
		return insertedRowIndex;
	}
	
	public long insertBookOrder(BookOrder book) {
		ContentValues bookOrderVals = new ContentValues();
		bookOrderVals.put(BOOK_ORDER_ISBN_COL_NAME, book.getISBN());
		bookOrderVals.put(BOOK_ORDER_CFN_COL_NAME, book.getFirstName());
		bookOrderVals.put(BOOK_ORDER_CLN_COL_NAME, book.getLastName());
		bookOrderVals.put(BOOK_ORDER_PRICE_COL_NAME, book.getPrice());
		
		long row_number = 
			mDb.insertWithOnConflict(BOOK_ORDER_TABLE, 
									null, 
									bookOrderVals, 
									SQLiteDatabase.CONFLICT_REPLACE);		

		Log.d(ADPTR_LOGTAG, "Inserted book order record " + row_number);
		return row_number;
	}
	
	public Bitmap retrieveBookCoverImage(String isbn) {
		
		byte[] image_bytes = null;
		Cursor rslt = 
			mDb.query(BOOK_COVER_IMAGE_TABLE, 
				new String[] { COVER_IMAGE_ISBN_COL_NAME, COVER_IMAGE_IMG_COL_NAME }, 
				COVER_IMAGE_ISBN_COL_NAME + "=" + "\"" + isbn + "\"", 
				null, null, null, null);
		if ( rslt.getCount() != 0 ) {
			rslt.moveToFirst();
			
			image_bytes = rslt.getBlob(rslt.getColumnIndex(COVER_IMAGE_IMG_COL_NAME));
			Bitmap bmp = BitmapFactory.decodeByteArray(image_bytes, 0, image_bytes.length);
			return bmp;
		}
		else {
			return null;
		}
	}
	
	// stored procedure for 'SELECT * FROM BOOK_TITLE'.
	static final String SQL_QUERY_01 = "SELECT * FROM book_title";
	String sample_query_01() {
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Log.d(ADPTR_LOGTAG, "QUERY_01 Readable DB opened...");
			
			Cursor crsr =
				db.query(BOOK_TITLE_TABLE, // table name 
						null, 				// column names 
						null, 			    // where clause
						null, 				// selection args
						null, 			    // group by
						null, 				// having
						null);				// order by

			StringBuffer buffer = new StringBuffer("");
			buffer.append("QUERY 01\n\n");
			buffer.append(SQL_QUERY_01+"\n\n");
			
			if ( crsr.getCount() != 0 ) {
				crsr.moveToFirst();
				while ( crsr.isAfterLast() == false ) {
					int id = crsr.getInt(crsr.getColumnIndex(BOOK_ID_COL_NAME));
					String book_title = crsr.getString(crsr.getColumnIndex(BOOK_TITLE_COL_NAME));
					String book_author = crsr.getString(crsr.getColumnIndex(BOOK_AUTHOR_COL_NAME));
					String book_translator = crsr.getString(crsr.getColumnIndex(BOOK_TRANSLATOR_COL_NAME));
					String book_isbn = crsr.getString(crsr.getColumnIndex(BOOK_ISBN_COL_NAME));
					float book_price = crsr.getFloat(crsr.getColumnIndex(BOOK_PRICE_COL_NAME));
					
					buffer.append("ID = " + id + "\n");
					buffer.append("TITLE = " + book_title + "\n");
					buffer.append("AUTHOR = " + book_author + "\n");
					buffer.append("TRANSLATOR = " + book_translator + "\n");
					buffer.append("ISBN = " + book_isbn + "\n");
					buffer.append("PRICE = " + book_price + "\n");
					buffer.append("=====================\n");
					crsr.moveToNext();
				}
			}
			
			crsr.close();
			db.close();
			Log.d(ADPTR_LOGTAG, "QUERY_01 Readable DB closed...");
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	// stored procedure for 'SELECT Name, BirthYear from book_author'.
	static final String SQL_QUERY_02 = "SELECT Name, BirthYear FROM book_author";
	static final String[] QUERY_02_COLNAMES =
		new String[]{AUTHOR_NAME_COL_NAME, AUTHOR_BIRTH_YEAR_COL_NAME};
	String sample_query_02() {
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Log.d(ADPTR_LOGTAG, "QUERY_02 Readable DB opened...");
			
			Cursor crsr =
				db.query(BOOK_AUTHOR_TABLE, // table name 
						QUERY_02_COLNAMES, 	// column names 
						null, 			    // where clause
						null, 				// selection args
						null, 			    // group by
						null, 				// having
						null);				// order by
			
			StringBuffer buffer = new StringBuffer("");
			buffer.append("QUERY 02\n\n");
			buffer.append(SQL_QUERY_02+"\n\n");
			
			if ( crsr.getCount() != 0 ) {
				crsr.moveToFirst();
				while ( crsr.isAfterLast() == false ) {
					// We are processing a data row
					String author_name = crsr.getString(crsr.getColumnIndex(AUTHOR_NAME_COL_NAME));
					int birth_year = crsr.getInt(crsr.getColumnIndex(AUTHOR_BIRTH_YEAR_COL_NAME));
					buffer.append("BookAuthor = " + author_name + "\n");
					buffer.append("BirthYear = " + birth_year + "\n");
					buffer.append("=====================\n");
					crsr.moveToNext();
				}
			}
			
			// close the cursor and database when you are done.
			crsr.close();
			db.close();
			Log.d(ADPTR_LOGTAG, "QUERY_02 Readable DB closed...");
			return buffer.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	// stored procedure for 'SELECT BirthYear, DeathYear, Name from book_author'.
	static final String SQL_QUERY_03 =
		"SELECT BirthYear, DeathYear, Name FROM book_author";
	static final String[] QUERY_03_COLNAMES =
		new String[]{AUTHOR_BIRTH_YEAR_COL_NAME, AUTHOR_DEATH_YEAR_COL_NAME, AUTHOR_NAME_COL_NAME};
	String sample_query_03() {
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Log.d(ADPTR_LOGTAG, "QUERY_02 Readable DB opened...");
			
			Cursor crsr =
				db.query(BOOK_AUTHOR_TABLE, // table name 
						QUERY_03_COLNAMES,   // column names 
						null, 			     // where clause
						null, 				 // selection args
						null, 			     // group by
						null, 				 // having
						null);				 // order by
			
			StringBuffer buffer = new StringBuffer("");
			buffer.append("QUERY 03\n\n");
			buffer.append(SQL_QUERY_03+"\n\n");
			
			if ( crsr.getCount() != 0 ) {
				crsr.moveToFirst();
				while ( crsr.isAfterLast() == false ) {
					int birth_year = crsr.getInt(crsr.getColumnIndex(AUTHOR_BIRTH_YEAR_COL_NAME));
					int death_year = crsr.getInt(crsr.getColumnIndex(AUTHOR_DEATH_YEAR_COL_NAME));
					String author_name = crsr.getString(crsr.getColumnIndex(AUTHOR_NAME_COL_NAME));
					buffer.append("BirthYear = " + birth_year + "\n");
					buffer.append("DeathYear = " + death_year + "\n");
					buffer.append("BookAuthor = " + author_name + "\n");
					buffer.append("=====================\n");
					crsr.moveToNext();
				}
			}
			
			crsr.close();
			db.close();
			Log.d(ADPTR_LOGTAG, "QUERY_03 Readable DB closed...");
			return buffer.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	// select Title, BookAuthor, ISBN, Price from book_title ORDER BY Price DESC;
	
	static final String SQL_QUERY_04 =
		"SELECT Title, BookAuthor, ISBN, Price FROM book_title ORDER BY Price DESC";
	static final String QUERY_04_ORDER_BY = BOOK_PRICE_COL_NAME + " DESC";
	static final String[] QUERY_04_COLNAMES =
		new String[]{BOOK_TITLE_COL_NAME, BOOK_AUTHOR_COL_NAME, BOOK_PRICE_COL_NAME};
	String sample_query_04() {
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Log.d(ADPTR_LOGTAG, "QUERY_04 Readable DB opened...");
			
			Cursor crsr =
				db.query(BOOK_TITLE_TABLE, // table name 
						 QUERY_04_COLNAMES, // column names 
						 null,     // where clause
						 null, 	  // selection args
						 null, 	  // group by
						 null,     // having
						 QUERY_04_ORDER_BY); // order by
			
			StringBuffer buffer = new StringBuffer("");
			buffer.append("QUERY 04\n\n");
			buffer.append(SQL_QUERY_04+"\n\n");
			
			if ( crsr.getCount() != 0 ) {
				crsr.moveToFirst();
				while ( crsr.isAfterLast() == false ) {
					String book_title = crsr.getString(crsr.getColumnIndex(BOOK_TITLE_COL_NAME));
					String book_author = crsr.getString(crsr.getColumnIndex(BOOK_AUTHOR_COL_NAME));
					float book_price = crsr.getFloat(crsr.getColumnIndex(BOOK_PRICE_COL_NAME));
					buffer.append("Title = " + book_title + "\n");
					buffer.append("BookAuthor = " + book_author + "\n");
					buffer.append("Price = " + book_price + "\n");
					buffer.append("=====================\n");
					crsr.moveToNext();
				}
			}
			
			crsr.close();
			db.close();
			Log.d(ADPTR_LOGTAG, "QUERY_04 Readable DB closed...");
			return buffer.toString();
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
    // SELECT Title, Translator, Price FROM book_title WHERE Price < 15 or Translator='C. Barks' ORDER BY Price DESC;
	static final String SQL_QUERY_05 = 
		"SELECT Title, Translator, Price FROM book_title WHERE Price < 15 or Translator=\'C. Barks\' ORDER BY Price DESC";
	static final String[] QUERY_05_COLNAMES = new String[]{BOOK_TITLE_COL_NAME, 
		  BOOK_TRANSLATOR_COL_NAME, 
		  BOOK_PRICE_COL_NAME};
	static final String QUERY_05_WHERE_CLAUSE = "Price < ? OR Translator = ?";
	static final String[] QUERY_05_SELECTION_ARGS = new String[]{"15", "C. Barks"};
	static final String QUERY_05_ORDER_BY = BOOK_PRICE_COL_NAME + " DESC";
	String sample_query_05() {
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Log.d(ADPTR_LOGTAG, "QUERY_05 Readable DB opened...");
			
			Cursor crsr =
				db.query(BOOK_TITLE_TABLE, // table name 
						QUERY_05_COLNAMES, 		// column names 
						QUERY_05_WHERE_CLAUSE, 		// where clause
						QUERY_05_SELECTION_ARGS, // selection args
						null, 			    // group by
						null, 				// having
						BOOK_PRICE_COL_NAME + " DESC"); // order by
			
			StringBuffer buffer = new StringBuffer("");
			buffer.append("QUERY 07\n\n");
			buffer.append(SQL_QUERY_07+"\n\n");
			
			if ( crsr.getCount() != 0 ) {
				crsr.moveToFirst();
				while ( crsr.isAfterLast() == false ) {
					String book_title = crsr.getString(crsr.getColumnIndex(BOOK_TITLE_COL_NAME));
					String book_translator = crsr.getString(crsr.getColumnIndex(BOOK_TRANSLATOR_COL_NAME));
					float book_price = crsr.getFloat(crsr.getColumnIndex(BOOK_PRICE_COL_NAME));
					buffer.append("Title      = " + book_title + "\n");
					buffer.append("Translator = " + book_translator + "\n");
					buffer.append("Price      = " + book_price + "\n");
					buffer.append("=====================\n");
					crsr.moveToNext();
				}
			}
			
			crsr.close();
			db.close();
			Log.d(ADPTR_LOGTAG, "QUERY_05 Readable DB closed...");
			
			return buffer.toString();
		} catch (SQLiteException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	// SELECT ISBN, SUM(Price) FROM book_order GROUP BY ISBN ORDER BY Sum(Price) DESC;
	static final String SQL_QUERY_06 = 
		"SELECT ISBN, SUM(Price) FROM book_order GROUP BY ISBN ORDER BY Sum(Price) DESC;";
	static final String[] QUERY_06_COLNAMES = new String[]{BOOK_ORDER_ISBN_COL_NAME, 
		  													"SUM(Price)"};
	static final String QUERY_06_GROUP_BY = BOOK_ORDER_ISBN_COL_NAME;
	static final String QUERY_06_ORDER_BY = "SUM(Price) DESC";
	String sample_query_06() {
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Log.d(ADPTR_LOGTAG, "QUERY_08 Readable DB opened...");
			
			Cursor crsr =
				db.query(BOOK_ORDER_TABLE, // table name 
						QUERY_06_COLNAMES, 		// column names 
						null, 		// where clause
						null, // selection args
						QUERY_06_GROUP_BY,  // group by
						null, 				// having
						QUERY_06_ORDER_BY); // order by
			
			StringBuffer buffer = new StringBuffer("");
			buffer.append("QUERY 06\n\n");
			buffer.append(SQL_QUERY_06+"\n\n");
			
			if ( crsr.getCount() != 0 ) {
				crsr.moveToFirst();
				while ( crsr.isAfterLast() == false ) {
					String book_isbn = crsr.getString(crsr.getColumnIndex(BOOK_ORDER_ISBN_COL_NAME));
					String book_total_sales = crsr.getString(crsr.getColumnIndex("SUM(Price)"));
					buffer.append("Title      = " + book_isbn + "\n");
					buffer.append("Total Sales = " + book_total_sales + "\n");
					buffer.append("=====================\n");
					crsr.moveToNext();
				}
			}
			
			crsr.close();
			db.close();
			Log.d(ADPTR_LOGTAG, "QUERY_08 Readable DB closed...");
			
			return buffer.toString();
		} catch (SQLiteException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	// SELECT ISBN, SUM(Price) FROM book_order GROUP BY ISBN ORDER BY Sum(Price) DESC;
	static final String SQL_QUERY_07 = 
		"SELECT LastName, SUM(Price) FROM book_order GROUP BY LastName HAVING SUM(Price) > 20 ORDER BY SUM(Price) DESC;";
	static final String[] QUERY_07_COLNAMES = new String[]{BOOK_ORDER_CLN_COL_NAME, "SUM(Price)"};
	static final String QUERY_07_GROUP_BY   = BOOK_ORDER_CLN_COL_NAME;
	static final String QUERY_07_HAVING     = "SUM(Price) > 20";
	static final String QUERY_07_ORDER_BY   = "SUM(Price) DESC";
	String sample_query_07() {
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Log.d(ADPTR_LOGTAG, "QUERY_07 Readable DB opened...");
			
			Cursor crsr =
				db.query(BOOK_ORDER_TABLE, // table name 
						QUERY_07_COLNAMES, 		// column names 
						null, 		// where clause
						null, // selection args
						QUERY_07_GROUP_BY,  // group by
						QUERY_07_HAVING, 	// having
						QUERY_07_ORDER_BY); // order by
			
			StringBuffer buffer = new StringBuffer("");
			buffer.append("QUERY 07\n\n");
			buffer.append(SQL_QUERY_07+"\n\n");
			
			if ( crsr.getCount() != 0 ) {
				crsr.moveToFirst();
				while ( crsr.isAfterLast() == false ) {
					String last_name = crsr.getString(crsr.getColumnIndex(BOOK_ORDER_CLN_COL_NAME));
					String book_total_sales = crsr.getString(crsr.getColumnIndex("SUM(Price)"));
					buffer.append("Last Name      = " + last_name + "\n");
					buffer.append("Total Sales = " + book_total_sales + "\n");
					buffer.append("=====================\n");
					crsr.moveToNext();
				}
			}
			
			crsr.close();
			db.close();
			Log.d(ADPTR_LOGTAG, "QUERY_07 Readable DB closed...");
			
			return buffer.toString();
		} catch (SQLiteException e) {
			e.printStackTrace();
			return "";
		}
	}
	

}
