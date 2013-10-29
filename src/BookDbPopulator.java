package org.vkedco.mobappdev.sqlite_image_blob_app_00001;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;

public class BookDbPopulator {
	
	static final String LOGTAG = BookDbPopulator.class.getSimpleName() + "_LOG";
	public static final String XML_ENTRY_SEPARATOR = ";";
	
	public static void populateBookInfoDb(Context cntxt, BookDbAdptr dbAdptr) {
    	dbAdptr.open();
    	
    	// Populate book titles
    	String[] book_title_table  = getXMLTableSpecs(cntxt, R.array.book_title_table);
    	String[] book_entry_parts;
    	for(String book_entry: book_title_table) {
    		book_entry_parts = book_entry.trim().split(XML_ENTRY_SEPARATOR);
    		Log.d(LOGTAG, book_entry);
    		
    		dbAdptr.insertUniqueBookTitle(
    				createBookTitleObject(book_entry_parts[0], // title
    								  	  book_entry_parts[1], // author
    								      book_entry_parts[2], // translator
    								      book_entry_parts[3], // isbn
    								      book_entry_parts[4]) // price
    				);
    	}
    	
    	// Populate book authors
    	String[] book_author_table = getXMLTableSpecs(cntxt, R.array.book_author_table);
    	String[] author_entry_parts;
    	for(String author_entry: book_author_table) {
    		author_entry_parts = author_entry.trim().split(XML_ENTRY_SEPARATOR);
    		Log.d(LOGTAG, author_entry);
    		dbAdptr.insertUniqueBookAuthor(
    				createBookAuthorObject(author_entry_parts[0], // author's name
    						   author_entry_parts[1], // author's birth year
    						   author_entry_parts[2], // author's death year
    						   author_entry_parts[3]) // author's country
    		); 									  
    	}
    	
    	// Populate book cover images
    	String[] book_cover_image_table = getXMLTableSpecs(cntxt, R.array.book_cover_image_table);
    	String[] book_cover_entry_parts;
    	for(String book_cover_entry: book_cover_image_table) {
    		book_cover_entry_parts = book_cover_entry.trim().split(XML_ENTRY_SEPARATOR);
    		Log.d(LOGTAG, book_cover_entry);
    		
    		dbAdptr.insertUniqueBookCoverImage(createBookCoverImageObject(
    				cntxt,
    				book_cover_entry_parts[0], // book's isbn
    				book_cover_entry_parts[1]  // book's cover image reference
    			)
    		); 									  
    	}
    	
    	// Populate book orders
    	String[] book_order_table  = getXMLTableSpecs(cntxt, R.array.book_order_table);
    	String[] book_order_parts;
    	for(String book_order: book_order_table) {
    		book_order_parts = book_order.trim().split(XML_ENTRY_SEPARATOR);
    		Log.d(LOGTAG, book_order);
    		
    		dbAdptr.insertBookOrder(
    			createBookOrderObject(
    				book_order_parts[0], // order's isbn
    				book_order_parts[1], // order's buyer's first name initial
    				book_order_parts[2], // order's buyer's last name initial
    				book_order_parts[3]  // order's price
    			)		
    		);
    	}

    	dbAdptr.close();
    }
    
    public static String[] getXMLTableSpecs(Context cntxt, int table_name) {
    	Resources mRes = cntxt.getResources();
    	switch ( table_name ) {
    	case R.array.book_title_table:
    		return mRes.getStringArray(R.array.book_title_table);
    	case R.array.book_author_table:
    		return mRes.getStringArray(R.array.book_author_table);
    	case R.array.book_cover_image_table:
    		return mRes.getStringArray(R.array.book_cover_image_table);
    	case R.array.book_order_table:
    		return mRes.getStringArray(R.array.book_order_table);
    	default:
    			return null;
    	}
    }
    
    private static BookTitle createBookTitleObject(String title, String author, String translator,
    		String isbn, String price) {
    	
    	return new BookTitle(title, 
    	  					 author, 
    	  					 translator, 
    	  					 isbn, 
    	  					 Float.parseFloat(price)); 
    	
    }
    
    private static BookAuthor createBookAuthorObject(String name, String birth_year, 
    		String death_year, String country) {
    	
    	return new BookAuthor(name, 
    	  					  Integer.parseInt(birth_year), 
    	  					  Integer.parseInt(death_year), 
    	  					  country); 
    }
    
    private static BookCoverImage createBookCoverImageObject(Context cntxt, 
    		String isbn, String cover_img_reference) {
		Bitmap bmp = null;
		Resources mRes = cntxt.getResources();
		
    	if ( cover_img_reference.equals(mRes.getString(R.string.essential_rumi_cover)) ) {
			bmp = BitmapFactory.decodeResource(mRes, R.drawable.essential_rumi_cover);
		}
		else if ( cover_img_reference.equals(mRes.getString(R.string.illuminated_rumi_cover)) ) {
			bmp = BitmapFactory.decodeResource(mRes, R.drawable.illuminated_rumi_cover);
		}
		else if ( cover_img_reference.equals(mRes.getString(R.string.year_with_rumi_cover)) ) {
			bmp = BitmapFactory.decodeResource(mRes, R.drawable.year_with_rumi_cover);
		}
		else if ( cover_img_reference.equals(mRes.getString(R.string.year_with_hafiz_cover)) ) {
			bmp = BitmapFactory.decodeResource(mRes, R.drawable.year_with_hafiz_cover);
		}
		else if ( cover_img_reference.equals(mRes.getString(R.string.the_gift_cover)) ) {
			bmp = BitmapFactory.decodeResource(mRes, R.drawable.the_gift_cover);
		}
    	
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] bitmapbytes = null;
    	bmp.compress(CompressFormat.PNG, 0, bos);
		bitmapbytes = bos.toByteArray();
    	bmp.recycle();
    	try {
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return new BookCoverImage(isbn, bitmapbytes);
    }
    
    private static BookOrder createBookOrderObject(String isbn, String cfn, String cln, String price) {
    	return new BookOrder(isbn, cfn, cln, Float.parseFloat(price));
    }

}
