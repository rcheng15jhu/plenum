## What has changed since original project proposal?
* Most if not all the must have features remained intact, except the import calendar feature, which we sadly did not have time to complete
* Must have features were expanded upon, so not one single must have feature would contain many implied features inside of it
* A lot more nice to have features were added as we thought more about what features may be helpful for users
* Our UI design has changed from the original wireframes

## Features delivered
* Ability to sign up, log in, and log out securely 
* Creating and saving named calendars which encode availability that are tied to user account
* Editing and deleting existing user calendars
* Creating and saving user-generated named events and time ranges
* Adding a calendar to an event 
* Changing calendar that is associated with an event
* Viewing aggregate availability for event based on all associated calendars, with Increasingly lighter hues for less availability and darker hues for more availability
* Viewing specific user availability for a particular time-slot via tooltip
* Easily view the full list of calendars/events and preview each of them
* A public events list, which you may browse if you need to join any event
* A link to share an event for others to add their availability to	
* Dashboard page
* Profile page, and ability to change profile information
* Salting and hashing of user passwords, as well as ability to securely change password

## Features not delivered
* Means of deciding on meeting time within the app itself
* Accommodating non-homogeneous timezones
* Linking with other websites to pull schedules
* App / AI suggested meeting times for an event
* Storing time slots for each day and being able to combine them
* Storing time slots for specific days and being able to combine them
* Storing time slots for each ‘activity’ and being able to combine them
* Uploading calendars to events as templates and having each event contain their own strict time availability (derived calendars)
* Being able to specify events on specific weeks / days of the year, or on only certain days of the week



## Briefly note the challenges you have had (and how you have overcome/dealt with them)
* Generating production-grade static content with Create React App (CRA) was a major hurdle initially due to the opinionated assumptions made about the style of application being developed. Specifically, CRA assumed that all apps are single page applications, with a single entry point. This was addressed by abandoning CRA and adopting a custom Webpack toolchain that generated transpiled and minified JS chunks and HTML boilerplate with automatically determined script tags and a “root” DOM node to serve from SparkJava
* Figuring out what exactly needed to be done to build this app and what features it would need, which was addressed by exploring more user stories
* Assigning tasks to do each iteration, which was partially addressed by assigning general roles for each team member (e.g. front end, back end)
* A lack of knowledge about React, which was addressed by everyone spending a week or so learning learning React on fullstackopen.com and referring to documentation a lot
* Extremely long Webpack build times at first, which was addressed with dynamic code splitting in which code that is shared among multiple JS files is extracted into vendor and component chunks, and appropriately injected into automatically generated HTML; this ensures that chunks are not unnecessarily large and transpilation of duplicate code does not occur
* Using different databases types in development vs production, which was addressed by provisioning an AWS PostgreSQL database to use in development
* After abandoning CRA, ensuring that changes made during development could be observed instantly without running npm run build, which was addressed by setting up a proxy when accessing endpoints/files on a front end development server with hot reloading on localhost:3000, which redirected some requests to the back end development server on localhost:7000, and redirected some requests to live Webpack transpilation files.

## Reflect on how you would do it again if you could go back in time and start at iteration-1
* Set up the entire development environment and build process first, for easier development and feedback while in a sprint
* Spending more time as a group thinking about how the app would be used by the end user (ideate user stories)
* Making sure everyone is on the same page, in terms of the vision of the app being developed, each person’s technical skills, and how tasks should be coordinated, at the beginning of development to prevent a slower start.
