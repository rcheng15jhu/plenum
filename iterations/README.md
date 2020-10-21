# Plenum

This package represents the second iteration of Plenum, a simple peer-scheduling application. In order to use this
iteration of Plenum, the main function of Server.java should be ran, and after server initialization, the current
frontend of the application can be accessed at localhost:7000, although in the future, there will be a fully web-based
frontend.

The available routes in main are the root route which displays a simple message,
"calendars" which gets a list of calendars as a JSON file,
"calendar" which takes an ID and gets the corresponding calendar,
"addcalendar" which adds (through post) a new calendar,
"delcalendar" which takes an ID and deletes (through post) the corresponding calendar,
"makecalendar" which returns the HTML corresponding to the page for calendar creation,
"viewstaticcalendar" which returns the HTML corresponding to the page for viewing a static (uploaded JSON) calendar,
"events" which gets a list of events as a JSON file, 
"addevents" which adds (through post) a new event,
"delevents" which takes an ID and deletes (through post) the corresponding event,
"viewevent" which returns the HTML corresponding to the page for viewing an (uploaded JSON) event,
"users" which gets a list of users as a JSON file,
"adduser" which adds (through post) a new user,
"deluser" which takes an ID and deletes (through post) the corresponding user,
"maketemplate" which gets and takes you to the template creation screen (frontend.)
"availabilities" which gets a list of availabilities as a JSON file,
"addavailability" which adds (through post) a new user, and
"delavailability" which takes an ID and deletes (through post) the corresponding availability