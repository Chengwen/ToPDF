package com.miracle.topdf;

import com.luminous.pick.Action;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class HomeFragment extends Fragment {

  private View parentView;
protected static final int IMAGE_PICKER_SELECT = 0;
 

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    parentView = inflater.inflate(R.layout.home, container, false);
    
    //TextView document = (TextView) parentView.findViewById(R.id.Document);
    TextView clipboard = (TextView) parentView.findViewById(R.id.Clipboard);
    TextView web = (TextView) parentView.findViewById(R.id.Web);
    TextView photo = (TextView) parentView.findViewById(R.id.Photo);
    TextView pdf = (TextView) parentView.findViewById(R.id.PDFs);
    
    clipboard.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			MenuActivity.mContext.changeFragment(new ClipboardFragment(), R.string.Clipboard);
		}
    });
    
    web.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			MenuActivity.mContext.changeFragment(new WebFragment(), R.string.Web);
		}
    });
    
    photo.setOnClickListener(new View.OnClickListener() {
		
		@Override 
		public void onClick(View v) { 
    		//Intent intent = new Intent();  
            //intent.setClass(MenuActivity.mContext, com.luminous.pick.MainActivity.class);  
            //startActivity(intent); 
            
			Intent i = new Intent(Action.ACTION_MULTIPLE_PICK);
			startActivityForResult(i, 200);
			}
		
    });
    
    pdf.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			MenuActivity.mContext.changeFragment(new PDFFragment(), R.string.PDFs);
		}
    });
    return parentView;

  }

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
			String[] all_path = data.getStringArrayExtra("all_path");
			
		}
	}
}
