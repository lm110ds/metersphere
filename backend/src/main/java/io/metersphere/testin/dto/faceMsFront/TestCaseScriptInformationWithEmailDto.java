package io.metersphere.testin.dto.faceMsFront;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
public class TestCaseScriptInformationWithEmailDto implements Serializable {
    //2.2 test ms front
    private String email;
    private static final long serialVersionUID = 385794188289146681L;
    /**
     * Test case ID
     */
    @Valid
    @NotBlank(message = "testCaseId cannot be empty")
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
    private String scriptDesc;
    /**
     * testin项目组ID
     */
    private Integer testInProjectId;
    /**
     * 脚本更新人
     */
    private Integer scriptUpdateUserid;

    /**
     * 渠道
     */
    private String channelId;

    private String appName;
    private String scriptName;
    /**
     * 脚本创建时间
     */
    private Long scriptCreateTime;
    /**
     * 脚本更新时间
     */
    private Long scriptUpdateTime;

}

