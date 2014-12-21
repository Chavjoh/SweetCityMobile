package ch.hesso.master.sweetcity.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import ch.hesso.master.sweetcity.Constants;
import ch.hesso.master.sweetcity.callback.AccountCallback;
import ch.hesso.master.sweetcity.callback.RankingCallback;
import ch.hesso.master.sweetcity.callback.TagCallback;
import ch.hesso.master.sweetcity.model.AccountCollection;
import ch.hesso.master.sweetcity.model.TagCollection;
import ch.hesso.master.sweetcity.utils.ServiceUtils;
import ch.hesso.master.sweetcity.webservice.account.AccountService;
import ch.hesso.master.sweetcity.webservice.tag.TagService;

public class GetRankingAsyncTask extends AsyncTask<String, Void, AccountCollection> {

    private Activity context;
    private RankingCallback callback;
    private GoogleAccountCredential credential;

    public GetRankingAsyncTask(Activity context, RankingCallback callback, GoogleAccountCredential credential) {
        this.context = context;
        this.callback = callback;
        this.credential = credential;
    }

    protected void onPreExecute(){
        super.onPreExecute();
        callback.beforeLoading();
    }

    protected AccountCollection doInBackground(String... params) {
        AccountCollection response = null;

        try {
            Log.d(Constants.PROJECT_NAME, "[AccountService] Calling ...");

            AccountService service = ServiceUtils.getAccountService(credential);
            response = service.accountService().ranking().execute();

            Log.d(Constants.PROJECT_NAME, "[AccountService] Response received");
        }
        catch (Exception e) {
            Log.d("[AccountService] Service unavailable : ", e.getMessage(), e);

            callback.failed();
        }

        return response;
    }

    protected void onPostExecute(AccountCollection accountCollection) {
        super.onPostExecute(accountCollection);

        if (accountCollection != null) {
            callback.loaded(accountCollection.getItems());
        }
    }
}
