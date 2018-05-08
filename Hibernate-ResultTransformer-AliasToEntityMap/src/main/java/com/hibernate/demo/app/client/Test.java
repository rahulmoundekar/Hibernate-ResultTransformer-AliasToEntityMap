package com.hibernate.demo.app.client;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

import com.hibernate.demo.app.model.Employee;

public class Test {

	public static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory factory = configuration.buildSessionFactory(builder.build());
		return factory;
	}

	@SuppressWarnings("unchecked")
	public void emloyeeAliasToBeanClassView() {
		Session session = getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Employee.class);
		List<Object> list = criteria
				.setProjection(Projections.projectionList().add(Projections.property("name").as("name")))
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		for (Object object : list) {
			Map<String, String> map = (Map<String, String>) object;
			Set<String> set = map.keySet();
			for (String key : set) {
				System.out.println(map.get(key));
			}
		}
	}

	public static void main(String[] args) {
		Test test = new Test();
		test.emloyeeAliasToBeanClassView();
	}

}
