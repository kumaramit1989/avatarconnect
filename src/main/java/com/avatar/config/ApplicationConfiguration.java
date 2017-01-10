package com.avatar.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.avatar.model.Blog;
import com.avatar.model.Chat;
import com.avatar.model.Event;

import com.avatar.model.Friend;
import com.avatar.model.Job;


import com.avatar.model.Role;

import com.avatar.model.UserDetails;
import com.avatar.model.UserRole;


@Configuration
@ComponentScan("com")
@EnableTransactionManagement
public class ApplicationConfiguration {
	
	@Autowired
	@Bean(name="dataSource")
	public DataSource getDataSource()
	{
		System.out.println("get datasource method called");
		DriverManagerDataSource dataSource=new DriverManagerDataSource();
		//h2 database setting
		//dataSource.setDriverClassName("org.h2.Driver");
	//	dataSource.setUsername("avatar");
		//dataSource.setPassword("avatar");
	//	dataSource.setUrl("jdbc:h2:tcp://localhost/~/avatardb");
		
		//oracle database setting
				dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
				dataSource.setUsername("sys");
				dataSource.setPassword("pass@123");
			dataSource.setUrl("jdbc:oracle:thin:@localhost:8080:XE");
			System.out.println("get datasource method end");
		return dataSource;
	}
	private  Properties getHibernateProperties()
	 {
		System.out.println("getHibernateProperties method called");
		  Properties properties=new Properties();
		  properties.setProperty("hibernate.show_sql", "true");
		  properties.setProperty("hibernate.dialect","org.hibernate.dialect.Oracle10gDialect");
		//	properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		  properties.setProperty("hibernate.default_schema", "AVATAR");
			properties.setProperty("hibernate.hbm2ddl.auto", "update");
			System.out.println("getHibernateProperties method end");
			return properties;
				  
	 }
	@Autowired
	@Bean(name="sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource)
	{
		LocalSessionFactoryBuilder localSessionFactoryBuilder=new LocalSessionFactoryBuilder(dataSource);
		localSessionFactoryBuilder.addProperties(getHibernateProperties());
		localSessionFactoryBuilder.addAnnotatedClass(UserDetails.class);
		localSessionFactoryBuilder.addAnnotatedClass(Blog.class);
		localSessionFactoryBuilder.addAnnotatedClass(UserRole.class);
		localSessionFactoryBuilder.addAnnotatedClass(Chat.class);
		localSessionFactoryBuilder.addAnnotatedClass(Event.class);
		localSessionFactoryBuilder.addAnnotatedClass(Friend.class);
		localSessionFactoryBuilder.addAnnotatedClass(Job.class);
		localSessionFactoryBuilder.addAnnotatedClass(Role.class);
		return localSessionFactoryBuilder.buildSessionFactory();
	}
	@Autowired
	@Bean(name="transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory)
	{
		HibernateTransactionManager hibernateTransactionManager=new HibernateTransactionManager(sessionFactory);
		return hibernateTransactionManager;
		
	}

}
