import java.util.*;

class UserTransactionHistory {
    private long lastTransactionTime;
    private Map<String, Integer> distinctServicesMap;
    private List<Double> transactionAmounts;
    private static final int WINDOW_DURATION = 5 * 60;
    private static final int MAX_DISTINCT_SERVICES_WITHIN_WINDOW = 3; // Maximum distinct services within window


    public UserTransactionHistory() {
        this.lastTransactionTime = 0;
        this.distinctServicesMap = new HashMap<>();
        this.transactionAmounts = new ArrayList<>();
    }

    // Method to update user's transaction history based on a new event
    public void update(Event event) {
        long currentTime = event.timestamp;

        // Check for out-of-order events and update last transaction time accordingly
        if (currentTime > lastTransactionTime) {
            lastTransactionTime = currentTime;
        }

        // Update distinct services map
        distinctServicesMap.put(event.serviceID, 1);

        // Update transaction amounts list
        transactionAmounts.add(event.amount);
    }

    // Method to check if distinct services exceeded within a window
    public boolean isDistinctServicesExceeded(int maxDistinctServicesWithinWindow) {
        long currentTimestamp = System.currentTimeMillis() / 1000; // Current timestamp in seconds

        // Check if transactions occurred within the window duration
        return (currentTimestamp - lastTransactionTime) <= WINDOW_DURATION && distinctServicesMap.size() > MAX_DISTINCT_SERVICES_WITHIN_WINDOW;
    }

    // Method to check if the transaction amount is significantly higher than the user's average
    public boolean isHighTransactionAmount(double multiplier) {
        if (transactionAmounts.isEmpty()) {
            return false;
        }

        double averageAmount = transactionAmounts.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double lastAmount = transactionAmounts.get(transactionAmounts.size() - 1);

        return lastAmount > multiplier * averageAmount;
    }
    public boolean isPingPongActivity(int pingPongActivityWindow) {
        long currentTimestamp = System.currentTimeMillis() / 1000; // Current timestamp in seconds

        // Check if transactions occurred within the window duration
        if ((currentTimestamp - lastTransactionTime) <= WINDOW_DURATION && transactionAmounts.size() >= 2) {
            for (int i = 0; i < transactionAmounts.size() - 1; i++) {
                if (i % 2 == 0 && !Objects.equals(transactionAmounts.get(i), transactionAmounts.get(i + 1))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
