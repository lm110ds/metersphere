package io.metersphere.testin.service.impl;

import io.metersphere.testin.boost.TestInApiExecutor;
import io.metersphere.testin.dto.faceMsFront.MsProjectTestinProjectTeamWithEmailDto;
import io.metersphere.testin.entity.MsProjectTestinProjectTeam;
import io.metersphere.testin.dao.MsProjectTestinProjectTeamDao;
import io.metersphere.testin.entity.MsProjectTestinProjectTeamNameOrDescr;
import io.metersphere.testin.service.MsProjectTestinProjectTeamService;
import io.metersphere.testin.util.JackJsonUtils;
import io.metersphere.testin.vo.MsProjectTestinProjectTeamCombinVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (MsProjectTestinProjectTeam)表服务实现类
 *
 * @author makejava
 * @since 2023-04-20 11:47:50
 */
@Service("msProjectTestinProjectTeamService")
public class MsProjectTestinProjectTeamServiceImpl implements MsProjectTestinProjectTeamService {
    @Resource
    private
    MsProjectTestinProjectTeamDao msProjectTestinProjectTeamDao;
    @Resource
    private TestInApiExecutor testInApiExecutor;

    /**
     * 通过ID查询单条数据
     *
     * @return 实例对象
     */
    @Override
    public MsProjectTestinProjectTeam queryById() {
        return this.msProjectTestinProjectTeamDao.queryById();
    }

