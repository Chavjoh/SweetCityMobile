package ch.hesso.master.sweetcity;

import android.accounts.AccountManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity {

    private GoogleMap map; // Might be null if Google Play services APK is not available.
    private List<Report> reports;
    public static final int REPORT_ACTIVITY = 12;

    private GoogleAccountCredential credential;
    private SharedPreferences settings;
    private String accountName;
    private static final int REQUEST_ACCOUNT_PICKER = 2;  

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        loadReportsFromPreferences();
        setReportsOnMap();
        
        setUpMapIfNeeded();
        Button btnDoReport = (Button)findViewById(R.id.btnDoReport);
        btnDoReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                startActivityForResult(intent, REPORT_ACTIVITY);
            }
        });

        //Account stuff
        settings = getSharedPreferences("FamousQuotesAndroid", 0);
        credential = GoogleAccountCredential.usingAudience(MapsActivity.this, "server:client_id:949477582144-vk2bci1ra92qpf1nmqoog7op4da8vmiv.apps.googleusercontent.com");
        setAccountName(settings.getString("ACCOUNT_NAME", null));
        if (credential.getSelectedAccountName() != null) {
            // Already signed in, begin app!
            new GetAcountAsyncTask(MapsActivity.this,credential,settings).execute();
            Toast.makeText(getBaseContext(), "Logged in with : " + credential.getSelectedAccountName(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(getBaseContext(), GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext()),Toast.LENGTH_SHORT).show();
        } else {
            // Not signed in, show login window or request an account.
            chooseAccount();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        setReportsOnMap();

        switch (requestCode) {
            case REQUEST_ACCOUNT_PICKER:
                if (data != null && data.getExtras() != null) {
                    String accountName =
                            data.getExtras().getString(
                                    AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        setAccountName(accountName);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("ACCOUNT_NAME", accountName);
                        editor.commit();
                        // User is authorized.
                    }
                }
                new GetAcountAsyncTask(MapsActivity.this,credential,settings).execute();
                break;
            case REPORT_ACTIVITY:
                if(resultCode == RESULT_OK){
                    Report reportResult = (Report)data.getParcelableExtra(Report.INTENT_TAG);
                    reports.add(reportResult);
                    onResume();
//            Toast.
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setReportsOnMap();
        setUpMapIfNeeded();
    }
    
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map == null) {
            // Try to obtain the map from the SupportMapFragment.
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Intent intent = new Intent(getApplicationContext(), ShowReportActivity.class);
                    intent.putExtra("image",marker.getTitle());
                    startActivity(intent);
                            
                    return false;
                }
            });
            // Check if we were successful in obtaining the map.
            if (map != null) {
                setUpMap();
            }
        }
    }
    
    public void setReportsOnMap(){
//        Toast.makeText(getApplicationContext(), "cocool", Toast.LENGTH_SHORT).show();
        if(map == null)return;
        for(Report report:reports){
            map.addMarker(new MarkerOptions().position(report.getLatlng())).setTitle(report.getPictureFileName());
        }
    }
    
    public void loadReportsFromPreferences(){
        reports = new ArrayList<Report>();
        
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(this, Report.INTENT_TAG, MODE_PRIVATE);
        ListReport complexObject = complexPreferences.getObject("list", ListReport.class);
        if(complexObject==null)return;
        
        for(Report report: complexObject.getReports()){
            reports.add(report);
//            Toast.makeText(getApplicationContext(),"proute",Toast.LENGTH_SHORT).show();
//            Log.i("ComplexPreferences", "item " + report);
        }
    }
    
    public void saveReportsToPreferences(){
        // populate your List
        ListReport complexObject = new ListReport();
        complexObject.setReports(reports);

        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(this, Report.INTENT_TAG, MODE_PRIVATE);;
        complexPreferences.putObject("list", complexObject);
        complexPreferences.commit();
    }
    
    @Override
    protected void onDestroy(){
        saveReportsToPreferences();
        
        super.onDestroy();
    }

    private void setUpMap() {
//        map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        try {
            setReportsOnMap();
//            map.setMyLocationEnabled(true);
//            Location location = map.getMyLocation();
//            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
//            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 10));
        }catch(RuntimeException e) {
            return;
        }
    }

    // setAccountName definition
    private void setAccountName(String accountName) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("ACCOUNT_NAME", accountName);
        editor.commit();
        credential.setSelectedAccountName(accountName);
        this.accountName = accountName;
    }

    void chooseAccount() {
        startActivityForResult(credential.newChooseAccountIntent(),REQUEST_ACCOUNT_PICKER);
//        Toast.makeText(getApplicationContext(), "fini choose", Toast.LENGTH_SHORT).show();
    }
}
