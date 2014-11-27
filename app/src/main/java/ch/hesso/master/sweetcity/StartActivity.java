package ch.hesso.master.sweetcity;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;


public class StartActivity extends Activity {

    private GoogleAccountCredential credential;
    private SharedPreferences settings;
    private String accountName;
    private static final int REQUEST_ACCOUNT_PICKER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button btnContinue = (Button)findViewById(R.id.usernameBtn);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                
//                create pseudo
                
                startActivityForResult(intent, IntentTags.MAPS);
            }
        });

        //Account stuff
        settings = getSharedPreferences("FamousQuotesAndroid", 0);
        credential = GoogleAccountCredential.usingAudience(StartActivity.this, "server:client_id:949477582144-vk2bci1ra92qpf1nmqoog7op4da8vmiv.apps.googleusercontent.com");
        setAccountName(settings.getString("ACCOUNT_NAME", null));
        if (credential.getSelectedAccountName() != null) {
            // Already signed in, begin app!
            new GetAcountAsyncTask(StartActivity.this,credential,settings).execute();
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

        switch (requestCode) {
            case REQUEST_ACCOUNT_PICKER:
                if (data != null && data.getExtras() != null) {
                    String accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);

                    if (accountName != null) {
                        setAccountName(accountName);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("ACCOUNT_NAME", accountName);
                        editor.commit();
                        // User is authorized.
                    }
                }else {
//                    Toast.makeText(getApplicationContext(),, Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                    dlgAlert.setTitle("Error login");
                    dlgAlert.setMessage( "You must be logged");
                    dlgAlert.setPositiveButton("Retry",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    });
                    dlgAlert.setNegativeButton("Quit",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            finish();
                        }
                    });
                    dlgAlert.create().show();
                }
                new GetAcountAsyncTask(StartActivity.this,credential,settings).execute();
                break;
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

    private void chooseAccount() {
        startActivityForResult(credential.newChooseAccountIntent(),REQUEST_ACCOUNT_PICKER);
//        Toast.makeText(getApplicationContext(), "fini choose", Toast.LENGTH_SHORT).show();
    }
}
