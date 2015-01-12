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

package ch.hesso.master.sweetcity.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import ch.hesso.master.sweetcity.Constants;
import ch.hesso.master.sweetcity.callback.AccountCallback;
import ch.hesso.master.sweetcity.callback.AccountCallbackImpl;
import ch.hesso.master.sweetcity.model.Account;

/**
 * @author Chavaillaz Johan
 * @author Burri Grégory
 * @author Frank Etienne
 * @version 1.0
 */
public class AuthUtils {

    public static final int REQUEST_ACCOUNT_PICKER = 2;
    private static final String KEY_ACCOUNT_NAME = "ACCOUNT_NAME";

    private static AuthUtils instance;
    private static GoogleAccountCredential credential;
    private static Account account;

    private AccountCallbackImpl callbackAccount;
    private SharedPreferences settings;
    private Activity context;

    public static AuthUtils getInstance(Activity context) {
        if (!isInstantiate()) {
            instance = new AuthUtils(context);
        } else {
            instance.setContext(context);
        }
        return instance;
    }

    private AuthUtils(Activity context) {
        this.callbackAccount = new AccountCallbackImpl(context);
        this.settings = context.getSharedPreferences(Constants.PROJECT_NAME, 0);
        this.setContext(context);

        credential = GoogleAccountCredential.usingAudience(context, Constants.AUDIENCE);
        saveAccountName(getAccountName());
    }

    public void setContext(Activity context) {
        this.context = context;
        this.callbackAccount.setContext(context);
    }

    public void checkAccount() {
        setAccountName(getAccountName());

        if (credential.getSelectedAccountName() == null) {
            chooseAccount();
        }
    }

    public void setAccountName(String accountName) {
        saveAccountName(accountName);

        if (accountName != null) {
            this.callbackAccount.selected();
        }
    }

    private void saveAccountName(String accountName) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_ACCOUNT_NAME, accountName);
        editor.commit();

        credential.setSelectedAccountName(accountName);
    }

    private void chooseAccount() {
        this.context.startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
    }

    public AccountCallback getCallback() {
        return this.callbackAccount;
    }

    public String getAccountName() {
        return settings.getString(KEY_ACCOUNT_NAME, null);
    }

    public static GoogleAccountCredential getCredential() {
        return credential;
    }

    public static boolean isInstantiate() {
        return instance != null;
    }

    public static void setAccount(Account newAccount) {
        account = newAccount;
    }

    public static Account getAccount() {
        return account;
    }

    public static boolean isLogged() {
        return account != null;
    }
}
