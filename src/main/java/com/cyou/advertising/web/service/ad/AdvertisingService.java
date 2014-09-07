package com.cyou.advertising.web.service.ad;

import java.util.List;

import com.cyou.advertising.web.common.Pagination;
import com.cyou.advertising.web.model.ad.AdClientVersion;
import com.cyou.advertising.web.model.ad.Advertising;
import com.cyou.advertising.web.model.ad.AdvertisingPv;

/**
 * @author  zhanagtao
 * @version  [版本号, 2014-8-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface AdvertisingService {
  
  public void insert(Advertising advertising);
  
  List<AdvertisingPv> getAdvertisingsByName(String name);
  
  /**
   * 分页查询
   * <功能详细描述>
   * @param name
   * @return
   * @see [类、类#方法、类#成员]
   */
  List<AdvertisingPv> getAdvertisingsByNameByPage(String name,Pagination page);
  
  /**
   * 求总数
   * <功能详细描述>
   * @param name
   * @return
   * @see [类、类#方法、类#成员]
   */
  Integer countAdvertisingsByName(String name);

	public void update(Advertising advertising);

	List<Advertising> list(Integer curPage, Integer pageSize);

	public Advertising findById(Integer id);

	public Integer getTotalCount();
}
