package io.metersphere.testin.controller;

import com.github.pagehelper.PageHelper;
import io.metersphere.commons.exception.MSException;
import io.metersphere.commons.utils.PageUtils;
import io.metersphere.commons.utils.Pager;
import io.metersphere.controller.handler.annotation.NoResultHolder;
import io.metersphere.testin.dto.faceMsFront.MsProjectTestinProjectTeamWithEmailDto;
import io.metersphere.testin.entity.MsProjectTestinProjectTeam;
import io.metersphere.testin.service.MsProjectTestinProjectTeamService;
import io.metersphere.testin.vo.MsProjectTestinProjectTeamCombinVo;
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
    @ApiModelProperty("查询企业下所有项目组列表")
    @PostMapping("/list/{goPage}/{pageSize}")
    @NoResultHolder
//    @RequiresPermissions("PROJECT_TRACK_PLAN:READ")
//    public Pager<List<MsProjectTestinProjectTeamCombinVo>> list(@PathVariable Integer goPage, @PathVariable Integer pageSize, @RequestBody MsProjectTestinProjectTeamWithEmailDto msProjectTestinProjectTeamWithEmailDto) {
    public Map<String, Object> list(@PathVariable Integer goPage, @PathVariable Integer pageSize, @RequestBody MsProjectTestinProjectTeamWithEmailDto msProjectTestinProjectTeamWithEmailDto) {
//        com.github.pagehelper.Page<Object> page = PageHelper.startPage(goPage, pageSize, true);
        List<MsProjectTestinProjectTeamCombinVo> msProjectTestinProjectTeamCombinVos = msProjectTestinProjectTeamService.listMsProjectTestinProjectTeam(goPage, pageSize, msProjectTestinProjectTeamWithEmailDto);
//        msProjectTestinProjectTeamCombinVos

        /*msProjectTestinProjectTeamCombinVos.stream()
                .filter(p ->
                        (int) p.get("age") > 25 &&
                                "女".equals(p.get("gender")))
                .skip(start)     // 跳过前面的记录
                .limit(pageSize)  // 取出指定数量的记录
                .collect(Collectors.toList());
        System.out.println(result2);*/
        List<MsProjectTestinProjectTeamCombinVo> result3 = new ArrayList<>();
        int start = (goPage - 1) * pageSize;  // 计算起始记录位置
        if (StringUtils.isNotBlank(msProjectTestinProjectTeamWithEmailDto.getNameOrDescr())){
            //List<MsProjectTestinProjectTeamCombinVo>
            result3 = msProjectTestinProjectTeamCombinVos.stream()
                    .filter(p ->
                            (p.getName()).contains(msProjectTestinProjectTeamWithEmailDto.getNameOrDescr()) ||
                                    (StringUtils.isNotBlank(p.getDescr())&&( p.getDescr()).contains(msProjectTestinProjectTeamWithEmailDto.getNameOrDescr())))
                    .skip(start)     // 跳过前面的记录
                    .limit(pageSize)  // 取出指定数量的记录
                    .collect(Collectors.toList());
        }else {
            result3=msProjectTestinProjectTeamCombinVos;
        }
        // 3. 模糊查询名字或性别包含 "五" 的Person


        int totalPages = (int) Math.ceil((double) result3.size() / pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put("data", result3);
        result.put("totalPages", totalPages);
        result.put("pageNum", goPage);
        result.put("pageSize", pageSize);
        return result;
//        return PageUtils.setPageInfo(page, msProjectTestinProjectTeamService.listMsProjectTestinProjectTeam(goPage,pageSize,msProjectTestinProjectTeamWithEmailDto));
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

