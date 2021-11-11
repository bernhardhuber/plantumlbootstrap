/*
 * Copyright 2021 berni3.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * fetch version info
 * @type Function
 */
var fetchVersionInfo = (function () {
    //const baseUrl = 'http://localhost:8081/bootstrap1-1.0-SNAPSHOT2/';
    const generateImageResourceUrl = 'webresources/applAndPlantumlVersion/applAndPlantumlVersion';
    const url = generateImageResourceUrl;
    return function fetchVersionInfo() {
        $.ajax({
            url: url,
            method: 'GET',
            dataType: 'text',
            success(data) {
                //console.log('fetchVersionInfo ' + data);
                alert('' + data);
            }
        });
    };
})();

