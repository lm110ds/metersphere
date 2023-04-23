package io.metersphere.testin.controller;

import com.github.pagehelper.PageHelper;
import io.metersphere.commons.exception.MSException;
import io.metersphere.commons.utils.PageUtils;
import io.metersphere.commons.utils.Pager;
import io.metersphere.testin.dto.faceMsFront.MsProjectTestinProjectTeamWithEmailDto;
import io.metersphere.testin.entity.MsProjectTestinProjectTeam;
import io.metersphere.testin.service.MsProjectTestinProjectTeamService;
import io.metersphere.testin.vo.MsProjectTestinProjectTeamCombinVo;
import io.metersphere.track.service.TestPlanService;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    @Resource
    TestPlanService testPlanService;
    /**
     * 分页查询
     *
     * @param
     * @param
     * @return 查询结果
     */
    /*@PostMapping("/queryByPage")
    public ResponseEntity<Page<MsProjectTestinProjectTeam>> queryByPage(@RequestBody MsProjectTestinProjectTeam msProjectTestinProjectTeam, PageRequest pageRequest) {
        return ResponseEntity.ok(this.msProjectTestinProjectTeamService.queryByPage(msProjectTestinProjectTeam, pageRequest));
    }*/

    @PostMapping("/list/{goPage}/{pageSize}")
//    @RequiresPermissions("PROJECT_TRACK_PLAN:READ")
    public Pager<List<MsProjectTestinProjectTeamCombinVo>> list(@PathVariable Integer goPage, @PathVariable Integer pageSize, @RequestBody MsProjectTestinProjectTeamWithEmailDto msProjectTestinProjectTeamWithEmailDto) {
        com.github.pagehelper.Page<Object> page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, msProjectTestinProjectTeamService.listMsProjectTestinProjectTeam(goPage,pageSize,msProjectTestinProjectTeamWithEmailDto));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    /*@GetMapping("{id}")
    public ResponseEntity<MsProjectTestinProjectTeam> queryById(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.msProjectTestinProjectTeamService.queryById());
    }*/

    /**
     * 新增数据
     *
     * @param msProjectTestinProjectTeam 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResponseEntity add(@RequestBody MsProjectTestinProjectTeam msProjectTestinProjectTeam) {
        if (StringUtils.isBlank(msProjectTestinProjectTeam.getMsProjectId())
                ||msProjectTestinProjectTeam.getTestInProjectId()==null
                ||StringUtils.isBlank(msProjectTestinProjectTeam.getName())
        )  MSException.throwException("msProjectId、testInProjectId、name不能为空");
        if (this.msProjectTestinProjectTeamService.queryIsHaveTestInProjectTeamByIdMsProject(msProjectTestinProjectTeam) != null) {
            return ResponseEntity.badRequest().body("该ms项目关联TestIn项目组，暂时无法关联");
        }
        return ResponseEntity.ok(this.msProjectTestinProjectTeamService.insert(msProjectTestinProjectTeam));
    }

    /**
     * 编辑数据
     *
     * @param msProjectTestinProjectTeam 实体
     * @return 编辑结果
     */
    /*@PutMapping
    public ResponseEntity<MsProjectTestinProjectTeam> edit(MsProjectTestinProjectTeam msProjectTestinProjectTeam) {
        return ResponseEntity.ok(this.msProjectTestinProjectTeamService.update(msProjectTestinProjectTeam));
    }*/

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    /*@DeleteMapping
    public ResponseEntity<Boolean> deleteById() {
        return ResponseEntity.ok(this.msProjectTestinProjectTeamService.deleteById());
    }*/

}

