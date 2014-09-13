package com.miracle.topdf;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ClipboardFragment extends Fragment{
	private View parentView;
	
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    parentView = inflater.inflate(R.layout.clipboard, container, false);
	    

	    
	    return parentView;
	  }
}
