package ch.hesso.master.sweetcity.callback;

import android.app.Activity;
import android.app.ProgressDialog;

import java.util.List;

import ch.hesso.master.sweetcity.data.CurrentReportList;
import ch.hesso.master.sweetcity.model.Report;
import ch.hesso.master.sweetcity.utils.DialogUtils;

public class ReportCallbackImpl implements ReportCallback {

    public Activity context;
    protected ProgressDialog progressDialog;
    protected boolean isFailed;

    public ReportCallbackImpl(Activity context) {
        this.context = context;
        this.isFailed = false;
    }

    @Override
    public void loaded(List<Report> list) {
        if (list != null) {
            CurrentReportList.getInstance().setList(list);
        }
    }

    @Override
    public void failed() {
        isFailed = true;
        DialogUtils.show(context, "Service unavailable");
    }

    @Override
    public void beforeAdding() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Adding report ...");
        progressDialog.show();
        isFailed = false;
    }

    @Override
    public void afterAdding() {
        progressDialog.dismiss();

        if (!isFailed) {
            context.finish();
        }
    }

}
