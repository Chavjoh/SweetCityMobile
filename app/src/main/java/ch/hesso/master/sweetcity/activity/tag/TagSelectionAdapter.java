package ch.hesso.master.sweetcity.activity.tag;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import java.util.ArrayList;
import java.util.List;

import ch.hesso.master.sweetcity.R;
import ch.hesso.master.sweetcity.model.Tag;
import ch.hesso.master.sweetcity.utils.LayoutUtils;

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
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(LAYOUT, null);

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
