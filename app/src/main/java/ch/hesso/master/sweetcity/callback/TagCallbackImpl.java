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

import ch.hesso.master.sweetcity.data.CurrentTagList;
import ch.hesso.master.sweetcity.model.Tag;
import ch.hesso.master.sweetcity.utils.DialogUtils;

/**
 * @author Chavaillaz Johan
 * @author Burri Grégory
 * @author Frank Etienne
 * @version 1.0
 */
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
        DialogUtils.showAndExit(context, "Service unavailable");
    }
}
