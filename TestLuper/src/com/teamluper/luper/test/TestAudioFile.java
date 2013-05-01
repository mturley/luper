package com.teamluper.luper.test;

import com.teamluper.luper.*;

import android.test.ActivityInstrumentationTestCase2;

/**
 * This is a collection of tests for AuioFile class. 
 * It uses an instance of SQLiteDataSource to ensure that 
 * setter methods of the AudioFile class update the database entries properly.
 * The class uses LuperMainActivity to create SQLiteDataSource.
 * All the entries that are inserted into the database are deleted at the end of each
 * test method, so we can use the same ID's in each test.
 * @author Sofya
 *
 */

public class TestAudioFile extends ActivityInstrumentationTestCase2<LuperMainActivity_> {
	
	private LuperMainActivity_ activity;
	private SQLiteDataSource datasource;
	
	public TestAudioFile() {
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
	 * Add an AudioFile entry to the database, 
	 * modify its parameters,
	 * fetch the file from the database by ID,
	 * check that the parameters have new values.
	 */
	public void testSetters()
	{
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		AudioFile file = datasource.createAudioFile(user, "testFilePath");
		file.setId(123);
		file.setOwnerUserID(456);
		file.setClientFilePath("testClientPath");
		file.setServerFilePath("testServerPath");
		file.setFileFormat("testFormat");
		file.setBitrate(4.2);
		file.setDurationMS(420.17);
		file.setReadyOnClient(false);
		file.setReadyOnServer(true);
		file.setRenderSequenceID(789);
		file.setDirty(false);
		
		AudioFile file2 = datasource.getAudioFileById(file.getId());
		assertEquals("fileID should be 123", 123, file2.getId());
		assertEquals("file owner user ID should be 456", 456, file2.getOwnerUserID());
		assertEquals("client file path should be testClientPath", "testClientPath", file2.getClientFilePath());
		assertEquals("server file path should be testServerPath", "testServerPath", file2.getServerFilePath());
		assertEquals("file format should be testFormat", "testFormat", file2.getFileFormat());
		assertEquals("bitrate should be 4.2", 4.2, file2.getBitrate());
		assertEquals("duration should be 420.17", 420.17, file2.getDurationMS());
		assertFalse("readyOnClient should be false", file2.isReadyOnClient());
		assertTrue("readyOnServer should be true", file2.isReadyOnServer());
		assertEquals("render sequence Id should be 789", 789, file2.getRenderSequenceID());
		assertFalse("dirty should be false", file.isDirty());		
		
		datasource.deleteAudioFile(file.getId());
		datasource.deleteUser(1234567890);
	}
	

}
