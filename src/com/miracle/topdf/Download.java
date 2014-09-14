package com.miracle.topdf;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.util.Log;


public class Download implements Runnable{  
    String URL=null;
    String fileName=null;
    
    public Download(String URL,String fileName){  
        this.URL=URL;
        this.fileName=fileName;  
    }  
    public void run() {  
      try {
        URL url = new URL(URL); //you can write here any link
        File file = new File(fileName);

        long startTime = System.currentTimeMillis();
        Log.e("ImageManager", "download url:" + url +" save as "+fileName);
        /* Open a connection to that URL. */
        URLConnection ucon = url.openConnection();

        /*
         * Define InputStreams to read from the URLConnection.
         */
        InputStream is = ucon.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);

        /*
         * Read bytes to the Buffer until there is nothing more to read(-1).
         */
        ByteArrayBuffer baf = new ByteArrayBuffer(50);
        int current = 0;
        while ((current = bis.read()) != -1) {
                baf.append((byte) current);
        }

        /* Convert the Bytes read to a String. */
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(baf.toByteArray());
        fos.close();
        Log.e("ImageManager", "download ready in"
                        + ((System.currentTimeMillis() - startTime) / 1000)
                        + " sec");

} catch (IOException e) {
        Log.e("ImageManager", "Error: " + e);
}
    }  
}
