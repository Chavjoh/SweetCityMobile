package ch.hesso.master.sweetcity;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;

import java.text.SimpleDateFormat;

public class Constants {

    public static final String PROJECT_NAME = "SweetCity";

    public static final String WEB_CLIENT_ID =
            "949477582144-vk2bci1ra92qpf1nmqoog7op4da8vmiv.apps.googleusercontent.com";

    public static final String AUDIENCE = "server:client_id:" + WEB_CLIENT_ID;

    public static final JsonFactory JSON_FACTORY = new AndroidJsonFactory();

    public static final HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();

    public static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("dd.MM.yyyy HH:mm");
    public static final SimpleDateFormat DATE_FORMAT_IMAGE =
            new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss-SSSZ");

}
