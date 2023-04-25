package io.metersphere.testin.service.impl;

import io.metersphere.base.domain.TestCaseWithBLOBs;
import io.metersphere.base.mapper.TestCaseMapper;
import io.metersphere.commons.exception.MSException;
import io.metersphere.testin.boost.TestInApiExecutor;
import io.metersphere.testin.dao.MsProjectTestinProjectTeamDao;
import io.metersphere.testin.dto.faceMsFront.TestCaseScriptInformationWithEmailDto;
import io.metersphere.testin.entity.MsProjectTestinProjectTeam;
import io.metersphere.testin.entity.TestCaseScriptInformation;
import io.metersphere.testin.dao.TestCaseScriptInformationDao;
import io.metersphere.testin.service.TestCaseScriptInformationService;
import io.metersphere.testin.util.JackJsonUtils;
import io.metersphere.testin.vo.TestCaseScriptInformationCombinVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (TestCaseScriptInformation)表服务实现类
 *
 * @author makejava
 * @since 2023-04-20 11:47:53
 */
@Service("testCaseScriptInformationService")
public class TestCaseScriptInformationServiceImpl implements TestCaseScriptInformationService {
    @Resource
    private TestCaseScriptInformationDao testCaseScriptInformationDao;
    @Resource
    private TestInApiExecutor testInApiExecutor;

    @Resource
    private TestCaseMapper testCaseMapper;

    @Resource
    private MsProjectTestinProjectTeamDao msProjectTestinProjectTeamDao;
    /**
     * 通过ID查询单条数据
     *
     * @return 实例对象
     */
    @Override
    public TestCaseScriptInformation queryById() {
        return this.testCaseScriptInformationDao.queryById();
    }

    @Override
    public TestCaseScriptInformation queryTestCaseScriptInformationByTestCaseId(String testCaseId) {
        return this.testCaseScriptInformationDao.queryTestCaseScriptInformationByTestCaseId(testCaseId);
    }

