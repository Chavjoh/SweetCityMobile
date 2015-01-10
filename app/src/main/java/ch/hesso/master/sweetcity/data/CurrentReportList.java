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

package ch.hesso.master.sweetcity.data;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import ch.hesso.master.sweetcity.callback.ReportCallback;
import ch.hesso.master.sweetcity.callback.ReportCallbackImpl;
import ch.hesso.master.sweetcity.model.Report;
import ch.hesso.master.sweetcity.task.GetReportListAsyncTask;
import ch.hesso.master.sweetcity.utils.AuthUtils;

/**
 * @author Chavaillaz Johan
 * @author Burri Grégory
 * @author Frank Etienne
 * @version 1.0
 */
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
        if ( position < 0 || position >= reportList.size()) {
            return null;
        }

        return reportList.get(position);
    }
}
