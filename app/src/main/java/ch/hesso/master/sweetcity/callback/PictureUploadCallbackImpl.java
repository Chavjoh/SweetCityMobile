package ch.hesso.master.sweetcity.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;

import ch.hesso.master.sweetcity.utils.DialogUtils;

public class PictureUploadCallbackImpl implements PictureUploadCallback {

    protected Activity context;
    protected ProgressDialog progressDialog;

    public PictureUploadCallbackImpl(Activity context) {
        this.context = context;
    }

    @Override
    public void beforeLoading() {
        this.progressDialog = new ProgressDialog(context);
        this.progressDialog.setMessage("Retrieving the picture ...");
        this.progressDialog.show();
    }

    @Override
    public void loaded(Bitmap picture) {
        this.progressDialog.dismiss();
    }

    @Override
    public void failed() {
        progressDialog.dismiss();
        DialogUtils.showAndExit(context, "Unable to get the picture");
    }
}
