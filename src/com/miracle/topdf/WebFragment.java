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
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class WebFragment extends Fragment{
	private View parentView;
	private EditText editText;
    private Button   button;
    private WebView  webView;
    private RequestQueue mQueue;
    //String myUrl;
    
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    parentView = inflater.inflate(R.layout.webview, container, false);
	    editText = (EditText) parentView.findViewById(R.id.editText);
        button   = (Button)   parentView.findViewById(R.id.buttonGo);
        webView  = (WebView)  parentView.findViewById(R.id.webView);
        webView.setWebViewClient(new MyWebViewClient());

        WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);
        
        mQueue = Volley.newRequestQueue(MenuActivity.mContext);  
        
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    String url="http://"+editText.getText().toString();
            		webView.loadUrl(url);
            		
            		String apiurl="";
            		
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
	            	webView.loadUrl("http://"+editText.getText().toString());
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
	     }
		
		
}
