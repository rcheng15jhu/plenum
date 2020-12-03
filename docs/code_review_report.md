## Code Review Report ##

#Front-end (Javascript files)#
- For almost every page, there is an if check at the beginning of the file that checks for username cookie. This violates the DRY principle. The if-statement is moved to cookie-manager.js and imported to other files. 
- Other miscellaneous code duplications like above. 

