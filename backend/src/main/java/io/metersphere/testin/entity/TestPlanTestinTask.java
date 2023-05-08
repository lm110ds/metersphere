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

}

