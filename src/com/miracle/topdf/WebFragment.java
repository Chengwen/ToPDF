package com.miracle.topdf;

import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.os.Bundle;
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
        
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            		webView.loadUrl("http://"+editText.getText().toString());
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
