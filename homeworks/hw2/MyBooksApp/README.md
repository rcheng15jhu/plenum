# MyBooksApp

The main functionality of this package is the representation of authors and corresponding books in a way that allows
persistence through the use of SQL databases and CRUD operations (we use sql2o for our ORM.) In addition to the main
functionality as mentioned before, this package also includes various tests for the implementations devised. This
package establishes a basis for setting up client-server architecture in the future.

We make the general assumption that all objects we add to the database have their requisite values (e.g. name, ISBN,
author, etc.)