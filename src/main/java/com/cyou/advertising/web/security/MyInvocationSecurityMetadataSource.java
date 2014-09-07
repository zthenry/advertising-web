///*
// * 文 件 名:  MyInvocationSecurityMetadataSource.java
// * 描    述:  <描述>
// * 修 改 人:  Administrator
// * 修改时间:  2014-8-14
// * 跟踪单号:  <跟踪单号>
// * 修改单号:  <修改单号>
// * 修改内容:  <修改内容>
// */
//package com.cyou.advertising.web.security;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.access.SecurityConfig;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//import org.springframework.util.AntPathMatcher;
//
///**
// * 权限资源的初始化
// * <功能详细描述>
// * 
// * @author  zhanagtao
// * @version  [版本号, 2014-8-14]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
//
//  private AntPathMatcher urlMatcher = new AntPathMatcher();
//  private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
//
//  public MyInvocationSecurityMetadataSource() {
//      loadResourceDefine();
//  }
//  
//  @Override
//  public Collection<ConfigAttribute> getAllConfigAttributes() {
//    // TODO Auto-generated method stub
//    return null;
//  }
//
//  @Override
//  public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
//    // guess object is a URL.
//    String url = ((FilterInvocation)object).getRequestUrl();
//    Iterator<String> ite = resourceMap.keySet().iterator();
//    while (ite.hasNext()) {
//        String resURL = ite.next();
//        if (urlMatcher.match(resURL, url))
//        {
//            return resourceMap.get(resURL);
//        }
//    }
//    return null;
//  }
//
//  @Override
//  public boolean supports(Class<?> arg0) {
//    return true;
//  }
//  
//  /**
//   * 加载定义的url权限 与角色定义
//   * <功能详细描述>
//   * @see [类、类#方法、类#成员]
//   */
//  private void loadResourceDefine() {
//    resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
//    Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
//    ConfigAttribute ca = new SecurityConfig("ROLE_ADMIN");
//    atts.add(ca);
//    resourceMap.put("/ad", atts);
//    resourceMap.put("/i.jsp", atts);
//}
//
//}
