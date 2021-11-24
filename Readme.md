
# Flexisaf Mini Project

*Author*: **Damilare Akinlose**

*Date*: **24/11/2021**

### Introduction

Flexi saf mini project

#### Technologies
Project is created with:
* Language: Java 8
* Framework: Springboot 2.6.0
* Package Manager: Maven
* Primary Database: Mongodb

### Architecture and Implementation
The feature/architecture of this service can be broadly classified into the following:

1. Create Student
   
      The service checks if student is between age bracket of 18 and 25 years before creating student
   
      The service generates Matric number on the fly
   
2. View Student
   
   Get Student and all Students using filters
   
3. Update Student
   
4. Delete Student
   
5. A scheduler job that runs 6 AM every morning to send Birthday message.
    
   Birthday message is sent via email

Environment config can be set in application.yml file


### Libraries
This service depends on a few libraries. You can check the [pom.xml](pom.xml) for a full list of the dependencies.

### Requirements
1. Mongo db url

## Running the service
Rub the service using:

mvn clean install package spring-boot:run
   
  
