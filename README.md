# Tomcat WebApp and Jaccard Indexing API

## Overview

The following repository contains an attempt at creating and designing an Apache Tomcat Web Application employing Java Servlets and JSP as well as an external API written for the processing of Documents.
The web client presents a basic user interface in which a user can upload a text document and submit it to be processed by the application. The application employs the JaccardAPI which has been designed to process a document into a set of shingles and with this, calculate a set of min hash values, storing the results using Db4O. The purpose of this project is to demonstrate a well articulated use of Object Orientated Programming. Message Queues are implemented using RabbitMQ and Worker threads are employed to process documents.

The following diagram depicts the overall system architecture:

![Imgur](https://i.imgur.com/4iNxZc4.png)

## Prerequisites

- [Apache Tomcat](http://tomcat.apache.org/)
- [Maven](https://maven.apache.org/)
- [RabbitMQ](https://www.rabbitmq.com/)

### Getting Started with RabbitMQ

If you are on OSX [Homebrew](https://brew.sh/) makes it easy to install various dependencies and manage services with [Brew Services](https://github.com/Homebrew/homebrew-services) such as RabbitMQ as well as various other dependencies such as databases - i.e. CouchDB or MySQL. To 
get started with RabbitMQ follow these instructions.

1. Update HomeBrew
```
brew update
```

2. Brew Install
```
brew install rabbitmq
```

3. Start the RabbitMQ Server
```
brew services start rabbitmq
```

To get setup using RabbitMQ on Windows please follow the instructions on the [RabbitMQ Installation Documentation](https://www.rabbitmq.com/install-windows.html). RabbitMQ offers a basic .exe installer that can be used to manage services - stopping, starting.

## Setup

Running a Maven Install inside the directory will generate the target directory and the associated war file - (Web Application Archieve). However this isn't necessary as I've included the associated war file and also the RMI Server Application jar file in this repository to avoid complications with different system environments and dependencies.

To use the application simply:

1. Clone this Respository
```
git clone https://github.com/damiannolan/jaccard-indexing-minhash-webapp.git
```

2. Start RabbitMQ as previously mentioned above

+ [Getting Started with RabbitMQ](#getting-started-with-rabbitmq)

3. Add the appropriate Db4O jar files to the tomcat lib directory.

4. Drag and Drop `Jaccard.war` into an Apache Tomcat Installation directory under the folder named webapps.

5. Start your Tomcat Server and navigate to `http://localhost:8080/Jaccard/`

#### OSX 
```
brew services start tomcat
```

#### Windows
```
-> C:\dev\tomcat\bin> startup.bat
```

### Note

As previously mentioned running an Maven Install will also generate the war file and target directory as well as download and install the dependencies needed for the application. You can also import the project into Eclipse and use the Maven plugin associated.

#### Maven Install
```
mvn install
```

#### Import into Eclipse
```
Eclipse -> File -> Import -> Maven -> Existing Maven Projects
```
