
-- for_ms_testin.ms_project_testin_project_team definition

CREATE TABLE IF NOT EXISTS `ms_project_testin_project_team` (
    `ms_project_id` varchar(100) NOT NULL COMMENT 'ms Project ID',
    `test_in_project_id` varchar(100) NOT NULL COMMENT 'testIn项目组ID',
    `eid` int(11) DEFAULT NULL COMMENT '	企业id',
    `name` varchar(100) DEFAULT NULL COMMENT '项目组名称',
    `third_party_projectid` varchar(100) DEFAULT NULL COMMENT '第三方项目组id',
    `status` int(11) DEFAULT NULL,
    `create_time` timestamp NULL DEFAULT NULL,
    `descr` varchar(100) DEFAULT NULL,
    `extend` varchar(100) DEFAULT NULL,
    `product_no` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- for_ms_testin.test_case_script_information definition

CREATE TABLE IF NOT EXISTS `test_case_script_information` (
    `test_case_id` varchar(100) NOT NULL COMMENT 'Test case ID',
    `script_id` int(11) DEFAULT NULL COMMENT '脚本id',
    `script_no` int(11) NOT NULL COMMENT '脚本编号',
    `script_create_user` int(11) DEFAULT NULL COMMENT '脚本创建人的ID',
    `script_create_desc` varchar(100) DEFAULT NULL COMMENT '	描述',
    `test_in_project_id` varchar(100) DEFAULT NULL COMMENT 'testin项目组ID',
    `script_update_userid` int(11) DEFAULT NULL COMMENT '	脚本更新人',
    `script_update_desc` varchar(100) DEFAULT NULL COMMENT '脚本更新描述',
    `channel_id` varchar(100) DEFAULT NULL COMMENT '	渠道',
    `app_name` varchar(100) DEFAULT NULL COMMENT 'appName',
    `cover_install` int(11) DEFAULT NULL COMMENT '脚本执行策略:覆盖安装 指脚本执行完是否 要保持App在前台,1：清除app数据、0：不清楚',
    `clean_data` int(11) DEFAULT NULL COMMENT '指脚本执行完是否要清除应用的数据1：清除app数据、0',
    `keep_app` int(11) DEFAULT NULL COMMENT '指脚本执行完是否 要保持App在前台1：要保持app活性  0：直接杀掉进程',
    `script_create_time` timestamp NULL DEFAULT NULL COMMENT '脚本创建时间',
    `script_update_time` timestamp NULL DEFAULT NULL COMMENT '脚本更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- for_ms_testin.test_plan_testin_task definition

CREATE TABLE IF NOT EXISTS `test_plan_testin_task` (
         `test_plan_id` varchar(100) NOT NULL COMMENT 'Test Plan ID',
         `taskid` varchar(100) NOT NULL COMMENT '任务ID ',
         `test_in_projectid` int(11) DEFAULT NULL COMMENT '项目组ID',
         `exec_standard` varchar(100) DEFAULT NULL COMMENT '执行的策略normal 普通执行',
         `summaryInfo` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8;