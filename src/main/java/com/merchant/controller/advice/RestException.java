package com.merchant.controller.advice;

import com.merchant.constants.ErrorLevel;
import lombok.Getter;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;

public class RestException extends NestedRuntimeException {

    @Getter
    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    @Getter
    private ErrorLevel errorLevel = ErrorLevel.ERROR;

    public RestException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    public RestException(HttpStatus httpStatus,String msg) {
        super(msg);
        this.httpStatus=httpStatus;
    }

    public RestException(HttpStatus httpStatus,String msg,ErrorLevel errorLevel) {
        super(msg);
        this.httpStatus=httpStatus;
        this.errorLevel=errorLevel;
    }

}
