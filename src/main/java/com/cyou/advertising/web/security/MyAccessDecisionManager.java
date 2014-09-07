///*
// * 文 件 名:  MyAccessDecisionManager.java
// * 描    述:  <描述>
// * 修 改 人:  Administrator
// * 修改时间:  2014-8-14
// * 跟踪单号:  <跟踪单号>
// * 修改单号:  <修改单号>
// * 修改内容:  <修改内容>
// */
//package com.cyou.advertising.web.security;
//
//import java.util.Collection;
//import java.util.Iterator;
//
//import org.springframework.security.access.AccessDecisionManager;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.access.SecurityConfig;
//import org.springframework.security.authentication.InsufficientAuthenticationException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//
///**
// * <一句话功能简述>
// * <功能详细描述>
// * 
// * @author zhangtao
// * @version [版本号, 2014-8-14]
// * @see [相关类/方法]
// * @since [产品/模块版本]
// */
//public class MyAccessDecisionManager implements AccessDecisionManager {
//
//  @Override
//  public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException,
//      InsufficientAuthenticationException {
//
//    if(configAttributes == null) {
//      return;
//    }
//    // object is a URL.
//    System.out.println(object.toString()); 
//    Iterator<ConfigAttribute> ite = configAttributes.iterator();
//    while(ite.hasNext()) {
//      ConfigAttribute ca = ite.next();
//      String needRole = ((SecurityConfig) ca).getAttribute();
//      for(GrantedAuthority ga : authentication.getAuthorities()) {
//        if(needRole.equals(ga.getAuthority())) { 
//          // ga is user's role.
//          return;
//        }
//      }
//    }
//    throw new AccessDeniedException("no right");
//  }
//
//  @Override
//  public boolean supports(ConfigAttribute arg0) {
//    // TODO Auto-generated method stub
//    return true;
//  }
//
//  @Override
//  public boolean supports(Class<?> arg0) {
//    // TODO Auto-generated method stub
//    return true;
//  }
//
//}
