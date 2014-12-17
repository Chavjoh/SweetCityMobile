package ch.hesso.master.sweetcity.activity.tag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ch.hesso.master.sweetcity.R;
import ch.hesso.master.sweetcity.callback.TagCallbackImpl;
import ch.hesso.master.sweetcity.data.CurrentTagList;
import ch.hesso.master.sweetcity.model.Tag;

public class TagSelectionActivity extends Activity implements View.OnClickListener {

    private Button button;
    private ListView listView;
    private TagSelectionAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_selection);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        findViews();
        loadList();

        button.setOnClickListener(this);
    }

    private void findViews() {
        listView = (ListView) findViewById(R.id.lv_tag);
        button = (Button) findViewById(R.id.btn_submit);
    }

    private void loadList() {
        if (CurrentTagList.getInstance().isEmpty()) {
            TagCallbackActivity callback = new TagCallbackActivity(this);
            CurrentTagList.getInstance().load(this, callback);
        } else {
            showList();
        }
    }

    private void showList(List<Tag> list) {
        adapter = new TagSelectionAdapter(this, list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void showList() {
        showList(CurrentTagList.getInstance().getList());
    }

    @Override
    public void onClick(View v) {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        ArrayList<Integer> selectedItems = new ArrayList<Integer>();
        for (int i = 0; i < checked.size(); i++) {
            int position = checked.keyAt(i);

            if (checked.valueAt(i)) {
                selectedItems.add(CurrentTagList.getInstance().getPosition(adapter.getItem(position)));
            }
        }

        // Return tag selection list to the parent activity
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("selectedItems", selectedItems);
        resultIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tag_selection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Tag callback with display of the tag list on the view
     */
    private class TagCallbackActivity extends TagCallbackImpl {

        public TagCallbackActivity(Activity context) {
            super(context);
        }

        @Override
        public void loaded(List<Tag> list) {
            super.loaded(list);
            showList(list);
        }
    }
}
