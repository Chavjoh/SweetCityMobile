package ch.hesso.master.sweetcity.activity.ranking;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ch.hesso.master.sweetcity.R;
import ch.hesso.master.sweetcity.model.Account;
import ch.hesso.master.sweetcity.utils.LayoutUtils;

public class RankingAdapter extends ArrayAdapter<Account> {

    private static final Integer LAYOUT = R.layout.list_row_ranking;
    private final Activity context;
    private final List<Account> values;

    private static class ViewHolder {
        public TextView tvRanking;
        public TextView tvAccount;
        public TextView tvPoints;
    }

    public RankingAdapter(Activity context, List<Account> values) {
        super(context, LAYOUT, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        RankingAdapter.ViewHolder holder;

        // Reuse views
        if (rowView == null) {
            LayoutInflater inflater = LayoutUtils.getInflater(context);
            rowView = inflater.inflate(LAYOUT, parent, false);

            holder = new RankingAdapter.ViewHolder();
            holder.tvRanking = LayoutUtils.findView(rowView, R.id.tv_ranking);
            holder.tvAccount = LayoutUtils.findView(rowView, R.id.tv_account);
            holder.tvPoints = LayoutUtils.findView(rowView, R.id.tv_points);
            rowView.setTag(holder);
        }

        // Fill data
        holder = (RankingAdapter.ViewHolder) rowView.getTag();

        Account account = values.get(position);
        holder.tvRanking.setText(String.valueOf(position + 1));
        holder.tvAccount.setText(account.getPseudo());
        holder.tvPoints.setText(account.getPoints() + " points");

        holder.tvRanking.setTextColor(this.context.getResources().getColor(R.color.dark_red));

        return rowView;
    }

}