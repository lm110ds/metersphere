package io.metersphere.track.dto;

import io.metersphere.base.domain.IssuesDao;
import io.metersphere.base.domain.TestCaseWithBLOBs;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class TestPlanCaseDtoWithResultCategory extends TestPlanCaseDTO {    //WithResultCategory
    private Integer resultCategory;
}
