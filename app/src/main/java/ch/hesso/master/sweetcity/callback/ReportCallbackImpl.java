package ch.hesso.master.sweetcity.callback;

import android.app.Activity;
import android.app.ProgressDialog;

import java.util.List;

import ch.hesso.master.sweetcity.data.CurrentReportList;
import ch.hesso.master.sweetcity.model.Report;
import ch.hesso.master.sweetcity.utils.DialogUtils;

public class ReportCallbackImpl implements ReportCallback {

    protected Activity context;
    protected ProgressDialog progressDialog;
    protected boolean isFailed;

    public ReportCallbackImpl(Activity context) {
        this.context = context;
        this.isFailed = false;
    }

    @Override
    public void beforeLoading() {
        this.isFailed = false;
    }

    @Override
    public void loaded(List<Report> list) {
        if (this.isFailed) {
            DialogUtils.show(this.context, "Service unavailable");
        } else if (list != null) {
            CurrentReportList.getInstance().setList(list);
        }
    }

    @Override
    public void failed() {
        this.isFailed = true;
    }

    @Override
    public void beforeAdding() {
        this.progressDialog = new ProgressDialog(context);
        this.progressDialog.setMessage("Adding report ...");
        this.progressDialog.show();
        this.isFailed = false;
    }

    @Override
    public void afterAdding() {
        this.progressDialog.dismiss();

        if (!this.isFailed) {
            this.context.finish();
        }
    }

}
