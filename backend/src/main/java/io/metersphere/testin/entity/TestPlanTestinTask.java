package io.metersphere.testin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (TestPlanTestinTask)实体类
 *
 * @author makejava
 * @since 2023-04-20 11:47:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestPlanTestinTask implements Serializable {
    private static final long serialVersionUID = -27866788315146567L;
    /**
     * Test Plan ID
     */
    private String testPlanId;
    /**
     * 任务ID
     */
    private String taskid;
    /**
     * 项目组ID
     */
    private Integer testInProjectid;
    /**
     * 执行的策略normal 普通执行
     */
    private String execStandard;

    private String summaryinfo;


    public String getTestPlanId() {
        return testPlanId;
    }

    public void setTestPlanId(String testPlanId) {
        this.testPlanId = testPlanId;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public Integer getTestInProjectid() {
        return testInProjectid;
    }

    public void setTestInProjectid(Integer testInProjectid) {
        this.testInProjectid = testInProjectid;
    }

    public String getExecStandard() {
        return execStandard;
    }

    public void setExecStandard(String execStandard) {
        this.execStandard = execStandard;
    }

    public String getSummaryinfo() {
        return summaryinfo;
    }

    public void setSummaryinfo(String summaryinfo) {
        this.summaryinfo = summaryinfo;
    }

}

