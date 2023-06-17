package cs544.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        String message=ex.getMessage();
        return new ResponseEntity(Map.of("message",message), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
        Map<String,String> resp=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error->{
            String fieldname=((FieldError)error).getField();
            String message=error.getDefaultMessage();
            resp.put(fieldname,message);
        });
        return new ResponseEntity(resp, HttpStatus.BAD_REQUEST);
    }
}
