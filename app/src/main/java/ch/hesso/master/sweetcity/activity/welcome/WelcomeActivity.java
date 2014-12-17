package ch.hesso.master.sweetcity.activity.welcome;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ch.hesso.master.sweetcity.R;
import ch.hesso.master.sweetcity.activity.map.MapActivity;
import ch.hesso.master.sweetcity.utils.AuthUtils;
import ch.hesso.master.sweetcity.utils.DialogUtils;
import ch.hesso.master.sweetcity.utils.ProjectUtils;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Check account, ask to choose one or/and register if necessary
        if (!DialogUtils.isDialogShown()) {
            if (AuthUtils.isLogged()) {
                ProjectUtils.startActivityWithoutBackStack(this, MapActivity.class);
            } else {
                AuthUtils.getInstance(this).checkAccount();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case AuthUtils.REQUEST_ACCOUNT_PICKER:
                if (data != null && data.getExtras() != null) {
                    String accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);

                    if (accountName != null) {
                        AuthUtils.getInstance(WelcomeActivity.this).setAccountName(accountName);
                    }
                } else {
                    DialogUtils.showAndExit(this, "You must be logged to use this application.");
                }

                break;
        }
    }
}
