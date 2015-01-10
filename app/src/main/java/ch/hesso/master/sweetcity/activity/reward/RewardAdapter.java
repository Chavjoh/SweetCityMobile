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

package ch.hesso.master.sweetcity.activity.reward;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ch.hesso.master.sweetcity.R;
import ch.hesso.master.sweetcity.model.Reward;
import ch.hesso.master.sweetcity.model.RewardType;
import ch.hesso.master.sweetcity.utils.LayoutUtils;
import ch.hesso.master.sweetcity.utils.ModelUtils;

/**
 * @author Chavaillaz Johan
 * @author Burri Grégory
 * @author Frank Etienne
 * @version 1.0
 */
public class RewardAdapter extends ArrayAdapter<Reward> {

    private static final Integer LAYOUT = R.layout.list_row_reward;
    private final Activity context;
    private final List<Reward> values;

    private static class ViewHolder {
        public TextView tvTitle;
        public TextView tvDescription;
        public ImageView ivIcon;
    }

    public RewardAdapter(Activity context, List<Reward> values) {
        super(context, LAYOUT, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        RewardAdapter.ViewHolder holder;

        // Reuse views
        if (rowView == null) {
            LayoutInflater inflater = LayoutUtils.getInflater(context);
            rowView = inflater.inflate(LAYOUT, parent, false);

            holder = new RewardAdapter.ViewHolder();
            holder.tvTitle = LayoutUtils.findView(rowView, R.id.tv_title);
            holder.tvDescription = LayoutUtils.findView(rowView, R.id.tv_description);
            holder.ivIcon = LayoutUtils.findView(rowView, R.id.iv_icon);
            rowView.setTag(holder);
        }

        // Fill data
        holder = (RewardAdapter.ViewHolder) rowView.getTag();

        Reward reward = values.get(position);
        holder.tvTitle.setText(reward.getName());
        holder.tvDescription.setText(reward.getDescription());

        switch (RewardType.valueOf(reward.getType())) {
            case GOLD:
                holder.ivIcon.setImageResource(R.drawable.medal_gold);
                break;

            case SILVER:
                holder.ivIcon.setImageResource(R.drawable.medal_silver);
                break;

            case BRONZE:
                holder.ivIcon.setImageResource(R.drawable.medal_bronze);
                break;
        }

        if (ModelUtils.hasReward(reward)) {
            holder.tvTitle.setAlpha(1.0f);
            holder.tvDescription.setAlpha(1.0f);
            holder.ivIcon.setAlpha(1.0f);
        } else {
            holder.tvTitle.setAlpha(0.3f);
            holder.tvDescription.setAlpha(0.3f);
            holder.ivIcon.setAlpha(0.3f);
        }

        return rowView;
    }

}