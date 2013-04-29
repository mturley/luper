package com.teamluper.luper.test;

import com.teamluper.luper.*;
import android.test.ActivityInstrumentationTestCase2;

/**
 * This is a collection of tests for SQLiteDataSource class 
 * that performs interactions with the database.
 * The class uses LupreMainActivity.
 * All the entries that are inserted into the database are deleted at the end of each
 * test method, so we can use the same ID's in each test.
 * @author Sofya
 *
 */

public class TestSQLiteDataSource extends ActivityInstrumentationTestCase2<LuperMainActivity_> {
	
	private LuperMainActivity_ activity;
	private SQLiteDataSource datasource;
	
	public TestSQLiteDataSource() {
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
	 * Create a user and make sure that the entry has correct ID, username, and email
	 */
	public void testCreateUser(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		assertNotNull(user);
		assertEquals("userID should be 123456787", "1234567890", ""+user.getId());
		assertEquals("username should be testUser", "testUser", user.getUsername());
		assertEquals("email should be testUser@mail.com", "testUser@mail.com", user.getEmail());
		datasource.deleteUser(1234567890);
	}
	
	/**
	 * Create a user, try fetching the entry by ID and make sure 
	 * the original user and fetched one are the same
	 */
	public void testGetUserById(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");		
		long userID = user.getId();
		User user2 = datasource.getUserById(userID);
		assertNotNull(user2);
		assertEquals("IDs of user and user2 should be the same", userID, user2.getId());
		datasource.deleteUser(1234567890);
	}
	
	/**
	 * Create a user, try fetching the entry by email and make sure 
	 * the original user and fetched one are the same
	 */
	public void testGetUserByEmail(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");		
		String email = user.getEmail();
		User user2 = datasource.getUserByEmail(email);
		assertNotNull(user2);
		assertEquals("emails of user and user2 should be the same", email, user2.getEmail());
		datasource.deleteUser(1234567890);
	}
	
	/**
	 * Create user, delete the user by ID, make sure the user entry doesn't exist anymore
	 */
	public void testDeleteUser(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		long userID = user.getId();
		datasource.deleteUser(userID);
		assertNull(datasource.getUserById(userID));
	}
	
	/**
	 * Create a sequence and make sure that the entry has correct owner user ID
	 * and title
	 */
	public void testCreateSequence(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		Sequence seq = datasource.createSequence(user, "testSequence");
		assertNotNull(seq);
		assertEquals("userOwnerID should be same as userID", user.getId(), seq.getOwnerUserID());
		assertEquals("title should be testSequence", "testSequence", seq.getTitle());
		datasource.deleteSequence(seq.getId());
		datasource.deleteUser(1234567890);
	}
	
	/**
	 * Create a sequence, try fetching the entry by ID and make sure 
	 * the original sequence and fetched one are the same
	 */
	public void testGetSequenceById(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		Sequence seq = datasource.createSequence(user, "testSequence");		
		long seqID = seq.getId();
		Sequence seq2 = datasource.getSequenceById(seqID);
		assertNotNull(seq2);
		assertEquals("IDs of seq and seq2 should be the same", seqID, seq2.getId());
		datasource.deleteSequence(seq.getId());
		datasource.deleteUser(1234567890);
	}
	
	/**
	 * Create sequence, delete the sequence by ID, make sure 
	 * the sequence entry doesn't exist anymore
	 */
	public void testDeleteSequence(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		Sequence seq = datasource.createSequence(user, "testSequence");
		long seqID = seq.getId();
		datasource.deleteSequence(seqID);
		assertNull(datasource.getUserById(seqID));
		datasource.deleteUser(1234567890);
	}
	
	/**
	 * Create a track and make sure that the entry has correct owner user ID
	 * and parent sequence ID
	 */
	public void testCreateTrack(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		Sequence seq = datasource.createSequence(user, "testSequence");
		Track track = datasource.createTrack(seq);
		assertNotNull(track);
		assertEquals("ownerUserID should be same as userID", user.getId(), track.getOwnerUserID());
		assertEquals("parentSequenceID should be same as sequenceID", seq.getId(), track.getParentSequenceID());
		datasource.deleteTrack(track.getId());
		datasource.deleteSequence(seq.getId());
		datasource.deleteUser(1234567890);
	}
	
	/**
	 * Create a track, try fetching the entry by ID and make sure 
	 * the original track and fetched one are the same
	 */
	public void testGetTrackById(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		Sequence seq = datasource.createSequence(user, "testSequence");
		Track track = datasource.createTrack(seq);
		long trackID = track.getId();
		Track track2 = datasource.getTrackById(trackID);
		assertNotNull(track2);
		assertEquals("IDs of track and track2 should be the same", trackID, track2.getId());
		datasource.deleteTrack(track.getId());
		datasource.deleteSequence(seq.getId());
		datasource.deleteUser(1234567890);
	}
	
	/**
	 * Create track, delete the track by ID, make sure the track entry doesn't exist anymore
	 */
	public void testDeleteTrack(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		Sequence seq = datasource.createSequence(user, "testSequence");
		Track track = datasource.createTrack(seq);
		long trackID = track.getId();
		datasource.deleteTrack(trackID);
		assertNull(datasource.getUserById(trackID));
		datasource.deleteSequence(seq.getId());
		datasource.deleteUser(1234567890);
	}
	
	/**
	 * Create an audio file and make sure that the entry has correct owner user ID
	 * and file path
	 */
	public void testCreateAudioFile(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		AudioFile file = datasource.createAudioFile(user, "testFilePath");
		assertNotNull(file);
		assertEquals("userOwnerID should be same as userID", user.getId(), file.getOwnerUserID());
		assertEquals("file path should be testFilePath", "testFilePath", file.getClientFilePath());
		datasource.deleteAudioFile(file.getId());
		datasource.deleteUser(1234567890);
		assertNull(datasource.getUserById(1234567890));
	}
	
	/**
	 * Create an audio file, try fetching the entry by ID and make sure 
	 * the original file and fetched one are the same
	 */
	public void testGetAudioFileById(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		AudioFile file = datasource.createAudioFile(user, "testFilePath");		
		long fileID = file.getId();
		AudioFile file2 = datasource.getAudioFileById(fileID);
		assertNotNull(file2);
		assertEquals("IDs of file and file2 should be the same", fileID, file2.getId());
		datasource.deleteAudioFile(file.getId());
		datasource.deleteUser(1234567890);
	}
	
	/**
	 * Create audio file, delete the file by ID, make sure 
	 * the file entry doesn't exist anymore
	 */
	public void testDeleteAudioFile(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		AudioFile file = datasource.createAudioFile(user, "testFilePath");
		long fileID = file.getId();
		datasource.deleteAudioFile(fileID);
		assertNull(datasource.getUserById(fileID));
		datasource.deleteUser(1234567890);
	}
	
	/**
	 * Create a user and make sure that the entry has correct owner user ID,
	 * parent track ID, and corresponding audio file ID
	 */
	public void testCreateClip(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		Sequence seq = datasource.createSequence(user, "testSequence");
		Track track = datasource.createTrack(seq);
		AudioFile file = datasource.createAudioFile(user, "testFilePath");
		Clip clip = datasource.createClip(track, file, 0);
		assertNotNull(clip);
		assertEquals("ownerUserID should be same as userID", user.getId(), clip.getOwnerUserID());
		assertEquals("parentTrackID should be same as trackID", track.getId(), clip.getParentTrackID());
		assertEquals("audioFileID should be same as fileID", file.getId(), clip.getAudioFileID());
		datasource.deleteClip(clip.getId());
		datasource.deleteTrack(track.getId());
		datasource.deleteSequence(seq.getId());
		datasource.deleteUser(1234567890);
	}
	
	/**
	 * Create a clip, try fetching the entry by ID and make sure 
	 * the original clip and fetched one are the same
	 */
	public void testGetClipById(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		Sequence seq = datasource.createSequence(user, "testSequence");
		Track track = datasource.createTrack(seq);
		AudioFile file = datasource.createAudioFile(user, "testFilePath");
		Clip clip = datasource.createClip(track, file, 0);
		long clipID = clip.getId();
		Clip clip2 = datasource.getClipById(clipID);
		assertNotNull(clip2);
		assertEquals("IDs of clip and clip2 should be the same", clipID, clip2.getId());
		datasource.deleteClip(clip.getId());
		datasource.deleteTrack(track.getId());
		datasource.deleteSequence(seq.getId());
		datasource.deleteUser(1234567890);
	}
	
	/**
	 * Create clip, delete the clip by ID, make sure the clip entry doesn't exist anymore
	 */
	public void testDeleteClip(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		Sequence seq = datasource.createSequence(user, "testSequence");
		Track track = datasource.createTrack(seq);
		AudioFile file = datasource.createAudioFile(user, "testFilePath");
		Clip clip = datasource.createClip(track, file, 0);
		long clipID = clip.getId();
		datasource.deleteClip(clipID);
		assertNull(datasource.getUserById(clipID));
		datasource.deleteTrack(track.getId());
		datasource.deleteSequence(seq.getId());
		datasource.deleteUser(1234567890);
	}

}
