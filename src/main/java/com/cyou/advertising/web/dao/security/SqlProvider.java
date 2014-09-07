/*
 * 文 件 名:  SqlProvider.java
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014-8-15
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.cyou.advertising.web.dao.security;

import java.util.List;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014-8-15]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SqlProvider {

  public static final String ALIGNNAME ="alias_manage_module0";

  public static final String VIEW ="alias_manage_module0.`id`, alias_manage_module0.`name`, alias_manage_module0.`url`, alias_manage_module0.`status`, alias_manage_module0.`order_num` as orderNum";

  public static final String TABLENAME ="manage_module";
  
  public String selectModulesByIds(List<Long> ids)
  {
    String idStr = "";
    for(Long id : ids) {
      idStr=id+",";
    }
    idStr = idStr.substring(0, idStr.length()-2);
    return "select distinct "+VIEW+" from "+TABLENAME+" "+ALIGNNAME+" where   "+ALIGNNAME+".id in ("+idStr+")";
  }
}
