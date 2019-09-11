package top.guitoubing.shirotest2.config;

import org.apache.shiro.authc.AuthenticationToken;
import top.guitoubing.shirotest2.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JWTToken implements AuthenticationToken {

  // 过期时间 30 分钟
  private static final long EXPIRE_TIME = 60 * 30 * 1000;

  private String token;

  public JWTToken(String token) {
    this.token = token;
  }

  public String getToken(){
    return token;
  }

  @Override
  public Object getPrincipal() {
    return getUsername(token);
  }

  @Override
  public Object getCredentials() {
    return JWT.decode(token).getSignature();
  }

  public static String getUsername(String token){
    String username;
    try {
      username = JWT.decode(token).getClaim("username").asString();
    } catch (JWTDecodeException j) {
      throw new RuntimeException("401");
    }
    return username;
  }

  public static Set<String> getRoles(String token){
    try {
      return new HashSet<>(JWT.decode(token).getClaim("roles").asList(String.class));
    } catch (JWTDecodeException j){
      throw new RuntimeException("401");
    }
  }

  public static Set<String> getPerms(String token){
    try {
      return new HashSet<>(JWT.decode(token).getClaim("perms").asList(String.class));
    } catch (JWTDecodeException j){
      throw new RuntimeException("401");
    }
  }

  public static String getToken(User user){
    return JWT.create()
            .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
            .withClaim("username", user.getUsername())
            .withArrayClaim("roles", user.getRoles().toArray(new String[]{}))
            .withArrayClaim("perms", user.getPerms().toArray(new String[]{}))
            .sign(Algorithm.HMAC256(user.getPassword()));
  }
}
