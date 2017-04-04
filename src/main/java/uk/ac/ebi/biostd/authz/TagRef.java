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

package uk.ac.ebi.biostd.authz;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
abstract public class TagRef
{

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 public long getId()
 {
  return id;
 }
 private long id;

 public void setId(long id)
 {
  this.id = id;
 }

 @ManyToOne
 @JoinColumn(name="tag_id")
 public Tag getTag()
 {
  return tag;
 }
 private Tag    tag;

 public void setTag(Tag tag)
 {
  this.tag = tag;
 }

 public String getParameter()
 {
  return parameter;
 }
 private String parameter;

 public void setParameter(String parameter)
 {
  this.parameter = parameter;
 }
}
