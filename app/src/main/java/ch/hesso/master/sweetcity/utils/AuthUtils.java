package ch.hesso.master.sweetcity.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import ch.hesso.master.sweetcity.Constants;
import ch.hesso.master.sweetcity.callback.AccountCallback;
import ch.hesso.master.sweetcity.callback.AccountCallbackImpl;

public class AuthUtils {

    public static final int REQUEST_ACCOUNT_PICKER = 2;
    private static final String KEY_ACCOUNT_NAME = "ACCOUNT_NAME";

    private static AuthUtils instance;
    private static GoogleAccountCredential credential;

    private AccountCallbackImpl callbackAccount;
    private SharedPreferences settings;
    private Activity context;

    public static AuthUtils getInstance(Activity context) {
        if (instance == null) {
            instance = new AuthUtils(context);
        } else {
            instance.setContext(context);
        }
        return instance;
    }

    public AuthUtils(Activity context) {
        this.callbackAccount = new AccountCallbackImpl(context);
        this.settings = context.getSharedPreferences(Constants.PROJECT_NAME, 0);
        this.setContext(context);

        credential = GoogleAccountCredential.usingAudience(context, Constants.AUDIENCE);
    }

    public void checkAccount() {
        setAccountName(settings.getString(KEY_ACCOUNT_NAME, null));

        if (credential.getSelectedAccountName() == null) {
            chooseAccount();
        } else {
            //Toast.makeText(context, "Logged in with : " + credential.getSelectedAccountName(), Toast.LENGTH_SHORT).show();
        }
    }

    public void setAccountName(String accountName) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_ACCOUNT_NAME, accountName);
        editor.commit();

        credential.setSelectedAccountName(accountName);

        if (accountName != null) {
            this.callbackAccount.selected();
        }
    }

    private void chooseAccount() {
        context.startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
    }

    public AccountCallback getCallback() {
        return this.callbackAccount;
    }

    public String getAccountName() {
        return settings.getString(KEY_ACCOUNT_NAME, null);
    }

    public void setContext(Activity context) {
        this.context = context;
        this.callbackAccount.setContext(context);
    }

    public static GoogleAccountCredential getCredential() {
        return credential;
    }

    public static boolean isInstantiate() {
        return instance != null;
    }
}
