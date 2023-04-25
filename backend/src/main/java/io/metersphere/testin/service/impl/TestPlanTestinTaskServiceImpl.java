package io.metersphere.testin.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import io.metersphere.base.domain.TestPlanWithBLOBs;
import io.metersphere.base.mapper.ext.ExtTestPlanTestCaseMapper;
import io.metersphere.commons.exception.MSException;
import io.metersphere.commons.utils.PageUtils;
import io.metersphere.commons.utils.Pager;
import io.metersphere.commons.utils.ServiceUtils;
import io.metersphere.testin.bo.QueryToObtainTheExecutionDetailsOfTheTestingReportGenerateBo;
import io.metersphere.testin.boost.TestInApiExecutor;
import io.metersphere.testin.dao.MsProjectTestinProjectTeamDao;
import io.metersphere.testin.dao.TestCaseScriptInformationDao;
import io.metersphere.testin.dto.faceMsFront.EmailDto;
import io.metersphere.testin.dto.faceMsFront.ToObtainTheExecutionDetailsOfTheTestingReportGenerateDto;
import io.metersphere.testin.dto.faceTestInFront.CallBackTaskTestingOrCompletionMessageRequestDto;
import io.metersphere.testin.dto.faceTestInFront.QueryToObtainTheExecutionDetailsOfTheTestingReportDto;
import io.metersphere.testin.entity.MsProjectTestinProjectTeam;
import io.metersphere.testin.entity.TestCaseScriptInformation;
import io.metersphere.testin.entity.TestPlanTestinTask;
import io.metersphere.testin.dao.TestPlanTestinTaskDao;
import io.metersphere.testin.service.TestPlanTestinTaskService;
import io.metersphere.testin.vo.TestPlanTestInTaskTokenReqIdCallbackUrlVo;
import io.metersphere.track.dto.TestPlanCaseDTO;
import io.metersphere.track.request.testplancase.QueryTestPlanCaseRequest;
import io.metersphere.track.service.TestPlanService;
import io.metersphere.track.service.TestPlanTestCaseService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (TestPlanTestinTask)表服务实现类
 *
 * @author makejava
 * @since 2023-04-20 11:47:53
 */
@Service("testPlanTestinTaskService")
public class TestPlanTestinTaskServiceImpl implements TestPlanTestinTaskService {
    @Resource
    private TestPlanTestinTaskDao testPlanTestinTaskDao;
    @Resource
    private TestPlanService testPlanService;
    @Resource
    private ExtTestPlanTestCaseMapper extTestPlanTestCaseMapper;
    @Resource
    private MsProjectTestinProjectTeamDao msProjectTestinProjectTeamDao;
    @Resource
    private TestCaseScriptInformationDao testCaseScriptInformationDao;
    @Resource
    TestPlanTestCaseService testPlanTestCaseService;
    @Resource
    private TestInApiExecutor testInApiExecutor;

    /**
     * 通过ID查询单条数据
     *
     * @return 实例对象
     */
    @Override
    public TestPlanTestinTask queryById(String testPlanId) {
        return this.testPlanTestinTaskDao.queryById(testPlanId);
    }

    /**
     * 分页查询
     *
     * @param testPlanTestinTask 筛选条件
     * @param pageRequest        分页对象
     * @return 查询结果
     */
    @Override
    public Page<TestPlanTestinTask> queryByPage(TestPlanTestinTask testPlanTestinTask, PageRequest pageRequest) {
        long total = this.testPlanTestinTaskDao.count(testPlanTestinTask);
        return new PageImpl<>(this.testPlanTestinTaskDao.queryAllByLimit(testPlanTestinTask, pageRequest), pageRequest, total);
    }
    @Override
    public List<TestPlanTestinTask> queryAll(TestPlanTestinTask testPlanTestinTask) {
        return this.testPlanTestinTaskDao.queryAll(testPlanTestinTask);
    }

    /**
     * 新增数据
     *
     * @param testPlanTestinTask 实例对象
     * @return 实例对象
     */
    @Override
    public TestPlanTestinTask insert(TestPlanTestinTask testPlanTestinTask) {
        this.testPlanTestinTaskDao.insert(testPlanTestinTask);
        return testPlanTestinTask;
    }

    /**
     * 修改数据
     *
     * @param testPlanTestinTask 实例对象
     * @return 实例对象
     */
    @Override
    public TestPlanTestinTask update(TestPlanTestinTask testPlanTestinTask) {
        this.testPlanTestinTaskDao.update(testPlanTestinTask);
        return this.queryById(testPlanTestinTask.getTestPlanId());
    }

    /**
     * 通过主键删除数据
     *
     * @return 是否成功
     */
    @Override
    public boolean deleteById() {
        return this.testPlanTestinTaskDao.deleteById() > 0;
    }

