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

package uk.ac.ebi.biostd.tagreq;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import uk.ac.ebi.biostd.model.Submission;

public class TagReqTest
{

 public static void main(String[] args)
 {
  Map<String, Object> conf = new TreeMap<String, Object>();

  conf.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
  conf.put("hibernate.connection.username", "biostd");
  conf.put("hibernate.connection.password", "biostd");
  conf.put("hibernate.connection.url", "jdbc:mysql://choc.windows.ebi.ac.uk/biostdsage?autoReconnect=true");
  conf.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
  conf.put("hibernate.hbm2ddl.auto", "verify");
  conf.put("hibernate.show_sql", "true");
//  conf.put("hibernate.archive.autodetection", "class, hbm");
  
  EntityManagerFactory fact = Persistence.createEntityManagerFactory ( "BioStdCoreModel", conf );
  
  EntityManager em = fact.createEntityManager();

  Query q = em.createQuery("SELECT s FROM Submission s where s.id not in (SELECT ss.id FROM Submission ss JOIN ss.accesTag t where t.name='Public') ");
  
  List<Submission> res = q.getResultList();
  
  for( Submission s: res )
  {
   System.out.println("ID="+s.getId()+" AccNo="+s.getAccNo());
  }
  
  em.close();
  fact.close();
 }

}
