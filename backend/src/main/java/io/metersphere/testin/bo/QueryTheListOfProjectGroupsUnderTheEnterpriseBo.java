package io.metersphere.testin.bo;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class  QueryTheListOfProjectGroupsUnderTheEnterpriseBo implements Serializable {
    // 2.1 response
    private String msg;

    private String apikey;

    private String op;

    private Integer code;

    private RequestTestInResultData data;

    private String action;
    @Data
    public static class RequestTestInResultData {
        private Integer totalRow;

        private Integer totalPage;

        private Integer pageSize;

        private Integer page;

        private List<TestInProjectGroup> list;


    }
    @Data
    public static class TestInProjectGroup {
        private Integer eid;

        private Long createTime;

        private String name;
        @SerializedName(value="projectid",alternate={"testInProjectId"})
        private Integer projectid;

        private Integer status;

        private String thirdPartyProjectid;


    }
    public boolean isSuccess() {
        return Objects.equals(code, 0);
    }
}






