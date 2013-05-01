package com.teamluper.luper.test;

import com.teamluper.luper.*;

import android.test.ActivityInstrumentationTestCase2;

/**
 * This is a collection of tests for Sequence class. 
 * It uses an instance of SQLiteDataSource to ensure that 
 * setter methods of the Sequence class update the database entries properly.
 * The class uses LuperMainActivity to create SQLiteDataSource.
 * All the entries that are inserted into the database are deleted at the end of each
 * test method, so we can use the same ID's in each test.
 * @author Sofya
 *
 */

public class TestSequence extends ActivityInstrumentationTestCase2<LuperMainActivity_> {
	
	private LuperMainActivity_ activity;
	private SQLiteDataSource datasource;
	
	public TestSequence() {
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
	 * Add a Sequence entry to the database, 
	 * modify its parameters,
	 * fetch the sequence from the database by ID,
	 * check that the parameters have new values.
	 */
	public void testSetters(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		Sequence seq = datasource.createSequence(user, "testSequence");
		
		seq.setId(123);
		seq.setOwnerUserID(456);
		seq.setTitle("testTitle");
		seq.setSharingLevel(3);
		seq.setPlaybackOptions("testOptions");
		seq.setDirty(true);
		
		Sequence seq2 = datasource.getSequenceById(seq.getId());
		assertEquals("sequence ID should be 123", 123, seq2.getId());
		assertEquals("owner user ID should be 456", 456, seq2.getOwnerUserID());
		assertEquals("title should be testTitle", "testTitle", seq2.getTitle());
		assertEquals("sharing level should be 3", 3, seq2.getSharingLevel());
		assertEquals("playback options should be testOptions", "testOptions", seq2.getPlaybackOptions());
		assertTrue("dirty should be true", seq2.isDirty());
		
		datasource.deleteSequence(seq.getId());
		datasource.deleteUser(1234567890);
	}
}