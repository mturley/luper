package com.teamluper.luper.test;

import com.teamluper.luper.*;
import android.test.ActivityInstrumentationTestCase2;
//import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;

public class TestSQLiteSource extends ActivityInstrumentationTestCase2<LuperMainActivity>{
	
	private LuperMainActivity activity;
	private SQLiteDataSource datasource;
	private User user;
	
	public TestSQLiteSource(String name) {
	    super(LuperMainActivity.class);
	    setName(name);
	  }
	

	public void setUp() throws Exception {
	    super.setUp();
	    activity = getActivity();
		datasource = new SQLiteDataSource(activity);
		datasource.open();
	}
	

	public void tearDown() throws Exception {
		datasource.close();
		super.tearDown();
	}
	
	@SmallTest
	public void testUser(){
		user = datasource.createUser("testUser", "testUser@mail.com", "testPassword");
		assertEquals("username should be testUser", "testUser", user.getUsername());
		assertEquals("email should be testUser@mail.com", "testUser@mail.com", user.getEmail());
		assertEquals("passwordHash should be testPassword", "testPassword", user.getPasswordHash());
	}
	/*
	@Test
	public void testSequences() {
		//datasource.createSequence(user, "TestSequenceTitle");
		
	}
	*/
	
}
