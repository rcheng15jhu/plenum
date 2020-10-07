# Quorum

This package represents the first iteration of Quorum, a simple peer-scheduling application. In order to use this
current iteration of Quorum, the main function of Server.java should be ran, although in future iterations, there will
be a frontend similar to that of test_checkbox.html, and exemplified in the template view page (which can be accessed
by running npm start in the project directory.)

The available routes in main are the root route which displays a simple message,
"calendars" which gets a list of calendars as a JSON file,
"calendar" which takes an ID and gets the corresponding calendar,
"addcalendar" which adds (through post) a new calendar,
"delcalendar" which takes an ID and deletes (through post) the corresponding calendar,
"events" which gets a list of events as a JSON file, 
"addevents" which adds (through post) a new event,
"delevents" which takes an ID and deletes (through post) the corresponding event,
"users" which gets a list of users as a JSON file,
"adduser" which adds (through post) a new user,
"deluser" which takes an ID and deletes (through post) the corresponding user, and
"maketemplate" which gets and takes you to the template creation screen (frontend.)