package io.metersphere.testin.dto.faceTestInFront;

import io.metersphere.testin.dto.BaseDto;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CallBackTaskTestingOrCompletionMessageRequestDto extends BaseDto{
    // 2.6 2.7 复合体
    private String serviceType;
    private String action;
    private String sig;
    private String taskid;
    private Integer projectid;
    //提测时提供
    private String additionalInfo;
    private Content content;

    @Data
    @Builder
    public static class Content {
        private int projectid;
        private String additionalInfo;
        private String execStandard;
        private AppInfo appInfo;
        private List<Object> devices;
        private List<Object> scripts;
        private int jobId;
        private SummaryInfo summaryInfo;
    }
    @Data
    @Builder
    public static class AppInfo {
        private String packageName;
        private String appName;
        private String startupPath;
        private long appSizde;
        private String appMd5;
    }
    @Data
    @Builder
    public static class CategorySummary {
        private int val;
        private int resultCategory;
        private String type;
    }
    @Data
    @Builder
    public static class SummaryInfo {
        private List<CategorySummary> categorySummary;
    }
    /*public class Devices {

    }
    public class Scripts {

    }*/
}












