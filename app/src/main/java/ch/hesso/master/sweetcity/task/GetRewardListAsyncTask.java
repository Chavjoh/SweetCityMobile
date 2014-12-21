package ch.hesso.master.sweetcity.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import ch.hesso.master.sweetcity.Constants;
import ch.hesso.master.sweetcity.callback.RewardCallback;
import ch.hesso.master.sweetcity.model.RewardCollection;
import ch.hesso.master.sweetcity.utils.ServiceUtils;
import ch.hesso.master.sweetcity.webservice.reward.RewardService;

public class GetRewardListAsyncTask extends AsyncTask<String, Void, RewardCollection> {

    private Activity context;
    private RewardCallback callback;
    private GoogleAccountCredential credential;

    public GetRewardListAsyncTask(Activity context, RewardCallback callback, GoogleAccountCredential credential) {
        this.context = context;
        this.callback = callback;
        this.credential = credential;
    }

    protected void onPreExecute(){
        super.onPreExecute();

        callback.beforeLoading();
    }

    protected RewardCollection doInBackground(String... params) {
        RewardCollection response = null;

        try {
            Log.d(Constants.PROJECT_NAME, "[RewardService] Calling ...");

            RewardService service = ServiceUtils.getRewardService(credential);
            response = service.rewardService().getRewardList().execute();

            Log.d(Constants.PROJECT_NAME, "[RewardService] Response received");
        }
        catch (Exception e) {
            Log.d("[RewardService] Service unavailable : ", e.getMessage(), e);

            callback.failed();
        }

        return response;
    }

    protected void onPostExecute(RewardCollection rewardCollection) {
        super.onPostExecute(rewardCollection);

        if (rewardCollection != null) {
            callback.loaded(rewardCollection.getItems());
        }
    }
}
