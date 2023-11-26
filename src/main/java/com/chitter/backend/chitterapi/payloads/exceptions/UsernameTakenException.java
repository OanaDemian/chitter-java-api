package com.chitter.backend.chitterapi.payloads.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Username is already taken.")

public class UsernameTakenException extends RuntimeException {
}
