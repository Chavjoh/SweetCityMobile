package ch.hesso.master.sweetcity.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;

import ch.hesso.master.sweetcity.utils.DialogUtils;
import ch.hesso.master.sweetcity.activity.report.ShowReportActivity;

public class PictureUploadCallbackImpl implements PictureUploadCallback {

    protected ShowReportActivity context;
    protected ProgressDialog progressDialog;

    public PictureUploadCallbackImpl(ShowReportActivity context) {
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

        if (picture != null) {
            this.context.setPicture(picture);
        }
    }

    @Override
    public void failed() {
        progressDialog.dismiss();
        DialogUtils.showAndExit(context, "Unable to get the picture");
    }
}
