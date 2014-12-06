package ch.hesso.master.sweetcity.callback;

import android.app.Activity;
import android.content.Intent;

import ch.hesso.master.sweetcity.activity.map.MapActivity;
import ch.hesso.master.sweetcity.activity.welcome.RegisterActivity;
import ch.hesso.master.sweetcity.model.Account;
import ch.hesso.master.sweetcity.task.GetAccountAsyncTask;
import ch.hesso.master.sweetcity.utils.AuthUtils;
import ch.hesso.master.sweetcity.utils.DialogUtils;
import ch.hesso.master.sweetcity.utils.ToastUtils;

public class AccountCallbackImpl implements AccountCallback {

    public Activity context;
    public boolean isFailed;

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
        if (account != null) {
            AuthUtils.setAccount(account);
            ToastUtils.show(context, "Logged as " + account.getPseudo());
            Intent i = new Intent(context, MapActivity.class);
            context.startActivity(i);
        } else if (isFailed) {
            DialogUtils.showAndExit(context, "Service unavailable");
        } else {
            Intent i = new Intent(context, RegisterActivity.class);
            context.startActivity(i);
        }
    }

    @Override
    public void failed() {
        isFailed = true;
    }

    public void setContext(Activity context) {
        this.context = context;
    }
}
