/**
 *  luper-web.js
 *  
 *  This is the Luper database server and web client.
 *  It is built with Meteor.js, a unified client/server realtime webapp
 *  framework with really flexible APIs, running on node.js and MongoDB.
 *
 *  to be deployed onto an Amazon S3 server as a node.js package
 *  generated with the mrt bundle command.  To run locally:
 *    1. Install Meteor and Meteorite
 *        $ curl https://install.meteor.com | /bin/sh
 *        $ sudo npm install -g meteorite
 *    2. Navigate to the luper-web directory and run `mrt`
 *        $ cd ~/projects/luper/luper-web
 *        $ mrt
 *  
 *  NOTE: Those unfamiliar with Meteor, before diving in:
 *        1. Watch this screencast: http://meteor.com/screencast
 *        2. (optional) watch the sequel: http://meteor.com/authcast
 *        3. Read the docs: http://docs.meteor.com/
 *           focus on the concepts and principles, you can skim the API.
 *        4. Set up and familiarize yourself with Meteorite,
 *           a community smart package manager for Meteor.
 *           https://github.com/oortcloud/meteorite
 *        5. Explore the Atmosphere, which is Meteorite's
 *           package directory: https://atmosphere.meteor.com/
 *  NOTE: Meteorite doesn't work properly on Windows yet.
 *        If you must develop on Windows, you'll need a Linux VM.
 *
 *  I plan to use meteor-router to expose the MongoDB database with
 *  an authenticated REST interface that the Android client can
 *  access with its Spring RestTemplate.
 *  
 *  If anyone wants to help with this part of the application
 *  (or convince me not to use Meteor, I'm a bit biased towards it)
 *  just let me know!
 *                               - Mike <mike@miketurley.com>
 */

if (Meteor.isClient) {
  Template.body.loggedIn = function() {
    return Meteor.user() !== null;
  };
}

if (Meteor.isServer) {
  Meteor.startup(function () {
    // code to run on server at startup
  });
}
