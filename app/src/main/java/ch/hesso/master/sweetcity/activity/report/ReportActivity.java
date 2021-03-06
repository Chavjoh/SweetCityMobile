/*
* Copyright 2014-2015 University of Applied Sciences and Arts Western Switzerland
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package ch.hesso.master.sweetcity.activity.report;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import ch.hesso.master.sweetcity.Constants;
import ch.hesso.master.sweetcity.R;
import ch.hesso.master.sweetcity.activity.IntentTag;
import ch.hesso.master.sweetcity.activity.tag.TagSelectionActivity;
import ch.hesso.master.sweetcity.callback.ReportCallbackImpl;
import ch.hesso.master.sweetcity.data.CurrentTagList;
import ch.hesso.master.sweetcity.model.Report;
import ch.hesso.master.sweetcity.model.Tag;
import ch.hesso.master.sweetcity.provider.CameraPictureProvider;
import ch.hesso.master.sweetcity.task.AddReportAsyncTask;
import ch.hesso.master.sweetcity.tools.BitmapScaler;
import ch.hesso.master.sweetcity.utils.AuthUtils;
import ch.hesso.master.sweetcity.utils.DialogUtils;
import ch.hesso.master.sweetcity.utils.ImageUtils;
import ch.hesso.master.sweetcity.utils.LayoutUtils;
import ch.hesso.master.sweetcity.utils.ModelUtils;

/**
 * @author Chavaillaz Johan
 * @author Burri Grégory
 * @author Frank Etienne
 * @version 1.0
 */
public class ReportActivity extends Activity {

    private TextView tagResume;
    private ImageView imageView;
    private Bitmap bitmapPicture;
    private HashMap<Integer, Tag> tagList;

    private Button capture;
    private Button tagSelection;
    private Button submit;

    Location currentLocation;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        this.updateCurrentLocation();

        getActionBar().setDisplayHomeAsUpEnabled(true);

        findViews();
        initButtons();
    }

    void updateCurrentLocation() {
        if (this.locationListener == null)
            this.locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    ReportActivity.this.currentLocation = location;
                }

                @Override
                public void onProviderDisabled(String provider) { }

                @Override
                public void onProviderEnabled(String provider) { }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) { }
            };

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_MEDIUM);
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        criteria.setSpeedAccuracy(Criteria.NO_REQUIREMENT);
        criteria.setSpeedRequired(false);

        LocationManager locationManager = (LocationManager)ReportActivity.this.getSystemService(LOCATION_SERVICE);
        locationManager.requestSingleUpdate(criteria, this.locationListener, null);
    }

    private void findViews() {
        tagList = new HashMap<Integer, Tag>();
        tagResume = LayoutUtils.findView(this, R.id.tv_tag);
        imageView = LayoutUtils.findView(this, R.id.iv_report);
        capture = LayoutUtils.findView(this, R.id.btn_picture);
        tagSelection = LayoutUtils.findView(this, R.id.btn_tag);
        submit = LayoutUtils.findView(this, R.id.btn_submit);
    }

    private void initButtons() {
        capture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, CameraPictureProvider.CONTENT_URI);
                startActivityForResult(cameraIntent, IntentTag.TAKE_PICTURE);
            }

        });

        tagSelection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent tagIntent = new Intent(getApplicationContext(), TagSelectionActivity.class);
                startActivityForResult(tagIntent, IntentTag.TAG_SELECTION);
            }

        });

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                submitReport();
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (bitmapPicture != null) {
            capture.setText(R.string.retake_picture);
        }

        if (tagList.size() > 0) {
            tagSelection.setText(R.string.reselect_tags);
        }
    }

    private void submitReport() {
        if (bitmapPicture == null) {
            DialogUtils.show(ReportActivity.this, "To submit a report, you need to take an representative image.");
            return;
        }

        if (this.currentLocation == null) {
            LocationManager locationManager = (LocationManager)ReportActivity.this.getSystemService(LOCATION_SERVICE);
            this.currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (this.currentLocation == null) {
                DialogUtils.show(ReportActivity.this, "Unable to get your current location.");
                return;
            }
        }

        Report newReport = new Report();
        newReport.setLatitude((float) this.currentLocation.getLatitude());
        newReport.setLongitude((float) this.currentLocation.getLongitude());
        newReport.setSubmitDate(new DateTime(new Date()));
        newReport.setUser(AuthUtils.getAccount());
        newReport.setListTag(new ArrayList(tagList.values()));

        ReportCallbackImpl callback = new ReportCallbackWithResult(ReportActivity.this);
        new AddReportAsyncTask(this, callback, AuthUtils.getCredential(), newReport, bitmapPicture).execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case IntentTag.TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    File picture = new File(getFilesDir(), CameraPictureProvider.PICTURE_FILE);

                    try {
                        BitmapScaler scaler = new BitmapScaler(picture, 1024);
                        bitmapPicture = ImageUtils.getRoundedCornerBitmap(scaler.getScaled(), 64);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

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
        tagResume.setText(ModelUtils.listToString(new ArrayList<Tag>(tagList.values())));
    }

    public void showPicture() {
        if (bitmapPicture != null) {
            imageView.setImageBitmap(bitmapPicture);
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
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putIntegerArrayList("tagList", new ArrayList<Integer>(tagList.keySet()));
        savedInstanceState.putParcelable("image", bitmapPicture);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        bitmapPicture = savedInstanceState.getParcelable("image");
        ArrayList<Integer> positionList = savedInstanceState.getIntegerArrayList("tagList");
        CurrentTagList.getInstance().putAllFromPositions(tagList, positionList);

        Log.d(Constants.PROJECT_NAME, "positionList " + positionList.toString());

        showTagList();
        showPicture();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        File picture = new File(getFilesDir(), CameraPictureProvider.PICTURE_FILE);
        if (picture.exists()) {
            picture.delete();
        }
    }

    private class ReportCallbackWithResult extends ReportCallbackImpl {

        public ReportCallbackWithResult(Activity context) {
            super(context);
        }

        @Override
        public void afterAdding() {
            if (!this.isFailed) {
                setResult(RESULT_OK, new Intent());
            }

            super.afterAdding();
        }
    }
}
