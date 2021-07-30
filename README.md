# Read Me First
This is an application to retrieve Stock Quotes in real time.

# Getting Started
The application was compiled with JDK 1.8. In order to run the jar, simply download the [zip file](https://github.com/codechunker/stockquotes/blob/master/stockquotes.zip)  and run the command below, and you are good to go - just make sure you have Java
running on your system. The application runs on port 8091 but of course you can change the port in the ***application.properties*** file in the config folder.
 ```
java -jar stockquotes.jar
```
On your browser, go to [http://localhost:8091](http://localhost:8091) to access the page
# Flow, Concepts and Deductions
The application was built with the concept of Schedulers and Websockets. 
When a user adds a stock to watch, a request to is made to YahooFinance API to get the quotes for the stocks added and then goes ahead to push them to a topic.
this automatically gets reflected on the frontend because of the use of websockets and some other dependencies.

