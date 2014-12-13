package ch.hesso.master.sweetcity.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import ch.hesso.master.sweetcity.activity.map.MapActivity;
import ch.hesso.master.sweetcity.activity.welcome.RegisterActivity;
import ch.hesso.master.sweetcity.model.Account;
import ch.hesso.master.sweetcity.task.GetAccountAsyncTask;
import ch.hesso.master.sweetcity.utils.AuthUtils;
import ch.hesso.master.sweetcity.utils.DialogUtils;
import ch.hesso.master.sweetcity.utils.ProjectUtils;
import ch.hesso.master.sweetcity.utils.ToastUtils;

public class AccountCallbackImpl implements AccountCallback {

    protected ProgressDialog progressDialog;
    public Activity context;
    public boolean isFailed;

    public AccountCallbackImpl(Activity context) {
        this.context = context;
    }

    @Override
    public void beforeRegistration() {
        isFailed = false;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Registering ...");
        progressDialog.show();
    }

    @Override
    public void registered() {
        if (isFailed) {
            progressDialog.dismiss();
            DialogUtils.show(context, "Service unavailable");
        } else {
            context.finish();
        }
    }

    @Override
    public void selected() {
        isFailed = false;
        new GetAccountAsyncTask(context, this, AuthUtils.getCredential()).execute();
    }

    @Override
    public void loaded(Account account) {
        if (account != null) {
            AuthUtils.setAccount(account);
            ToastUtils.show(context, "Logged as " + account.getPseudo());
            ProjectUtils.startActivityWithoutBackStack(context, MapActivity.class);
        } else if (isFailed) {
            DialogUtils.showAndExit(context, "Service unavailable");
        } else {
            Intent intent = new Intent(context, RegisterActivity.class);
            context.startActivity(intent);
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
