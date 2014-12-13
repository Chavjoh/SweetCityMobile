package ch.hesso.master.sweetcity.utils;

import android.widget.EditText;

public class LayoutUtils {

    public static boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

}
