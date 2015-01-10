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

import ch.hesso.master.sweetcity.callback.TagCallback;
import ch.hesso.master.sweetcity.callback.TagCallbackImpl;
import ch.hesso.master.sweetcity.model.Tag;
import ch.hesso.master.sweetcity.task.GetTagListAsyncTask;
import ch.hesso.master.sweetcity.utils.AuthUtils;

/**
 * @author Chavaillaz Johan
 * @author Burri Grégory
 * @author Frank Etienne
 * @version 1.0
 */
public class CurrentTagList {

    private static CurrentTagList instance;

    private List<Tag> tagList;

    public static CurrentTagList getInstance() {
        if (instance == null) {
            instance = new CurrentTagList();
        }
        return instance;
    }

    private CurrentTagList() {
        this.tagList = new ArrayList<Tag>();
    }

    public void load(Activity context) {
        TagCallback callback = new TagCallbackImpl(context);
        load(context, callback);
    }

    public void load(Activity context, TagCallback callback) {
        new GetTagListAsyncTask(context, callback, AuthUtils.getCredential()).execute();
    }

    public void setList(List<Tag> tagList) {
        if (tagList != null) {
            this.tagList = tagList;
        }
    }

    public int getPosition(Tag tag) {
        return this.tagList.indexOf(tag);
    }

    public Tag get(Integer position) {
        return this.tagList.get(position);
    }

    public void putAllFromPositions(HashMap<Integer, Tag> map, ArrayList<Integer> positionList) {
        for (Integer position:positionList) {
            map.put(position, CurrentTagList.getInstance().get(position));
        }
    }

    public List<Tag> getList() {
        return this.tagList;
    }

    public boolean isEmpty() {
        return tagList.isEmpty();
    }
}
