Mike's TODO list for development:
=================================

1. Write getSequenceById, and other get_____ById methods in SQLiteDataSource
2. use those getters in LuperProjectEditorActivity to load the sequence data for rendering
3. implement the proper fields in the data classes (Sequence, Track, Clip, etc) for wiring up to the database
4. Wire up all the setter methods in the data classes so that they actually update the SQLiteDataSource (in the background).
5. Build a sync routine for updating the cloud database with local changes, and updating the local database with cloud changes (less important)

	a) make use of the isDirty flags, always set dirty on setter call

	b) query for all things that are dirty, then serialize a digest of all that.  JSON perhaps?

	c) send the JSON up via the HttpRestAPI, using an endpoint established in PHP on luper-web

	d) in the PHP, decode the JSON and convert it to a series of INSERT and UPDATE queries

	e) send these JSON digests in a queue with something like a 15 second delay.

	f) after the queue has been emptied, calulate the sha1 or md5 checksum of the entire local database and server database (or just the rows that were altered) and compare them.

		i. in the event of mismatch, reattempt a sync.  If the mismatch persists, ask the user if they want the server version or the phone version.
6. Register the sync routine properly as a passive notification (so it shows up in the pulldown).
7. Make the sync frequency be a configurable field in the settings.  Also make it possible to disable sync entirely.
