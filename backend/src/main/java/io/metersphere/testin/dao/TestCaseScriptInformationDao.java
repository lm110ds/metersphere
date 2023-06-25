package io.metersphere.testin.dao;

import io.metersphere.testin.entity.MsProjectTestinProjectTeam;
import io.metersphere.testin.entity.TestCaseScriptInformation;
import io.metersphere.testin.entity.TestCaseScriptInformationScriptDesc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (TestCaseScriptInformation)表数据库访问层
 *
 * @author makejava
 * @since 2023-04-20 11:47:52
 */
@Repository
public interface TestCaseScriptInformationDao {

    /**
     * 通过ID查询单条数据
     *
     * @return 实例对象
     */
    TestCaseScriptInformation queryById();

    TestCaseScriptInformation queryTestCaseScriptInformationByTestCaseId(String testCaseId);

    /**
     * 查询指定行数据
     *
     * @param testCaseScriptInformation 查询条件
     * @param pageable                  分页对象
     * @return 对象列表
     */
    List<TestCaseScriptInformation> queryAllByLimit(TestCaseScriptInformation testCaseScriptInformation, @Param("pageable") Pageable pageable);

    List<TestCaseScriptInformation> queryAll(TestCaseScriptInformation testCaseScriptInformation);
    List<TestCaseScriptInformation> queryScriptDesc(TestCaseScriptInformationScriptDesc testCaseScriptInformation);

    /**
     * 统计总行数
     *
     * @param testCaseScriptInformation 查询条件
     * @return 总行数
     */
    long count(TestCaseScriptInformation testCaseScriptInformation);

    /**
     * 新增数据
     *
     * @param testCaseScriptInformation 实例对象
     * @return 影响行数
     */
    int insert(TestCaseScriptInformation testCaseScriptInformation);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TestCaseScriptInformation> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TestCaseScriptInformation> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TestCaseScriptInformation> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TestCaseScriptInformation> entities);

    /**
     * 修改数据
     *
     * @param testCaseScriptInformation 实例对象
     * @return 影响行数
     */
    int update(TestCaseScriptInformation testCaseScriptInformation);
    int updateBatchSelective(List<TestCaseScriptInformation> list);

//    MsProjectTestinProjectTeam queryIsHaveTestInProjectTeamByIdMsProject(String msProjectId);

    /**
     * 通过主键删除数据
     *
     * @param testCaseId
     * @return 影响行数
     */
    int deleteById(String testCaseId);

}
