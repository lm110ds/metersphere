package io.metersphere.testin.service;

import io.metersphere.testin.dto.faceMsFront.MsProjectTestinProjectTeamWithEmailDto;
import io.metersphere.testin.entity.MsProjectTestinProjectTeam;
import io.metersphere.testin.vo.MsProjectTestinProjectTeamCombinVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * (MsProjectTestinProjectTeam)表服务接口
 *
 * @author makejava
 * @since 2023-04-20 11:47:47
 */
public interface MsProjectTestinProjectTeamService {

    /**
     * 通过ID查询单条数据
     *
     * @param 主键
     * @return 实例对象
     */
    MsProjectTestinProjectTeam queryById();

    /**
     * 分页查询
     *
     * @param msProjectTestinProjectTeam 筛选条件
     * @param pageRequest                分页对象
     * @return 查询结果
     */
    Page<MsProjectTestinProjectTeam> queryByPage(MsProjectTestinProjectTeam msProjectTestinProjectTeam, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param msProjectTestinProjectTeam 实例对象
     * @return 实例对象
     */
    Boolean insert(MsProjectTestinProjectTeam msProjectTestinProjectTeam);
    MsProjectTestinProjectTeam queryIsHaveTestInProjectTeamByIdMsProject(MsProjectTestinProjectTeam msProjectTestinProjectTeam);

    /**
     * 修改数据
     *
     * @param msProjectTestinProjectTeam 实例对象
     * @return 实例对象
     */
    MsProjectTestinProjectTeam update(MsProjectTestinProjectTeam msProjectTestinProjectTeam);

    /**
     * 通过主键删除数据
     *
     * @param 主键
     * @return 是否成功
     */
    boolean deleteById();

    List<MsProjectTestinProjectTeamCombinVo> listMsProjectTestinProjectTeam(Integer goPage, Integer pageSize, MsProjectTestinProjectTeamWithEmailDto msProjectTestinProjectTeamWithEmailDto);
}
