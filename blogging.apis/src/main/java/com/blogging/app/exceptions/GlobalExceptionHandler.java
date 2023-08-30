package com.blogging.app.exceptions;

import com.blogging.app.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value=ResourceNotFoundException.class)
    private ResponseEntity<ApiResponse> resourceNotFoundHandler(ResourceNotFoundException ex){
        String mess=ex.getMessage();
        ApiResponse ap=new ApiResponse(mess,false);
        return new ResponseEntity<>(ap, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value=UsernameNotFoundException.class)
    private ResponseEntity<ApiResponse> usernameNotFoundHandler(UsernameNotFoundException ex){
        String ms=ex.getMessage();
        ApiResponse ap=new ApiResponse(ms,false);
        return new ResponseEntity<>(ap,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = PasswordInvalidException.class)
    private  ResponseEntity<ApiResponse> PasswordInvalid(PasswordInvalidException ex){
        String mss=ex.getMessage();
        ApiResponse ap=new ApiResponse(mss,false);
        return new ResponseEntity<>(ap,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<Map<String,String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException arg){

        Map<String,String> err=new HashMap<>();
        arg.getBindingResult().getAllErrors().forEach((error)->{
            String field=((FieldError)error).getField();
            String mess=error.getDefaultMessage();
            err.put(field,mess);
        });
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    private  ResponseEntity<Map<String,String>> methodNotSupportedException(HttpRequestMethodNotSupportedException e){
        Map<String,String> hm=new HashMap<>();
        String field="message";
        String mes=e.getMessage();
        hm.put(field,mes);
        return new ResponseEntity<>(hm,HttpStatus.METHOD_NOT_ALLOWED);
    }
}
