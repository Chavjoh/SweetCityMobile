package ch.hesso.master.sweetcity.callback;

import java.util.List;

import ch.hesso.master.sweetcity.model.Report;

public interface ReportCallback {

    /**
     * Report list was loaded from the server
     * @param list
     */
    public void loaded(List<Report> list);

    /**
     * An error occurred during server communication
     */
    public void failed();

    /**
     * Executed before a report was added
     */
    void beforeAdding();

    /**
     * Executed after a report was added
     */
    void afterAdding();
}
