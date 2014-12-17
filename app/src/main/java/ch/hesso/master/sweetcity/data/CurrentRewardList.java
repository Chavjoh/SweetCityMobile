package ch.hesso.master.sweetcity.data;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import ch.hesso.master.sweetcity.callback.RewardCallback;
import ch.hesso.master.sweetcity.callback.RewardCallbackImpl;
import ch.hesso.master.sweetcity.model.Reward;
import ch.hesso.master.sweetcity.task.GetRewardListAsyncTask;
import ch.hesso.master.sweetcity.utils.AuthUtils;

public class CurrentRewardList {

    private static CurrentRewardList instance;

    private List<Reward> rewardList;

    public static CurrentRewardList getInstance() {
        if (instance == null) {
            instance = new CurrentRewardList();
        }
        return instance;
    }

    private CurrentRewardList() {
        this.rewardList = new ArrayList<Reward>();
    }

    public void setList(List<Reward> rewardList) {
        if (rewardList != null) {
            this.rewardList = rewardList;
        }
    }

    public List<Reward> getList() {
        return this.rewardList;
    }

    public void load(Activity context) {
        RewardCallback callback = new RewardCallbackImpl(context);
        load(context, callback);
    }

    public void load(Activity context, RewardCallback callback) {
        new GetRewardListAsyncTask(context, callback, AuthUtils.getCredential()).execute();
    }

    public Integer getPosition(Reward reward) {
        return rewardList.indexOf(reward);
    }

    public Reward get(Integer position) {
        return rewardList.get(position);
    }

    public boolean isEmpty() {
        return rewardList.isEmpty();
    }
}
