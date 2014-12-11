package ch.hesso.master.sweetcity.utils;

import android.app.Activity;
import android.content.Intent;

public class ProjectUtils {

    public static void startActivityWithoutBackStack(Activity context, Class<? extends Activity> activity) {
        Intent intent = new Intent(context, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        context.finish();
    }

}
