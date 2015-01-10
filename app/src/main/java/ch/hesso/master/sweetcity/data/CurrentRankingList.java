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

/**
 * @author Chavaillaz Johan
 * @author Burri Grégory
 * @author Frank Etienne
 * @version 1.0
 */
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
