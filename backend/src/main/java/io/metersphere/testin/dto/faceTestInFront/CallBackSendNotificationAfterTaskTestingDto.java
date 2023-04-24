package io.metersphere.testin.dto.faceTestInFront;

import io.metersphere.testin.dto.BaseDto;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CallBackSendNotificationAfterTaskTestingDto extends BaseDto {
    // 2.6	任务提测消息  任务提测后发送通知
    private String serviceType;
    private String action;
    private String sig;
    private String taskid;
    //提测时提供
    private String additionalInfo;
    private Content content;

    @Data
    @Builder
    public static class Content {
        private int projectid;
        private String additionalInfo;
        private String execStandard;
        //AppInfo
        private AppInfo appInfo;
        private List<Object> devices;
        private List<Object> scripts;
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

}



