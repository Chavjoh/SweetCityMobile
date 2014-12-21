package ch.hesso.master.sweetcity.data;

import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.hesso.master.sweetcity.callback.RankingCallback;
import ch.hesso.master.sweetcity.callback.RankingCallbackImpl;
import ch.hesso.master.sweetcity.callback.TagCallback;
import ch.hesso.master.sweetcity.callback.TagCallbackImpl;
import ch.hesso.master.sweetcity.model.Account;
import ch.hesso.master.sweetcity.model.Tag;
import ch.hesso.master.sweetcity.task.GetRankingAsyncTask;
import ch.hesso.master.sweetcity.task.GetTagListAsyncTask;
import ch.hesso.master.sweetcity.utils.AuthUtils;

public class CurrentRankingList {

    private static CurrentRankingList instance;

    private List<Account> accountList;

    public static CurrentRankingList getInstance() {
        if (instance == null) {
            instance = new CurrentRankingList();
        }
        return instance;
    }

    private CurrentRankingList() {
        this.accountList = new ArrayList<Account>();
    }

    public void load(Activity context) {
        RankingCallback callback = new RankingCallbackImpl(context);
        load(context, callback);
    }

    public void load(Activity context, RankingCallback callback) {
        new GetRankingAsyncTask(context, callback, AuthUtils.getCredential()).execute();
    }

    public void setList(List<Account> accountList) {
        if (accountList != null) {
            this.accountList = accountList;
        }
    }

    public int getPosition(Tag tag) {
        return this.accountList.indexOf(tag);
    }

    public Account get(Integer position) {
        return this.accountList.get(position);
    }

    public List<Account> getList() {
        return this.accountList;
    }

    public boolean isEmpty() {
        return accountList.isEmpty();
    }
}
