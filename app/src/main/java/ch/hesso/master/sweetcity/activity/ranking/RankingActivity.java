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

/**
 * @author Chavaillaz Johan
 * @author Burri Grégory
 * @author Frank Etienne
 * @version 1.0
 */
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
