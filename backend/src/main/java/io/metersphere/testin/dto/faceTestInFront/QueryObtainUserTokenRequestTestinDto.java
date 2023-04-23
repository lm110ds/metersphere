package io.metersphere.testin.dto.faceTestInFront;


import io.metersphere.testin.dto.BaseDto;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryObtainUserTokenRequestTestinDto extends BaseDto{
    // 2.4 face testin front
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

    }
}



