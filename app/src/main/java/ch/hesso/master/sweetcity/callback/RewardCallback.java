package ch.hesso.master.sweetcity.callback;

import java.util.List;

import ch.hesso.master.sweetcity.model.Reward;

public interface RewardCallback {

    /**
     * Before reward list loading
     */
    public void beforeLoading();

    /**
     * Reward list was loaded from the server
     * @param list
     */
    public void loaded(List<Reward> list);

    /**
     * An error occurred during server communication
     */
    public void failed();
}
