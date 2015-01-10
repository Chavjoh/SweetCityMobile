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
import ch.hesso.master.sweetcity.callback.AccountCallback;
import ch.hesso.master.sweetcity.utils.ServiceUtils;
import ch.hesso.master.sweetcity.webservice.account.AccountService;

/**
 * @author Chavaillaz Johan
 * @author Burri Grégory
 * @author Frank Etienne
 * @version 1.0
 */
public class RegisterAsyncTask extends AsyncTask<String, Void, Void> {

    private Activity context;
    private AccountCallback callback;
    private GoogleAccountCredential credential;
    private String login;

    public RegisterAsyncTask(Activity context, AccountCallback callback, GoogleAccountCredential credential, String login) {
        this.context = context;
        this.callback = callback;
        this.credential = credential;
        this.login = login;
    }

    protected void onPreExecute(){
        super.onPreExecute();
        callback.beforeRegistration();
    }

    protected Void doInBackground(String... params) {

        try {
            Log.d(Constants.PROJECT_NAME, "[AccountService] Calling for registration ...");

            AccountService service = ServiceUtils.getAccountService(this.credential);
            service.accountService().register(this.login).execute();

            Log.d(Constants.PROJECT_NAME, "[AccountService] Registered");
        }
        catch (Exception e) {
            Log.d("[AccountService] Service unavailable : ", e.getMessage(), e);

            callback.failed();
        }

        return null;
    }

    protected void onPostExecute(Void nothing) {
        super.onPostExecute(nothing);
        callback.registered();
    }
}
