package top.guitoubing.shirotest2.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

  @GetMapping("/getMessage")
  public String getMessage(){
    return "您拥有管理员权限，可以获得该接口的信息！";
  }

}
