# Plenum

This package represents the fourth iteration of Plenum, a simple peer-scheduling application. In order to use this
iteration of Plenum, the current deployed build of the project can be found in Heroku at
http://damp-garden-37477.herokuapp.com/. Alternatively, to access the code in a dev environment, the main function of
Server.java can be ran, and npm start can be run in the src/main/js folder, and after server initialization, the frontend
of the application can be accessed at localhost:3000. 

Additionally, work was done with webpack and github actions to ease the development process, including shortening build
times by building common chunks, manually deploying to heroku, and switching to postgresql on dev as well. UI was
improved, merging the view and edit calendar pages, and previewing events from the list-events page. Additionally,
calendars may now be successfully connected to events, and a live preview on the view-event page is in progress. A
view public event page was also created to make it easier to see what events there are, and a easy copy button is on
the view-event page to share the event with others, so that they may indicate their availability.

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
"api/updateavailability", which updates the availability corresponding to supplied parameters,
"api/addconnection", which creates a new connection based off of event, calendar, and user parameters,
"api/delconnection", which deletes a connection based off of the parameters,
"api/updateconnection", which changes the connections depending on the given parameters,
"api/changepassword", which allows the user to change their password
