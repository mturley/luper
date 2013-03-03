CS320 Spring 2013 Software Engineering Project: Luper
=====================================================

This is the source code for the Luper android application and
accompanying database server and web client.

So far, this code is Mike Turley and anyone who feels like joining, tinkering with tools and ideas for certain implementation details with a naive knowledge of the final architecture.
-----------------------------------------

Most if not all of what I'm writing now will be refactored by the group, and any of it is subject to being dropped if the group determines there's a better solution.

Alpha Release Deadline: March 5, 2013
-------------------------------------

Contributing Developers: Getting Started
========================================

1. Clone the luper repo somewhere.

2. Make sure you have a working ADT Bundle.  Launch Eclipse.

3. Use File -> Import -> Existing Projects Into Workspace to import the luper/luper-android subdirectory (the main android app)

4. Use File -> New -> Project... and then Android -> Android Project From Existing Code to import the luper/actionbarsherlock subdirectory (the action bar dependency.  read the LuperApp.java comments for more details)

5. Right-click on Luper in the projects list and Run As -> Android Application

6. Create a new AVD if you haven't already, or connect a device to ADB.  The app will compile, install and launch in the emulator or on your ADB-connected device.


Changelog:
==========

2/22/13: Created Hello World Android app to build on.