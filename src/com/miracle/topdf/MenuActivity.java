package com.miracle.topdf;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.miracle.topdf.ResideMenu.ResideMenu;
import com.miracle.topdf.ResideMenu.ResideMenuItem;



public class MenuActivity extends FragmentActivity implements View.OnClickListener{

	  private ResideMenu resideMenu;
	  private ResideMenuItem itemHome;
	  private ResideMenuItem itemVideo;
	  private ResideMenuItem itemImages;
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
	    setUpMenu();
	    // changeFragment(new VideoFragment());
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
	
	  private void setUpMenu() {
	
	    // attach to current activity;
	    resideMenu = new ResideMenu(this);
	    resideMenu.setBackground(R.drawable.menu_background);
	    // resideMenu.setBackground(R.drawable.gradient_dark_teal);
	    resideMenu.attachToActivity(this);
	    // resideMenu.setMenuListener(menuListener);
	    // valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
	    resideMenu.setScaleValue(0.6f);
	
	    // create menu items;
	    itemHome = new ResideMenuItem(this, R.drawable.icon_home, R.string.home);
	    itemVideo = new ResideMenuItem(this, R.drawable.icon_video, R.string.video);
	    itemImages = new ResideMenuItem(this, R.drawable.icon_image, R.string.image);
	    // itemCalendar = new ResideMenuItem(this, R.drawable.icon_calendar, "Progress");
	    // itemSettings = new ResideMenuItem(this, R.drawable.icon_settings, "Settings");
	
	    itemHome.setOnClickListener(this);
	    itemVideo.setOnClickListener(this);
	    itemImages.setOnClickListener(this);
	    // itemCalendar.setOnClickListener(this);
	    // itemSettings.setOnClickListener(this);
	
	    resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
	    resideMenu.addMenuItem(itemVideo, ResideMenu.DIRECTION_LEFT);
	    resideMenu.addMenuItem(itemImages, ResideMenu.DIRECTION_LEFT);
	    // resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_LEFT);
	    // resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_LEFT);
	
	    // You can disable a direction by setting ->
	    resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
	    resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);
	
	    /*findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
	      @Override
	      public void onClick(View view) {
	        resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
	      }
	    });*/
	
	
	    // Create an ad.
	    AdView adView = new AdView(this);
	    adView.setAdSize(AdSize.SMART_BANNER);
	    adView.setAdUnitId("ca-app-pub-3568858304593155/8968970224");
	
	    // Add the AdView to the view hierarchy. The view will have no size
	    // until the ad is loaded.
	    LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
	    layout.addView(adView);
	
	    // Create an ad request. Check logcat output for the hashed device ID to
	    // get test ads on a physical device.
	    AdRequest adRequest =
	        new AdRequest.Builder().addTestDevice("FC7D96AEFE29BFC22052BE7C45B50486").build();
	
	    // Start loading the ad in the background.
	    adView.loadAd(adRequest);
	
	  }
	
	  /*
	   * //disable the menu move when touch
	   * 
	   * @Override public boolean dispatchTouchEvent(MotionEvent ev) { return
	   * resideMenu.dispatchTouchEvent(ev); }
	   */
	
	  @Override
	  public void onClick(View view) {
	    if (view == itemHome) {
	      changeFragment(new HomeFragment(), R.string.home);
	    }
	
	
	    resideMenu.closeMenu();
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
	    resideMenu.clearIgnoredViewList();
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
	
	  // What good method is to access resideMen
	  public ResideMenu getResideMenu() {
	    return resideMenu;
	  }

}
