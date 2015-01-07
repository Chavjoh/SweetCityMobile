package ch.hesso.master.sweetcity.callback;

import android.app.Activity;
import android.graphics.Bitmap;

import ch.hesso.master.sweetcity.utils.DialogUtils;

public class PictureUploadCallbackImpl implements PictureUploadCallback {

    protected Activity context;

    public PictureUploadCallbackImpl(Activity context) {
        this.context = context;
    }

    @Override
    public void beforeLoading() {

    }

    @Override
    public void loaded(Bitmap picture) {

    }

    @Override
    public void failed() {
        DialogUtils.showAndExit(context, "Unable to get the picture");
    }
}
