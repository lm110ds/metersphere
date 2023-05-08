package io.metersphere.testin.controller;

import com.github.pagehelper.PageHelper;
import io.metersphere.commons.exception.MSException;
import io.metersphere.commons.utils.PageUtils;
import io.metersphere.commons.utils.Pager;
import io.metersphere.controller.handler.annotation.NoResultHolder;
import io.metersphere.testin.dto.faceMsFront.MsProjectTestinProjectTeamWithEmailDto;
import io.metersphere.testin.entity.MsProjectTestinProjectTeam;
import io.metersphere.testin.entity.TestCaseScriptInformation;
import io.metersphere.testin.service.MsProjectTestinProjectTeamService;
import io.metersphere.testin.vo.MsProjectTestinProjectTeamCombinVo;
import io.metersphere.testin.vo.TestCaseScriptInformationCombinVo;
import io.metersphere.track.service.TestPlanService;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (MsProjectTestinProjectTeam)表控制层
 *
 * @author makejava
 * @since 2023-04-20 11:47:29
 */
@RestController
@RequestMapping("/msProjectTestinProjectTeam")
public class MsProjectTestinProjectTeamController {
    /**
     * 服务对象
     */
    @Resource
    private MsProjectTestinProjectTeamService msProjectTestinProjectTeamService;

    @ApiModelProperty("查询企业下所有项目组列表")
    @PostMapping("/list/{goPage}/{pageSize}")
    @NoResultHolder
    public Map<String, Object> list(@PathVariable Integer goPage, @PathVariable Integer pageSize, @RequestBody MsProjectTestinProjectTeamWithEmailDto msProjectTestinProjectTeamWithEmailDto) {
        List<MsProjectTestinProjectTeamCombinVo> msProjectTestinProjectTeamCombinVos = msProjectTestinProjectTeamService.listMsProjectTestinProjectTeam(goPage, pageSize, msProjectTestinProjectTeamWithEmailDto);
        List<MsProjectTestinProjectTeamCombinVo> result3 = new ArrayList<>();
        int start = (goPage - 1) * pageSize;
        if (StringUtils.isNotBlank(msProjectTestinProjectTeamWithEmailDto.getNameOrDescr())){
            result3 = msProjectTestinProjectTeamCombinVos.stream()
                    .filter(p ->
                            (p.getName()).contains(msProjectTestinProjectTeamWithEmailDto.getNameOrDescr()) ||
                                    (StringUtils.isNotBlank(p.getDescr())&&( p.getDescr()).contains(msProjectTestinProjectTeamWithEmailDto.getNameOrDescr()))
                    )
                    .skip(start)
                    .limit(pageSize)
                    .collect(Collectors.toList());
        }else {
            result3=msProjectTestinProjectTeamCombinVos;
        }

        int totalPages = (int) Math.ceil((double) result3.size() / pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put("totalCount", result3.size());
        result.put("totalPages", totalPages);

        int totalItems = result3.size();
        int fromIndex = Math.min((goPage-1) * pageSize, totalItems);
        int toIndex = Math.min(goPage * pageSize, totalItems);
        List<MsProjectTestinProjectTeamCombinVo> pagedItems = result3.subList(fromIndex, toIndex);

        result.put("totalPages", totalPages);
        result.put("pageNumber", goPage);
        result.put("data", pagedItems);
        result.put("pageSize", pageSize);
        return result;
    }

    /**
     * 通过主键查询单条数据
     *
     * @param msProjectId 主键
     * @return 单条数据
     */
    @GetMapping("/GetMsProjectTestinProjectTeam/{msProjectId}")
    public MsProjectTestinProjectTeam getMsProjectTestinProjectTeam(@PathVariable String msProjectId) {
        MsProjectTestinProjectTeam msProjectTestinProjectTeam=new MsProjectTestinProjectTeam();
        msProjectTestinProjectTeam.setMsProjectId(msProjectId);
        return this.msProjectTestinProjectTeamService.queryIsHaveTestInProjectTeamByIdMsProject(msProjectTestinProjectTeam);
    }

    /**
     * 新增数据
     *
     * @param msProjectTestinProjectTeam 实体
     * @return 新增结果
     */

    @ApiModelProperty("关联企业下的项目组")
    @PostMapping("/add")
    public Boolean add(@RequestBody MsProjectTestinProjectTeam msProjectTestinProjectTeam) {
        if (StringUtils.isBlank(msProjectTestinProjectTeam.getMsProjectId())
                ||msProjectTestinProjectTeam.getTestInProjectId()==null
                ||StringUtils.isBlank(msProjectTestinProjectTeam.getName())
        )  MSException.throwException("msProjectId、testInProjectId、name不能为空");
        if (this.msProjectTestinProjectTeamService.queryIsHaveTestInProjectTeamByIdMsProject(msProjectTestinProjectTeam) != null) {
            return false;
        }
        return this.msProjectTestinProjectTeamService.insert(msProjectTestinProjectTeam);
    }
}

