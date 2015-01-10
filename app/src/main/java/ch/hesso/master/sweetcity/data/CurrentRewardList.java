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
import java.util.List;

import ch.hesso.master.sweetcity.callback.RewardCallback;
import ch.hesso.master.sweetcity.callback.RewardCallbackImpl;
import ch.hesso.master.sweetcity.model.Reward;
import ch.hesso.master.sweetcity.task.GetRewardListAsyncTask;
import ch.hesso.master.sweetcity.utils.AuthUtils;

/**
 * @author Chavaillaz Johan
 * @author Burri Grégory
 * @author Frank Etienne
 * @version 1.0
 */
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
