class Event {
    long timestamp;
    double amount;
    String userID;
    String serviceID;

    public Event(long timestamp, double amount, String userID, String serviceID) {
        this.timestamp = timestamp;
        this.amount = amount;
        this.userID = userID;
        this.serviceID = serviceID;
    }
}