package ch.hesso.master.sweetcity.callback;

import java.util.List;

import ch.hesso.master.sweetcity.model.Tag;

public interface TagCallback {

    /**
     * Before tag list loading
     */
    public void beforeLoading();

    /**
     * Tag list was loaded from the server
     * @param list
     */
    public void loaded(List<Tag> list);

    /**
     * An error occurred during server communication
     */
    public void failed();
}
