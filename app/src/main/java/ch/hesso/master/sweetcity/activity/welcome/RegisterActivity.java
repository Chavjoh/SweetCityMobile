package ch.hesso.master.sweetcity.activity.welcome;

import android.app.Activity;
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


public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText login = (EditText) findViewById(R.id.et_register);

        Button submit = (Button) findViewById(R.id.btn_register);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (LayoutUtils.isEmpty(login)) {
                    DialogUtils.show(RegisterActivity.this, "Please enter a login name to register");
                } else {
                    AccountCallback callback = new AccountCallbackImpl(RegisterActivity.this);
                    new RegisterAsyncTask(
                            RegisterActivity.this,
                            callback,
                            AuthUtils.getCredential(),
                            login.getText().toString()
                    ).execute();
                }
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
