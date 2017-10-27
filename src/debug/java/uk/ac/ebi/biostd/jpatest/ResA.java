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

import javax.persistence.DiscriminatorValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

//@Entity
@DiscriminatorValue("ResA")
public class ResA extends Res {

    private long id;
    private BaseA base;
    private String name;

    @Id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    @ManyToOne(targetEntity = BaseA.class)
    public BaseA getBase() {
        return base;
    }

    @Override
    public void setBase(Base b) {
        setBase((BaseA) b);
    }

    public void setBase(BaseA b) {
        base = b;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

}
