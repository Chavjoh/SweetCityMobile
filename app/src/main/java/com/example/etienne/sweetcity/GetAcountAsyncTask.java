package com.example.etienne.sweetcity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;

import ch.hesso.master.sweetcity.model.Account;
import ch.hesso.master.sweetcity.webservice.account.AccountService;

/**
 * Flatseeker MobOp
 * HES-SO
 * Created by Gregori BURRI, Etienne FRANK on 26.11.2014.
 */
public class GetAcountAsyncTask extends AsyncTask<String, Void, Account> {
    Context context;
    private ProgressDialog pd;
    private GoogleAccountCredential credential;
    private SharedPreferences settings;
    
    public GetAcountAsyncTask(Context context, GoogleAccountCredential credential, SharedPreferences settings) {
        this.context = context;
        this.credential = credential;
        this.settings = settings;
    }

    protected void onPreExecute(){
        super.onPreExecute();
        String accountName = settings.getString("ACCOUNT_NAME", null);
        pd = new ProgressDialog(context);
        pd.setMessage("[" + accountName + "]" + " Adding the Quote...");
        pd.show();
    }

    protected Account doInBackground(String... params) {
        Account response = null;
        try {
            AccountService.Builder builder = new AccountService.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), credential);
            AccountService service =  builder.build();
            response = service.accountService().getCurrentAccount().execute();
            Log.d("Response from call", response.getPseudo());
        } catch (Exception e) {
            Log.d("Could not get Account", e.getMessage(), e);
        }
        return response; 
    }

    protected void onPostExecute(Account quote) {
        //Clear the progress dialog and the fields
        pd.dismiss();

        //Display success message to user
//        Toast.makeText(context, "Quote added succesfully", Toast.LENGTH_SHORT).show();
    }
}
