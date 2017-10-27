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

package uk.ac.ebi.biostd.jpatest;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

//@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "allres")
@DiscriminatorColumn(name = "CTYPE")
@DiscriminatorValue("res")
public abstract class Res {
// @Id
// abstract long getId();
// abstract void setId( long id);

    abstract Base getBase();

    abstract void setBase(Base b);

    abstract String getName();

    abstract void setName(String n);
}
