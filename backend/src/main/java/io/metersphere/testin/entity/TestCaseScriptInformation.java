package io.metersphere.testin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (TestCaseScriptInformation)实体类
 *
 * @author makejava
 * @since 2023-04-20 11:47:52
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseScriptInformation implements Serializable {
    private static final long serialVersionUID = 385794188289146681L;
    /**
     * Test case ID
     */
    private String testCaseId;
    /**
     * 脚本id
     */
    private Integer scriptId;
    /**
     * 脚本编号
     */
    private Integer scriptNo;
    /**
     * 脚本创建人的ID
     */
    private Integer scriptCreateUser;
    /**
     * 描述
     */
    private String scriptCreateDesc;
    /**
     * testin项目组ID
     */
    private Integer testInProjectId;
    /**
     * 脚本更新人
     */
    private Integer scriptUpdateUserid;
    /**
     * 脚本更新描述
     */
    private String scriptUpdateDesc;
    /**
     * 渠道
     */
    private String channelId;

    private String appName;
    private String scriptName;
    /**
     * 脚本执行策略:覆盖安装 指脚本执行完是否要覆盖安装1：要覆盖 0：不覆盖
     */
    private Integer coverInstall;
    /**
     * 指脚本执行完是否要清除应用的数据1：清除app数据、0
     */
    private Integer cleanData;
    /**
     * 指脚本执行完是否 要保持App在前台1：要保持app活性  0：直接杀掉进程
     */
    private Integer keepApp;
    /**
     * 错误结果分类 1 通过,2 安装失败,3 卸载失败,4 启动失败,5 运行失败,6 功能异常,7 未执行,8 超时,9 取消,10 monkey失败,11 警告,12 应用崩溃,13 脚本错误,14 应用无响应,15 环境异常,16 遍历失败,100 忽略
     */
    private Integer resultCategory;
    /**
     * 脚本创建时间
     */
    private Long scriptCreateTime;
    /**
     * 脚本更新时间
     */
    private Long scriptUpdateTime;

}

