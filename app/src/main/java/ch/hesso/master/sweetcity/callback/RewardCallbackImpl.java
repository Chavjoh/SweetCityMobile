package ch.hesso.master.sweetcity.callback;

import android.app.Activity;
import android.app.ProgressDialog;

import java.util.List;

import ch.hesso.master.sweetcity.data.CurrentRewardList;
import ch.hesso.master.sweetcity.model.Reward;
import ch.hesso.master.sweetcity.utils.DialogUtils;

public class RewardCallbackImpl implements RewardCallback {

    protected Activity context;
    protected ProgressDialog progressDialog;

    public RewardCallbackImpl(Activity context) {
        this.context = context;
    }

    @Override
    public void beforeLoading() {
        progressDialog = DialogUtils.showProgressDialog(context, "Retrieving reward list ...");
    }

    @Override
    public void loaded(List<Reward> list) {
        DialogUtils.hideProgressDialog(progressDialog);

        if (list != null) {
            CurrentRewardList.getInstance().setList(list);
        }
    }

    @Override
    public void failed() {
        DialogUtils.hideProgressDialog(progressDialog);
        DialogUtils.showAndExit(context, "Service unavailable");
    }
}
