package com.blackbox.exception;

import com.blackbox.dao.model.User;
import com.blackbox.dto.WsErrorResponse;
import java.util.Locale;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class Advicer extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(UserException.class)
	public ResponseEntity<WsErrorResponse> handleUserException(UserException ex, Locale locale) {
		WsErrorResponse errors = new WsErrorResponse();
		errors.setCode(ex.getErrorCode().name());
		UserException.ErrorCode enumCode = ex.getEnumCode(ex.getErrorCode().name());
		errors.setMessage(messageSource.getMessage(enumCode.getCode(),ex.getMsgArgs(),locale));
		return new ResponseEntity<>(errors, enumCode.getStatus());

	}
}
