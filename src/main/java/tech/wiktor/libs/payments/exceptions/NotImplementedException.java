package tech.wiktor.libs.payments.exceptions;

public class NotImplementedException extends UnsupportedOperationException {
    public NotImplementedException() {
        super("Not implemented, yet");
    }
    public NotImplementedException(String method) {
        super(method + " not implemented");
    }
}
