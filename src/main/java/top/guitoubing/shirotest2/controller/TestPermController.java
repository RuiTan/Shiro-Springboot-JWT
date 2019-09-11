package top.guitoubing.shirotest2.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/perm")
public class TestPermController {

  @GetMapping("/index")
  @RequiresPermissions("perm")
  public String index(){
    return "权限perm可以访问！";
  }

  @GetMapping("/index2")
  @RequiresPermissions({"perm1", "perm2"})
  public String index2(){
    return "权限perm1,perm2可以访问！";
  }

}
