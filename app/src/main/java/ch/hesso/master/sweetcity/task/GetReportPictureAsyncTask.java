package ch.hesso.master.sweetcity.task;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import ch.hesso.master.sweetcity.callback.PictureUploadCallback;
import ch.hesso.master.sweetcity.utils.PictureUtils;

public class GetReportPictureAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private Activity context;
    private PictureUploadCallback callback;
    private PictureUtils.Key key;

    public GetReportPictureAsyncTask(Activity context, PictureUploadCallback callback, PictureUtils.Key key) {
        this.context = context;
        this.callback = callback;
        this.key = key;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.callback.beforeLoading();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            return BitmapFactory.decodeStream(PictureUtils.getPicture(this.key));
        } catch (Exception e) {
            Log.d("[ReportService] Unable to get the picture : ", e.getMessage(), e);
            callback.failed();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap picture) {
        super.onPostExecute(null);

        if (picture != null)
            this.callback.loaded(picture);
    }
}