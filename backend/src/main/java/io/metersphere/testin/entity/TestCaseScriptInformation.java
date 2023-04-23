package io.metersphere.testin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
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
    /**
     * App信息
     */
    private String appinfo;
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

