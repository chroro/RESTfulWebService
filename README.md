	This project is my solution for the following problem: 

Problem definition

Create a tiny RESTful web service with the following business requirements:

Application must expose REST API endpoints for the following functionality:

apply for loan (loan amount, term, name, surname and personal id must be provided)
list all approved loans
list all approved loans by user

Service must perform loan application validation according to the following rules and reject application if:
Application comes from blacklisted personal id
N application / second are received from a single country (essentially we want to limit number of loan applications coming from a country in a given timeframe)
Service must perform origin country resolution using a web service (you should choose one) and store country code together with the loan application. Because network is unreliable and services tend to fail, let's agree on default country code - "lv".
Technical requirements

You have total control over framework and tools, as long as application is written in Java. Feel free to write tests in any JVM language.

What gets evaluated

Conformance to business requirements
Code quality, including testability
How easy it is to run and deploy the service (don't make us install Oracle database please ;)

	The project was created using Spring Boot and Eclipse IDE. The project was tested using JUnit. The program can be run as a Java Application. 
	
	Most part of the program is straightforward, the only interesting part of the program can be ApplicationValidationService class. This class is responsible for detecting N applications from the same country per time frame. Time frame (limitTimeFrame) and N (limitNumber) can be configured within the class. Default values are 3 applications and 1000 millisecond. 

