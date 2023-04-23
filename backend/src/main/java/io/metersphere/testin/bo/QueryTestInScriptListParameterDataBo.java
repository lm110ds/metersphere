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
public class QueryTestInScriptListParameterDataBo implements Serializable {
    // 2.2 response
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
        private Integer scriptUpdateType;
        private String scriptUpdateDesc;
        private String taginfos;
        private Long scriptUpdateTime;
        private String remark;
        private Integer ostype;
        private Integer scriptFromType;
        private Integer type;
        private Integer scriptUpdateUserid;
        @SerializedName(value="scriptid",alternate={"scriptId"})
        private Integer scriptid;
        private Integer build;
        private String imageurl;
        private Integer scriptNo;
        private Integer scriptCreateUser;
        private Fileinfo fileinfo;
        private String scriptName;
        private String scriptCreateDesc;
        private String adapterversionname;
        @SerializedName(value="projectId",alternate={"testInProjectId"})
        private Integer projectId;
        private Long scriptCreateTime;
        private String adapterversioncode;
        private String channelId;
        private String appinfo;

    }
    @Data
    @Builder
    public static class Fileinfo {

        private Integer createUserId;
        private Long expireTime;
        private Long size;
        private Long createTime;
        private String uploadUserName;
        private String fileUrl;
        private String remark;
        private Integer isdelete;
        private String filemd5;
        private Integer type;
        private Long fileId;

    }
    public boolean isSuccess() {
        return Objects.equals(code, 0);
    }
}



