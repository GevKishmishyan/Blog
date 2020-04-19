package exceptions;

public class ModelExistingException extends Exception  {
    public ModelExistingException() {
    }

    public ModelExistingException(String message) {
        super(message);
    }

    public ModelExistingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelExistingException(Throwable cause) {
        super(cause);
    }

    public ModelExistingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
