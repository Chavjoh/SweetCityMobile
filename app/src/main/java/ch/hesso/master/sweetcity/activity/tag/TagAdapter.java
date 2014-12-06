package ch.hesso.master.sweetcity.activity.tag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import java.util.ArrayList;
import java.util.List;

import ch.hesso.master.sweetcity.model.Tag;

public class TagAdapter extends ArrayAdapter<Tag> {

    private final Context context;
    private final ArrayList<Tag> tagList;

    public TagAdapter(Context context, List<Tag> tagList) {
        super(context, android.R.layout.simple_list_item_multiple_choice, tagList);

        this.context = context;
        this.tagList = new ArrayList<Tag>(tagList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(android.R.layout.simple_list_item_multiple_choice, parent, false);

        CheckedTextView checkedTextView = (CheckedTextView) rowView.findViewById(android.R.id.text1);
        checkedTextView.setText(tagList.get(position).getName());

        return rowView;
    }
}
