///*
// * 文 件 名:  MyFilterSecurityInterceptor.java
// * 描    述:  <描述>
// * 修 改 人:  Administrator
// * 修改时间:  2014-8-14
// * 跟踪单号:  <跟踪单号>
// * 修改单号:  <修改单号>
// * 修改内容:  <修改内容>
// */
//package com.cyou.advertising.web.filter;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//
//import org.springframework.security.access.SecurityMetadataSource;
//import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
//import org.springframework.security.access.intercept.InterceptorStatusToken;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
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
//public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
//
//  private FilterInvocationSecurityMetadataSource securityMetadataSource;
//  
//  
//  public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
//    return securityMetadataSource;
//  }
//
//  public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
//    this.securityMetadataSource = securityMetadataSource;
//  }
//
//  @Override
//  public Class<?> getSecureObjectClass() {
//    return FilterInvocation.class;
//  }
//
//  @Override
//  public SecurityMetadataSource obtainSecurityMetadataSource() {
//    return this.securityMetadataSource;
//  }
//
//  @Override
//  public void destroy() {
//    // TODO Auto-generated method stub
//
//  }
//
//  @Override
//  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
//      ServletException {
//    FilterInvocation fi = new FilterInvocation(request, response, chain);
//    invoke(fi);
//
//  }
//
//  @Override
//  public void init(FilterConfig filterConfig) throws ServletException {
//    // TODO Auto-generated method stub
//
//  }
//
//  public void invoke(FilterInvocation fi) throws IOException, ServletException {
//    InterceptorStatusToken token = super.beforeInvocation(fi);
//    try {
//      fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
//    }
//    finally {
//      super.afterInvocation(token, null);
//    }
//  }
//}
