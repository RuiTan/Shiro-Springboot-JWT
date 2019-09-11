package top.guitoubing.shirotest2.entity;

import java.util.Set;

public class User {
  private String username;
  private String password;
  private Set<String> roles;
  private Set<String> perms;

  public User(String username, String password, Set<String> roles, Set<String> perms) {
    this.username = username;
    this.password = password;
    this.roles = roles;
    this.perms = perms;
  }

  public User() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<String> getRoles() {
    return roles;
  }

  public void setRoles(Set<String> roles) {
    this.roles = roles;
  }

  public Set<String> getPerms() {
    return perms;
  }

  public void setPerms(Set<String> perms) {
    this.perms = perms;
  }
}
