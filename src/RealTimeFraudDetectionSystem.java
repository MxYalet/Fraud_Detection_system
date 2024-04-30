import java.util.*;
import java.util.List;

public class RealTimeFraudDetectionSystem {

    private static final Map<String, UserTransactionHistory> userTransactionHistoryMap = new HashMap<>();

    // Constants for fraud detection rules
    private static final int MAX_DISTINCT_SERVICES_WITHIN_WINDOW = 3;
    private static final double TRANSACTION_AMOUNT_MULTIPLIER = 5.0;
    private static final int PING_PONG_ACTIVITY_WINDOW = 10 * 60; // 10 minutes in seconds
    private static final int USER_ACTIVITY_WINDOW = 24 * 60 * 60; // 24 hours in seconds


    private static void processEvent(Event event) {
        // Get the user's transaction history
        UserTransactionHistory userHistory = userTransactionHistoryMap.getOrDefault(event.userID, new UserTransactionHistory());

        // Update user's transaction history
       // userHistory.update(event);

        // Check for fraudulent patterns
        if (userHistory.isDistinctServicesExceeded(MAX_DISTINCT_SERVICES_WITHIN_WINDOW)) {
            generateAlert("Suspicious activity detected: User " + event.userID + " conducting transactions in more than 3 distinct services within a 5-minute window.");
        }

        if (userHistory.isHighTransactionAmount(TRANSACTION_AMOUNT_MULTIPLIER)) {
            generateAlert("Suspicious activity detected: User " + event.userID + " conducting transactions that are 5x above their average transaction amount in the last 24 hours.");
        }

        if (userHistory.isPingPongActivity(PING_PONG_ACTIVITY_WINDOW)) {
            generateAlert("Suspicious activity detected: User " + event.userID + " involved in 'ping-pong' activity (transactions bouncing back and forth between two services) within 10 minutes.");
        }

        // Update user's transaction history in the map
        userTransactionHistoryMap.put(event.userID, userHistory);
    }

    // Method to generate an alert for suspicious behavior


    private static void generateAlert(String message) {
        System.out.println("ALERT: " + message);
    }

    public static void main(String[] args) {
        // Simulated transaction stream represented as a series of events
        Event[] transactionStream = {
                new Event(1617906000, 150.00, "user1", "serviceA"),
                new Event(1617906060, 4500.00, "user2", "serviceB"),
                new Event(1617906120, 75.00, "user1", "serviceC"),
                new Event(1617906180, 3000.00, "user3", "serviceA"),
                new Event(1617906240, 200.00, "user1", "serviceB"),
                new Event(1617906300, 4800.00, "user2", "serviceC"),
                new Event(1617906360, 100.00, "user4", "serviceA"),
                new Event(1617906420, 4900.00, "user3", "serviceB"),
                new Event(1617906480, 120.00, "user1", "serviceD"),
                new Event(1617906540, 5000.00, "user3", "serviceC"),

                // Additional transactions to simulate scenarios for user1, user2, and user3
                new Event(1617906600, 300.00, "user1", "serviceE"), // User1 - More than 3 distinct services in 5 minutes
                new Event(1617906720, 10000.00, "user2", "serviceD"),
                new Event(1617906840, 6000.00, "user3", "serviceE")
        };

        // Process transaction stream
        for (Event event : transactionStream) {
            processEvent(event);
        }
    }

}


