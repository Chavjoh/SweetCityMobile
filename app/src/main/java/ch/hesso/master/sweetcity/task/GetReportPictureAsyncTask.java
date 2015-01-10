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

package ch.hesso.master.sweetcity.task;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import ch.hesso.master.sweetcity.callback.PictureUploadCallback;
import ch.hesso.master.sweetcity.utils.PictureUtils;

/**
 * @author Chavaillaz Johan
 * @author Burri Grégory
 * @author Frank Etienne
 * @version 1.0
 */
public class GetReportPictureAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private Activity context;
    private PictureUploadCallback callback;
    private PictureUtils.Key key;

    public GetReportPictureAsyncTask(Activity context, PictureUploadCallback callback, PictureUtils.Key key) {
        this.context = context;
        this.callback = callback;
        this.key = key;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.callback.beforeLoading();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            return BitmapFactory.decodeStream(PictureUtils.getPicture(this.key));
        } catch (Exception e) {
            Log.d("[ReportService] Unable to get the picture : ", e.getMessage(), e);
            callback.failed();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap picture) {
        super.onPostExecute(null);

        if (picture != null)
            this.callback.loaded(picture);
    }
}