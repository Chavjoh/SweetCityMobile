package ch.hesso.master.sweetcity.activity.reward;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

import ch.hesso.master.sweetcity.R;
import ch.hesso.master.sweetcity.callback.RewardCallbackImpl;
import ch.hesso.master.sweetcity.data.CurrentRewardList;
import ch.hesso.master.sweetcity.data.CurrentTagList;
import ch.hesso.master.sweetcity.model.Reward;
import ch.hesso.master.sweetcity.task.GetRewardListAsyncTask;
import ch.hesso.master.sweetcity.utils.AuthUtils;

public class RewardActivity extends Activity {

    private ListView listView;
    private RewardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        findViewsById();
        loadTagList();
    }

    private void findViewsById() {
        listView = (ListView) findViewById(R.id.lv_reward);
    }

    private void loadTagList() {
        if (CurrentRewardList.getInstance().isEmpty()) {
            RewardCallbackActivity callback = new RewardCallbackActivity(this);
            new GetRewardListAsyncTask(this, callback, AuthUtils.getCredential()).execute();
        } else {
            showList();
        }
    }

    private void showList(List<Reward> list) {
        adapter = new RewardAdapter(this, list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void showList() {
        showList(CurrentRewardList.getInstance().getList());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reward, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class RewardCallbackActivity extends RewardCallbackImpl {

        public RewardCallbackActivity(Activity context) {
            super(context);
        }

        @Override
        public void loaded(List<Reward> list) {
            super.loaded(list);
            showList(list);
        }
    }
}
