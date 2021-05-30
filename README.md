# Live Server
Simple and light weight implementation of Live Server. 

## Introduction
This is a light weight implementation of Live Server using Java and JavaScript. It works similar to the Live Server Plugin available in VS Code. This is a varaiation of the [Local Server](https://github.com/satanic-devil/Local-Server). This works with Notepad too!

## How to Compile
Run the following command
```bash
  javac LocalServer.java
```

## How to Run
Run the following command to run
```bash
  java LocalServer
```


## How to Access the Server
1. Open browser on any device connected to the server/computer
2. Get the address of the server/computer running the program
3. Type the address in the address bar followed by ":9000"

## Changes in the code as compared to Local Server 
The *BaseServer* class is the Base class for both the *LiveServer* and the *LocalServer* as both perform the exact function only difference arises in the output that has to be sent which is taken care by the abstract method implemented by both the classes.
