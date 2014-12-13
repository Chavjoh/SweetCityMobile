package ch.hesso.master.sweetcity.callback;

import android.app.Activity;

import ch.hesso.master.sweetcity.model.Account;

public interface AccountCallback {

    /**
     * Called before user registration
     */
    public void beforeRegistration();

    /**
     * Called after user registration
     */
    public void registered();

    /**
     * Account selection was made by user
     */
    public void selected();

    /**
     * Account was loaded from the server
     * @param account
     */
    public void loaded(Account account);

    /**
     * An error occurred during server communication
     */
    public void failed();
}
