package io.metersphere.testin.dao;

import io.metersphere.testin.entity.TestPlanTestinTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (TestPlanTestinTask)表数据库访问层
 *
 * @author makejava
 * @since 2023-04-20 11:47:53
 */
@Repository
public interface TestPlanTestinTaskDao {

    /**
     * 通过ID查询单条数据
     *
     * @param 主键
     * @return 实例对象
     */
    TestPlanTestinTask queryById(String testPlanId);

    /**
     * 查询指定行数据
     *
     * @param testPlanTestinTask 查询条件
     * @param pageable           分页对象
     * @return 对象列表
     */
    List<TestPlanTestinTask> queryAll(TestPlanTestinTask testPlanTestinTask);

    List<TestPlanTestinTask> queryAllByLimit(TestPlanTestinTask testPlanTestinTask, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param testPlanTestinTask 查询条件
     * @return 总行数
     */
    long count(TestPlanTestinTask testPlanTestinTask);

    /**
     * 新增数据
     *
     * @param testPlanTestinTask 实例对象
     * @return 影响行数
     */
    int insert(TestPlanTestinTask testPlanTestinTask);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TestPlanTestinTask> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TestPlanTestinTask> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TestPlanTestinTask> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TestPlanTestinTask> entities);

    /**
     * 修改数据
     *
     * @param testPlanTestinTask 实例对象
     * @return 影响行数
     */
    int update(TestPlanTestinTask testPlanTestinTask);

    /**
     * 通过主键删除数据
     *
     * @param 主键
     * @return 影响行数
     */
    int deleteById();

}

