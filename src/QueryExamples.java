package org.vkedco.mobappdev.sqlite_image_blob_app_00001;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class QueryExamples extends Activity {
	
	EditText mEdTxtQueryOutput = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_examples_layout);
	
		Intent i = getIntent();
		int qn = i.getIntExtra(getResources().getString(R.string.query_number_key), 0);
		
		if ( qn > 0 ) {
			BookDbAdptr dbAdptr = new BookDbAdptr(getApplicationContext());
			//dbAdptr.open();
			String query_output = null;
			switch ( qn ) {
			case 1: query_output = dbAdptr.sample_query_01(); break;
			case 2: query_output = dbAdptr.sample_query_02(); break;
			case 3: query_output = dbAdptr.sample_query_03(); break;
			case 4: query_output = dbAdptr.sample_query_04(); break;
			case 5: query_output = dbAdptr.sample_query_05(); break;
			case 6: query_output = dbAdptr.sample_query_06(); break;
			case 7: query_output = dbAdptr.sample_query_07(); break;
			}
			EditText mEdTxtQueryOutput = (EditText) this.findViewById(R.id.ed_txt_query_output);
			mEdTxtQueryOutput.setText(query_output);
			//dbAdptr.close();
		}
	}
}
