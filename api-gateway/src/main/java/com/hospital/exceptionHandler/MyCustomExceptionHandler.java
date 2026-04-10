package com.hospital.exceptionHandler;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyCustomExceptionHandler {



    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handle(ExpiredJwtException expiredJwtException){
        return ResponseEntity.status(439).body("Token is expire");
    }



}
