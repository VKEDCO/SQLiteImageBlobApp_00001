package org.vkedco.mobappdev.sqlite_image_blob_app_00001;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/*
 * ***************************************************
 * CoverImageISBNSelectionAct.java
 * MainActivity of SimpleDbApp02 application.
 * Displays ISBNs of five books available on amazon.com:
 * 1. "The Essential Rumi" by Jalal adDin Rumi, Translated by C. Barks & J. Moyne, ISBN: 9780062509581
 * 2. "The Illuminated Rumi" by Jalal adDin Rumi, Translated by C. Barks, ISBN: 9780767900027
 * 3. "A Year with Rumi: Daily Readings" by Jalal adDin Rumi, Translated by C. Barks, ISBN: 9780060845971
 * 4. "A Year with Hafiz: Daily Contemplations" by Hafiz, Translated by D. Ladinsky, ISBN: 9780143117544
 * 5. "The Gift" by Hafiz, Translated by D. Ladinsky, ISBN: 9780140195811
 * The activity populates book_info.db with three tables. 
 * The db population is done on the main UI thread.
 * 
 * Bugs to vladimir dot kulyukin at gmail dot com
 * ***************************************************
 */

public class CoverImageISBNSelectionAct extends ListActivity implements
		OnItemClickListener {
	String[] mBookISBNs = null;
	BookDbAdptr mDbAdptr = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		populateListView();
	}

	private void populateListView() {
		String[] xml_cover_image_table = BookDbPopulator.getXMLTableSpecs(this,
				R.array.book_cover_image_table);
		if (mBookISBNs == null) {
			mBookISBNs = new String[5];
			int index = 0;
			for (String cover_image_entry : xml_cover_image_table) {
				mBookISBNs[index++] = cover_image_entry.trim().split(
						BookDbPopulator.XML_ENTRY_SEPARATOR)[0];
			}
		}

		// 1. Create an adapter
		ArrayAdapter<String> adptr = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mBookISBNs);
		// 2. Bind the adapter to the list view
		getListView().setAdapter(adptr);
		// 3. set this class as the implementation of
		// the OnItemClickListener interface
		getListView().setOnItemClickListener(this);

		if (mDbAdptr == null) {
			mDbAdptr = new BookDbAdptr(this);
			BookDbPopulator.populateBookInfoDb(this, mDbAdptr);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent i = new Intent(getApplicationContext(),
				BookCoverImageViewer.class);
		i.putExtra(getResources().getString(R.string.isbn_key),
				((TextView) view).getText().toString());
		startActivity(i);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mDbAdptr != null)
			mDbAdptr.close();
	}

	@Override
	public void onStart() {
		super.onStart();
		this.populateListView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.query_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		Intent i = null;
		switch ( item.getItemId() ) {
		case R.id.query_01:
			i = new Intent(this, QueryExamples.class);
			i.putExtra(getResources().getString(R.string.query_number_key), 1);
			startActivity(i);
			break;
		case R.id.query_02:
			i = new Intent(this, QueryExamples.class);
			i.putExtra(getResources().getString(R.string.query_number_key), 2);
			startActivity(i);
			break;
		case R.id.query_03:
			i = new Intent(this, QueryExamples.class);
			i.putExtra(getResources().getString(R.string.query_number_key), 3);
			startActivity(i);
			break;
		case R.id.query_04:
			i = new Intent(this, QueryExamples.class);
			i.putExtra(getResources().getString(R.string.query_number_key), 4);
			startActivity(i);
			break;
		case R.id.query_05:
			i = new Intent(this, QueryExamples.class);
			i.putExtra(this.getResources().getString(R.string.query_number_key), 5);
			startActivity(i);
			break;
		case R.id.query_06:
			i = new Intent(this, QueryExamples.class);
			i.putExtra(this.getResources().getString(R.string.query_number_key), 6);
			startActivity(i);
			break;
		case R.id.query_07:
			i = new Intent(this, QueryExamples.class);
			i.putExtra(this.getResources().getString(R.string.query_number_key), 7);
			startActivity(i);
			break;
		
		}
		
		return true;
	}
}
