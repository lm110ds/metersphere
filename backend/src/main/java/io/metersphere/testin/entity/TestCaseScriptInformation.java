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
/*    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")*/
    private Long scriptCreateTime;
    /**
     * 脚本更新时间
     */
/*    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")*/
    private Long scriptUpdateTime;
/*    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Field("updateTime")
    private Date updateTime;*/

/*
    public String getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(String testCaseId) {
        this.testCaseId = testCaseId;
    }

    public Integer getScriptId() {
        return scriptId;
    }

    public void setScriptId(Integer scriptId) {
        this.scriptId = scriptId;
    }

    public Integer getScriptNo() {
        return scriptNo;
    }

    public void setScriptNo(Integer scriptNo) {
        this.scriptNo = scriptNo;
    }

    public Integer getScriptCreateUser() {
        return scriptCreateUser;
    }

    public void setScriptCreateUser(Integer scriptCreateUser) {
        this.scriptCreateUser = scriptCreateUser;
    }

    public String getScriptCreateDesc() {
        return scriptCreateDesc;
    }

    public void setScriptCreateDesc(String scriptCreateDesc) {
        this.scriptCreateDesc = scriptCreateDesc;
    }

    public String getTestInProjectId() {
        return testInProjectId;
    }

    public void setTestInProjectId(String testInProjectId) {
        this.testInProjectId = testInProjectId;
    }

    public Integer getScriptUpdateUserid() {
        return scriptUpdateUserid;
    }

    public void setScriptUpdateUserid(Integer scriptUpdateUserid) {
        this.scriptUpdateUserid = scriptUpdateUserid;
    }

    public String getScriptUpdateDesc() {
        return scriptUpdateDesc;
    }

    public void setScriptUpdateDesc(String scriptUpdateDesc) {
        this.scriptUpdateDesc = scriptUpdateDesc;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getAppinfo() {
        return appinfo;
    }

    public void setAppinfo(String appinfo) {
        this.appinfo = appinfo;
    }

    public Date getScriptCreateTime() {
        return scriptCreateTime;
    }

    public void setScriptCreateTime(Date scriptCreateTime) {
        this.scriptCreateTime = scriptCreateTime;
    }

    public Date getScriptUpdateTime() {
        return scriptUpdateTime;
    }

    public void setScriptUpdateTime(Date scriptUpdateTime) {
        this.scriptUpdateTime = scriptUpdateTime;
    }
*/

}

