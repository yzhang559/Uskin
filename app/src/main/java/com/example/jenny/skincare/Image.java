package com.example.jenny.skincare;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jenny on 7/6/16.
 */
public class Image extends AppCompatActivity {
    public static int use = 0;
    public final static String SHARED_PREFS_FILE = "Records";
    public final static String APP_PATH_SD_CARD = "/imageDir/";
    public final static String APP_THUMBNAIL_PATH_SD_CARD = "thumbnails";
    ImageView standard, toneColor, pic;
    TextView colorcard;
    int averageColor;
    int[] standardSkin;
    String[] standardName;
    double[] div = new double[76];
    ArrayList<Record> recordArrayList;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    SimpleDateFormat sdf;
    Record oldRecord = new Record(0, 0, "", "");
    double Ydif = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);
        standard = (ImageView) findViewById(R.id.standardTone);
        toneColor = (ImageView) findViewById(R.id.tone_color);
        colorcard = (TextView) findViewById(R.id.color_card);
        pic = (ImageView) findViewById(R.id.tone_id);
        prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        editor = prefs.edit();
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        String dir = cw.getDir("imageDir", Context.MODE_PRIVATE).getPath();
        loadImageFromStorage(dir);


    }


    private void loadImageFromStorage(String path) {

        try {
            File f = new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            long redBucket = 0;
            long greenBucket = 0;
            long blueBucket = 0;
            long pixelCount = 0;

            for (int y = 0; y < b.getHeight(); y++) {
                for (int x = 0; x < b.getWidth(); x++) {
                    int c = b.getPixel(x, y);

                    pixelCount++;
                    redBucket += Color.red(c);
                    greenBucket += Color.green(c);
                    blueBucket += Color.blue(c);
                }
            }

            Integer r = (int) (long) (redBucket / pixelCount);
            Integer g = (int) (long) (greenBucket / pixelCount);
            Integer blue = (int) (long) (blueBucket / pixelCount);
            averageColor = Color.rgb(r, g, blue);
            toneColor.setBackgroundColor(averageColor);
            Calendar c = Calendar.getInstance();
            int seconds = c.get(Calendar.SECOND);


            try {
                recordArrayList = (ArrayList<Record>) ObjectSerializer.deserialize(prefs.getString("re", ObjectSerializer.serialize(new ArrayList<Record>())));

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }

            if (null == recordArrayList) {
                recordArrayList = new ArrayList<>();
            }

            if (recordArrayList.size() > 0) {
                Log.v(Integer.toString(recordArrayList.size()), "!!!" + "");
                oldRecord = recordArrayList.get(recordArrayList.size() - 1);
            }

            sdf = new SimpleDateFormat("dd/MM/yyyy" + " " + "HH:mm");
            Record record = new Record(seconds, averageColor, sdf.format(new Date()), null);

            if (recordArrayList.size() > 0) {
                Ydif = (record.getY() - oldRecord.getY()) / (0.01 * oldRecord.getY());
                Ydif = Math.floor(Ydif * 100) / 100;
                record.setImprove(Double.toString(Ydif) + "%");
            } else {
                record.setImprove("0%");
            }
            recordArrayList.add(record);

            try {
                editor.putString("re", ObjectSerializer.serialize(recordArrayList));
                Log.v("save the record", "!!!" + "");
            } catch (IOException e) {
                e.printStackTrace();
            }
            editor.commit();


            pic.setImageBitmap(Bitmap.createScaledBitmap(b, 410, 120, false));
            getStandard();
            Log.v("color analysis finished", "!!!" + "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void getStandard() {
        double[] lab = new double[3];
        double[][] labs = new double[76][3];
        double min = 1000;
        ColorUtils.colorToLAB(averageColor, lab);
        standardSkin = getResources().getIntArray(R.array.standardSkin);
        standardName = getResources().getStringArray(R.array.fi);
        for (int i = 0; i < 76; i++) {

        /* ----------- convert RGB to L a b with Utility ---------*/
            ColorUtils.colorToLAB(standardSkin[i], labs[i]);

        /* ------- method L a b to calculate color difference ----*/

            div[i] = Math.sqrt(Math.pow((labs[i][1] - lab[1]), 2)
                    + Math.pow((labs[i][2] - lab[2]), 2) + Math.pow((labs[i][0] - lab[0]), 2));

            Log.v("color 's value", Double.toString(div[i]));
            min = Math.min(min, div[i]);
            Log.v("color 's min value", Double.toString(min));

        }

        for (int i = 0; i < 76; i++) {
            if (Math.abs(div[i] - min) < 1) {
                Log.v("color 's sequence", Integer.toString(i));
                colorcard.setBackgroundColor(standardSkin[i]);
                colorcard.setText(standardName[i]);
            }
        }
    }


    public boolean isSdReadable() {

        boolean mExternalStorageAvailable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = true;
            Log.i("isSdReadable", "External storage card is readable.");
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            Log.i("isSdReadable", "External storage card is readable.");
            mExternalStorageAvailable = true;
        } else {
            // Something else is wrong. It may be one of many other
            // states, but all we need to know is we can neither read nor write
            mExternalStorageAvailable = false;
        }

        return mExternalStorageAvailable;
    }


    public Bitmap getThumbnail(String filename) {

        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_SD_CARD + APP_THUMBNAIL_PATH_SD_CARD;
        Bitmap thumbnail = null;

        // Look for the file on the external storage
        try {
            if (isSdReadable() == true) {
                thumbnail = BitmapFactory.decodeFile(fullPath + "/" + filename);
            }
        } catch (Exception e) {
            Log.e("getThumb", e.getMessage());
        }

        // If no file on external storage, look in internal storage
        if (thumbnail == null) {
            try {
                File filePath = getFileStreamPath(filename);
                FileInputStream fi = new FileInputStream(filePath);
                thumbnail = BitmapFactory.decodeStream(fi);
            } catch (Exception ex) {
                Log.e("getThumbnail", ex.getMessage());
            }
        }
        return thumbnail;
    }
}

