package com.miracle.topdf;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter{
    private Context mContext;
    Cursor cursor;
    public ListAdapter(Context context,Cursor cur) 
    {
            super();
            mContext=context;
            cursor=cur;
           
    }
       
    public int getCount() 
    {
        // return the number of records in cursor
        return cursor.getCount();
    }
    
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

    // getView method is called for each item of ListView
    public View getView(int position,  View view, ViewGroup parent) 
    {
                    // inflate the layout for each item of listView
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflater.inflate(R.layout.list, null);
            
                    // move the cursor to required position 
                    cursor.moveToPosition(position);
                    
                    // fetch the sender number and sms body from cursor
                    
                    ArrayList<String> FilesInFolder = GetFiles("/storage/emulated/0/Android/data/com.miracle.topdf/files/data");
                    String title = FilesInFolder.toString();
                    //String date = new java.text.SimpleDateFormat("dd/LLL").format(new java.util.Date(cursor.getInt(1) * 1000L));
    		 		//String time = new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date(cursor.getInt(1) * 1000L));
                   
                    // get the reference of textViews
                    TextView textViewTitle = (TextView)view.findViewById(R.id.title);
                    //TextView textViewDate = (TextView)view.findViewById(R.id.getDate);
                    //TextView textViewTime = (TextView)view.findViewById(R.id.getTime);
                    
                    // Set the Sender number and smsBody to respective TextViews 
                    textViewTitle.setText(title);
                    //textViewDate.setText(date);
                    //textViewTime.setText(time);

                    return view;
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
}
