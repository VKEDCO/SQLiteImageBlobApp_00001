package org.vkedco.mobappdev.sqlite_image_blob_app_00001;

/*
 * ***************************************************
 * BookCoverImage.java
 * OO Model of the Book Cover Image table in book_info.db
 * Bugs to vladimir dot kulyukin at gmail dot com
 * ***************************************************
 */

public class BookCoverImage {
	
	protected String mISBN;
	protected byte[] mCoverRef;
	
	public BookCoverImage(String isbn, byte[] coverRef) {
		mISBN = isbn;
		mCoverRef = coverRef;
	}
	
	public String getISBN() {
		return mISBN;
	}
	
	public byte[] getCoverRef() {
		return mCoverRef;
	}
}
