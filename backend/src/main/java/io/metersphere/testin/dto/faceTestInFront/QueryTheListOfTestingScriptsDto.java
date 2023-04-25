package io.metersphere.testin.dto.faceTestInFront;

import com.google.gson.annotations.SerializedName;

import io.metersphere.testin.dto.BaseDto;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryTheListOfTestingScriptsDto extends BaseDto {
    //2.2 face testin front
    private String apikey;

    private String mkey;

    private String sid;

    private String op;

    private String action;

    private Long timestamp;

    private QueryTestinScriptListParameterData data;
    @Builder
    @Data
    public static class OnlineUserInfo {
        private String email;
    }

    @Data
    @Builder
    public static class QueryTestinScriptListParameterData {
        private OnlineUserInfo onlineUserInfo;

        private String scriptDesc;
        private Integer scriptType;
        private Integer scriptNo;

        private int startPageNo;

        private int pageSize;
        @SerializedName(value="projectId",alternate={"testInProjectId"})
        private int projectId;

    }

}
