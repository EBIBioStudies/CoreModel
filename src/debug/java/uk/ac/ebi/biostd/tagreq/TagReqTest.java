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
