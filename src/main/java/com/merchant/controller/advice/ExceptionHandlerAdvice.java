package com.merchant.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    private final static String LOG_EXCEPTION_ADVICE = " ** Exception Leaked & captured ";

    @ExceptionHandler(RestException.class)
    public ResponseEntity<?> handleRestException(RestException ex) {
        log.error("{}: {}; {}", LOG_EXCEPTION_ADVICE, ex.getClass().getSimpleName(), ex.getLocalizedMessage());
        ex.printStackTrace();
        return ResponseEntity.
                status(ex.getHttpStatus()).
                body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        log.error("{}: {}; {}", LOG_EXCEPTION_ADVICE, ex.getClass().getSimpleName(), ex.getLocalizedMessage());
        ex.printStackTrace();
        return ResponseEntity.
                status(HttpStatus.INTERNAL_SERVER_ERROR).
                body(ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<?> handleNullPointerException(NullPointerException ex) {
        log.error("{}: {}; {}", LOG_EXCEPTION_ADVICE, ex.getClass().getSimpleName(), ex.getLocalizedMessage());
        ex.printStackTrace();
        return ResponseEntity.
                status(HttpStatus.INTERNAL_SERVER_ERROR).
                body(ex.getMessage());
    }

//    @ExceptionHandler(ClassNotFoundException.class)
//    protected ResponseEntity<?> handleClassNotFoundException(ClassNotFoundException ex) {
//        log.error("{}: {}; {}", LOG_EXCEPTION_ADVICE, ex.getClass().getSimpleName(), ex.getLocalizedMessage());
//        ex.printStackTrace();
//        return ResponseEntity.
//                status(HttpStatus.INTERNAL_SERVER_ERROR).
//                body(ex.getMessage());
//    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    protected ResponseEntity<?> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex) {
        log.error("{}: {}; {}", LOG_EXCEPTION_ADVICE, ex.getClass().getSimpleName(), ex.getLocalizedMessage());
        ex.printStackTrace();
        return ResponseEntity.
                status(HttpStatus.INTERNAL_SERVER_ERROR).
                body(ex.getMessage());
    }

    @ExceptionHandler(ClassNotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(ClassNotFoundException ex) {
        log.error("{}: {}; {}", LOG_EXCEPTION_ADVICE, ex.getClass().getSimpleName(), ex.getLocalizedMessage());
        ex.printStackTrace();
        return ResponseEntity.
                status(HttpStatus.NOT_FOUND).
                body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("{}: {}; {}", LOG_EXCEPTION_ADVICE, ex.getClass().getSimpleName(), ex.getLocalizedMessage());
        ex.printStackTrace();
        return ResponseEntity.
                status(HttpStatus.UNPROCESSABLE_ENTITY).
                body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage;
        if (ex.getBindingResult().hasFieldErrors()) {
            if (log.isDebugEnabled()) {
                ex.getBindingResult().getFieldErrors().
                        forEach(e -> log.debug("Field:{} - Error:{}", e.getField(), e.getDefaultMessage()));
            }
            errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        } else if (ex.getBindingResult().hasGlobalErrors()) {
            errorMessage = ex.getBindingResult().getGlobalErrors().get(0).getDefaultMessage();
        } else {
            errorMessage = "Invalid argument(s) provided to api";
        }

        log.error("{}: {}; {} ({})", LOG_EXCEPTION_ADVICE, ex.getClass().getSimpleName(), ex.getLocalizedMessage(), errorMessage);
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentTypeMismatchException ex) {
        String errorMessage = "Invalid argument(s) provided to api: ";
        log.error("{}: {}; {} ({})", LOG_EXCEPTION_ADVICE, ex.getClass().getSimpleName(), ex.getLocalizedMessage(), errorMessage);
        return ResponseEntity.badRequest().body(errorMessage+ex.getMessage());
    }


    @ResponseBody
    private Map<String, String> errorJson(HttpStatus httpStatus, String message) {
        Map<String, String> result = new HashMap<>();
        result.put("errorCode", httpStatus.toString());
        result.put("errorDescription", message);
        return result;
    }




}
