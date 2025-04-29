package com.demo.app.demo_msvc_app.exceptions;

public class UnauthorizedAccessException extends RuntimeException{
 public UnauthorizedAccessException (String message) {
    super(message);
 }
}
