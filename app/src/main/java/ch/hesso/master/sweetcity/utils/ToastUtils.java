package ch.hesso.master.sweetcity.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    public static Toast show(Context context, String message) {
        return show(context, message, false);
    }

    public static Toast show(Context context, String message, boolean longLength) {
        int length = (longLength) ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
        final Toast toast = Toast.makeText(context, message, length);
        toast.show();
        return toast;
    }
}
