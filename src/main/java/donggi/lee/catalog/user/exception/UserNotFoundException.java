package donggi.lee.catalog.user.exception;

import donggi.lee.catalog.common.exception.ErrorCodeAndMessage;
import donggi.lee.catalog.common.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(final String email) {
        super(String.format(ErrorCodeAndMessage.USER_NOT_FOUND.getMessage(), email));
    }
}
