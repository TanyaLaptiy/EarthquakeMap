package com.lineate.buscompany.error;

import com.lineate.buscompany.errors.ErrorCodeApplication;
import com.lineate.buscompany.errors.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalErrorHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalErrorHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorList handleValidation(MethodArgumentNotValidException exc) {
        final ErrorList error = new ErrorList();
        ErrorItem errorItem = new ErrorItem();
        exc.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorItem.setError(String.format("%s:%s", fieldError.getField(), fieldError.getDefaultMessage()));
            error.getAllErrors().add(errorItem);
        });
        exc.getBindingResult().getGlobalErrors().forEach(err -> {
            errorItem.setError(String.format("global:%s", err.getDefaultMessage()));
            error.getAllErrors().add(errorItem);
        });
        return error;
    }

    public static class ErrorList {

        private List<ErrorItem> allErrors = new ArrayList<>();

        public List<ErrorItem> getAllErrors() {
            return allErrors;
        }

        public void setAllErrors(List<ErrorItem> allErrors) {
            this.allErrors = allErrors;
        }
    }


    @ExceptionHandler(ServerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorList handleValidation(ServerException e) {
        List<ErrorItem> errorDtoItems = new ArrayList<>();

        ErrorCodeApplication serverErrorCodeWithField = e.getErrorCode();

        errorDtoItems.add(
                new ErrorItem(serverErrorCodeWithField.getErrorString())
        );
        ErrorList errorList = new ErrorList();
        errorList.setAllErrors(errorDtoItems);

        return errorList;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorItem forbidden(HttpServletRequest request, AccessDeniedException e) {
        LOGGER.warn(ErrorCodeApplication.INTERNAL_ERROR.getErrorString(), e);
        return new ErrorItem(e.getMessage());
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void notAllowed(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        LOGGER.warn(ErrorCodeApplication.INTERNAL_ERROR.getErrorString(), e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorItem missingRequiredParam(HttpServletRequest request, MissingServletRequestParameterException e) {
        LOGGER.warn(ErrorCodeApplication.INTERNAL_ERROR.getErrorString(), request, e);
        return new ErrorItem(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorItem methodArgumentMismatch(HttpServletRequest request, MethodArgumentTypeMismatchException e) {
        LOGGER.warn(ErrorCodeApplication.INTERNAL_ERROR.getErrorString(), request, e);
        return new ErrorItem(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorItem validationException(HttpServletRequest request, ConstraintViolationException e) {
        LOGGER.warn(ErrorCodeApplication.INTERNAL_ERROR.getErrorString(), request, e);
        return new ErrorItem(e.getMessage());

    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ErrorItem optimisticLockingException(HttpServletRequest request, OptimisticLockingFailureException e) {
        LOGGER.warn(ErrorCodeApplication.INTERNAL_ERROR.getErrorString(), request, e);
        return new ErrorItem(e.getMessage());
    }
}
