package ch.hesso.master.sweetcity.callback;

import android.app.Activity;
import android.app.ProgressDialog;

import java.util.List;

import ch.hesso.master.sweetcity.data.CurrentTagList;
import ch.hesso.master.sweetcity.model.Tag;
import ch.hesso.master.sweetcity.utils.DialogUtils;

public class TagCallbackImpl implements TagCallback {

    protected Activity context;
    protected ProgressDialog progressDialog;

    public TagCallbackImpl(Activity context) {
        this.context = context;
    }

    @Override
    public void beforeLoading() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Retrieving tag list ...");
        progressDialog.show();
    }

    @Override
    public void loaded(List<Tag> list) {
        progressDialog.dismiss();

        if (list != null) {
            CurrentTagList.getInstance().setList(list);
        }
    }

    @Override
    public void failed() {
        progressDialog.dismiss();
        DialogUtils.show(context, "Service unavailable");
    }
}
