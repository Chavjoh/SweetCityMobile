package ch.hesso.master.sweetcity.activity.reward;

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
import ch.hesso.master.sweetcity.utils.AuthUtils;
import ch.hesso.master.sweetcity.utils.ModelUtils;

public class RewardAdapter extends ArrayAdapter<Reward> {

    private final Context context;
    private final List<Reward> values;

    public RewardAdapter(Context context, List<Reward> values) {
        super(context, R.layout.list_row_reward, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_row_reward, parent, false);

        TextView tvTitle = (TextView) rowView.findViewById(R.id.tv_title);
        TextView tvDescription = (TextView) rowView.findViewById(R.id.tv_description);
        ImageView ivIcon = (ImageView) rowView.findViewById(R.id.iv_icon);

        Reward reward = values.get(position);
        tvTitle.setText(reward.getName());
        tvDescription.setText(reward.getDescription());

        switch (RewardType.valueOf(reward.getType())) {
            case GOLD:
                ivIcon.setImageResource(R.drawable.medal_gold);
                break;

            case SILVER:
                ivIcon.setImageResource(R.drawable.medal_silver);
                break;

            case BRONZE:
                ivIcon.setImageResource(R.drawable.medal_bronze);
                break;
        }

        if (ModelUtils.hasReward(reward)) {
            tvTitle.setAlpha(1.0f);
            tvDescription.setAlpha(1.0f);
            ivIcon.setAlpha(1.0f);
        } else {
            tvTitle.setAlpha(0.3f);
            tvDescription.setAlpha(0.3f);
            ivIcon.setAlpha(0.3f);
        }

        return rowView;
    }

}