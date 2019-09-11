package top.guitoubing.shirotest2.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.guitoubing.shirotest2.config.CustomRealm;
import top.guitoubing.shirotest2.config.JWTToken;
import top.guitoubing.shirotest2.entity.User;
import top.guitoubing.shirotest2.service.UserService;
import com.alibaba.fastjson.JSONObject;

import java.util.Set;


@RestController
public class LoginController {

  @Autowired
  UserService userService;

  @GetMapping("/notLogin")
  public String notLogin(){
    return "您尚未登陆！";
  }

  @GetMapping("/notRole")
  public String notRole(){
    return "您没有权限！";
  }

  @GetMapping("/logout")
  public String logout(){
    Subject subject = SecurityUtils.getSubject();
    subject.logout();
    return "注销成功！";
  }

  @PostMapping("/login")
  public JSONObject login(String username, String password){
    Subject subject = SecurityUtils.getSubject();
    User loginUser = new User(username, CustomRealm.MD5(password), userService.getRoles(username), userService.getPerms(username));
    JWTToken token = new JWTToken(JWTToken.getToken(loginUser));
    subject.login(token);
    Set<String> roles = userService.getRoles(username);

    if (roles.contains("user")){
      return new JSONObject()
              .fluentPut("message", "欢迎用户登录")
              .fluentPut("token", token.getCredentials());
    } else if (roles.contains("admin")) {
      return new JSONObject()
              .fluentPut("message", "欢迎管理员登录")
              .fluentPut("token", token.getCredentials());
    }
    return new JSONObject()
            .fluentPut("message", "没有登录权限");
  }

}
