package com.teamluper.luper.test;

import com.teamluper.luper.*;

import android.test.ActivityInstrumentationTestCase2;

/**
 * This is a collection of tests for Track class. 
 * It uses an instance of SQLiteDataSource to ensure that 
 * setter methods of the Track class update the database entries properly.
 * The class uses LuperMainActivity to create SQLiteDataSource.
 * All the entries that are inserted into the database are deleted at the end of each
 * test method, so we can use the same ID's in each test.
 * @author Sofya
 *
 */

public class TestTrack extends ActivityInstrumentationTestCase2<LuperMainActivity_> {
	
	private LuperMainActivity_ activity;
	private SQLiteDataSource datasource;
	
	public TestTrack() {
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
	 * Add a Track entry to the database, 
	 * modify its parameters,
	 * fetch the track from the database by ID,
	 * check that the parameters have new values.
	 */
	public void testSetters(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		Sequence seq = datasource.createSequence(user, "testSequence");
		Track track = datasource.createTrack(seq);
		
		track.setId(123);
		track.setOwnerUserID(456);
		track.setParentSequenceID(789);
		track.setMuted(true);
		track.setLocked(false);
		track.setPlaybackOptions("testOptions");
		track.setDirty(true);
		
		Track track2 = datasource.getTrackById(track.getId());
		assertEquals("track ID should be 123", 123, track2.getId());
		assertEquals("owner user ID should be 456", 456, track2.getOwnerUserID());
		assertEquals("parent sequence ID should be 789", 789, track2.getParentSequenceID());
		assertTrue("muted should be true", track2.isMuted());
		assertFalse("locked should be false", track2.isLocked());
		assertEquals("playback options should be testOptions", "testOptions", track2.getPlaybackOptions());
		assertTrue("dirty should be true", track2.isDirty());
		
		datasource.deleteTrack(track.getId());
		datasource.deleteSequence(seq.getId());
		datasource.deleteUser(1234567890);
	}

}
