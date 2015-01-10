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

package ch.hesso.master.sweetcity.utils;

import java.util.List;

import ch.hesso.master.sweetcity.model.Reward;
import ch.hesso.master.sweetcity.model.Tag;

/**
 * @author Chavaillaz Johan
 * @author Burri Grégory
 * @author Frank Etienne
 * @version 1.0
 */
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
