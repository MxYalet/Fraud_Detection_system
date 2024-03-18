# Real-Time Fraud Detection System

## Description
This prototype implements a real-time fraud detection system for analyzing transaction streams to identify potential fraudulent patterns. It processes a stream of transactions represented as a series of events, each containing a timestamp, transaction amount, user ID, and service ID. 

## Algorithm
The system identifies fraudulent patterns based on the following rules:
1. User conducting transactions in more than 3 distinct services within a 5-minute window.
2. Transactions that are 5 times above the user's average transaction amount in the last 24 hours.
3. A sequence of transactions indicating "ping-pong" activity (transactions bouncing back and forth between two services) within 10 minutes.

## Instructions to Run
1. Compile the Java files: `javac RealTimeFraudDetectionSystem.java`
2. Run the program: `java RealTimeFraudDetectionSystem`

## Handling Out-of-Order Events
The system handles out-of-order events by updating the user's transaction history based on the event's timestamp. It ensures that the last transaction time is correctly updated, allowing the system to process events in real-time without relying on external frameworks.

## Test Dataset
Included in the source code.

## Expected Results
Alerts are generated for suspicious activities based on the provided test dataset.

