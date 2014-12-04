package ch.hesso.master.sweetcity.callback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ch.hesso.master.sweetcity.Constants;
import ch.hesso.master.sweetcity.MapsActivity;
import ch.hesso.master.sweetcity.RegisterActivity;
import ch.hesso.master.sweetcity.model.Account;
import ch.hesso.master.sweetcity.task.GetAccountAsyncTask;
import ch.hesso.master.sweetcity.utils.AuthUtils;
import ch.hesso.master.sweetcity.utils.DialogUtils;
import ch.hesso.master.sweetcity.utils.ToastUtils;

public class AccountCallbackImpl implements AccountCallback {

    public Activity context;

    public AccountCallbackImpl(Activity context) {
        this.context = context;
    }

    @Override
    public void selected() {
        new GetAccountAsyncTask(
                context,
                this,
                AuthUtils.getCredential()
        ).execute();
    }

    @Override
    public void loaded(Account account) {
        if (account == null) {
            Intent i = new Intent(context, RegisterActivity.class);
            context.startActivity(i);
        } else {
            ToastUtils.show(context, "Logged as " + account.getPseudo());

            Intent i = new Intent(context, MapsActivity.class);
            context.startActivity(i);
        }
    }

    @Override
    public void failed() {
        DialogUtils.show(context, "Service unavailable");
    }

    public void setContext(Activity context) {
        this.context = context;
    }
}
