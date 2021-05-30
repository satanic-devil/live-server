# Live Server
Simple and light weight implementation of Live Server. 

## Introduction
This is a light weight implementation of Live Server using Java and JavaScript. It works similar to the Live Server Plugin available in VS Code. This is a varaiation of the [Local Server](https://github.com/satanic-devil/Local-Server). It reloads pages automatically if there is any changes in the html or any of its related files. This works with Notepad too!

[Click here](https://youtu.be/2l7_yBnIEmE) to see the working of the project.

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
![Example Output of Running the Live Server](https://github.com/satanic-devil/output-files/blob/main/live-server-cmd.png?raw=true)

## How to Access the Server
1. Open browser on any device connected to the server/computer
2. Get the address of the server/computer running the program
3. Type the address in the address bar followed by ":9000"

![Example Output of Accessing the Server on Browser](https://github.com/satanic-devil/output-files/blob/main/live-server-browser.png?raw=true)

## Limitations
As of now only HTML, CSS and JS files are currently checked for changes.

## Changes in the code as compared to Local Server 
The *BaseServer* class is the Base class for both the *LiveServer* and the *LocalServer* as both perform the exact function only difference arises in the output that has to be sent which is taken care by the abstract method implemented by both the classes.
