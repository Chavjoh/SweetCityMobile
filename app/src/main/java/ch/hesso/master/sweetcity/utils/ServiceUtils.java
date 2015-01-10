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

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Nullable;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import ch.hesso.master.sweetcity.Constants;
import ch.hesso.master.sweetcity.webservice.account.AccountService;
import ch.hesso.master.sweetcity.webservice.report.ReportService;
import ch.hesso.master.sweetcity.webservice.reward.RewardService;
import ch.hesso.master.sweetcity.webservice.tag.TagService;
import ch.hesso.master.sweetcity.webservice.vote.VoteService;

/**
 * @author Chavaillaz Johan
 * @author Burri Grégory
 * @author Frank Etienne
 * @version 1.0
 */
public class ServiceUtils {

    public static int countGoogleAccounts(Context context) {
        AccountManager am = AccountManager.get(context);
        Account[] accounts = am.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);

        if (accounts == null || accounts.length < 1) {
            return 0;
        } else {
            return accounts.length;
        }
    }

    public static boolean checkGooglePlayServicesAvailable(Activity activity) {
        final int connectionStatusCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);

        if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(activity, connectionStatusCode);
            return false;
        }

        return true;
    }

    public static void showGooglePlayServicesAvailabilityErrorDialog(final Activity activity,
                                                                     final int connectionStatusCode) {
        final int REQUEST_GOOGLE_PLAY_SERVICES = 0;

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
                        connectionStatusCode, activity, REQUEST_GOOGLE_PLAY_SERVICES);
                dialog.show();
            }
        });
    }

    public static AccountService getAccountService(@Nullable GoogleAccountCredential credential) {
        AccountService.Builder service = new AccountService.Builder(
                Constants.HTTP_TRANSPORT,
                Constants.JSON_FACTORY,
                credential);
        return service.build();
    }

    public static ReportService getReportService(@Nullable GoogleAccountCredential credential) {
        ReportService.Builder service = new ReportService.Builder(
                Constants.HTTP_TRANSPORT,
                Constants.JSON_FACTORY,
                credential);
        return service.build();
    }

    public static RewardService getRewardService(@Nullable GoogleAccountCredential credential) {
        RewardService.Builder service = new RewardService.Builder(
                Constants.HTTP_TRANSPORT,
                Constants.JSON_FACTORY,
                credential);
        return service.build();
    }

    public static TagService getTagService(@Nullable GoogleAccountCredential credential) {
        TagService.Builder service = new TagService.Builder(
                Constants.HTTP_TRANSPORT,
                Constants.JSON_FACTORY,
                credential);
        return service.build();
    }

    public static VoteService getVoteService(@Nullable GoogleAccountCredential credential) {
        VoteService.Builder service = new VoteService.Builder(
                Constants.HTTP_TRANSPORT,
                Constants.JSON_FACTORY,
                credential);
        return service.build();
    }

}
