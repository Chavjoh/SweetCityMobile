package ch.hesso.master.sweetcity.activity.report;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.TextView;

import com.google.api.client.util.DateTime;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.hesso.master.sweetcity.Constants;
import ch.hesso.master.sweetcity.R;
import ch.hesso.master.sweetcity.activity.IntentTag;
import ch.hesso.master.sweetcity.activity.tag.TagSelectionActivity;
import ch.hesso.master.sweetcity.callback.ReportCallbackImpl;
import ch.hesso.master.sweetcity.data.CurrentTagList;
import ch.hesso.master.sweetcity.model.Report;
import ch.hesso.master.sweetcity.model.Tag;
import ch.hesso.master.sweetcity.task.AddReportAsyncTask;
import ch.hesso.master.sweetcity.utils.AuthUtils;

public class ReportActivity extends Activity {

    private TextView tagResume;
    private ImageView imageView;
    private String filePath;
    private HashMap<Integer, Tag> tagList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        tagList = new HashMap<Integer, Tag>();
        tagResume = (TextView)findViewById(R.id.tv_tag);
        imageView = (ImageView)findViewById(R.id.iv_report);

        final String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/sweetCity/";
        File directory = new File(path);
        directory.mkdirs();

        for (File file : directory.listFiles()) {
            Log.d(Constants.PROJECT_NAME, file.getPath());
        }

        Button capture = (Button) findViewById(R.id.btn_picture);
        capture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                filePath = path+(new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss-SSSZ").format(new Date()))+".jpg";

                File fileImage = new File(filePath);
                try {
                    fileImage.createNewFile();
                } catch (IOException e) {
                    Log.d(Constants.PROJECT_NAME, e.toString());
                }

                Uri outputFileUri = Uri.fromFile(fileImage);

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(cameraIntent, IntentTag.TAKE_PICTURE);
            }

        });

        Button tagSelection = (Button) findViewById(R.id.btn_tag);
        tagSelection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(Constants.PROJECT_NAME, "Tag Selection");
                Intent tagIntent = new Intent(getApplicationContext(), TagSelectionActivity.class);
                startActivityForResult(tagIntent, IntentTag.TAG_SELECTION);
            }

        });

        Button submit = (Button) findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(Constants.PROJECT_NAME, "Submit");

                LocationManager locationManager = (LocationManager) ReportActivity.this.getSystemService(LOCATION_SERVICE);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                Report newReport = new Report();
                newReport.setImage(filePath);
                newReport.setLatitude((float) location.getLatitude());
                newReport.setLongitude((float) location.getLongitude());
                newReport.setSubmitDate(new DateTime(new Date()));
                newReport.setUser(AuthUtils.getAccount());
                newReport.setListTag(new ArrayList(tagList.values()));

                ReportCallbackImpl callback = new ReportCallbackImpl(ReportActivity.this);
                new AddReportAsyncTask(ReportActivity.this, callback, AuthUtils.getCredential(), newReport).execute();
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case IntentTag.TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    showPicture();
                }
                break;

            case IntentTag.TAG_SELECTION:
                if (resultCode == RESULT_OK) {
                    ArrayList<Integer> result = data.getExtras().getIntegerArrayList("selectedItems");

                    tagList.clear();
                    CurrentTagList.getInstance().putAllFromPositions(tagList, result);
                    showTagList();
                }
                break;
        }
    }

    public void showTagList() {
        StringBuilder tagListString = new StringBuilder("Tag list : ");
        for (Map.Entry<Integer, Tag> tag:tagList.entrySet()) {
            tagListString.append(tag.getValue().getName());
            tagListString.append(" ");
        }

        tagResume.setText(tagListString.toString());
    }

    public void showPicture() {
        if (filePath != null) {
            Bitmap pictureBitmap = BitmapFactory.decodeFile(filePath);
            imageView.setImageBitmap(pictureBitmap);
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putIntegerArrayList("tagList", new ArrayList<Integer>(tagList.keySet()));
        savedInstanceState.putString("filePath", filePath);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        filePath = savedInstanceState.getString("filePath");
        ArrayList<Integer> positionList = savedInstanceState.getIntegerArrayList("tagList");
        CurrentTagList.getInstance().putAllFromPositions(tagList, positionList);

        Log.d(Constants.PROJECT_NAME, "filePath " + filePath);
        Log.d(Constants.PROJECT_NAME, "positionList " + positionList.toString());

        showTagList();
        showPicture();
    }
    
}
