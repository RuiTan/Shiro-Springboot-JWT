package top.guitoubing.shirotest2.controller;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.AccountException;

@RestControllerAdvice
public class ExceptionController {

  @ExceptionHandler(AccountException.class)
  public String handleShiroException(Exception e  ){
    return e.getMessage();
  }

  @ExceptionHandler(UnauthorizedException.class)
  public String handleUnauthorizedException(UnauthorizedException e){
    return e.getLocalizedMessage();
  }
}
