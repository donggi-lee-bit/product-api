package donggi.lee.catalog.user.exception;

import donggi.lee.catalog.common.exception.DomainException;
import donggi.lee.catalog.common.exception.ErrorCodeAndMessage;

public class EmailDuplicationException extends DomainException {
    public EmailDuplicationException(final String email) {
        super(
            ErrorCodeAndMessage.EMAIL_DUPLICATION.getCode(),
            String.format(ErrorCodeAndMessage.EMAIL_DUPLICATION.getMessage(), email)
        );
    }
}
