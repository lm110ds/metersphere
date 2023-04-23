package io.metersphere.testin.bo;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryTestInScriptListParameterDataFromExcelBo implements Serializable {
    // 2.2 response
    private String apikey;

    private String mkey;

    private String msg;
    private String action;
    private String op;
    private Integer code;
    private GetScriptRequestTestInData data;

    @Data
    @Builder
    public static class GetScriptRequestTestInData {

        private Integer totalRow;
        private Integer pageSize;
        private Integer page;
        private List<ScriptInformationResultFromRequest> list;

    }
    @Data
    @Builder
    public static class ScriptInformationResultFromRequest {
        @SerializedName(value="scriptid",alternate={"scriptId"})
        private Integer scriptid;
        private Integer scriptNo;
        @SerializedName(value="projectId",alternate={"testInProjectId"})
        private Integer projectId;
        private Long scriptCreateTime;
        private Integer scriptCreateUser;
        private String scriptCreateDesc;
        private Integer ostype;
        private String taginfos;
        private Long scriptUpdateTime;
        private Integer scriptUpdateUserid;
        private String scriptUpdateDesc;
        private String channelId;
        private String appinfo;
    }
    public boolean isSuccess() {
        return Objects.equals(code, 0);
    }
}



