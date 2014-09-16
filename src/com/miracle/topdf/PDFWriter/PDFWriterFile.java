//
// Android PDF Writer
// http://coderesearchlabs.com/androidpdfwriter
//
// by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com)
//

package com.miracle.topdf.PDFWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;

public class PDFWriterFile {

  private String generateHelloWorldPDF(String[] Files) {
    int pageWidth = PaperSize.FOLIO_WIDTH;
    int pageHeight = PaperSize.FOLIO_HEIGHT;
    PDFWriter mPDFWriter = new PDFWriter(pageWidth, pageHeight);

    // note that to make this images snippet work
    // you have to uncompress the assets.zip file
    // included into your project assets folder
    try {
      for (int i = 0; i < Files.length; i++) {
        if (i > 0)
          mPDFWriter.newPage();

        Log.d("image", Files[i]);
        InputStream in = new FileInputStream(Files[i]);
        Bitmap xoiJPG = ImageCrop(BitmapFactory.decodeStream(in));
        mPDFWriter.addImageKeepRatio((pageWidth - xoiJPG.getWidth()) / 2,
            (pageHeight - xoiJPG.getHeight()) / 2, xoiJPG.getWidth(), xoiJPG.getHeight(), xoiJPG);
        in.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    int pageCount = mPDFWriter.getPageCount();
    for (int i = 0; i < pageCount; i++) {
      mPDFWriter.setCurrentPage(i);
      mPDFWriter.addText(10, 10, 8, Integer.toString(i + 1) + " / " + Integer.toString(pageCount));
    }

    String s = mPDFWriter.asString();
    return s;
  }

  /**
   * Image Crop
   */
  public static Bitmap ImageCrop(Bitmap bitmap) {

    if (bitmap.getWidth() < 500 && bitmap.getHeight() < 800) {
      return bitmap;
    }
    float ratiowidth = 500.0f / (float) bitmap.getWidth();
    float ratioheight = 800.0f / (float) bitmap.getHeight();
    float ratio = ratiowidth;
    if (ratiowidth > ratioheight) {
      ratio = ratioheight;
    }

    
    Matrix matrix = new Matrix();
    matrix.postScale(ratio, ratio);
    Bitmap resizeBmp =
        Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    return resizeBmp;
  }

  private void outputToFile(String fileName, String pdfContent, String encoding) {
    File newFile = new File( fileName);
    Log.e("output"," PDF path:"+  fileName);
    try {
      newFile.createNewFile();
      try {
        FileOutputStream pdfFile = new FileOutputStream(newFile);
        pdfFile.write(pdfContent.getBytes(encoding));
        pdfFile.close();
      } catch (FileNotFoundException e) {
        //
      }
    } catch (IOException e) {
      //
    }
  }

  /**
   * Images to PDF
   * @param Files ,input file array
   * @param outname ,PDF output name
   */
  public void Create(String[] Files, String outname) {
    Log.d("path", Environment.getExternalStorageDirectory().toString());
    String pdfcontent = generateHelloWorldPDF(Files);
    outputToFile(outname, pdfcontent, "ISO-8859-1");
  }
}
