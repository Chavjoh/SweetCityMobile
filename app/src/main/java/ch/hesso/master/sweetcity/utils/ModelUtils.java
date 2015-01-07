package ch.hesso.master.sweetcity.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

import ch.hesso.master.sweetcity.model.Reward;
import ch.hesso.master.sweetcity.model.Tag;

public class ModelUtils {

    public static boolean hasReward(Reward reward) {
        if (reward == null)
            return false;

        // Generated model don't have equals comparison ...
        if (AuthUtils.getAccount().getListReward() != null) {
            for (Reward current:AuthUtils.getAccount().getListReward()) {
                if (current != null && current.getId().equals(reward.getId())) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String listToString(List<Tag> listTag) {
        StringBuilder sbTags = new StringBuilder();

        if (listTag == null)
            return "-";

        for (Tag tag : listTag)
            sbTags.append((sbTags.length() > 0 ? ", " : "") + tag.getName());

        return sbTags.length() == 0 ? "-" : sbTags.toString();
    }
}
