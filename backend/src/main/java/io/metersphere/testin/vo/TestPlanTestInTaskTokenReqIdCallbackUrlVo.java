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

    /**
     * testIn项目组ID
     */
    private String Token;
    private String gotoUrl;

    private String reqId;

}
