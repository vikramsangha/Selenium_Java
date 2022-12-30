# About Framework

This Test Automation Framework is created using Java + Selenium Web Driver + TestNG. Which can be used across different web based applications. The framework is containerized using Docker and the tests can be run in following ways:

- Run tests on a local machine
- Run tests locally using docker
- Run tests on a remote selenium server

The framework is designed using Page Object Model. It has a page layer containing page classes and a test layer that contains test class and test suite files. The common properties of framework are defined in src/main/java/com/qa/config/config.properties


## Pre-requisite

- Java jdk-1.7 or higher
- Apache Maven 3 or higher

## Getting Started

For easiest way to getting started, extract this project and open it from Eclipse.


## Run tests on local machine

- Extract the project from git and open it in Eclipse.

- Make sure that remote_execution in src/main/java/com/qa/config/config.properties is set as false.

- Go to src/test/java/com/qa/suites/ and execute the suite.xml file.

## Run tests locally using docker

- Extract the project from git

- Build a docker image using : 
	
	```bash
	$ docker build -t <your_image_name> .
	```
	
	The image is built by customizing the selenium/standalone-chrome image from Selenium.
	
- Run the image using docker run command and pass the test name as docker environment variable.

	```bash
	$ docker run -it --shm-size="2g" -it -e test=<testclass>#<testmethod> <your_image_name>
	```
	
	For example, the test class name inside the framework is TestClass.java and one of the test method name is smokeTest. Now in order to execute smoke test using docker image , execute the following command:
	
	```bash
	$ docker run -it --shm-size="2g" -it -e test=TestClass#smokeTest <your_image_name>
	```
	or for another test called secondHighestTelevision, execute: 
	
	```bash
	$ docker run -it --shm-size="2g" -it -e test=TestClass#secondHighestTelevision <your_image_name>
	```
	
	The output of the docker run command will either be status code 0 or 1. If the tests has passed the status code will be 0. 
	The test report can be extracted from the contianer by using docker copy commands. The report and screenshots will be availbale at /opt/selenium/reports inside container. In order to copy the report following command can be used :
	
	```bash
	$ docker cp <your_container_id>:/opt/selenium/reports/ .
	```
	
	Container id can be fetched from 
	
	```bash
	$ docker ps
	```
	
	Other options that can be passed to the image are:
	 - headless=true -> This can be passed to image using -e headless=true or -e headless=false to decide the mode in which chrome will be launched inside running container.
	 - record_execution=true -> This parameter is used to record video of the execution inside container. Video file can be found at /opt/selenium/video at the end of execution.
	 - remote_selenium_url=<url to grid or standalone server> -> This can be passed to docker contianer to run a test on any remote server that is up.
	

## Run tests on Selenium remote server

- Start a selenium remote server using docker:

	```bash
	$ docker run -d -p 4444:4444 --shm-size="2g" selenium/standalone-chrome:4.7.2-20221219
	```
- Make sure that remote_execution in src/main/java/com/qa/config/config.properties is set as true.

- Make sure that remote_selenium_url in src/main/java/com/qa/config/config.properties is pointing towards the machine where standalone-chrome was started.

- Go to src/test/java/com/qa/suites/ and execute the suite.xml file.


	


