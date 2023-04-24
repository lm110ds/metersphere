package io.metersphere.testin.dto.faceTestInFront;


import io.metersphere.testin.dto.BaseDto;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryToObtainTheExecutionDetailsOfTheTestingReportDto extends BaseDto{
    // 2.5 face testin front
    private String apikey;

    private String mkey;

    private String op;

    private RequestTestInData data;

    private String action;

    private String sid;

    private Long timestamp;

    @Builder
    @Data
    public static class OnlineUserInfo {
        private String email;
    }
    @Builder
    @Data
    public static class RequestTestInData {
        private OnlineUserInfo onlineUserInfo;
        private String taskid;
        private Integer projectid;
        private List<Integer> resultCategorys;
        private Integer page;
        private Integer pageSize;
    }
}



