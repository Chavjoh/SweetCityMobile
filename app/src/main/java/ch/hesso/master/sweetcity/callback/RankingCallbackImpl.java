package ch.hesso.master.sweetcity.callback;

import android.app.Activity;
import android.app.ProgressDialog;

import java.util.List;

import ch.hesso.master.sweetcity.data.CurrentRankingList;
import ch.hesso.master.sweetcity.model.Account;
import ch.hesso.master.sweetcity.utils.DialogUtils;

public class RankingCallbackImpl implements RankingCallback {

    protected Activity context;
    protected ProgressDialog progressDialog;

    public RankingCallbackImpl(Activity context) {
        this.context = context;
    }

    @Override
    public void beforeLoading() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Retrieving ranking ...");
        progressDialog.show();
    }

    @Override
    public void loaded(List<Account> list) {
        progressDialog.dismiss();

        if (list != null) {
            CurrentRankingList.getInstance().setList(list);
        }
    }

    @Override
    public void failed() {
        progressDialog.dismiss();
        DialogUtils.showAndExit(context, "Service unavailable");
    }
}
