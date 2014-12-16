package ch.hesso.master.sweetcity.data;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import ch.hesso.master.sweetcity.callback.ReportCallback;
import ch.hesso.master.sweetcity.callback.ReportCallbackImpl;
import ch.hesso.master.sweetcity.model.Report;
import ch.hesso.master.sweetcity.task.GetReportListAsyncTask;
import ch.hesso.master.sweetcity.utils.AuthUtils;

public class CurrentReportList {

    private static CurrentReportList instance;

    private List<Report> reportList;

    public static CurrentReportList getInstance() {
        if (instance == null) {
            instance = new CurrentReportList();
        }
        return instance;
    }

    public CurrentReportList() {
        this.reportList = new ArrayList<Report>();
    }

    public void setList(List<Report> reportList) {
        if (reportList != null) {
            this.reportList = reportList;
        }
    }

    public List<Report> getList() {
        return this.reportList;
    }

    public void load(Activity context) {
        ReportCallback callback = new ReportCallbackImpl(context);
        load(context, callback);
    }

    public void load(Activity context, ReportCallback callback) {
        new GetReportListAsyncTask(context, callback, AuthUtils.getCredential()).execute();
    }

    public Integer getPosition(Report report) {
        return reportList.indexOf(report);
    }

    public Report get(Integer position) {
        return reportList.get(position);
    }
}
