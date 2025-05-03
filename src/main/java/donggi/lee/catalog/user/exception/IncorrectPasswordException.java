package donggi.lee.catalog.user.exception;

import donggi.lee.catalog.common.exception.DomainException;
import donggi.lee.catalog.common.exception.ErrorCodeAndMessage;

public class IncorrectPasswordException extends DomainException {
    public IncorrectPasswordException() {
        super(ErrorCodeAndMessage.INCORRECT_PASSWORD.getMessage());
    }
}