    @Override
    public TestPlanTestInTaskTokenReqIdCallbackUrlVo getTestPlanTestInTaskTokenReqIdCallbackUrlVo(String testPlanId, EmailDto emailDto) {
        TestPlanTestInTaskTokenReqIdCallbackUrlVo testPlanTestInTaskTokenReqIdCallbackUrlVo = new TestPlanTestInTaskTokenReqIdCallbackUrlVo();
        //测试计划所属项目关联TestIn项目组
        MsProjectTestinProjectTeam msProjectTestinProjectTeam=HasTheProjectToWhichTheTestPlanBelongsAlreadyBeenAssociatedWithTheTestinProjectGroup(testPlanId);
        if (msProjectTestinProjectTeam == null) {
            //MSException.throwException("该测试计划所属项目未关联TestIn项目组");
            return testPlanTestInTaskTokenReqIdCallbackUrlVo;
        }
        //找计划下的脚本
        List<TestPlanCaseDTO> testPlanCaseDTOList = getTestPlanCases(testPlanId,msProjectTestinProjectTeam.getMsProjectId());
        if (CollectionUtils.isEmpty(testPlanCaseDTOList)) {
            MSException.throwException("该测试计划未关联测试用例");
            return testPlanTestInTaskTokenReqIdCallbackUrlVo;
        }
        //找token
        String token=testInApiExecutor.ObtainTheUserTokenForTheTestInSystem(msProjectTestinProjectTeam.getTestInProjectId(),emailDto.getEmail());
        if (StringUtils.isEmpty(token)){
            MSException.throwException("获取token异常,请稍后重试");
            return testPlanTestInTaskTokenReqIdCallbackUrlVo;
        }
        //生成任务
        String callbackUrl="https://10.245.9.13:8443/testPlanTestInTask/callback";
        String reqId=testInApiExecutor.InitiateDataRequestForTestingTaskTestin(testPlanId,callbackUrl,testPlanCaseDTOList,emailDto,token,msProjectTestinProjectTeam);
        TestPlanTestinTask testPlanTestinTask =new TestPlanTestinTask();
        if (StringUtils.isNotBlank(reqId)) {
            testPlanTestinTask.setTestPlanId(testPlanId);
            testPlanTestinTask.setTaskid(reqId);
            //先查询，如果有则更新，否则 insert
            List<TestPlanTestinTask> testPlanTestinTasks = this.testPlanTestinTaskDao.queryAll(testPlanTestinTask);
            if (CollectionUtils.isNotEmpty(testPlanTestinTasks)){   //如果更新成功 return
                if(this.testPlanTestinTaskDao.update(testPlanTestinTask)>0){
                    testPlanTestInTaskTokenReqIdCallbackUrlVo.setReqId(reqId);
                    testPlanTestInTaskTokenReqIdCallbackUrlVo.setToken(token);
                    return testPlanTestInTaskTokenReqIdCallbackUrlVo;
                }
            }
            //如果insert成功 return
            if(this.testPlanTestinTaskDao.insert(testPlanTestinTask)>0){
                testPlanTestInTaskTokenReqIdCallbackUrlVo.setReqId(reqId);
                testPlanTestInTaskTokenReqIdCallbackUrlVo.setToken(token);
                return testPlanTestInTaskTokenReqIdCallbackUrlVo;
            }
        }
        // 成功 保存任务_计划 return token reqId
        return testPlanTestInTaskTokenReqIdCallbackUrlVo;
    }

