package ch.hesso.master.sweetcity.callback;

import java.util.List;

import ch.hesso.master.sweetcity.model.Account;

public interface RankingCallback {

    /**
     * Before account list loading
     */
    public void beforeLoading();

    /**
     * Account list was loaded from the server
     * @param list
     */
    public void loaded(List<Account> list);

    /**
     * An error occurred during server communication
     */
    public void failed();
}
