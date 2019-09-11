package top.guitoubing.shirotest2.service;

import org.springframework.stereotype.Service;
import top.guitoubing.shirotest2.entity.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
/**
 * 模拟连接数据库返回数据
 */
public class UserService {

  public User getUser(String username){
    User user = new User();
    user.setUsername(username);
    user.setPassword(getPassword(username));
    user.setRoles(getRoles(username));
    user.setPerms(getPerms(username));
    return user;
  }

  // CustomRealm.MD5() 加密后的密码
  public String getPassword(String username){
    if (username.equals("tanrui")) {
      return "ea48576f30be1669971699c09ad05c94";
    }
    else {
      return "ea48576f30be1669971699c09ad05c94";
    }
  }

  public Set<String> getRoles(String username){
    if (username.equals("tanrui")){
      return new HashSet<>(Arrays.asList("user", "role1", "role2"));
    }
    else if (username.equals("admin")){
      return new HashSet<>(Arrays.asList("admin", "role1", "role2"));
    }
    return Collections.emptySet();
  }

  public Set<String> getPerms(String username){
    if (username.equals("tanrui")){
      return new HashSet<>(Arrays.asList("perm", "perm2", "perm3"));
    }
    else if (username.equals("admin")){
      return new HashSet<>(Arrays.asList("perm1", "perm2", "perm3"));
    }
    return Collections.emptySet();
  }

}
