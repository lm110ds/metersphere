package io.metersphere.track.dto;

import io.metersphere.base.domain.IssuesDao;
import io.metersphere.base.domain.TestCaseWithBLOBs;
import io.metersphere.commons.constants.TestPlanTestCaseStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Setter
public class TestPlanCaseDtoWithResultCategory extends TestPlanCaseDTO {    //WithResultCategory
    private Integer resultCategory;

//    public String getResultCategoryName() {return resultCategoryName;}
    public String getResultCategoryName() {
        return mapResultCategoryNameMap.get(this.resultCategory);
    }

    /*public void setResultCategoryName(String resultCategoryName) {
        this.resultCategoryName = resultCategoryName;
    }*/

    private String resultCategoryName;
    private String resultCategoryNameStatus;
    public String getResultCategoryNameStatus() {
        return mapResultCategoryNameStatusMap.get(getResultCategoryName());
    }
    public static Map<Integer, String> mapResultCategoryNameMap=new HashMap<Integer, String>(){{
        put(1,"通过");
        put(2,"安装失败");
        put(3,"卸载失败");
        put(4,"启动失败");
        put(5,"运行失败");
        put(6,"功能异常");
        put(7,"未执行");
        put(8,"超时");
        put(9,"取消");
        put(10,"monkey失败");
        put(11,"警告");
        put(12,"应用崩溃");
        put(13,"脚本错误");
        put(14,"应用无响应");
        put(16,"环境异常");
        put(100,"忽略");
        put(null,"");
    }};
    public static Map<String, String> mapResultCategoryNameStatusMap=new HashMap<String, String>(){{
        put("通过", TestPlanTestCaseStatus.Pass.name());
        put("安装失败", TestPlanTestCaseStatus.Failure.name());
        put("卸载失败", TestPlanTestCaseStatus.Failure.name());
        put("启动失败", TestPlanTestCaseStatus.Failure.name());
        put("运行失败", TestPlanTestCaseStatus.Failure.name());
        put("monkey失败", TestPlanTestCaseStatus.Failure.name());
        put("应用崩溃", TestPlanTestCaseStatus.Failure.name());
        put("脚本错误", TestPlanTestCaseStatus.Failure.name());
        put("应用无响应", TestPlanTestCaseStatus.Failure.name());

        put("忽略", TestPlanTestCaseStatus.Skip.name());
        put("取消", TestPlanTestCaseStatus.Skip.name());

        put("功能异常", TestPlanTestCaseStatus.Blocking.name());
        put("未执行", TestPlanTestCaseStatus.Blocking.name());
        put("超时", TestPlanTestCaseStatus.Blocking.name());
        put("警告", TestPlanTestCaseStatus.Blocking.name());
        put("环境异常", TestPlanTestCaseStatus.Blocking.name());
        put("","");
    }};
}
