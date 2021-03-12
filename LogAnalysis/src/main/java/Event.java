public class Event {
    String timestamp;
    String subject;
    String action;
    String object;

    public Event(String timestamp, String subject, String action, String object){
        this.timestamp = timestamp;
        this.subject = subject;
        this.action = action;
        this.object = object;
    }
}
