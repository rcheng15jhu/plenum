# MyBooksApp

This is a simple application we build during lectures in fall 2020 OOSE class together to practice with various concepts and technologies. This 
is a web app conforming to Client-Server Architecture where user(s) can store their favorite books and authors. The app
will store data in a database and its backend functionalities are implemented as RESTful API end-points.

Work Done:
- implemented CSS styling on the pages dedicated to adding authors or books
- modified provided css, right justifying the labels and displaying in an invisible table, and centering the whole box
- created validation functions for ISBN, author name, and title via regex and incorporated into author and book forms
- created a new parameter to the top.vm template, where the .css file used to render the page would depend on that new parameter
- added delete buttons for each book in the books view
- added submission forms in books and authors view
- created onclick handler to refresh page instead of redirecting after submission
- implemented CSS styling on the pages dedicated to listing authors or books, as well as the CSS of the landing page, used some of the provided CSS, but also incorporated other CSS, for example with use in the input textboxes

Assumptions Made:
- Names do not contain apostrophes or hyphens
- Validation not necessary for addbook and addauthor routes, only the forms in books and authors
