package com.miracle.topdf;

import java.io.File;
import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PDFFragment extends Fragment{
	private View parentView;
	private ListView listview;
	
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
	    listview = (ListView) parentView.findViewById(R.id.list);
	    String outputPath =
                PreferenceManager.getDefaultSharedPreferences(MenuActivity.mContext).getString(
                    "outputPath",
                    MenuActivity.mContext.getExternalFilesDir(
                        Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath());
	    ArrayList<String> FilesInFolder = GetFiles(outputPath);
	    if (FilesInFolder==null){
	    	Toast toast = Toast.makeText(MenuActivity.mContext, "No PDFs, start convert PDF", Toast.LENGTH_SHORT); 
    		toast.show(); 
	    }
	    else{
	    listview.setAdapter(new ArrayAdapter<String>(MenuActivity.mContext, android.R.layout.simple_list_item_1, FilesInFolder));
        Log.e("file", FilesInFolder.toString());
        
	    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

	    }
	    return parentView;
	  }
}
