package com.miracle.topdf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class HomeFragment extends Fragment {

  private View parentView;
  private static final int SELECT_VIDEO = 31212;
protected static final int IMAGE_PICKER_SELECT = 0;
 

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    parentView = inflater.inflate(R.layout.home, container, false);
    
    TextView document = (TextView) parentView.findViewById(R.id.Document);
    TextView clipboard = (TextView) parentView.findViewById(R.id.Clipboard);
    TextView web = (TextView) parentView.findViewById(R.id.Web);
    TextView photo = (TextView) parentView.findViewById(R.id.Photo);
    
    document.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			MenuActivity.mContext.changeFragment(new DocumentsFragment(), R.string.Documents);
		}
    });
    
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
		
		//@Override
		//public void onClick(View v) {
			// TODO Auto-generated method stub
			//MenuActivity.mContext.changeFragment(new DocumentsFragment(), R.string.image_details);
		//}
		
		@Override 
		public void onClick(View v) { 
			Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI); 
			startActivityForResult(i, IMAGE_PICKER_SELECT); } /** * Photo Selection result */ 
			public void onActivityResult(int requestCode, int resultCode, Intent data) { 
				/*if (requestCode == IMAGE_PICKER_SELECT && resultCode == Activity.RESULT_OK) { 
					MainActivity activity = (MainActivity)getActivity(); 
					Bitmap bitmap = getBitmapFromCameraData(data, activity); 
					mSelectedImage.setImageBitmap(bitmap); 
				} */
			}
		
    });
    
    return parentView;

  }

}
