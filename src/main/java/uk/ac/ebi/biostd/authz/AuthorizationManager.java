/**
 * Copyright 2014-2017 Functional Genomics Development Team, European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * @author Mikhail Gostev <gostev@gmail.com>
 **/

package uk.ac.ebi.biostd.authz;

import java.util.Collection;
import uk.ac.ebi.biostd.authz.ACR.Permit;
import uk.ac.ebi.biostd.model.Classified;


public interface AuthorizationManager {

    Permit checkSystemPermission(SystemAction act);

    Permit checkPermission(SystemAction act, Classified obj);

    Permit checkPermission(SystemAction act, User usr, Classified objId);

    Permit checkSystemPermission(SystemAction act, User usr);

    Collection<Tag> getEffectiveTags(Classified objId);

    Collection<Tag> getAllowTags(SystemAction act, User usr);

    Collection<Tag> getDenyTags(SystemAction act, User usr);

}