    /**
     * 分页查询
     *
     * @param msProjectTestinProjectTeam 筛选条件
     * @param pageRequest                分页对象
     * @return 查询结果
     */
    @Override
    public Page<MsProjectTestinProjectTeam> queryByPage(MsProjectTestinProjectTeam msProjectTestinProjectTeam, PageRequest pageRequest) {
        long total = this.msProjectTestinProjectTeamDao.count(msProjectTestinProjectTeam);
        return new PageImpl<>(this.msProjectTestinProjectTeamDao.queryAllByLimit(msProjectTestinProjectTeam, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param msProjectTestinProjectTeam 实例对象
     * @return 实例对象
     */
    @Override
    public Boolean insert(MsProjectTestinProjectTeam msProjectTestinProjectTeam) {
        return this.msProjectTestinProjectTeamDao.insert(msProjectTestinProjectTeam)>0;
    }
    @Override
    public MsProjectTestinProjectTeam queryIsHaveTestInProjectTeamByIdMsProject(MsProjectTestinProjectTeam msProjectTestinProjectTeam) {
        return msProjectTestinProjectTeamDao.queryIsHaveTestInProjectTeamByIdMsProject(msProjectTestinProjectTeam.getMsProjectId());
    }

    /**
     * 修改数据
     *
     * @param msProjectTestinProjectTeam 实例对象
     * @return 实例对象
     */
    @Override
    public MsProjectTestinProjectTeam update(MsProjectTestinProjectTeam msProjectTestinProjectTeam) {
        this.msProjectTestinProjectTeamDao.update(msProjectTestinProjectTeam);
        return this.queryById();
    }

    /**
     * 通过主键删除数据
     *
     * @return 是否成功
     */
    @Override
    public boolean deleteById() {
        return this.msProjectTestinProjectTeamDao.deleteById() > 0;
    }

    @Override
    public List<MsProjectTestinProjectTeamCombinVo> listMsProjectTestinProjectTeam(Integer goPage, Integer pageSize, MsProjectTestinProjectTeamWithEmailDto msProjectTestinProjectTeamWithEmailDto) {
        List<MsProjectTestinProjectTeamCombinVo> result=new ArrayList<>();
        MsProjectTestinProjectTeamNameOrDescr msProjectTestinProjectTeamNameOrDescr =new MsProjectTestinProjectTeamNameOrDescr();
        msProjectTestinProjectTeamNameOrDescr.setMsProjectId(msProjectTestinProjectTeamWithEmailDto.getMsProjectId());
        msProjectTestinProjectTeamNameOrDescr.setTestInProjectId(msProjectTestinProjectTeamWithEmailDto.getTestInProjectId());
        msProjectTestinProjectTeamNameOrDescr.setEid(msProjectTestinProjectTeamWithEmailDto.getEid());
        msProjectTestinProjectTeamNameOrDescr.setThirdPartyProjectid(msProjectTestinProjectTeamWithEmailDto.getThirdPartyProjectid());
        msProjectTestinProjectTeamNameOrDescr.setStatus(msProjectTestinProjectTeamWithEmailDto.getStatus());
        msProjectTestinProjectTeamNameOrDescr.setCreateTime(msProjectTestinProjectTeamWithEmailDto.getCreateTime());
        msProjectTestinProjectTeamNameOrDescr.setExtend(msProjectTestinProjectTeamWithEmailDto.getExtend());
        msProjectTestinProjectTeamNameOrDescr.setProductNo(msProjectTestinProjectTeamWithEmailDto.getProductNo());
        msProjectTestinProjectTeamNameOrDescr.setNameOrDescr(msProjectTestinProjectTeamWithEmailDto.getNameOrDescr());

        List<MsProjectTestinProjectTeam> msProjectTestInProjectTeams = this.msProjectTestinProjectTeamDao.queryNameOrDesc(msProjectTestinProjectTeamNameOrDescr);
        List<MsProjectTestinProjectTeam> msProjectTestInProjectTeamsFromQueryTestIn = testInApiExecutor.msProjectTestInProjectTeamsFromQueryTestIn(goPage, pageSize, msProjectTestinProjectTeamWithEmailDto);
        if (CollectionUtils.isNotEmpty(msProjectTestInProjectTeams)) {
            if (CollectionUtils.isNotEmpty(msProjectTestInProjectTeamsFromQueryTestIn)) {
                result= msProjectTestInProjectTeamsFromQueryTestInWrap(msProjectTestInProjectTeams.get(0),msProjectTestInProjectTeamsFromQueryTestIn);
            }
        }else{
            if (CollectionUtils.isNotEmpty(msProjectTestInProjectTeamsFromQueryTestIn)) {
                result= msProjectTestInProjectTeamsFromQueryTestInStraight(msProjectTestInProjectTeamsFromQueryTestIn);
            }
        }
        return result;
    }
    public List<MsProjectTestinProjectTeamCombinVo> msProjectTestInProjectTeamsFromQueryTestInStraight(List<MsProjectTestinProjectTeam> list) {
        List<MsProjectTestinProjectTeamCombinVo> result=new ArrayList<>();
        for (MsProjectTestinProjectTeam msProjectTestinProjectTeam : list) {
            MsProjectTestinProjectTeamCombinVo msProjectTestinProjectTeamCombinVo = JackJsonUtils.obj2pojo(msProjectTestinProjectTeam, MsProjectTestinProjectTeamCombinVo.class);
            msProjectTestinProjectTeamCombinVo.setFlag(false);
            result.add(msProjectTestinProjectTeamCombinVo);
        }
        return result;
    }
    public List<MsProjectTestinProjectTeamCombinVo> msProjectTestInProjectTeamsFromQueryTestInWrap(MsProjectTestinProjectTeam msProjectTestInProjectTeamFromDb,List<MsProjectTestinProjectTeam> list) {
        List<MsProjectTestinProjectTeamCombinVo> result=new ArrayList<>();
        for (MsProjectTestinProjectTeam msProjectTestinProjectTeam : list) {
            MsProjectTestinProjectTeamCombinVo msProjectTestinProjectTeamCombinVo;
            if (msProjectTestInProjectTeamFromDb.getTestInProjectId().equals(msProjectTestinProjectTeam.getTestInProjectId())){
                msProjectTestinProjectTeamCombinVo = JackJsonUtils.obj2pojo(msProjectTestinProjectTeam, MsProjectTestinProjectTeamCombinVo.class);
                msProjectTestinProjectTeamCombinVo.setFlag(true);
            }else {
                msProjectTestinProjectTeamCombinVo = JackJsonUtils.obj2pojo(msProjectTestinProjectTeam, MsProjectTestinProjectTeamCombinVo.class);
                msProjectTestinProjectTeamCombinVo.setFlag(false);
            }
            result.add(msProjectTestinProjectTeamCombinVo);
        }
        return result;
    }
}
