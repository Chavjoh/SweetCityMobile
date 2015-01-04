package ch.hesso.master.sweetcity.utils;

import ch.hesso.master.sweetcity.model.Reward;

public class ModelUtils {

    public static boolean hasReward(Reward reward) {
        // Generated model don't have equals comparison ...
        if (AuthUtils.getAccount().getListReward() != null) {
            for (Reward current:AuthUtils.getAccount().getListReward()) {
                if (current.getId().equals(reward.getId())) {
                    return true;
                }
            }
        }

        return false;
    }

}