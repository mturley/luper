package com.teamluper.luper.test;

import com.teamluper.luper.*;

import android.test.ActivityInstrumentationTestCase2;

/**
 * This is a collection of tests for Clip class. 
 * It uses an instance of SQLiteDataSource to ensure that 
 * setter methods of the Clip class update the database entries properly.
 * The class uses LuperMainActivity to create SQLiteDataSource.
 * All the entries that are inserted into the database are deleted at the end of each
 * test method, so we can use the same ID's in each test.
 * @author Sofya
 *
 */

public class TestClip extends ActivityInstrumentationTestCase2<LuperMainActivity_> {
	
	private LuperMainActivity_ activity;
	private SQLiteDataSource datasource;
	
	public TestClip() {
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
	 * Add a Clip entry to the database, 
	 * modify its parameters,
	 * fetch the clip from the database by ID,
	 * check that the parameters have new values.
	 */
	public void testSetters(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		Sequence seq = datasource.createSequence(user, "testSequence");
		Track track = datasource.createTrack(seq);
		AudioFile file = datasource.createAudioFile(user, "testFilePath");
		Clip clip = datasource.createClip(track, file, 0, 0);
		
		clip.setId(123);
		clip.setOwnerUserID(456);
		clip.setParentTrackID(789);
		clip.setAudioFileID(321);
		clip.setStartTime(13);
		clip.setDurationMS(420);
		clip.setLoopCount(17);
		clip.setColor(42);
		clip.setLocked(false);
		clip.setPlaybackOptions("testOptions");
		clip.setDirty(true);
		
		Clip clip2 = datasource.getClipById(clip.getId());
		assertEquals("clip ID should be 123", 123, clip2.getId());
		assertEquals("owner user ID should be 456", 456, clip.getOwnerUserID());
		assertEquals("parent track ID should be 789", 789, clip2.getParentTrackID());
		assertEquals("audio file ID should be 321", 321, clip2.getAudioFileID());
		assertEquals("start time should be 13", 13, clip2.getStartTime());
		assertEquals("duration should be 420", 420, clip2.getDurationMS());
		assertEquals("loop count should be 17", 17, clip.getLoopCount());
		assertEquals("color should be 42", 42, clip2.getColor());
		assertFalse("locked should be false", clip2.isLocked());
		assertEquals("playback options should be testOptions", "testOptions", clip2.getPlaybackOptions());
		assertTrue("dirty should be true", clip2.isDirty());
		
		datasource.deleteTrack(track.getId());
		datasource.deleteSequence(seq.getId());
		datasource.deleteUser(1234567890);
	}

}
