package com.miracle.topdf;

import java.io.File;
import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PDFFragment extends Fragment{
	private View parentView;
	private ListView list;
	
	public ArrayList<String> GetFiles(String DirectoryPath) {
	    ArrayList<String> MyFiles = new ArrayList<String>();
	    File f = new File(DirectoryPath);

	    f.mkdirs();
	    File[] files = f.listFiles();
	    if (files.length == 0)
	        return null;
	    else {
	        for (int i=0; i<files.length; i++) 
	            MyFiles.add(files[i].getName());
	    }

	    return MyFiles;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    parentView = inflater.inflate(R.layout.pdflist, container, false);
	    list = (ListView) parentView.findViewById(R.id.list);
	    
	    ArrayList<String> FilesInFolder = GetFiles("/storage/emulated/0/Android/data/com.miracle.topdf/files/data");
	    list.setAdapter(new ArrayAdapter<String>(MenuActivity.mContext, android.R.layout.simple_list_item_1, FilesInFolder));
        Log.e("file", FilesInFolder.toString());
	    
	    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	                // Clicking on items
	        	String selected = parent.getItemAtPosition(position).toString();
	        	Log.e("select", selected);
	        	
	        	Intent intent = new Intent();  
                intent = intent.setClass(MenuActivity.mContext, ViewActivity.class);  
                Bundle bundle = new Bundle();
                bundle.putString("selected", selected);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0); 
	             }
	        });

	    
	    return parentView;
	  }
}
