package com.teamluper.luper.test;

import com.teamluper.luper.*;

import android.test.ActivityInstrumentationTestCase2;

/**
 * This is a collection of tests for User class. 
 * It uses an instance of SQLiteDataSource to ensure that 
 * setter methods of the User class update the database entries properly.
 * The class uses LuperMainActivity to create SQLiteDataSource.
 * All the entries that are inserted into the database are deleted at the end of each
 * test method, so we can use the same ID's in each test.
 * @author Sofya
 *
 */

public class TestUser extends ActivityInstrumentationTestCase2<LuperMainActivity_> {
	
	private LuperMainActivity_ activity;
	private SQLiteDataSource datasource;
	
	public TestUser() {
	    super(LuperMainActivity_.class);
	  }
	
	/**
	 * This method is called before running the tests.
	 * It creates a new instance of SQLiteDataSource and 
	 * makes sure that there is no data and we can use any ID's for testing
	 */
	public void setUp() throws Exception {
	    super.setUp();
	    activity = getActivity();
		datasource = new SQLiteDataSource(activity);
		datasource.open();
		datasource.dropAllData();
	}
	
	/**
	 * This method is called after running all the tests to clean up.
	 */
	public void tearDown() throws Exception {
		datasource.close();
		super.tearDown();
	}
	
	/**
	 * Add a User entry to the database, 
	 * modify its parameters,
	 * fetch the user from the database by ID,
	 * check that the parameters have new values.
	 */
	public void testSetters(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		user.setId(123);
		user.setUsername("userTest");
		user.setEmail("userTest@mail.com");
		user.setActiveUser(false);
		user.setPreferences("testPreferences");
		user.setDirty(true);
		
		User user2 = datasource.getUserById(user.getId());
		assertEquals("user ID should be 123", 123, user2.getId());
		assertEquals("username should be userTest", "userTest", user2.getUsername());
		assertEquals("email should be userTest@mail.com", "userTest@mail.com", user2.getEmail());
		assertFalse("active user should be false", user2.isActiveUser());
		assertEquals("preferences should be testPreferences", "testPreferences", user2.getPreferences());
		assertTrue("dirty should be true", user2.isDirty());
		
		datasource.deleteUser(user.getId());
	}

}
