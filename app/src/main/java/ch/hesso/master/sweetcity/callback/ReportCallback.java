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

import java.util.List;

import ch.hesso.master.sweetcity.model.Report;

/**
 * @author Chavaillaz Johan
 * @author Burri Grégory
 * @author Frank Etienne
 * @version 1.0
 */
public interface ReportCallback {

    /**
     * Executed before report list loading
     */
    void beforeLoading();

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
