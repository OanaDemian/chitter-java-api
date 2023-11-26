package com.chitter.backend.chitterapi.payloads.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason="Invalid Credentials")
public class UnauthorisedException extends RuntimeException {

}
