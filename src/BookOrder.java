package org.vkedco.mobappdev.sqlite_image_blob_app_00001;

public class BookOrder {
	
	protected String mISBN = null;
	protected String mFirstName = null;
	protected String mLastName = null;
	protected float mPrice = 0.0f;
	
	public BookOrder(String isbn, String fn, String ln, float price) {
		mISBN = isbn;
		mFirstName = fn;
		mLastName = ln;
		mPrice = price;
	}
	
	public String getISBN() {
		return mISBN;
	}
	
	public String getFirstName() {
		return mFirstName;
	}
	
	public String getLastName() {
		return mLastName;
	}
	
	public float getPrice() {
		return mPrice;
	}

}
