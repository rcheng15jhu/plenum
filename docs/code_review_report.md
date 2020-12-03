## Code Review Report ##

#Front-end (Javascript files)#
- For almost every page, there is an if check at the beginning of the file that checks for username cookie. This violates the DRY principle. The if-statement is moved to cookie-manager.js and imported to other files. 
- Other miscellaneous code duplications like above. 

This part of the review is for the services directory and profile.js. 
-	There aren’t any glaring code smells. The profile page has a long method, but that is a result of styling and is completely fine. 
-	Code seems to be simple enough and can’t be simplified much. 
-	I don’t see many tests, but there are log reports here and there for testing purposes. 
-	Calendar-manager.js and getcookie.js can have better naming as some of the variable names are non-descriptive. 
-	There aren’t really any comments. 
-	Style is good. Indents and spaces are where they should be.
