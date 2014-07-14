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
  conf.put("hibernate.connection.username", "mike");
  conf.put("hibernate.connection.password", "mike");
  conf.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/biostd");
  conf.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
  conf.put("hibernate.hbm2ddl.auto", "update");
  conf.put("hibernate.show_sql", "true");
  
  EntityManagerFactory fact = Persistence.createEntityManagerFactory ( "BioStd", conf );
  
  EntityManager em = fact.createEntityManager();
  
  Submission st  = new Submission();
  
  Section s1 = new Section();
  s1.setAcc("S1");
  s1.setType("ST1");
  
  Section s2 = new Section();
  s2.setAcc("S2");
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
  
  
//  <Parameter name='biosddb[dev].hibernate.connection.driver_class' value='oracle.jdbc.driver.OracleDriver'/>
//  <Parameter name='biosddb[dev].hibernate.connection.username' value='biosddev'/>
//  <Parameter name='biosddb[dev].hibernate.connection.password' value='b10sdd3v'/>
//  <Parameter name='biosddb[dev].hibernate.connection.url' value='jdbc:oracle:thin:@ora-vm-031.ebi.ac.uk:1531:biosdtst'/>
//  <Parameter name='biosddb[dev].hibernate.dialect' value='org.hibernate.dialect.Oracle10gDialect'/>
//  <Parameter name='biosddb[dev].hibernate.hbm2ddl.auto' value='validate'/>

  
 }
}
