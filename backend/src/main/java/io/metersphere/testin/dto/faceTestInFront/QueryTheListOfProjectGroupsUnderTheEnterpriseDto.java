package io.metersphere.testin.dto.faceTestInFront;


import io.metersphere.testin.dto.BaseDto;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryTheListOfProjectGroupsUnderTheEnterpriseDto extends BaseDto {
    // 2.1 face testin front
    private String apikey;

    private String mkey;

    private String op;

    private RequestTestInData data;

    private String action;

    private Long timestamp;
    @Builder
    @Data
    public static class OnlineUserInfo {
        private String email;
        private Integer projectid;
    }
    @Builder
    @Data
    public static class RequestTestInData {
        private OnlineUserInfo onlineUserInfo;

        private Integer page;

        private Integer status;

        private Integer pageSize;
    }
}



