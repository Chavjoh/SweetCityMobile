/*
* Copyright 2014-2015 University of Applied Sciences and Arts Western Switzerland
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package ch.hesso.master.sweetcity.callback;

import android.app.Activity;
import android.app.ProgressDialog;

import java.util.List;

import ch.hesso.master.sweetcity.data.CurrentReportList;
import ch.hesso.master.sweetcity.model.Report;
import ch.hesso.master.sweetcity.utils.DialogUtils;

/**
 * @author Chavaillaz Johan
 * @author Burri Grégory
 * @author Frank Etienne
 * @version 1.0
 */
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
