package ch.hesso.master.sweetcity.utils;

import android.util.Log;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import ch.hesso.master.sweetcity.Constants;

public class PictureUtils {

    private static AWSCredentials awsCredentials = new BasicAWSCredentials(Constants.AWS_ACCESS_KEY, Constants.AWS_SECRET_ACCESS_KEY);
    private final static String END_POINT = "s3-eu-west-1.amazonaws.com"; // See http://docs.aws.amazon.com/general/latest/gr/rande.html .
    private final static String BUCKET_NAME = "sweet-city";

    public static class Key {
        private String key;
        protected Key(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return this.key;
        }

        public static Key fromString(String str) {
            return new Key(str);
        }
    }

    public static Key uploadPicture(String localPath, GoogleAccountCredential googleCredential) {
        if (localPath == null)
            return null;

        try {
            ClientConfiguration clientConfig = new ClientConfiguration();
            clientConfig.setProtocol(Protocol.HTTP);
            AmazonS3 s3Connection = new AmazonS3Client(awsCredentials, clientConfig);
            s3Connection.setEndpoint(END_POINT); // http://docs.aws.amazon.com/general/latest/gr/rande.html

            InputStream localStreamPicture = new FileInputStream(localPath);
            ObjectMetadata pictureMetadata = new ObjectMetadata();

            String key = String.format("pictures/%s/img_%s.jpg", googleCredential.getSelectedAccountName(), new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss-SSSZ").format(new Date())); // %s/
            s3Connection.putObject(BUCKET_NAME, key, localStreamPicture, pictureMetadata);

            return new Key(key);
        } catch (Exception e) {
            Log.d(Constants.PROJECT_NAME, e.toString());
        }
        return null;
    }

    public static InputStream getPicture(Key key) {
        if (key == null)
            return null;

        try {
            ClientConfiguration clientConfig = new ClientConfiguration();
            clientConfig.setProtocol(Protocol.HTTP);
            AmazonS3 s3Connection = new AmazonS3Client(awsCredentials, clientConfig);
            s3Connection.setEndpoint(END_POINT);

            S3Object obj = s3Connection.getObject(BUCKET_NAME, key.toString());
            return obj.getObjectContent();
        } catch (Exception e) {
            Log.d(Constants.PROJECT_NAME, e.toString());
        }
        return null;
    }
}
