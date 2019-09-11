package top.guitoubing.shirotest2.config;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter extends BasicHttpAuthenticationFilter {

  @Override
  /**
   * 最顶层过滤，检查request header，除了login、notLogin、notRole三个页面无需token，其余页面均需要验证token
   */
  protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
    if (((HttpServletRequest) request).getHeader("token") != null){
      try {
        executeLogin(request, response);
        return true;
      } catch (Exception e) {
        try {
          ((HttpServletResponse) response).sendError(401, "验证失败");
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    } else if (((HttpServletRequest) request).getContextPath().equals("/login")
            || ((HttpServletRequest) request).getContextPath().equals("/notLogin")
            || ((HttpServletRequest) request).getContextPath().equals("/notRole")){
      return true;
    }
    else {
      try {
        ((HttpServletResponse) response).sendError(401, "请求无token");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return false;
  }

  @Override
  protected boolean executeLogin(ServletRequest request, ServletResponse response) {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String token = httpServletRequest.getHeader("token");
    JWTToken jwtToken = new JWTToken(token);
    getSubject(request, response).login(jwtToken);
    return true;
  }
}
