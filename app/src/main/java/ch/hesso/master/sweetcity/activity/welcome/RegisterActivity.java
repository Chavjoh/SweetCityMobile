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

package ch.hesso.master.sweetcity.activity.welcome;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ch.hesso.master.sweetcity.R;
import ch.hesso.master.sweetcity.callback.AccountCallback;
import ch.hesso.master.sweetcity.callback.AccountCallbackImpl;
import ch.hesso.master.sweetcity.task.RegisterAsyncTask;
import ch.hesso.master.sweetcity.utils.AuthUtils;
import ch.hesso.master.sweetcity.utils.DialogUtils;
import ch.hesso.master.sweetcity.utils.LayoutUtils;

/**
 * @author Chavaillaz Johan
 * @author Burri Grégory
 * @author Frank Etienne
 * @version 1.0
 */
public class RegisterActivity extends Activity implements View.OnClickListener {

    private EditText login;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login = LayoutUtils.findView(this, R.id.et_register);
        login.setTextColor(Color.BLACK);

        submit = LayoutUtils.findView(this, R.id.btn_register);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (LayoutUtils.isEmpty(login)) {
            DialogUtils.show(this, "Please enter a login name to register");
        } else {
            AccountCallback callback = AuthUtils.getInstance(this).getCallback();
            new RegisterAsyncTask(
                    this,
                    callback,
                    AuthUtils.getCredential(),
                    login.getText().toString()
            ).execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
