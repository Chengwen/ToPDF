package com.miracle.topdf;

import java.io.File;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import static java.lang.String.format;

public class ViewActivity extends Activity implements OnPageChangeListener {
	private PDFView pdfView;
	private ImageView share;
	private String filename;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdfpreview); 
        
        pdfView = (PDFView) findViewById(R.id.pdfview);
        share = (ImageView) findViewById(R.id.share);
        
        String outputPath =
                PreferenceManager.getDefaultSharedPreferences(MenuActivity.mContext).getString(
                    "outputPath",
                    MenuActivity.mContext.getExternalFilesDir(
                        Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath());

        
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        String selected = bundle.getString("selected");
        
        filename = outputPath + "/" + selected;
        Log.e("filename", filename);
        display(filename, false);
        
        share.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent shareIntent = new Intent(Intent.ACTION_SEND);
		        shareIntent.setType("application/pdf");
		        Uri uri = Uri.fromFile(new File(filename));
		        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
		        startActivity(shareIntent);
				
			}
		});

    }

    public static final String SAMPLE_FILE = "sample.pdf";

    public static final String ABOUT_FILE = "about.pdf";

    String pdfName = SAMPLE_FILE;

    Integer pageNumber = 1;

    void afterViews() {
    	 Log.e("afterViews","start");
    }

    public void about() {
        if (!displaying(ABOUT_FILE))
            display(ABOUT_FILE, true);
    }

    private void display(String assetFileName, boolean jumpToFirstPage) {
        if (jumpToFirstPage) pageNumber = 1;
        setTitle(pdfName = assetFileName);
        Log.e("read PDF",assetFileName);
        pdfView.fromFile(new File(assetFileName))
                .defaultPage(pageNumber)
                .onPageChange(this)
                .load();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(format("%s %s / %s", pdfName, page, pageCount));
    }

    @Override
    public void onBackPressed() {
        if (ABOUT_FILE.equals(pdfName)) {
            display(SAMPLE_FILE, true);
        } else {
            super.onBackPressed();
        }
    }

    private boolean displaying(String fileName) {
        return fileName.equals(pdfName);
    }
}