    @Override
    public Object callback(CallBackTaskTestingOrCompletionMessageRequestDto callBackTaskTestingOrCompletionMessageRequestDto) {
        CallBackTaskTestingOrCompletionMessageRequestDto.Content content = callBackTaskTestingOrCompletionMessageRequestDto.getContent();
        String taskid = callBackTaskTestingOrCompletionMessageRequestDto.getTaskid();
        String planId = content.getAdditionalInfo();
        Integer projectid = content.getProjectid();

        TestPlanTestinTask testPlanTestinTask=new TestPlanTestinTask();
        if (StringUtils.isNotBlank(planId)){
            testPlanTestinTask.setTestPlanId(planId);
        }
        testPlanTestinTask.setTaskid(taskid);

        if (callBackTaskTestingOrCompletionMessageRequestDto.getAction().equals("createTask")) {
            String execStandard = content.getExecStandard();
            // 查询是否存在，如果存在则更新

            List<TestPlanTestinTask> testPlanTestinTasks = this.testPlanTestinTaskDao.queryAll(testPlanTestinTask);
            if (CollectionUtils.isNotEmpty(testPlanTestinTasks)){   //如果更新成功 return
                TestPlanTestinTask testPlanTestInTaskFromDb = testPlanTestinTasks.get(0);
                testPlanTestInTaskFromDb.setTestInProjectid(projectid);
                testPlanTestInTaskFromDb.setExecStandard(execStandard);
                if (StringUtils.isNotBlank(planId)){
                    testPlanTestinTask.setTestPlanId(planId);
                }
                if(this.testPlanTestinTaskDao.update(testPlanTestInTaskFromDb)>0){
                    // 是否是执行获取报告存一份 获取报告执行明细 是分页的
                    return true;
                }
            }
        }
        if (callBackTaskTestingOrCompletionMessageRequestDto.getAction().equals("complete")) {
            List<CallBackTaskTestingOrCompletionMessageRequestDto.CategorySummary> categorySummary = content.getSummaryInfo().getCategorySummary();
            // 查询是否存在，如果存在则更新
            //如果完成，更新报告->最后一个接口请求报告明细，可以放单独某个字段下或者其他分表细表里面
            List<TestPlanTestinTask> testPlanTestinTasks = this.testPlanTestinTaskDao.queryAll(testPlanTestinTask);
            if (CollectionUtils.isNotEmpty(testPlanTestinTasks)){   //如果更新成功 return
                TestPlanTestinTask testPlanTestInTaskFromDb = testPlanTestinTasks.get(0);
                testPlanTestInTaskFromDb.setTestInProjectid(projectid);
                testPlanTestInTaskFromDb.setSummaryinfo(JSON.toJSONString(categorySummary));
                if (StringUtils.isNotBlank(planId)){
                    testPlanTestinTask.setTestPlanId(planId);
                }
                if(this.testPlanTestinTaskDao.update(testPlanTestInTaskFromDb)>0){
                    ToObtainTheExecutionDetailsOfTheTestingReportGenerateDto toObtainTheExecutionDetailsOfTheTestingReportGenerateDto=new ToObtainTheExecutionDetailsOfTheTestingReportGenerateDto();
                    toObtainTheExecutionDetailsOfTheTestingReportGenerateDto.setProjectid(projectid);
                    toObtainTheExecutionDetailsOfTheTestingReportGenerateDto.setTestPlanId(planId);
                    toObtainTheExecutionDetailsOfTheTestingReportGenerateDto.setTaskid(taskid);
                    List<QueryToObtainTheExecutionDetailsOfTheTestingReportGenerateBo.TestInProjectGroupTask> testInProjectGroupTasks = testInApiExecutor.queryToObtainTheExecutionDetailsOfTheTestingReport(1, Integer.MAX_VALUE - 1, toObtainTheExecutionDetailsOfTheTestingReportGenerateDto);
                    if (CollectionUtils.isNotEmpty(testInProjectGroupTasks)) {
                        List<TestCaseScriptInformation> list=new ArrayList<>();
                        for (QueryToObtainTheExecutionDetailsOfTheTestingReportGenerateBo.TestInProjectGroupTask testInProjectGroupTask : testInProjectGroupTasks) {
                            Integer scriptNo = testInProjectGroupTask.getReportScript().getScriptNo();
                            Integer resultCategory = testInProjectGroupTask.getReportRunInfo().getStepInfo().getResultCategory();
                            list.add(TestCaseScriptInformation.builder().scriptNo(scriptNo).resultCategory(resultCategory).build());
                        }
                        this.testCaseScriptInformationDao.updateBatchSelective(list);
                    }
                    return true;
                }
            }
        }
        return null;
    }

    @Override
    public List<QueryToObtainTheExecutionDetailsOfTheTestingReportGenerateBo.TestInProjectGroupTask> listQueryToObtainTheExecutionDetailsOfTheTestingReport(Integer goPage, Integer pageSize, ToObtainTheExecutionDetailsOfTheTestingReportGenerateDto toObtainTheExecutionDetailsOfTheTestingReportGenerateDto) {
        return testInApiExecutor.queryToObtainTheExecutionDetailsOfTheTestingReport(goPage, pageSize, toObtainTheExecutionDetailsOfTheTestingReportGenerateDto);

        /*QueryToObtainTheExecutionDetailsOfTheTestingReportDto queryToObtainTheExecutionDetailsOfTheExecutionDetail=QueryToObtainTheExecutionDetailsOfTheTestingReportDto
                .builder()
                .
                .build();
        return null;*/
    }

    public MsProjectTestinProjectTeam HasTheProjectToWhichTheTestPlanBelongsAlreadyBeenAssociatedWithTheTestinProjectGroup(String testPlanId) {
        MsProjectTestinProjectTeam msProjectTestinProjectTeam = new MsProjectTestinProjectTeam();
        TestPlanWithBLOBs testPlanWithBLOBs = testPlanService.get(testPlanId);
        if (testPlanWithBLOBs == null) {
            MSException.throwException("该测试计划未在项目下");
            return null;
        }
        MsProjectTestinProjectTeam msProjectTestInProjectTeamFromDb = msProjectTestinProjectTeamDao.queryIsHaveTestInProjectTeamByIdMsProject(testPlanWithBLOBs.getProjectId());
        if (msProjectTestInProjectTeamFromDb == null) {
            //MSException.throwException("该测试计划所属项目未关联TestIn项目组");
            return msProjectTestinProjectTeam;
        }
        return msProjectTestInProjectTeamFromDb;
    }

    public List<TestPlanCaseDTO> getTestPlanCases(String testPlanId,String msProjectId){
        QueryTestPlanCaseRequest request=new QueryTestPlanCaseRequest();
        request.setPlanId(testPlanId);
        request.setProjectId(msProjectId);
        return testPlanTestCaseService.list(request);
    }
}
