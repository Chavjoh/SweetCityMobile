package ch.hesso.master.sweetcity.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import ch.hesso.master.sweetcity.Constants;
import ch.hesso.master.sweetcity.callback.ReportCallback;
import ch.hesso.master.sweetcity.model.ReportCollection;
import ch.hesso.master.sweetcity.utils.ServiceUtils;
import ch.hesso.master.sweetcity.webservice.report.ReportService;

public class GetReportListAsyncTask extends AsyncTask<String, Void, ReportCollection> {

    private Activity context;
    private ReportCallback callback;
    private GoogleAccountCredential credential;

    public GetReportListAsyncTask(Activity context, ReportCallback callback, GoogleAccountCredential credential) {
        this.context = context;
        this.callback = callback;
        this.credential = credential;
    }

    protected void onPreExecute(){
        super.onPreExecute();
        callback.beforeLoading();
    }

    protected ReportCollection doInBackground(String... params) {
        ReportCollection response = null;

        try {
            Log.d(Constants.PROJECT_NAME, "[ReportService] Calling ...");

            ReportService service = ServiceUtils.getReportService(credential);
            response = service.reportService().getListReportBySubmitDate().execute();

            Log.d(Constants.PROJECT_NAME, "[ReportService] Response received");
        }
        catch (Exception e) {
            Log.d("[ReportService] Service unavailable : ", e.getMessage(), e);

            callback.failed();
        }

        return response;
    }

    protected void onPostExecute(ReportCollection reportCollection) {
        super.onPostExecute(reportCollection);

        if (reportCollection != null) {
            callback.loaded(reportCollection.getItems());
        }
    }
}
