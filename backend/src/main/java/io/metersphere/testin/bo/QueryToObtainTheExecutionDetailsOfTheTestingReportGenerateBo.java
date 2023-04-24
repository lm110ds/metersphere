package io.metersphere.testin.bo;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class  QueryToObtainTheExecutionDetailsOfTheTestingReportGenerateBo implements Serializable {
    private String msg;

    private String apikey;
    private String mkey;

    private String op;

    private Integer code;

    private RequestTestInResultData data;

    private String action;
//    private Long timestamp;

    @Data
    public static class RequestTestInResultData {
        private Integer totalRow;
        private Integer totalPage;
        private Integer pageSize;
        private Integer page;
        private List<TestInProjectGroupTask> list;
    }
    @Data
    public static class TestInProjectGroupTask {
        private String taskid;
        private String subtaskid;
        private String subsubtaskid;
        private Long timeConsuming;

        private Integer ignoreMark;
        private Integer errorCode;
        private Integer hasSupplementary;
        private List<ReportLogExceptions> reportLogExceptions;
        private ReportDevice reportDevice;
        private ReportScript reportScript;
        private ReportRunInfo reportRunInfo;
       /* private Long createTime;

        private String name;
        @SerializedName(value="projectid",alternate={"testInProjectId"})
        private Integer projectid;

        private Integer status;

        private String thirdPartyProjectid;*/
    }
    @Data
    public static class ReportLogExceptions {
        private String pid;
        private String type;
        private String beginNum;
        private String errorMsg;
        private Integer endNum;
    }
    @Data
    public static class ReportDevice {
        private String deviceid;
        private String aliasName;
        private String brandName;
        private String ucomid;
        private String releaseVer;
        private Integer brandid;
        private Integer modelid;
        private Integer dpiHeight;
        private Integer dpiWidth;
        private Float screenSize;
    }
    @Data
    public static class ReportScript {
        private String scriptDescr;
        private String scriptUrl;
        private String scriptMd5;
        private Integer scriptid;
        private Integer scriptNo;
        private Integer scriptType;
        private Integer osType;
        private Integer orderNum;
        private List<Object> scriptTags;
    }
    @Data
    public static class ReportRunInfo {
        private String errorMsg;
        private Integer errorCode;
        private List<TestProcesses> testProcesses;
        private VideoInfo videoInfo;
        private StepInfo stepInfo;

/*        private Integer scriptType;
        private Integer osType;
        private Integer orderNum;
        private List<Object> scriptTags;*/
    }
    @Data
    public static class TestProcesses {
        private String stage;
        private String name;
        private Long startTime;
        private Long endTime;
        private Long totalTime;
    }
    @Data
    public static class VideoInfo {
        private Integer errorCode;
        private String url;

    }
    @Data
    public static class StepInfo {
        private Integer stepId;
        private Integer errorCode;
        private Integer resultCategory;
        private String action;
        private String descr;
        private String errorMsg;
        private String callTag;
        private Long startTime;
        private Long endTime;

    }
    public boolean isSuccess() {
        return Objects.equals(code, 0);
    }
}
