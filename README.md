# TreeClient
TreeClient is client application for TreeManager webAPI allowing to manage a tree structure.

## Description

TreeClient provides console, text user interface for manage Tree. Communication with WebAPI are provided via RESTfull.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Application needs no additional prerequisites, naturally except Java Runtime Environment.


### Installing

You can use IDE or Maven in command line for make package:

```
mvn package
```

Attention! Application run integration tests which NEEDS running TreeManager on local server *http://localhost:8080*. Without that creating .jar pacakge will be unsuccessful. Tests could be ommited by use command:

```
mvn package -DskipTests
```

## Running the tests

Client has tests:
- Integration controllers layer tests with use TreeManager

Application run integration tests which NEEDS running TreeManager on local server.

Running tests 
```
mvn test
```



## How to use it?

### Start

Default package file is .jar, which could be started from command line

```
java -jar treeclient-0.0.1-SNAPSHOT.jar
```

After start application tries to connect with server on path saved in *ClientController* interface (default *http://localhost:8080*). In case of unsuccessful connection app will ask user for input new server path.

### Functionalities

Functionalities are mirror of TreeManager functionalities. Tree structure are get from webAPI after each modification and after application start. Menu are below.

1 - Add new leaf
2 - Change node value
3 - Move branch
4 - Remove node with children
5 - Remove node without children
6 - Set new server path
7 - Save tree in internal database
8 - Load tree from internal database


## Built With

* [Spring](https://projects.spring.io/spring-boot/) - The web framework used, version 1.4.1
* [Maven](https://maven.apache.org/) - Dependency Management
* [JUnit](http://junit.org/junit4/) - Unit testing framework