    /**
     * 分页查询
     *
     * @param testCaseScriptInformation 筛选条件
     * @param pageRequest               分页对象
     * @return 查询结果
     */
    @Override
    public Page<TestCaseScriptInformation> queryByPage(TestCaseScriptInformation testCaseScriptInformation, PageRequest pageRequest) {
        long total = this.testCaseScriptInformationDao.count(testCaseScriptInformation);
        return new PageImpl<>(this.testCaseScriptInformationDao.queryAllByLimit(testCaseScriptInformation, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param testCaseScriptInformation 实例对象
     * @return 实例对象
     */
    //TestCaseScriptInformation
    @Override
    public Boolean insert(TestCaseScriptInformation testCaseScriptInformation) {
        return this.testCaseScriptInformationDao.insert(testCaseScriptInformation)>0;
        //return testCaseScriptInformation;
    }

    /**
     * 修改数据
     *
     * @param testCaseScriptInformation 实例对象
     * @return 实例对象
     */
    //TestCaseScriptInformation
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean update(TestCaseScriptInformation testCaseScriptInformation) {
        return this.testCaseScriptInformationDao.update(testCaseScriptInformation)>0;
        //return this.queryById();
    }

    /**
     * 通过主键删除数据
     *
     * @param
     * @param testCaseId
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String testCaseId) {
        return this.testCaseScriptInformationDao.deleteById(testCaseId) > 0;
    }
    public String getProjectIdByTestCaseId(String testCaseId) {
        TestCaseWithBLOBs testCaseWithBLOBs = testCaseMapper.selectByPrimaryKey(testCaseId);
        if (testCaseWithBLOBs == null) {
            return null;
        }else {
            return testCaseWithBLOBs.getProjectId();
        }
    }
    @Override
    public List<TestCaseScriptInformationCombinVo> listTestCaseScriptInformation(int goPage, int pageSize, TestCaseScriptInformationWithEmailDto testCaseScriptInformationWithEmailDto) {
        List<TestCaseScriptInformationCombinVo> result=new ArrayList<>();
        String testCaseId = testCaseScriptInformationWithEmailDto.getTestCaseId();
        String msProjectId=getProjectIdByTestCaseId(testCaseId);
        if (StringUtils.isEmpty(msProjectId)) {
            MSException.throwException("测试用例不在ms项目组下,非法的测试用例");
        }else {
            MsProjectTestinProjectTeam msProjectTestinProjectTeam = msProjectTestinProjectTeamDao.queryIsHaveTestInProjectTeamByIdMsProject(msProjectId);
            if (msProjectTestinProjectTeam.getTestInProjectId() != null) {
                TestCaseScriptInformation testCaseScriptInformation=TestCaseScriptInformation.builder()
                        .testCaseId(testCaseScriptInformationWithEmailDto.getTestCaseId())
                        .scriptNo(testCaseScriptInformationWithEmailDto.getScriptNo())
                        .scriptId(testCaseScriptInformationWithEmailDto.getScriptId())
                        .scriptCreateUser(testCaseScriptInformationWithEmailDto.getScriptCreateUser())
                        .scriptCreateDesc(testCaseScriptInformationWithEmailDto.getScriptCreateDesc())
                        .testInProjectId(testCaseScriptInformationWithEmailDto.getTestInProjectId())
                        .scriptUpdateUserid(testCaseScriptInformationWithEmailDto.getScriptUpdateUserid())
                        .scriptUpdateDesc(testCaseScriptInformationWithEmailDto.getScriptUpdateDesc())
                        .channelId(testCaseScriptInformationWithEmailDto.getChannelId())
                        .scriptCreateTime(testCaseScriptInformationWithEmailDto.getScriptCreateTime())
                        .scriptUpdateTime(testCaseScriptInformationWithEmailDto.getScriptUpdateTime())
                        .appName(testCaseScriptInformationWithEmailDto.getAppName())
                        .build();
                List<TestCaseScriptInformation> msTestCaseScriptInformationFromDb = this.testCaseScriptInformationDao.queryAll(testCaseScriptInformation);

                List<TestCaseScriptInformation> msTestCaseScriptInformationFromQueryTestIn = testInApiExecutor.queryTestCaseScriptInformationFromQueryTestIn(goPage, pageSize, msProjectTestinProjectTeam.getTestInProjectId(),testCaseScriptInformationWithEmailDto);

                if (CollectionUtils.isNotEmpty(msTestCaseScriptInformationFromDb)) {
                    if (CollectionUtils.isNotEmpty(msTestCaseScriptInformationFromQueryTestIn)) {
                        //包装，返从testIn那边查到的结果和数据查的结果并级
                        result= msTestCaseScriptInformationFromQueryTestInWrap(msTestCaseScriptInformationFromDb.get(0),msTestCaseScriptInformationFromQueryTestIn);
                    }
                }else{
                    if (CollectionUtils.isNotEmpty(msTestCaseScriptInformationFromQueryTestIn)) {
                        //包装，直接返从testIn那边查到的结果
                        result= msTestCaseScriptInformationFromQueryTestInStraight(msTestCaseScriptInformationFromQueryTestIn);
                    }
                }

            }else {
                MSException.throwException("该用例所属项目未关联testIn项目组");
            }
        }
        return result;
    }

    @Override
    public List<TestCaseScriptInformation> queryAll(TestCaseScriptInformation testCaseScriptInformation) {
        return this.testCaseScriptInformationDao.queryAll(testCaseScriptInformation);
    }

    private List<TestCaseScriptInformationCombinVo> msTestCaseScriptInformationFromQueryTestInStraight(List<TestCaseScriptInformation> list) {
        List<TestCaseScriptInformationCombinVo> result=new ArrayList<>();
        for (TestCaseScriptInformation testCaseScriptInformation : list) {
            TestCaseScriptInformationCombinVo testCaseScriptInformationCombinVo = JackJsonUtils.obj2pojo(testCaseScriptInformation, TestCaseScriptInformationCombinVo.class);
            testCaseScriptInformationCombinVo.setFlag(false);
            result.add(testCaseScriptInformationCombinVo);
        }
        return result;
    }

    private List<TestCaseScriptInformationCombinVo> msTestCaseScriptInformationFromQueryTestInWrap(TestCaseScriptInformation testCaseScriptInformationFromDb, List<TestCaseScriptInformation> list) {
        List<TestCaseScriptInformationCombinVo> result=new ArrayList<>();
        for (TestCaseScriptInformation testCaseScriptInformation : list) {

            TestCaseScriptInformationCombinVo testCaseScriptInformationCombinVo;

            if (testCaseScriptInformationFromDb.getScriptNo().equals(testCaseScriptInformation.getScriptNo())){
                testCaseScriptInformationCombinVo = JackJsonUtils.obj2pojo(testCaseScriptInformation, TestCaseScriptInformationCombinVo.class);
                testCaseScriptInformationCombinVo.setFlag(true);
            }else {
                testCaseScriptInformationCombinVo = JackJsonUtils.obj2pojo(testCaseScriptInformation, TestCaseScriptInformationCombinVo.class);
                testCaseScriptInformationCombinVo.setFlag(false);
            }
            result.add(testCaseScriptInformationCombinVo);
        }
        return result;
    }

}
