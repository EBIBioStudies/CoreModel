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

package uk.ac.ebi.biostd.modtest;

import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import uk.ac.ebi.biostd.model.FileAttribute;
import uk.ac.ebi.biostd.model.FileRef;
import uk.ac.ebi.biostd.model.Link;
import uk.ac.ebi.biostd.model.LinkAttribute;
import uk.ac.ebi.biostd.model.Section;
import uk.ac.ebi.biostd.model.SectionAttribute;
import uk.ac.ebi.biostd.model.Submission;

public class ModTest
{
 public static void main( String[] args )
 {
  
  Map<String, Object> conf = new TreeMap<String, Object>();
  
  conf.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
  conf.put("hibernate.connection.username", "biostd");
  conf.put("hibernate.connection.password", "biostd");
  conf.put("hibernate.connection.url", "jdbc:mysql://mysql-fg-biostudy.ebi.ac.uk:4469/biostd_test");
  conf.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
  conf.put("hibernate.hbm2ddl.auto", "update");
  conf.put("hibernate.show_sql", "true");
//  conf.put("hibernate.archive.autodetection", "class, hbm");
  
  EntityManagerFactory fact = Persistence.createEntityManagerFactory ( "BioStdCoreModel", conf );
  
  EntityManager em = fact.createEntityManager();
  
  Submission st  = new Submission();
  
  Section s1 = new Section();
  s1.setAccNo("S1");
  s1.setType("ST1");
  
  Section s2 = new Section();
  s2.setAccNo("S2");
  s2.setType("ST2");
  
  FileRef fr = new FileRef();
  fr.setName("fname");
  
  Link l = new Link();
  l.setUrl("http://aaa.com");
  l.setLocal(true);
  
  st.setRootSection(s1);
  
  s1.addSection(s2);
  
  s1.addAttribute( new SectionAttribute("n1","v1") );
  s2.addAttribute(new SectionAttribute("n2","v2") );
  
  s1.addFileRef( fr );
  s1.addLink( l );
  
  l.addAttribute( new LinkAttribute("n3","v3") );
  fr.addAttribute( new FileAttribute("n4","v4") );
  
  EntityTransaction trn = em.getTransaction();
  
  trn.begin();
  
  em.persist(st);
  
  trn.commit();
  
  em.close();
  
  fact.close();
 }
}
