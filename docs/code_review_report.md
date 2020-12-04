# Code Review Report

## Front-end: calendar, aggregrate-calendar, cell, editable-cell
- For almost every page, there is an if check at the beginning of the file that checks for username cookie. This violates the DRY principle. The if-statement is moved to cookie-manager.js and imported to other files. 
- Similar to the change above, there are many duplicating functions reused in pages with similar layouts. These functions are consolidated to one of the files in under /service.
- When aggregated-calendar.js was created, the html code was copied directly from calendar.js. The only part different between the two calendar types is the body content. The file calendar-template.js was created to be imported into aggregate-calendar and calendar as the template for the calendars. 

## Front-end: services, profile 
-	There aren’t any glaring code smells. The profile page has a long method, but that is a result of styling and is completely fine. 
-	Code seems to be simple enough and can’t be simplified much. 
-	There aren't many tests, but there are log reports here and there for testing purposes. Also, front end is kind of difficult to test and can be done by just looking at the site.
-	Calendar-manager.js and getcookie.js can have better naming as some of the variable names are non-descriptive. 
-	There aren’t really any comments, so I have added some for better clarity.
-	Style is good. Indents and spaces are where they should be.
