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
