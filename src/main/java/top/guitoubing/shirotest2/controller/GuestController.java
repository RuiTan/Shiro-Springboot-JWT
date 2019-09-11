package top.guitoubing.shirotest2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guest")
public class GuestController {

  @GetMapping("/getMessage")
  public String getMessage(){
    return "欢迎进入，您的身份是游客";
  }

}
