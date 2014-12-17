package ch.hesso.master.sweetcity.activity.reward;

import android.app.Activity;
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

        // Reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(LAYOUT, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tvTitle = LayoutUtils.findView(rowView, R.id.tv_title);
            viewHolder.tvDescription = LayoutUtils.findView(rowView, R.id.tv_description);
            viewHolder.ivIcon = LayoutUtils.findView(rowView, R.id.iv_icon);
            rowView.setTag(viewHolder);
        }

        // Fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();

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