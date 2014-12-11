package ch.hesso.master.sweetcity.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import ch.hesso.master.sweetcity.Constants;
import ch.hesso.master.sweetcity.callback.ReportCallback;
import ch.hesso.master.sweetcity.callback.TagCallback;
import ch.hesso.master.sweetcity.model.Report;
import ch.hesso.master.sweetcity.model.TagCollection;
import ch.hesso.master.sweetcity.utils.ServiceUtils;
import ch.hesso.master.sweetcity.webservice.report.ReportService;
import ch.hesso.master.sweetcity.webservice.tag.TagService;

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
        if (this.localPicturePath == null)
            return;

        try {
            AWSCredentials awsCredentials = new BasicAWSCredentials("AKIAIATVJQKE6HKWZD4A", "+ETAEkTjx479guBASDUvnxLj4KqMEHRzJvyzk2X5");
            ClientConfiguration clientConfig = new ClientConfiguration();
            clientConfig.setProtocol(Protocol.HTTP);
            AmazonS3 s3Connection = new AmazonS3Client(awsCredentials, clientConfig);
            s3Connection.setEndpoint("s3-eu-west-1.amazonaws.com"); // http://docs.aws.amazon.com/general/latest/gr/rande.html

            InputStream localStreamPicture = new FileInputStream(this.localPicturePath);
            ObjectMetadata pictureMetadata = new ObjectMetadata();

            String key = String.format("pictures/%s/img_%s.jpg", this.credential.getSelectedAccountName(), new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss-SSSZ").format(new Date())); // %s/
            s3Connection.putObject("sweet-city", key, localStreamPicture, pictureMetadata);
            this.report.setImage(key); // TODO: put an URL. like : https://s3-eu-west-1.amazonaws.com/sweet-city/pictures/greg.burri%40gmail.com/img_2014-12-11-11-09-32-717%2B0100.jpg
        } catch (Exception e) {
            Log.d(Constants.PROJECT_NAME, e.toString());
        }
    }

    @Override
    protected void onPostExecute(Void useless) {
        super.onPostExecute(null);
        callback.afterAdding();
    }
}
