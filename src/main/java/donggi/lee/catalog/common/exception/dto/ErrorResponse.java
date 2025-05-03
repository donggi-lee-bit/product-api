package donggi.lee.catalog.common.exception.dto;

import donggi.lee.catalog.common.exception.BusinessException;

public record ErrorResponse(
    String errorCode,
    String errorMessage
) {
    public static ErrorResponse from(final BusinessException exception) {
        return new ErrorResponse(exception.getErrorCode(), exception.getMessage());
    }
}
