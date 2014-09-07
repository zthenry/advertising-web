///*
// * 文 件 名:  CustomUserDetailsService.java
// * 描    述:  <描述>
// * 修 改 人:  zhangtao
// * 修改时间:  2014-8-13
// * 跟踪单号:  <跟踪单号>
// * 修改单号:  <修改单号>
// * 修改内容:  <修改内容>
// */
//package com.cyou.advertising.web.security;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import com.cyou.advertising.web.dao.security.UsersDAO;
//import com.cyou.advertising.web.model.security.Users;
//
///**
// * 获取用户的权限认证
// * <功能详细描述>
// * 
// * @author  Administrator
// * @version  [版本号, 2014-8-13]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class CustomUserDetailsService implements UserDetailsService {
//
//  @Autowired
//  private UsersDAO usersDAO;
//  @Override
//  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//    UserDetails userDetails=null;
//    Users user = usersDAO.findByUsername(username);
//    userDetails = new User(username, user.getPassword(), true, true, true, true, getAuthorities(1));
//    return userDetails;
//  }
//
//  /** 
//   * 获得访问角色权限 
//   *  
//   * @param access 
//   * @return 
//   */  
//  public Collection<GrantedAuthority> getAuthorities(Integer access) {  
//
//      List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);  
//
//      // 所有的用户默认拥有ROLE_USER权限  
//      //logger.debug("Grant ROLE_USER to this user");  
//      authList.add(new SimpleGrantedAuthority("ROLE_USER"));  
//
//      // 如果参数access为1.则拥有ROLE_ADMIN权限  
//      if (access.compareTo(1) == 0) {  
//          //logger.debug("Grant ROLE_ADMIN to this user");  
//          authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));  
//      }  
//
//      return authList;  
//  }  
//}
