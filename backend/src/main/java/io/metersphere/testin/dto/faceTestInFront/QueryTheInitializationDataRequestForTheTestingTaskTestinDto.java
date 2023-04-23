package io.metersphere.testin.dto.faceTestInFront;


import io.metersphere.testin.dto.BaseDto;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryTheInitializationDataRequestForTheTestingTaskTestinDto extends BaseDto {
    // 2.3 face testin front
    private String apikey;
    private String mkey;
    private String action;
    private String op;
    private Long timestamp;
    private String sid;
    private String sig;
    private QueryTheInitializationDataRequestForTheTestingTaskTestinRequestData data;
    @Data
    @Builder
    public static class QueryTheInitializationDataRequestForTheTestingTaskTestinRequestData {
        private String taskDescr;
        private String additionalInfo;
        private String callbackUrl;
        private String extendedChannel;
        private String noApp;
        private Integer bizCode;
        private List<Scripts> scripts;
        private OnlineUserInfo onlineUserInfo;

    }
    @Builder
    @Data
    public static class OnlineUserInfo {
        private String email;
    }
    @Data
    @Builder
    public static class Scripts {
        private Integer scriptNo;
        private Standard standard;
    }
    @Data
    @Builder
    public static class Standard {
        private Integer coverInstall;
        private Integer cleanData;
        private Integer keepApp;

    }
}


