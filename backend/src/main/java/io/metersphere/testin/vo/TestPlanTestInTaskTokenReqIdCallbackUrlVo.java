package io.metersphere.testin.vo;

import io.metersphere.testin.entity.MsProjectTestinProjectTeam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestPlanTestInTaskTokenReqIdCallbackUrlVo implements Serializable {

//    private String callbackUrl;
//    private String openTheTestingPageUrl;
    /**
     * testIn项目组ID
     */
//    @NotNull(message = "项目组ID不能为空")
//    private Integer testInProjectId;

//    @NotBlank(message = "项目组名称不能为空")
    private String Token;

    private String reqId;

}
