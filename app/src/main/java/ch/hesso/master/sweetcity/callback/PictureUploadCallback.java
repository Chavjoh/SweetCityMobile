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

import android.graphics.Bitmap;

/**
 * @author Chavaillaz Johan
 * @author Burri Grégory
 * @author Frank Etienne
 * @version 1.0
 */
public interface PictureUploadCallback {
    /**
     * Before the picture is loaded.
     */
    public void beforeLoading();

    /**
     * The picture has been loaded.
     */
    public void loaded(Bitmap picture);

    /**
     * An error occurred.
     */
    public void failed();
}
