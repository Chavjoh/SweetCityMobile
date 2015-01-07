package ch.hesso.master.sweetcity.activity.reward;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

import ch.hesso.master.sweetcity.R;
import ch.hesso.master.sweetcity.callback.RewardCallbackImpl;
import ch.hesso.master.sweetcity.data.CurrentRewardList;
import ch.hesso.master.sweetcity.model.Reward;
import ch.hesso.master.sweetcity.utils.AuthUtils;
import ch.hesso.master.sweetcity.utils.LayoutUtils;

public class RewardActivity extends Activity {

    private static final Integer LEVEL_POINT = 10;

    private ListView listView;
    private ArrayAdapter adapter;
    private TextView tvLevel;
    private TextView tvPoints;
    private NumberProgressBar pbExperience;
    private ProgressWheel progressWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        findViews();
        loadList();
        showExperience();
    }

    private void showExperience() {
        Integer points = AuthUtils.getAccount().getPoints();
        Integer level = (int) Math.floor((double) points / (double) LEVEL_POINT) + 1;
        Integer progress = (int) ((( (double)(points % LEVEL_POINT) ) / LEVEL_POINT) * 100);

        tvLevel.setText(String.valueOf(level));
        tvPoints.setText(String.valueOf(points));
        pbExperience.setProgress(progress);
    }

    private void findViews() {
        listView = (ListView) findViewById(R.id.lv_reward);
        tvLevel = LayoutUtils.findView(this, R.id.tv_level_value);
        tvPoints = LayoutUtils.findView(this, R.id.tv_points_value);
        pbExperience = LayoutUtils.findView(this, R.id.pb_experience);
        progressWheel = LayoutUtils.findView(this, R.id.progress_wheel);
    }

    private void loadList() {
        if (CurrentRewardList.getInstance().isEmpty()) {
            RewardCallbackActivity callback = new RewardCallbackActivity(this);
            CurrentRewardList.getInstance().load(this, callback);
        } else {
            showList();
        }
    }

    private void showList(List<Reward> list) {
        adapter = new RewardAdapter(this, list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        progressWheel.setVisibility(View.INVISIBLE);
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
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Reward callback with display of the reward list on the view
     */
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
