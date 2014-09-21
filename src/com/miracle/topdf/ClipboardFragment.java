package com.miracle.topdf;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.support.v4.app.Fragment;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ClipboardFragment extends Fragment{
	private View parentView;
	private EditText editText;
	private ImageView convert;
	private RequestQueue mQueue;
	public static Boolean downloaded=false;
	public static String filename="";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    parentView = inflater.inflate(R.layout.clipboard, container, false);
	    editText = (EditText) parentView.findViewById(R.id.editText1);
	    convert = (ImageView) parentView.findViewById(R.id.convert_text);
	    mQueue = Volley.newRequestQueue(MenuActivity.mContext); 
	    
		ClipboardManager clipboard = (ClipboardManager) MenuActivity.mContext.getSystemService(Context.CLIPBOARD_SERVICE); 
		//clipboard.setText("Text to copy");
		String copytext = clipboard.getText().toString();
		editText.setText(copytext);
	    
	    convert.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				// custom dialog
				final Dialog dialog = new Dialog(MenuActivity.mContext);
				dialog.setContentView(R.layout.convert_dialog);
				dialog.setTitle("Convert to PDF?");
				
				Button yes = (Button) dialog.findViewById(R.id.yes);
				yes.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String apiurl="";
				    	String text = editText.getText().toString();
				    	//if text is empty
				    	if("".equals(editText.getText().toString().trim())){
				    		Toast toast = Toast.makeText(MenuActivity.mContext, "Text is Empty", Toast.LENGTH_SHORT); 
				    		toast.show(); 
				    	}
				    	
				    	else{
				    		
							Log.e("test", text);
							try {
				                  apiurl = "http://api.docs88.com/v1/texttopdf?text=" + URLEncoder.encode(text, "UTF-8");
				      	    		
				                } catch (UnsupportedEncodingException e1) {
				                  // TODO Auto-generated catch block
				                  e1.printStackTrace();
				                }
			
			            		JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET,
			            		    apiurl, null, 
			            		                            new Response.Listener<JSONObject>() {
			            		    @Override
			            		    public void onResponse(JSONObject response) {
			            		      try {
			                            Log.e("status",String.valueOf(response.getInt("status"))+" "+response.getString("url"));
			                            String outputPath =
			                                PreferenceManager.getDefaultSharedPreferences(MenuActivity.mContext).getString(
			                                    "outputPath",
			                                    MenuActivity.mContext.getExternalFilesDir(
			                                        Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath());
			
			                            String date =
			                                new java.text.SimpleDateFormat("yyyy-MM-dd_k-m-s_S").format(new java.util.Date(System
			                                    .currentTimeMillis()));
			
			                            downloaded=false;
			                            String realoutput =filename= outputPath + "/" + date + ".pdf";
			
			                            //Download thread
			                              Thread t=new Thread(new Download(response.getString("url"), realoutput));  
			                              t.start();  
			                              
			                          } catch (JSONException e) {
			                            // TODO Auto-generated catch block
			                            Log.e("error",e.toString());
			                          }
			            		    }
			            		}, new Response.ErrorListener() {
			
			        		    @Override
			        		    public void onErrorResponse(VolleyError error) {
			        		    }
			        		});
			
			        		mQueue.add(jsonObjRequest); 
				    	}
					}
				});
	 
				Button no = (Button) dialog.findViewById(R.id.no);
				// if button is clicked, close the custom dialog
				no.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
	 
				dialog.show();
			}
			
		});
	    
	    return parentView;
	  }
}
