package io.metersphere.testin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.io.Serializable;

/**
 * (MsProjectTestinProjectTeam)实体类
 *
 * @author makejava
 * @since 2023-04-20 11:47:38
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MsProjectTestinProjectTeam implements Serializable {
    private static final long serialVersionUID = -18658560269840346L;
    /**
     * ms Project ID
     */
//    @NotNull(message = "项目组ID不能为空")
    private String msProjectId;
    /**
     * testIn项目组ID
     */
//    @NotNull(message = "项目组ID不能为空")
    private Integer testInProjectId;
    /**
     * 企业id
     */
    private Integer eid;
    /**
     * 项目组名称
     */
//    @NotBlank(message = "项目组名称不能为空")
    private String name;
    /**
     * 第三方项目组id
     */
    private String thirdPartyProjectid;

    private Integer status;

    private Long createTime;

/*
    public String getMsProjectId() {
        return msProjectId;
    }

    public void setMsProjectId(String msProjectId) {
        this.msProjectId = msProjectId;
    }

    public String getTestInProjectId() {
        return testInProjectId;
    }

    public void setTestInProjectId(String testInProjectId) {
        this.testInProjectId = testInProjectId;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThirdPartyProjectid() {
        return thirdPartyProjectid;
    }

    public void setThirdPartyProjectid(String thirdPartyProjectid) {
        this.thirdPartyProjectid = thirdPartyProjectid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }*/

}

