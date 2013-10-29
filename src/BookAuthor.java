package org.vkedco.mobappdev.sqlite_image_blob_app_00001;

/*
 * ***************************************************
 * BookAuthor.java
 * OO Model of the Book Author table in book_info.db
 * bugs to vladimir dot kulyukin @ gmail dot com
 * ***************************************************
 */

public class BookAuthor {
	
	protected String mName;
	protected int mBirthYear;
	protected int mDeathYear;
	protected String mCountry;
	
	public BookAuthor(String name, int by, int dy, String country) {
		mName = name;
		mBirthYear = by;
		mDeathYear = dy;
		mCountry = country;
	}
	
	public String getName() {
		return mName;
	}
	
	public int getBirthYear() {
		return mBirthYear;
	}
	
	public int getDeathYear() {
		return mDeathYear;
	}
	
	public String getCountry() {
		return mCountry;
	}

}
