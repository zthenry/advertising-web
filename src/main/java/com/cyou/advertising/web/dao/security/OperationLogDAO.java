package com.cyou.advertising.web.dao.security;

import org.apache.ibatis.annotations.Insert;

import com.cyou.advertising.web.model.security.OperationLog;


public interface OperationLogDAO {

public static final String ALIGNNAME ="alias_operation_log3";

public static final String VIEW ="alias_operation_log3.`id`, alias_operation_log3.`user_id`,alias_operation_log3.`op_username`, alias_operation_log3.`op_time`, alias_operation_log3.`op_type`, alias_operation_log3.`op_detail`, alias_operation_log3.`ad_space_id`, alias_operation_log3.`advertising_id`, alias_operation_log3.`ad_space_conf_id`";

public static final String TABLENAME ="operation_log";

@Insert("INSERT INTO operation_log (user_id,op_username,op_time,op_type,op_detail,ad_space_id,advertising_id,ad_space_conf_id) VALUES (#{userId},#{opUsername},#{opTime},#{opType},#{opDetail},#{adSpaceId},#{advertisingId},#{adSpaceConfId}); ")
public void insert(OperationLog operationLog);

}
