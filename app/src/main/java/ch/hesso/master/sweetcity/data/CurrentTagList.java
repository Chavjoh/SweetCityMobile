package ch.hesso.master.sweetcity.data;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.hesso.master.sweetcity.Constants;
import ch.hesso.master.sweetcity.callback.TagCallback;
import ch.hesso.master.sweetcity.callback.TagCallbackImpl;
import ch.hesso.master.sweetcity.model.Tag;
import ch.hesso.master.sweetcity.task.GetTagListAsyncTask;
import ch.hesso.master.sweetcity.utils.AuthUtils;

public class CurrentTagList {

    private static CurrentTagList instance;

    private List<Tag> tagList;

    public static CurrentTagList getInstance() {
        if (instance == null) {
            instance = new CurrentTagList();
        }
        return instance;
    }

    public CurrentTagList() {
        this.tagList = new ArrayList<Tag>();
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

    public void load(Activity context) {
        TagCallback callback = new TagCallbackImpl(context);
        new GetTagListAsyncTask(context, callback, AuthUtils.getCredential());
    }

}
