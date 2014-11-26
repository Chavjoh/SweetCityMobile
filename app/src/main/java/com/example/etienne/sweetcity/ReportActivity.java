package com.example.etienne.sweetcity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ReportActivity extends Activity {

    int TAKE_PHOTO_CODE = 0;
    private ImageView imageView;
    private Bitmap bitMap;
    private String file;
    private List<Report> reports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        imageView = (ImageView)findViewById(R.id.imageReport);

        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/sweetCity/";
        File newdir = new File(dir);
        newdir.mkdirs();
        
//        for (File file : newdir.listFiles()) {
//            Toast.makeText(getApplicationContext(), file.getPath(), Toast.LENGTH_SHORT).show();
//        }
        
        

        Button capture = (Button) findViewById(R.id.btnTakePicture);
        capture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                file = dir+(new SimpleDateFormat("yyyyMMddhhmmssSSSZ").format(new Date()))+".jpg";
//                String file = dir+"proute.jpg";
                File newfile = new File(file);
                try {
                    newfile.createNewFile();
                } catch (IOException e) {}
                Uri outputFileUri = Uri.fromFile(newfile);
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        InputStream stream = null;
        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {

            LocationManager  locationManager = (LocationManager)this.getSystemService(LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Report newReport = new Report(file,new LatLng(location.getLatitude(),location.getLongitude()));

            Intent resultData = new Intent();
            resultData.putExtra(Report.INTENT_TAG, newReport);
            setResult(Activity.RESULT_OK, resultData);
            finish();
            
//            try {
//                // recyle unused bitmaps
//                if (bitMap != null) {
//                    bitMap.recycle();
//                }
//                stream = getContentResolver().openInputStream(data.getData());
//                bitMap = BitmapFactory.decodeStream(stream);
//
//                imageView.setImageBitmap(bitMap);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } finally {
//                if (stream != null) {
//                    try {
//                        stream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
            
            Log.d("CameraDemo", "Pic saved");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    
}
