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

package ch.hesso.master.sweetcity.activity.tag;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import java.util.ArrayList;
import java.util.List;

import ch.hesso.master.sweetcity.model.Tag;
import ch.hesso.master.sweetcity.utils.LayoutUtils;

/**
 * @author Chavaillaz Johan
 * @author Burri Grégory
 * @author Frank Etienne
 * @version 1.0
 */
public class TagSelectionAdapter extends ArrayAdapter<Tag> {

    private static final Integer LAYOUT = android.R.layout.simple_list_item_multiple_choice;
    private final Activity context;
    private final ArrayList<Tag> tagList;

    private static class ViewHolder {
        public CheckedTextView ctvTag;
    }

    public TagSelectionAdapter(Activity context, List<Tag> tagList) {
        super(context, LAYOUT, tagList);

        this.context = context;
        this.tagList = new ArrayList<Tag>(tagList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        // Reuse views
        if (rowView == null) {
            LayoutInflater inflater = LayoutUtils.getInflater(context);
            rowView = inflater.inflate(LAYOUT, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.ctvTag = LayoutUtils.findView(rowView, android.R.id.text1);
            rowView.setTag(viewHolder);
        }

        // Fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();

        holder.ctvTag.setText(tagList.get(position).getName());
        holder.ctvTag.setTextColor(Color.BLACK);

        return rowView;
    }
}
