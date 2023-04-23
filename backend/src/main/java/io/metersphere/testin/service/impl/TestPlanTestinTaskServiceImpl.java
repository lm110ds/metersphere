package io.metersphere.testin.service.impl;

import com.github.pagehelper.PageHelper;
import io.metersphere.base.domain.TestPlanWithBLOBs;
import io.metersphere.base.mapper.ext.ExtTestPlanTestCaseMapper;
import io.metersphere.commons.exception.MSException;
import io.metersphere.commons.utils.PageUtils;
import io.metersphere.commons.utils.Pager;
import io.metersphere.commons.utils.ServiceUtils;
import io.metersphere.testin.boost.TestInApiExecutor;
import io.metersphere.testin.dao.MsProjectTestinProjectTeamDao;
import io.metersphere.testin.dto.faceMsFront.EmailDto;
import io.metersphere.testin.entity.MsProjectTestinProjectTeam;
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
    TestPlanTestCaseService testPlanTestCaseService;
    @Resource
    private TestInApiExecutor testInApiExecutor;

    /**
     * 通过ID查询单条数据
     *
     * @param 主键
     * @return 实例对象
     */
    @Override
    public TestPlanTestinTask queryById() {
        return this.testPlanTestinTaskDao.queryById();
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
        return this.queryById();
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
        String token=testInApiExecutor.ObtainTheUserTokenForTheTestInSystem(msProjectTestinProjectTeam.getTestInProjectId(),emailDto);
        if (StringUtils.isEmpty(token)){
            MSException.throwException("获取token异常,请稍后重试");
            return testPlanTestInTaskTokenReqIdCallbackUrlVo;
        }
        //生成任务
        String callbackUrl="https";
        String reqId=testInApiExecutor.InitiateDataRequestForTestingTaskTestin(testPlanId,callbackUrl,testPlanCaseDTOList,emailDto,token,msProjectTestinProjectTeam);
        TestPlanTestinTask testPlanTestinTask =new TestPlanTestinTask();
        if (StringUtils.isNotBlank(reqId)) {
            testPlanTestinTask.setTestPlanId(testPlanId);
            testPlanTestinTask.setTaskid(reqId);
            //先查询，如果有则更新，否则 insert
            //如果更新成功或者insert成功
            if(this.testPlanTestinTaskDao.insert(testPlanTestinTask)>0){
                testPlanTestInTaskTokenReqIdCallbackUrlVo.setReqId(reqId);
                testPlanTestInTaskTokenReqIdCallbackUrlVo.setToken(token);
            }
        }
        // 成功 保存任务_计划 return token reqId
        return testPlanTestInTaskTokenReqIdCallbackUrlVo;
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
