package org.vkedco.mobappdev.sqlite_image_blob_app_00001;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * ***************************************************
 * BookCoverImageViewer.java
 * This activity is launched from CoverImageISBNSelection activity.
 * It takes the isbn selected in CoverImageISBNSelection,
 * retrieves the book cover image from book_info.db and displays it
 * along with the book's isbn code.
 * 
 * All book cover images are taken from www.amazon.com
 * 
 * Bugs to vladimir dot kulyukin at gmail dot com
 * ***************************************************
 */

public class BookCoverImageViewer extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_cover_image_viewer_layout);
		
		ImageView imgView = (ImageView) this.findViewById(R.id.img_view);
		imgView.setImageDrawable(null);
		TextView tvISBN   = (TextView) this.findViewById(R.id.tv_isbn);
		
		Bundle extras = getIntent().getExtras();
		String isbn = extras.getString(getResources().getString(R.string.isbn_key));
		if ( isbn != null ) {
			BookDbAdptr dbAdptr = new BookDbAdptr(getApplicationContext());
			dbAdptr.open();
			Bitmap bmp = dbAdptr.retrieveBookCoverImage(isbn);
			Log.d(getResources().getString(R.string.isbn_key), isbn);
			Log.d(getResources().getString(R.string.bmp_size_key), bmp.toString());
			imgView.setImageBitmap(bmp);
			tvISBN.setText(isbn);
			dbAdptr.close();
		}
	}
}
