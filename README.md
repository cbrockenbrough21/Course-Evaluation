[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/DC1SF4uZ)
# Homework 6 - Responding to Change

## Authors
1) Riley Wilson, nvf5kp, rileywilson7
2) Catherine Brockenbrough, cqt5de, cbrockenbrough21
3) Laine Quillian, zcx6na, [zcx6na]
4) Olivia Warren, rgx6mx, owarren22

## To Run

Run CourseReviewsApplication.java to start the program. VM arguments should be --module-path [Path to JavaFX folder] --add-modules javafx.controls,javafx.fxml.

## Contributions

List the primary contributions of each author. It is recommended to update this with your contributions after each coding session.:

### Riley Wilson

* Built feature where you can search course by subject, number, and title
* Added close button on login page
* Converted timestamp to local time and added to reviews tables
* Added error messages for user creation
* Built original structure of database tables
* Helped edit database tables to new structure
* Tested that all wrong inputs give error messages
* Fixed crashes when inputting wrong types into course search
* Error messages for leading/reailing spaces

### Catherine Brockenbrough

* built section for users to add their own reviews, update them and delete them
* formatted fxml file for course review page, my reviews, course search page and course search page
* worked on passing activeUser and activeCourse between pages
* set up screen switch buttons, back buttons, log out button, my reviews button
* set up tableView for course review and my review page 
* made course review table clickable and go to the correct course's review page
* wrote database queries 

### Laine Quillian

* connected the add Course page to the database to add new Courses -- displayed appropriate error messages
    if the course was already added or if there was invalid input
* helped format the My Review fxml
* Reformatted Course Database for constraints at software rather than database level
* Displayed average review on the course search page
* Displayed average review for the active course in course reviews to simultaneously update
* Changed the password field in the login screen to block the text
* Formatted course search warming to only display with improper searches
* Add unique error messages to invalid input for adding a course

### Olivia Warren

* connected the Login page to the database to create new accounts, produce necessary error messages,
    log in an existing user, and ensure their correct data is pulled up when logged in
* reformatted some existing controllers to connect to the database through a service class
* formatted the fxml file/controller for login and create-user
* helped rework DatabaseConnection to allow for easier connections
* fixed major bug with connections in service class that was not allowing the database to be created
* created User and Review classes
* worked on bugs with passing activeUser between scenes
* wrote database queries for various service classes
* helped make MyReviews scene connect to the correct User's reviews as well as fixed a bug with passing activeCourse

## Issues

List any known issues (bugs, incorrect behavior, etc.) at the time of submission.
