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
    private String msProjectId;
    /**
     * testIn项目组ID
     */
    private Integer testInProjectId;
    /**
     * 企业id
     */
    private Integer eid;
    /**
     * 项目组名称
     */
    private String name;
    /**
     * 第三方项目组id
     */
    private String thirdPartyProjectid;

    private Integer status;

    private Long createTime;

    private String descr;
    private String extend;
    private String productNo;


}

