# SweetCityMobile
Mobile application for SweetCity project. MSE HES-SO 2014-2015

Library
-------
This project use the following libraries :

* Google Play Services
* [Amazon Web Services SDK](https://github.com/aws/aws-sdk-android)
* [Materialish Progress](https://github.com/pnikosis/materialish-progress)
* [Progress Menu Item](https://github.com/hotchemi/ProgressMenuItem)
* [Number Progress Bar](https://github.com/daimajia/NumberProgressBar)

Project constants
-----------------
You have to modify the class **ch.hesso.master.sweetcity.Constants** :

```java
public static final String PROJECT_NAME = "SweetCity";
public static final String WEB_CLIENT_ID = "";
public static final String AUDIENCE = "server:client_id:" + WEB_CLIENT_ID;
public static final JsonFactory JSON_FACTORY = new AndroidJsonFactory();
public static final HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();

public static final SimpleDateFormat DATE_FORMAT =
        new SimpleDateFormat("dd.MM.yyyy HH:mm");
public static final SimpleDateFormat DATE_FORMAT_IMAGE =
        new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss-SSSZ");
```

And the class **ch.hesso.master.sweetcity.ConstantsAWS** :
```java
public static final String AWS_ACCESS_KEY = "";
public static final String AWS_SECRET_ACCESS_KEY = "";

public static final String S3_PICTURE_NAME_FORMAT = "pictures/%s/img_%s.jpg";
public static final String S3_END_POINT = "s3-eu-west-1.amazonaws.com";
public static final String S3_BUCKET_NAME = "sweet-city";
```

Complete the missing fields in the codes below.