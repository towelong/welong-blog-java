package io.github.towelong.blog.core;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import io.github.towelong.blog.core.configuration.ExceptionCodeConfiguration;
import io.github.towelong.blog.exception.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionAdvice {

    @Autowired
    private ExceptionCodeConfiguration codeConfiguration;

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handleException(Exception e) {
        UnifyResponse message = new UnifyResponse(9999,"服务器开小差了");
        System.out.println(e);
        return message;
    }

    @ExceptionHandler(HttpException.class)
    @ResponseBody
    public ResponseEntity<UnifyResponse> handleHttpException(HttpException e) {
        UnifyResponse message = new UnifyResponse(e.getCode(),codeConfiguration.getMessage(e.getCode()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());
        assert httpStatus != null;
        return new ResponseEntity<>(message,headers,httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public UnifyResponse handleBeanException(MethodArgumentNotValidException e) {
        List<ObjectError> errors =  e.getBindingResult().getAllErrors();
        String message = this.formatAllErrorsMessages(errors);
        return new UnifyResponse(10001, message);
    }

    // ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public UnifyResponse handleConstraintViolationException(ConstraintViolationException e) {
//        for(ConstraintViolation err:e.getConstraintViolations()){
//            ConstraintViolation a = err;
//        }
        return new UnifyResponse(10001, e.getMessage());
    }

    @ExceptionHandler({JWTDecodeException.class, SignatureVerificationException.class})
    @ResponseBody
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public UnifyResponse handleJWTException(){
        return new UnifyResponse(10004, "令牌不合法");
    }


    @ExceptionHandler({TokenExpiredException.class})
    @ResponseBody
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public UnifyResponse handleJwtExpiredException(){
        return new UnifyResponse(10007,"令牌已过期");
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public UnifyResponse handleNoHandlerFoundException(){
        return new UnifyResponse(10002,"资源未找到");
    }




    private String formatAllErrorsMessages(List<ObjectError> errors){
        StringBuffer errorMsg = new StringBuffer();
        errors.forEach(error ->
                errorMsg.append(error.getDefaultMessage()).append(','));
        return errorMsg.toString();
    }
}