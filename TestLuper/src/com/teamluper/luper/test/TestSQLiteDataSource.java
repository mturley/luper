package com.teamluper.luper.test;

import java.util.List;
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
		datasource.deleteUser(user.getId());
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
		datasource.deleteUser(user.getId());
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
		datasource.deleteUser(user.getId());
	}
	
	/**
	 * Create a user, try fetching the entry with a query (in this case by username)
	 * and make sure the original user and fetched one are the same
	 */
	public void testGetUserWhere(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");		
		User user2 = datasource.getUserWhere("username = '" + user.getUsername() + "'");
		assertNotNull(user2);
		assertEquals("IDs of user and user2 should be the same", user.getId(), user2.getId());
		assertEquals("usernames of user and user2 should be the same", user.getUsername(), user2.getUsername());
		assertEquals("emails of user and user2 should be the same", user.getEmail(), user2.getEmail());
		datasource.deleteUser(user.getId());
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
	 * Create a user, make the user active, get active user from the database,
	 * make sure the returned user is the sae as original one
	 */
	public void testGetSetActiveUser(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		datasource.setActiveUser(user);
		User user2 = datasource.getActiveUser();
		assertNotNull(user2);
		assertEquals("IDs of user and user2 should be the same", user.getId(), user2.getId());
		assertEquals("usernames of user and user2 should be the same", user.getUsername(), user2.getUsername());
		assertEquals("emails of user and user2 should be the same", user.getEmail(), user2.getEmail());
		datasource.deleteUser(user.getId());		
	}
	
	/**
	 * Create a user, call logoutActive User,
	 * fetch active user and make use null is returned
	 */
	public void testLogoutActiveUser(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		datasource.logoutActiveUser();
		User user2 = datasource.getActiveUser();
		assertNull(user2);
		datasource.deleteUser(user.getId());
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
		datasource.deleteUser(user.getId());
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
		datasource.deleteUser(user.getId());
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
		datasource.deleteUser(user.getId());
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
		datasource.deleteUser(user.getId());
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
		datasource.deleteUser(user.getId());
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
		datasource.deleteUser(user.getId());
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
		datasource.deleteUser(user.getId());
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
		datasource.deleteUser(user.getId());
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
		datasource.deleteUser(user.getId());
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
		Clip clip = datasource.createClip(track, file, 0, 0);
		assertNotNull(clip);
		assertEquals("ownerUserID should be same as userID", user.getId(), clip.getOwnerUserID());
		assertEquals("parentTrackID should be same as trackID", track.getId(), clip.getParentTrackID());
		assertEquals("audioFileID should be same as fileID", file.getId(), clip.getAudioFileID());
		datasource.deleteClip(clip.getId());
		datasource.deleteTrack(track.getId());
		datasource.deleteSequence(seq.getId());
		datasource.deleteUser(user.getId());
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
		Clip clip = datasource.createClip(track, file, 0, 0);
		long clipID = clip.getId();
		Clip clip2 = datasource.getClipById(clipID);
		assertNotNull(clip2);
		assertEquals("IDs of clip and clip2 should be the same", clipID, clip2.getId());
		datasource.deleteClip(clip.getId());
		datasource.deleteTrack(track.getId());
		datasource.deleteSequence(seq.getId());
		datasource.deleteUser(user.getId());
	}
	
	/**
	 * Create clip, delete the clip by ID, make sure the clip entry doesn't exist anymore
	 */
	public void testDeleteClip(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		Sequence seq = datasource.createSequence(user, "testSequence");
		Track track = datasource.createTrack(seq);
		AudioFile file = datasource.createAudioFile(user, "testFilePath");
		Clip clip = datasource.createClip(track, file, 0, 0);
		long clipID = clip.getId();
		datasource.deleteClip(clipID);
		assertNull(datasource.getUserById(clipID));
		datasource.deleteTrack(track.getId());
		datasource.deleteSequence(seq.getId());
		datasource.deleteUser(user.getId());
	}
	
	
	/**
	 * Create 2 sequences, fetch a list of all sequences from the database.
	 * Note that the database has a dummy project hardcoded, so one sequence already
	 * exists in the sequence table and the size of returned list will be 3 and not 2.
	 * Check that list size is 2, and sequences on the list are the same as created ones
	 */
	public void testGetAllSequences(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		Sequence seq1 = datasource.createSequence(user, "testSequence1");
		Sequence seq2 = datasource.createSequence(user, "testSequence2");
		List<Sequence> list = datasource.getAllSequences();
		
		assertNotNull(list);
		assertEquals("size of list should be 2", 3, list.size());
		assertEquals("IDs of seq1 and first sequence on the list should be the same", seq1.getId(), list.get(1).getId());
		assertEquals("IDs of seq2 and second sequence on the list should be the same", seq2.getId(), list.get(2).getId());
		
		datasource.deleteSequence(seq1.getId());
		datasource.deleteSequence(seq2.getId());
		datasource.deleteUser(user.getId());
	}
	
	/**
	 * Create 2 tracks with the same parent sequence, 
	 * fetch a list of all tracks from the database by the parent sequence ID,
	 * check that list size is 2, and tracks on the list are the same as created ones
	 */
	public void testGetTrackBySequenceId(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		Sequence seq = datasource.createSequence(user, "testSequence");
		Track track1 = datasource.createTrack(seq);
		Track track2 = datasource.createTrack(seq);
		
		List<Track> list = datasource.getTracksBySequenceId(seq.getId());
		assertNotNull(list);
		assertEquals("size of list should be 2", 2, list.size());
		assertEquals("IDs of track1 and first track on the list should be the same", track1.getId(), list.get(0).getId());
		assertEquals("IDs of track2 and second track on the list should be the same", track2.getId(), list.get(1).getId());
		
		datasource.deleteTrack(track1.getId());
		datasource.deleteTrack(track2.getId());
		datasource.deleteSequence(seq.getId());
		datasource.deleteUser(user.getId());		
	}
	
	/**
	 * Create 2 clips with the same parent track, 
	 * fetch a list of all clips from the database by the parent track ID,
	 * check that list size is 2, and clips on the list are the same as created ones
	 */
	public void testGetClipsByTrackId(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		Sequence seq = datasource.createSequence(user, "testSequence");
		Track track = datasource.createTrack(seq);
		AudioFile file = datasource.createAudioFile(user, "testFilePath");
		Clip clip1 = datasource.createClip(track, file, 0, 0);
		Clip clip2 = datasource.createClip(track, file, 0, 0);
		
		List<Clip> list = datasource.getClipsByTrackId(track.getId());
		assertNotNull(list);
		assertEquals("size of list should be 2", 2, list.size());
		assertEquals("IDs of clip1 and first clip on the list should be the same", clip1.getId(), list.get(0).getId());
		assertEquals("IDs of clip2 and second clip on the list should be the same", clip2.getId(), list.get(1).getId());
		
		datasource.deleteClip(clip1.getId());
		datasource.deleteClip(clip2.getId());
		datasource.deleteTrack(track.getId());
		datasource.deleteSequence(seq.getId());
		datasource.deleteUser(user.getId());
	}
	
	/**
	 * Create 2 audio files with the same owner user, 
	 * fetch a list of all audio files from the database by the owner user ID,
	 * check that list size is 2, and audio files on the list are the same as created ones
	 */
	public void testGetAudioFilesByUserId(){
		User user = datasource.createUser(1234567890, "testUser", "testUser@mail.com");
		AudioFile file1 = datasource.createAudioFile(user, "testFilePath1");
		AudioFile file2 = datasource.createAudioFile(user, "testFilePath2");
		
		AudioFile[] arr = datasource.getAudioFilesByUserId(user.getId());
		assertNotNull(arr);
		assertEquals("size of list should be 2", 2, arr.length);
		assertEquals("IDs of file1 and first audio file on the list should be the same", file1.getId(), arr[0].getId());
		assertEquals("IDs of file2 and second audio file on the list should be the same", file2.getId(), arr[1].getId());
		
		datasource.deleteAudioFile(file1.getId());
		datasource.deleteAudioFile(file2.getId());
		datasource.deleteUser(user.getId());
	}

}
