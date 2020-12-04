# Code Review Report

## Front-end: calendar, aggregrate-calendar, cell, editable-cell (reviewed by Melody)
- For almost every page, there is an if check at the beginning of the file that checks for username cookie. This violates the DRY principle. The if-statement is moved to cookie-manager.js and imported to other files. 
- Similar to the change above, there are many duplicating functions reused in pages with similar layouts. These functions are consolidated to one of the files in under /service.
- When aggregated-calendar.js was created, the html code was copied directly from calendar.js. The only part different between the two calendar types is the body content. The file calendar-template.js was created to be imported into aggregate-calendar and calendar as the template for the calendars. 
- Added more comments to the functions and gave more meaningful names to variables for readability. 
- Other than duplications, these front-end files follow SOLID principles. The purpose for each component/page/service is cohesive. 

## Front-end: services, profile (reviewed by Sion)
-	There aren’t any glaring code smells. The profile page has a long method, but that is a result of styling and is completely fine. 
-	Code seems to be simple enough and can’t be simplified much. 
-	There aren't many tests, but there are log reports here and there for testing purposes. Also, front end is kind of difficult to test and can be done by just looking at the site.
-	Calendar-manager.js and getcookie.js can have better naming as some of the variable names are non-descriptive. 
-	There aren’t really any comments, so I have added some for better clarity.
-	Style is good. Indents and spaces are where they should be.

## Server.java (reviewed by Everett)
- Server.java follows the SOLID principles. It contains a single responsibility (to handle all server requests and responses). It is specifically designed to be open for extension but closed for modification. It is not extended, and does not extend any classes and thus avoids substitution problems. It does not implement any interfaces and thus avoids any interface-related problems. Although it does not use abstractions such as interfaces to aid in dependency inversion, Server.java keeps its inner workings enclosed and hidden in a way that allows it to be used without needing to know its aforementioned workings.
- At the same time, the file also expresses certain code smells. In particular, the class is large and contains examples of duplicate code. The size of the class does not indicate a violation of the single responsibility principle in this case, as all methods are pertinent to the class's single responsibility of handling server requests and responses. The duplication of code, however, may be remediable and does present a violation of the DRY design principle. As a quick side note, it is also worth noting that there is a high volume of primitive types, but this is more a consequence of the nature of req.
- Despite the presence of many methods, the methods in question are understandable and not overly complex. In terms of proper usage, the main factors of confusion are a lack of commentary and unit tests; naming has been refactored to be more intuitive, and will soon be followed by commentary and unit test updates.

## Persistence and Model packages (reviewed by Tyler)
- Every implementation of a DAO interface contains methods that perform CRUD operations. In every method across every implementation, a connection is opened in an identical try-with-resources statement, and any exceptions are caught in the catch clause. This is the DRY code smell, as the same code is repeated in several instances, contributing to bloat. To resolve the DRY issue, a static helper method (Transaction.execute()) which runs a Function wrapped within a try-with-resources statement. It does this by accepting a Function as a parameter, and the code block constituting the DAO query is passed via lambda statement into execute() which is run using the apply() method of Function in execute(). Because the DAO interfaces have a one-to-one correspondence with their implementations,the Interface segregation principle is satisfied by default.
- The DAO pattern implemented here complies with the Single Responsibility Principle, as each DAO is responsible only for persistence of its respective model class (Sql2oCalendarDao is responsible for persistence of Calendars, etc.). The pattern also complies with the Open-Closed Principle, as accomodating queries for a new model class is accomplished by creating a new DAO interface and implementation, rather than modifying an existing class. 
- The reviewed code has an appropriate level of complexity and is readable.
- Style is good, and variable naming is clear and thoughtful.
- There were no tests, but they were implemented while refactoring.
- There were no comments. but they were implemented while refactoring.
