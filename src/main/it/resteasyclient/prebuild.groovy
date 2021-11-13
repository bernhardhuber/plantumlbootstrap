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
This script may be written with either BeanShell or Groovy (since 1.3). 
If the file extension is omitted (e.g. verify), the plugin searches for the 
file by trying out the well-known extensions .bsh and .groovy. 

If this script exists for a particular project but returns any non-null value 
different from true or throws an exception, 
the corresponding build is flagged as a failure.

Default value is: prebuild.
User property is: invoker.preBuildHookScript.
*/

def name='resteasyclient-integrationtest'
println "Prebuild $name!"

return true
