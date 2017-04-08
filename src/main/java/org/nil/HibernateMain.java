package org.nil;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.nil.entity.Name;

public class HibernateMain {

	private static final SessionFactory sessionFactory;

	static {
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
			SessionFactoryImpl inst = (SessionFactoryImpl)sessionFactory;
			
		} catch (Throwable th) {
			System.err.println("sessionFactory initialization failed");
			throw new ExceptionInInitializerError(th);
		}

	}

	public void addName(String name) {
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			Name nameInst = new Name();
			nameInst.setName(name);

			session.save(nameInst);
			session.getTransaction().commit();
			session.close();
		} catch (Throwable th) {
			System.out.println("Error occured:" + th.getCause());
			sessionFactory.close();
		}

	}

	public List<Name> getAllNames() {
		List<Name> names = null;
		
		try {
			Session session = sessionFactory.openSession();
			names = session.createQuery("from Name").list();
			session.close();
		} catch (Throwable th) {
			System.out.println("Error occured:" + th.getCause());
			sessionFactory.close();
		}		

		return names;
	}
	
	public void deleteAllNames() {
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			session.createQuery("delete from Name").executeUpdate();
			
			session.getTransaction().commit();
			session.close();
		} catch (Throwable th) {
			System.out.println("Error occured:" + th.getCause());
			sessionFactory.close();
		}
	}	

	public void printNames(List<Name> names) {
		if(names == null) {
			return;
		}
		
		for (Name name : names) {
			System.out.println(name);
		}
	}

	public static void main(String[] args) {
		HibernateMain inst = new HibernateMain();
		inst.addName("Shankhanil");
		inst.addName("Soumya");
		inst.addName("Saptaparna");

		inst.printNames(inst.getAllNames());
		
		inst.deleteAllNames();

		sessionFactory.close();
	}
}
