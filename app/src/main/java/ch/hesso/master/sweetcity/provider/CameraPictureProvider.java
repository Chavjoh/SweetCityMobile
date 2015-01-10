/*
* Copyright 2014-2015 University of Applied Sciences and Arts Western Switzerland
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package ch.hesso.master.sweetcity.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * @author Chavaillaz Johan
 * @author Burri Grégory
 * @author Frank Etienne
 * @version 1.0
 */
public class CameraPictureProvider extends ContentProvider {

    public static final Uri CONTENT_URI =
            Uri.parse("content://ch.hesso.master.sweetcity.cameraimageprovider/");
    public static final String PICTURE_FILE = "picture.jpg";
    private static final HashMap<String, String> MIME_TYPES = new HashMap<String, String>();

    static {
        MIME_TYPES.put(".jpg", "image/jpeg");
        MIME_TYPES.put(".jpeg", "image/jpeg");
    }

    @Override
    public boolean onCreate() {
        try {
            File mFile = new File(getContext().getFilesDir(), PICTURE_FILE);

            if(!mFile.exists()) {
                mFile.createNewFile();
            }

            getContext().getContentResolver().notifyChange(CONTENT_URI, null);

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getType(Uri uri) {
        String path = uri.toString();

        for (String extension : MIME_TYPES.keySet()) {
            if (path.endsWith(extension)) {
                return (MIME_TYPES.get(extension));
            }
        }

        return null;
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        File f = new File(getContext().getFilesDir(), PICTURE_FILE);

        if (f.exists()) {
            return ParcelFileDescriptor.open(f, ParcelFileDescriptor.MODE_READ_WRITE);
        }

        throw new FileNotFoundException(uri.getPath());
    }

    @Override
    public Cursor query(Uri url, String[] projection, String selection, String[] selectionArgs, String sort) {
        throw new RuntimeException("Operation not supported");
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        throw new RuntimeException("Operation not supported");
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        throw new RuntimeException("Operation not supported");
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        throw new RuntimeException("Operation not supported");
    }

}
