package com.miracle.topdf;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class WebFragment extends Fragment{
	private View parentView;
	private EditText editText;
    private Button   button;
    private WebView  webView;
    private RequestQueue mQueue;
    private ImageView go;
    private String url;
    private ImageView convert;
    //String myUrl;
    
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    parentView = inflater.inflate(R.layout.webview, container, false);
	    editText = (EditText) parentView.findViewById(R.id.editText);
	    convert   = (ImageView)   parentView.findViewById(R.id.convert);
	    go = (ImageView) parentView.findViewById(R.id.imageView1);
        webView  = (WebView)  parentView.findViewById(R.id.webView);
        webView.setWebViewClient(new MyWebViewClient());

        WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);
        
        mQueue = Volley.newRequestQueue(MenuActivity.mContext);  
        
        go.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    url=editText.getText().toString();
                    if (url.startsWith("http://")||url.startsWith("https://"))
                    	webView.loadUrl(url);
                    else 
                    	webView.loadUrl("http://"+url);
            		InputMethodManager in = (InputMethodManager) MenuActivity.mContext.getSystemService(MenuActivity.INPUT_METHOD_SERVICE);
	                in.hideSoftInputFromWindow(editText.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
        
        
        convert.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String apiurl="";
				editText.setText(webView.getUrl());
				url=webView.getUrl();
                try {
                  apiurl = "http://api.docs88.com/v1/webtopdf?url="+URLEncoder.encode(url, "UTF-8");
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
                            Log.d("status",String.valueOf(response.getInt("status"))+" "+response.getString("url"));
                            String outputPath =
                                PreferenceManager.getDefaultSharedPreferences(MenuActivity.mContext).getString(
                                    "outputPath",
                                    MenuActivity.mContext.getExternalFilesDir(
                                        Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath());

                            String date =
                                new java.text.SimpleDateFormat("yyyy-MM-dd_k-m-s_S").format(new java.util.Date(System
                                    .currentTimeMillis()));

                            String realoutput = outputPath + "/" + date + ".pdf";

                            //创建下载文件的线程  
                              Thread t=new Thread(new Download(response.getString("url"), realoutput));  
                              t.start();  
                              
                          } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                          }
            		    }
            		}, new Response.ErrorListener() {

        		    @Override
        		    public void onErrorResponse(VolleyError error) {
        		    }
        		});

        		mQueue.add(jsonObjRequest); 
				
			}
		});
        
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
            // TODO Auto-generated method stub
	            if(keyCode==KeyEvent.KEYCODE_ENTER) {
	            	url=editText.getText().toString();
	            	if (url.startsWith("http://")||url.startsWith("https://"))
                    	webView.loadUrl(url);
                    else 
                    	webView.loadUrl("http://"+url);
	                     
	            	InputMethodManager in = (InputMethodManager) MenuActivity.mContext.getSystemService(MenuActivity.INPUT_METHOD_SERVICE);
	                in.hideSoftInputFromWindow(editText.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

	            	return true;
	            }
	            return false;
	        }
        });

	    return parentView;
	  }
	
		 private class MyWebViewClient extends WebViewClient {

	         @Override
	         public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        	 //myUrl = url;

	        	view.loadUrl(url);
	             return true;
	         }
	         
	         @Override  
	         public void onPageStarted(WebView view, String url, Bitmap favicon) {  
	             super.onPageStarted(view, url, favicon);  
	         }  
	           
	         @Override  
	         public void onPageFinished(WebView view, String url) {  
	             super.onPageFinished(view, url);  
	         }  
	           
	         @Override  
	         public void onReceivedError(WebView view, int errorCode,  
	                 String description, String failingUrl) {  
	             super.onReceivedError(view, errorCode, description, failingUrl);  
	         } 
	     }
		
		
}
