package ch.hesso.master.sweetcity.callback;

import android.graphics.Bitmap;

public interface PictureUploadCallback {
    /**
     * Before the picture is loaded.
     */
    public void beforeLoading();

    /**
     * The picture has been loaded.
     */
    public void loaded(Bitmap picture);

    /**
     * An error occurred.
     */
    public void failed();
}
