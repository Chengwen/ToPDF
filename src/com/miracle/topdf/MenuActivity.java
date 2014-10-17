package com.miracle.topdf;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;

public class MenuActivity extends FragmentActivity implements View.OnClickListener{


	  public static MenuActivity mContext;
	  public static String videoURL = null;
	  public static String imageURL = null;
	  public static String lastPage = null;
	  public static int title = 0;
	
	
	  /**
	   * Called when the activity is first created.
	   */
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    mContext = this;
	   
	   
	    changeFragment(new HomeFragment());
	    
	  }
	
	  @Override
	  public void onStart() {
	    super.onStart();
	    // The rest of your onStart() code.
	    EasyTracker.getInstance(this).activityStart(this); // Add this method.
	  }
	
	  @Override
	  public void onStop() {
	    super.onStop();
	    // The rest of your onStop() code.
	    EasyTracker.getInstance(this).activityStop(this); // Add this method.
	  }
	
	  @Override
	  public void onClick(View view) {
	    //if (view == itemHome) {
	      //changeFragment(new HomeFragment(), R.string.home);
	    //}

	  }
	
	  @Override
	  public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	if (MenuActivity.mContext.title == R.string.home)
	    		finish();
	    	else
	    		changeFragment(new HomeFragment(), R.string.home);
	     
	      return true;
	    } else {
	      return super.onKeyDown(keyCode, event);
	    }
	  }
	
	  public void changeFragment(Fragment targetFragment) {
	    getSupportFragmentManager().beginTransaction()
	        .replace(R.id.main_fragment, targetFragment, "fragment")
	        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
	  }
	
	  public void changeFragment(Fragment targetFragment, int title) {
	    try {
	      TextView t = (TextView) MenuActivity.mContext.findViewById(R.id.title_bar);
	      MenuActivity.mContext.title = title;
	
	      t.getHandler().post(new Runnable() {
	        public void run() {
	          TextView t = (TextView) MenuActivity.mContext.findViewById(R.id.title_bar);
	          t.setText(MenuActivity.mContext.title);
	        }
	      });
	    } catch (Exception Ex) {
	    }
	
	    changeFragment(targetFragment);
	  }

}
