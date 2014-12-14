package ch.hesso.master.sweetcity.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import ch.hesso.master.sweetcity.Constants;
import ch.hesso.master.sweetcity.model.Account;
import ch.hesso.master.sweetcity.utils.AuthUtils;
import ch.hesso.master.sweetcity.utils.ServiceUtils;
import ch.hesso.master.sweetcity.webservice.account.AccountService;
import ch.hesso.master.sweetcity.callback.AccountCallback;

public class GetAccountAsyncTask extends AsyncTask<String, Void, Account> {

    private Activity context;
    private AccountCallback callback;
    private GoogleAccountCredential credential;

    public GetAccountAsyncTask(Activity context, AccountCallback callback, GoogleAccountCredential credential) {
        this.context = context;
        this.callback = callback;
        this.credential = credential;
    }

    protected void onPreExecute(){
        super.onPreExecute();
    }

    protected Account doInBackground(String... params) {
        Account response = null;

        try {
            Log.d(Constants.PROJECT_NAME, "[AccountService] Calling for account ...");

            AccountService service = ServiceUtils.getAccountService(credential);
            response = service.accountService().getCurrentAccount().execute();

            Log.d(Constants.PROJECT_NAME, "[AccountService] Response received");
        }
        catch (Exception e) {
            Log.d("[AccountService] Service unavailable : ", e.getMessage(), e);

            callback.failed();
        }

        return response;
    }

    protected void onPostExecute(Account account) {
        super.onPostExecute(account);

        callback.loaded(account);
    }
}
