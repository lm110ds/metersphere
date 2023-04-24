package io.metersphere.testin.service;

import io.metersphere.testin.dto.faceMsFront.EmailDto;
import io.metersphere.testin.dto.faceMsFront.ToObtainTheExecutionDetailsOfTheTestingReportGenerateDto;
import io.metersphere.testin.dto.faceTestInFront.CallBackTaskTestingOrCompletionMessageRequestDto;
import io.metersphere.testin.entity.TestPlanTestinTask;
import io.metersphere.testin.vo.TestPlanTestInTaskTokenReqIdCallbackUrlVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * (TestPlanTestinTask)表服务接口
 *
 * @author makejava
 * @since 2023-04-20 11:47:53
 */
public interface TestPlanTestinTaskService {

    /**
     * 通过ID查询单条数据
     *
     * @param 主键
     * @return 实例对象
     */
    TestPlanTestinTask queryById(String testPlanId);

    /**
     * 分页查询
     *
     * @param testPlanTestinTask 筛选条件
     * @param pageRequest        分页对象
     * @return 查询结果
     */

    List<TestPlanTestinTask> queryAll(TestPlanTestinTask testPlanTestinTask);

    Page<TestPlanTestinTask> queryByPage(TestPlanTestinTask testPlanTestinTask, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param testPlanTestinTask 实例对象
     * @return 实例对象
     */
    TestPlanTestinTask insert(TestPlanTestinTask testPlanTestinTask);

    /**
     * 修改数据
     *
     * @param testPlanTestinTask 实例对象
     * @return 实例对象
     */
    TestPlanTestinTask update(TestPlanTestinTask testPlanTestinTask);

    /**
     * 通过主键删除数据
     *
     * @return 是否成功
     */
    boolean deleteById();

    TestPlanTestInTaskTokenReqIdCallbackUrlVo getTestPlanTestInTaskTokenReqIdCallbackUrlVo(String testPlanId, EmailDto emailDto);

    Object callback(CallBackTaskTestingOrCompletionMessageRequestDto callBackTaskTestingOrCompletionMessageRequestDto);

    Object listQueryToObtainTheExecutionDetailsOfTheTestingReport(Integer goPage, Integer pageSize, ToObtainTheExecutionDetailsOfTheTestingReportGenerateDto toObtainTheExecutionDetailsOfTheTestingReportGenerateDto);
}
