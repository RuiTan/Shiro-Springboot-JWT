package top.guitoubing.shirotest2.config;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import top.guitoubing.shirotest2.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

  @Autowired
  UserService userService;

  /**
   * 必须重写此方法，不然会报错
   */
  @Override
  public boolean supports(AuthenticationToken token) {
    return token instanceof JWTToken;
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    System.out.println("————权限认证————");
    // 从token载荷中解析roles和perms
    Set<String> roles = JWTToken.getRoles(principalCollection.toString());
    Set<String> perms = JWTToken.getPerms(principalCollection.toString());

    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    info.setRoles(roles);
    info.setStringPermissions(perms);
    return info;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
    System.out.println("————身份认证方法————");
    String token =  ((JWTToken)authenticationToken).getToken();

    // 从数据库获取对应用户名密码的用户
    String password = userService.getPassword(JWTToken.getUsername(token));
    if (null == password) {
      throw new AccountException("用户名不正确");
    } else {
      // 通过正确密码来验证token是否正确
      JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(password)).build();
      try {
        jwtVerifier.verify(token);
      } catch (JWTVerificationException e) {
        throw new AccountException("密码不正确");
      }
    }
    return new SimpleAuthenticationInfo(token, token, getName());
  }

  public static String MD5(String password){
    return new SimpleHash("MD5", password, ByteSource.Util.bytes(password), 1).toString();
  }

}
