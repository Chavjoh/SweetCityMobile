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

/**
 * @author Chavaillaz Johan
 * @author Burri Grégory
 * @author Frank Etienne
 * @version 1.0
 */
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