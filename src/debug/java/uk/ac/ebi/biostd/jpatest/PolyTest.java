package uk.ac.ebi.biostd.jpatest;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class PolyTest
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
  
  EntityManagerFactory fact = Persistence.createEntityManagerFactory ( "Test1", conf );
  
  EntityManager em = fact.createEntityManager();
  

  BaseA ba = new BaseA();
  
  ba.setName("ba1");
  
  ArrayList<ResA> rs = new ArrayList<>();
  
  ResA ra = new ResA();
  ra.setBase(ba);
  ra.setName("ra1");
  rs.add( new ResA() );
  
  ra = new ResA();
  ra.setBase(ba);
  ra.setName("ra2");
  rs.add( new ResA() );  

  ba.setRes(rs);
  
  EntityTransaction trn = em.getTransaction();
  
  trn.begin();
  
  em.persist(ba);
  
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
