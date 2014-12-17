package ch.hesso.master.sweetcity.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import ch.hesso.master.sweetcity.Constants;
import ch.hesso.master.sweetcity.callback.ReportCallback;
import ch.hesso.master.sweetcity.model.Report;
import ch.hesso.master.sweetcity.model.TagCollection;
import ch.hesso.master.sweetcity.utils.PictureUtils;
import ch.hesso.master.sweetcity.utils.ServiceUtils;
import ch.hesso.master.sweetcity.webservice.report.ReportService;

public class AddReportAsyncTask extends AsyncTask<String, Void, Void> {

    private Activity context;
    private ReportCallback callback;
    private GoogleAccountCredential credential;
    private Report report;
    private String localPicturePath;

    public AddReportAsyncTask(Activity context, ReportCallback callback, GoogleAccountCredential credential, Report report, String localPicturePath) {
        this.context = context;
        this.callback = callback;
        this.credential = credential;
        this.report = report;
        this.localPicturePath = localPicturePath;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.beforeAdding();
    }

    @Override
    protected Void doInBackground(String... params) {
        TagCollection response = null;

        try {
            Log.d(Constants.PROJECT_NAME, "[ReportService] Adding ...");

            this.uploadPicture();
            PictureUtils.Key key = PictureUtils.uploadPicture(this.localPicturePath, this.credential);
            report.setImage(key.toString());

            ReportService service = ServiceUtils.getReportService(credential);
            service.reportService().addReport(report).execute();

            Log.d(Constants.PROJECT_NAME, "[ReportService] Report added");
        }
        catch (Exception e) {
            Log.d("[ReportService] Service unavailable : ", e.getMessage(), e);

            callback.failed();
        }

        return null;
    }

    /**
     * Upload the image to AWS
     * Create an URL and store it into 'this.report'.
     */
    private void uploadPicture() {
    }

    @Override
    protected void onPostExecute(Void useless) {
        super.onPostExecute(null);
        callback.afterAdding();
    }
}
