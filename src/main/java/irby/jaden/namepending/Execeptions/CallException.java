package irby.jaden.namepending.Execeptions;

public class CallException extends Exception {
    public CallException(String message) {
        super(message);
    }

    public static class MessageException extends Exception{
        public MessageException(String message) {
            super(message);
        }
    }
}
