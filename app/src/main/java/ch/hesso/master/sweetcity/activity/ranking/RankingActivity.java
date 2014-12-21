package ch.hesso.master.sweetcity.activity.ranking;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import ch.hesso.master.sweetcity.R;
import ch.hesso.master.sweetcity.callback.RankingCallback;
import ch.hesso.master.sweetcity.callback.RankingCallbackImpl;
import ch.hesso.master.sweetcity.data.CurrentRankingList;
import ch.hesso.master.sweetcity.model.Account;

public class RankingActivity extends Activity {

    private ListView listView;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        findViews();
        loadList();
    }


    private void findViews() {
        listView = (ListView) findViewById(R.id.lv_ranking);
    }

    private void loadList() {
        if (CurrentRankingList.getInstance().isEmpty()) {
            RankingCallback callback = new RankingCallbackActivity(this);
            CurrentRankingList.getInstance().load(this, callback);
        } else {
            showList();
        }
    }

    private void showList(List<Account> list) {
        adapter = new RankingAdapter(this, list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void showList() {
        showList(CurrentRankingList.getInstance().getList());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ranking, menu);
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

    /**
     * Ranking callback with display of the ranking list on the view
     */
    private class RankingCallbackActivity extends RankingCallbackImpl {

        public RankingCallbackActivity(Activity context) {
            super(context);
        }

        @Override
        public void loaded(List<Account> list) {
            super.loaded(list);
            showList(list);
        }
    }
}
