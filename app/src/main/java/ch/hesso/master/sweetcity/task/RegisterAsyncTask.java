package ch.hesso.master.sweetcity.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import ch.hesso.master.sweetcity.Constants;
import ch.hesso.master.sweetcity.callback.AccountCallback;
import ch.hesso.master.sweetcity.utils.ServiceUtils;
import ch.hesso.master.sweetcity.webservice.account.AccountService;

public class RegisterAsyncTask extends AsyncTask<String, Void, Void> {

    private Activity context;
    private AccountCallback callback;
    private GoogleAccountCredential credential;
    private String login;

    public RegisterAsyncTask(Activity context, AccountCallback callback, GoogleAccountCredential credential, String login) {
        this.context = context;
        this.callback = callback;
        this.credential = credential;
        this.login = login;
    }

    protected void onPreExecute(){
        super.onPreExecute();
        callback.beforeRegistration();
    }

    protected Void doInBackground(String... params) {

        try {
            Log.d(Constants.PROJECT_NAME, "[AccountService] Calling for registration ...");

            AccountService service = ServiceUtils.getAccountService(this.credential);
            service.accountService().register(this.login).execute();

            Log.d(Constants.PROJECT_NAME, "[AccountService] Registered");
        }
        catch (Exception e) {
            Log.d("[AccountService] Service unavailable : ", e.getMessage(), e);

            callback.failed();
        }

        return null;
    }

    protected void onPostExecute(Void nothing) {
        super.onPostExecute(nothing);
        callback.registered();
    }
}
