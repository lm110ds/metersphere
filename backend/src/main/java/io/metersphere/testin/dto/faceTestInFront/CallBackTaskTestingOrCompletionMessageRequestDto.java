package io.metersphere.testin.dto.faceTestInFront;

import io.metersphere.testin.dto.BaseDto;
import lombok.*;

import java.io.Serializable;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CallBackTaskTestingOrCompletionMessageRequestDto extends BaseDto{
    // 2.6 2.7 复合体
    private String serviceType;
    private String action;
    private String taskid;
    //提测时提供
    private Content content;

    @Data
    public static class Content {
        private Integer projectid;
        private String additionalInfo;
        private String execStandard;
        private AppInfo appInfo;
        private List<PmRealAdaptDevice> devices;
        private List<PmRealAdaptScript> scripts;
        private Integer jobId;
        private SummaryInfo summaryInfo;
    }

    @Data
    public static class PmRealAdaptDevice {
        private Integer status;

        // 设备云信息
        private String cloud;

        // 设备串号
        private String deviceid;

        // 上位机账号
        private String ucomid;


        // 机型版本
        private String releaseVer;

        // 机型名称
        private String modelName;

        // 品牌id
        private Integer brandid;

        // 品牌名称
        private String brandName;

        // 别名
        private String aliasName;

        // 屏宽
        private Integer dpiWidth;

        // 屏高
        private Integer dpiHeight;

        // 屏幕尺寸信息
        private Double screenSize;

        // 设备执行状态
        private Integer execStatus;

        // 剩余任务执行数量
        private Integer nonexecutionNum;

        // 当前正在执行的子子任务
        private String curExecSubsubtaskid;

        // 脚本的总数
        private Integer scriptNum;

        // 运行结果
        private Integer resultCategory;

        // 开始执行时间
        private Long startExecTime;

        // 完成时间
        private Long finishTime;

        // 网络类型
        private String network;

        // 所属轮数
        private Integer round;

        // 设备云是否收费的标识【0：免费配额；1：付费配额；2：专享设备云】
        private Integer cloudMark;

        // 设备是否补测的标识；1：是；0：否
        private Integer retestMark;

        private String iccid;

        private String serialNumber;

        private String deviceParamSign;

        private Integer sysos;

        // 机型id
        private Integer modelid;

        private String machineVersion;
    }

    @Data
    public static class PmRealAdaptScript {
        private Integer status;
        // 使用最新脚本的标识
//        public static final Integer LAST = 1;
        public Integer LAST;

        // 脚本id
        private Integer scriptid;

        // 脚本No
        private Integer scriptNo;

        // 脚本名称
        private String scriptName;

        // 脚本地址
        private String scriptUrl;

        // 脚本标签
        private String scriptTags;

        // MD5
        private String scriptMd5;

        // 系统平台类型
        private Integer osType;

        // 脚本类型
        private Integer scriptType;

        // 脚本的执行状态
        private Integer execStatus;

        // 测试指标
        private PmRealStandard standard;

        // 脚本顺序
        private Integer orderNum;

        // 脚本级别的参数
        private String params;

        // 脚本组id
        private Integer groupid;

        // 脚本组提测数量
        private Integer groupCount;

        /*
        脚本批次，脚本列表中如果包含了脚本组数据，在快速测试执行策略中将脚本分别入库时可使用此字段作为数据批次分割判定，
        相同批次号的脚本是不可分割的必须同时入库子子任务表，同时下发给上位机
        */
        private String scriptBatchNo;

        // 脚本描述
        private String scriptDescr;

        //脚本版本锁定
        private Integer lock;

        //脚本多次遍历批次号
        private String traversingBatchNo;

        // 多端任务脚本原始编排顺序
        private Integer originalOrderNum;
    }

    @Data
    public static class PmRealStandard {
        private Integer coverInstall;
        private Integer cleanData;
        private Integer keepApp;

    }

    @Data
    public static class AppInfo {
        private String packageName;
        private String appName;
        private String startupPath;
        private Long appSizde;
        private String appMd5;
    }

    @Data
    public static class CategorySummary {
        private Integer val;
        private Integer resultCategory;
        private String type;
    }

    @Data
    public static class SummaryInfo {
        private List<CategorySummary> categorySummary;
    }
    /*public class Devices {

    }
    public class Scripts {

    }*/
}












