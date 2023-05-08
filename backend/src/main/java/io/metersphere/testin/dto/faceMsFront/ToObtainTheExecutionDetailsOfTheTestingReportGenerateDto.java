package io.metersphere.testin.dto.faceMsFront;

import io.metersphere.testin.dto.BaseDto;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToObtainTheExecutionDetailsOfTheTestingReportGenerateDto extends BaseDto {
    private String email;
    private Integer projectid;
    private String taskid;
    private String testPlanId;
    private String token;

}
