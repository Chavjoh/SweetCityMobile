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
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import ch.hesso.master.sweetcity.Constants;
import ch.hesso.master.sweetcity.callback.TagCallback;
import ch.hesso.master.sweetcity.model.TagCollection;
import ch.hesso.master.sweetcity.utils.ServiceUtils;
import ch.hesso.master.sweetcity.webservice.tag.TagService;

/**
 * @author Chavaillaz Johan
 * @author Burri Grégory
 * @author Frank Etienne
 * @version 1.0
 */
public class GetTagListAsyncTask extends AsyncTask<String, Void, TagCollection> {

    private Activity context;
    private TagCallback callback;
    private GoogleAccountCredential credential;

    public GetTagListAsyncTask(Activity context, TagCallback callback, GoogleAccountCredential credential) {
        this.context = context;
        this.callback = callback;
        this.credential = credential;
    }

    protected void onPreExecute(){
        super.onPreExecute();
        callback.beforeLoading();
    }

    protected TagCollection doInBackground(String... params) {
        TagCollection response = null;

        try {
            Log.d(Constants.PROJECT_NAME, "[TagService] Calling ...");

            TagService service = ServiceUtils.getTagService(credential);
            response = service.tagService().getTagList().execute();

            Log.d(Constants.PROJECT_NAME, "[TagService] Response received");
        }
        catch (Exception e) {
            Log.d("[TagService] Service unavailable : ", e.getMessage(), e);

            callback.failed();
        }

        return response;
    }

    protected void onPostExecute(TagCollection tagCollection) {
        super.onPostExecute(tagCollection);

        if (tagCollection != null) {
            callback.loaded(tagCollection.getItems());
        }
    }
}
