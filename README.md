CS320 Spring 2013 Software Engineering Project: Lüper
=====================================================

This is the source code for the Lüper android application and
accompanying database server and web client.

Lüper is an audio editing app for Android with cloud sync capabilities and social features.

Annotated Source Documentation
==============================
If you are reading this README at [www.teamluper.com/docs/](http://www.teamluper.com/docs/) rather than on github, you are looking at a generated webpage version of our source code with the comments aligned on the left side of the screen and the code stripped of comments on the right.  You can use the Jump To... menu in the upper right to switch from file to file.

Contributing Developers: Getting Started
========================================

1. Clone the luper repo somewhere.

2. Make sure you have a working ADT Bundle.  Launch Eclipse.

3. Use File -> Import -> Existing Projects Into Workspace to import the luper/luper-android subdirectory (the main android app)

4. Use File -> New -> Project... and then Android -> Android Project From Existing Code to import the luper/actionbarsherlock subdirectory (the action bar dependency.  read the LuperApp.java comments for more details)

5. Right-click on Luper in the projects list and Run As -> Android Application

6. Create a new AVD if you haven't already, or connect a device to ADB.  The app will compile, install and launch in the emulator or on your ADB-connected device.