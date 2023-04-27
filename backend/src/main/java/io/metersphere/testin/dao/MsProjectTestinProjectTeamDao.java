package io.metersphere.testin.dao;

import io.metersphere.testin.entity.MsProjectTestinProjectTeam;
import io.metersphere.testin.entity.MsProjectTestinProjectTeamNameOrDescr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (MsProjectTestinProjectTeam)表数据库访问层
 *
 * @author makejava
 * @since 2023-04-20 11:47:34
 */
@Repository
public interface MsProjectTestinProjectTeamDao {

    /**
     * 通过ID查询单条数据
     *
     * @param 主键
     * @return 实例对象
     */
    MsProjectTestinProjectTeam queryById();

    MsProjectTestinProjectTeam queryIsHaveTestInProjectTeamByIdMsProject(String msProjectId);

    /**
     * 查询指定行数据
     *
     * @param msProjectTestinProjectTeam 查询条件
     * @param pageable                   分页对象
     * @return 对象列表
     */
    List<MsProjectTestinProjectTeam> queryAllByLimit(MsProjectTestinProjectTeam msProjectTestinProjectTeam, @Param("pageable") Pageable pageable);

    List<MsProjectTestinProjectTeam> queryAll(MsProjectTestinProjectTeam msProjectTestinProjectTeam);
    List<MsProjectTestinProjectTeam> queryNameOrDesc(MsProjectTestinProjectTeamNameOrDescr msProjectTestinProjectTeamNameOrDescr);

    /**
     * 统计总行数
     *
     * @param msProjectTestinProjectTeam 查询条件
     * @return 总行数
     */
    long count(MsProjectTestinProjectTeam msProjectTestinProjectTeam);

    /**
     * 新增数据
     *
     * @param msProjectTestinProjectTeam 实例对象
     * @return 影响行数
     */
    int insert(MsProjectTestinProjectTeam msProjectTestinProjectTeam);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MsProjectTestinProjectTeam> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MsProjectTestinProjectTeam> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MsProjectTestinProjectTeam> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MsProjectTestinProjectTeam> entities);

    /**
     * 修改数据
     *
     * @param msProjectTestinProjectTeam 实例对象
     * @return 影响行数
     */
    int update(MsProjectTestinProjectTeam msProjectTestinProjectTeam);

    /**
     * 通过主键删除数据
     *
     * @param 主键
     * @return 影响行数
     */
    int deleteById();

}

