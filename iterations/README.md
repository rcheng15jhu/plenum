# Plenum

This package represents the fourth iteration of Plenum, a simple peer-scheduling application. In order to use this
iteration of Plenum, the main function of Server.java can be ran, and after server initialization, the current
frontend of the application can be accessed at localhost:3000. Alternatively, the current working build of the project can be found in Heroku at http://warm-ridge-98252.herokuapp.com/

Additionally, work was done with webpack and github actions to ease the development process, including defining what endpoints to build, and what folders the compiled files should automatically move to. More work was done to set up a proxy to ensure that the dev and build environments could use the same endpoints without much issue.

The available routes in main are the root route which displays a simple message,
"api/calendars" which gets a list of calendars as a JSON file,
"api/calendar" which takes a calendar ID and gets the availabilities of the corresponding calendar,
"api/aggregate" which takes an event ID and gets an aggregate of all availabilities associated with the event,
"api/addcalendar" which adds (through post) a new calendar,
"api/delcalendar" which takes an ID and deletes (through post) the corresponding calendar,
"api/events" which gets a list of events as a JSON file, 
"api/addevent" which adds (through post) a new event,
"api/delevent" which takes an ID and deletes (through post) the corresponding event,
"viewevent" which allows the user to view aggregate availability.
"users" which gets a list of users as a JSON file,
"adduser" which adds (through post) a new user,
"deluser" which takes an ID and deletes (through post) the corresponding user,
"availabilities" which gets a list of availabilities as a JSON file,
"addavailability" which adds (through post) a new user,
"delavailability" which takes an ID and deletes (through post) the corresponding availability,
"api/updateavailability", which updates the availability corresponding to supplied parameters, and
"api/addconnection", which creates a new connection based off of event, calendar, and user parameters.
