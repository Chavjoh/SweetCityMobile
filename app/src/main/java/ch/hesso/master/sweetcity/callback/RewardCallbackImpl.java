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

    }

    @Override
    public void loaded(List<Reward> list) {
        if (list != null) {
            CurrentRewardList.getInstance().setList(list);
        }
    }

    @Override
    public void failed() {
        DialogUtils.showAndExit(context, "Service unavailable");
    }
}
