package org.nil.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.nil.HibernateMain;
import org.nil.entity.Name;

public class HibernateMainTest {
	
	HibernateMain inst;	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		HibernateMain.initSessionFactory();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		HibernateMain.closeSessionFactory();
	}

	@Before
	public void setUp() throws Exception {
		inst = new HibernateMain();
		inst.deleteAllNames();
	}
	
	@After
	public void tearDown() throws Exception {
		inst.deleteAllNames();
		inst = null;
	}

	@Test
	public void addNameTest() {
		assertTrue(inst.getAllNames().isEmpty());
		
		inst.addName("test1");
		inst.addName("test2");
		
		assertTrue(inst.getAllNames().size() == 2);
	}
	
	@Test
	public void getAllNamesTest() {
		List<Name> allNames = inst.getAllNames();
		
		assertTrue(allNames.isEmpty());
		
		inst.addName("test1");
		inst.addName("test2");
		
		allNames = inst.getAllNames();
		
		assertTrue(allNames.get(0).getName().equals("test1"));
		assertTrue(allNames.get(1).getName().equals("test2"));		
	}	

}
