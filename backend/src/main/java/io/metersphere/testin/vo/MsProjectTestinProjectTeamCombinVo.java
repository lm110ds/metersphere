package io.metersphere.testin.vo;

import io.metersphere.testin.bo.QueryTheListOfProjectGroupsUnderTheEnterpriseBo;
import io.metersphere.testin.entity.MsProjectTestinProjectTeam;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsProjectTestinProjectTeamCombinVo extends MsProjectTestinProjectTeam {

    private Boolean flag;

}
