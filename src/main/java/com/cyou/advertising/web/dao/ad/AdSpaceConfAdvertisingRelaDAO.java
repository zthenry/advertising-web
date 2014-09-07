package com.cyou.advertising.web.dao.ad;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.cyou.advertising.web.model.ad.AdSpaceConfAdRela;
import com.cyou.advertising.web.model.ad.Advertising;



public interface AdSpaceConfAdvertisingRelaDAO {

public static final String ALIGNNAME ="alias_ad_space_conf_ad_rela0";

public static final String VIEW ="alias_ad_space_conf_ad_rela0.`id`, alias_ad_space_conf_ad_rela0.`ad_space_conf_id`, alias_ad_space_conf_ad_rela0.`advertising_id`, alias_ad_space_conf_ad_rela0.`create_time`, alias_ad_space_conf_ad_rela0.`update_time`, alias_ad_space_conf_ad_rela0.`op_user`, alias_ad_space_conf_advertising_rela0.`ad_ordernum`, alias_ad_space_conf_advertising_rela0.`ad_flag_name` as adFlagName, alias_ad_space_conf_ad_rela0.`status`";

public static final String TABLENAME ="ad_space_conf_ad_rela";


@Insert("insert into ad_space_conf_ad_rela (ad_space_conf_id,advertising_id,create_time,ad_ordernum,status,ad_flag_name) values(#{adSpaceConfId},#{advertisingId},#{createTime},#{adOrdernum},#{status},#{adFlagName})")
public void insert(AdSpaceConfAdRela adSpaceConfAdRela);

@Delete("DELETE FROM ad_space_conf_ad_rela WHERE ad_space_conf_id=#{adSpaceConfId}")
public void deleteByAdSpaceConfId(Long adSpaceConfId);

}
