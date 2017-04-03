/**

Copyright 2014-2017 Functional Genomics Development Team, European Bioinformatics Institute 

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

@author Mikhail Gostev <gostev@gmail.com>

**/

package uk.ac.ebi.biostd.jpatest;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

//@Entity
public class BaseB implements Base
{
 private long id;

 @Override
 @Id
 public long getId()
 {
  return id;
 }

 @Override
 public void setId(long id)
 {
  this.id = id;
 }
 
 @Override
 public String getName()
 {
  return "BaseB: "+name;
 }
 String name;

 @Override
 public void setName(String nm)
 {
  name = nm;
 }
 
 @Override
 @OneToMany(mappedBy="base", cascade=CascadeType.ALL)
 public Collection<ResB> getRes()
 {
  return res;
 }
 private Collection<ResB> res;

 public void setRes(Collection<ResB> r)
 {
  res = r;  
 }
}
