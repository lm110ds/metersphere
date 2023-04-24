package io.metersphere.testin.controller;

import com.github.pagehelper.PageHelper;
import io.metersphere.commons.utils.PageUtils;
import io.metersphere.commons.utils.Pager;
import io.metersphere.testin.bo.QueryToObtainTheExecutionDetailsOfTheTestingReportGenerateBo;
import io.metersphere.testin.dto.faceMsFront.EmailDto;
import io.metersphere.testin.dto.faceMsFront.MsProjectTestinProjectTeamWithEmailDto;
import io.metersphere.testin.dto.faceMsFront.ToObtainTheExecutionDetailsOfTheTestingReportGenerateDto;
import io.metersphere.testin.dto.faceTestInFront.CallBackTaskTestingOrCompletionMessageRequestDto;
import io.metersphere.testin.entity.TestPlanTestinTask;
import io.metersphere.testin.service.TestPlanTestinTaskService;
import io.metersphere.testin.vo.MsProjectTestinProjectTeamCombinVo;
import io.metersphere.testin.vo.TestPlanTestInTaskTokenReqIdCallbackUrlVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (TestPlanTestinTask)表控制层
 *
 * @author makejava
 * @since 2023-04-20 11:47:53
 */
@RestController
@RequestMapping("/testPlanTestInTask")
public class TestPlanTestInTaskController {
    /**
     * 服务对象
     */
    @Resource
    private TestPlanTestinTaskService testPlanTestinTaskService;

    /**
     * 分页查询
     *
     * @param testPlanTestinTask 筛选条件
     * @param pageRequest        分页对象
     * @return 查询结果
     */
    /*@GetMapping
    public ResponseEntity<Page<TestPlanTestinTask>> queryByPage(TestPlanTestinTask testPlanTestinTask, PageRequest pageRequest) {
        return ResponseEntity.ok(this.testPlanTestinTaskService.queryByPage(testPlanTestinTask, pageRequest));
    }*/
    //QueryToObtainTheExecutionDetailsOfTheTestingReportDto
    @PostMapping("/GetReportList/{goPage}/{pageSize}")
    //    @RequiresPermissions("PROJECT_TRACK_PLAN:READ")
//    public Pager<List<MsProjectTestinProjectTeamCombinVo>> list(@PathVariable Integer goPage, @PathVariable Integer pageSize, @RequestBody ToObtainTheExecutionDetailsOfTheTestingReportGenerateDto toObtainTheExecutionDetailsOfTheTestingReportGenerateDto) {
    public Pager<List<QueryToObtainTheExecutionDetailsOfTheTestingReportGenerateBo.TestInProjectGroupTask>> list(@PathVariable Integer goPage, @PathVariable Integer pageSize, @RequestBody ToObtainTheExecutionDetailsOfTheTestingReportGenerateDto toObtainTheExecutionDetailsOfTheTestingReportGenerateDto) {
        com.github.pagehelper.Page<Object> page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, testPlanTestinTaskService.listQueryToObtainTheExecutionDetailsOfTheTestingReport(goPage,pageSize,toObtainTheExecutionDetailsOfTheTestingReportGenerateDto));
    }
    /**
     * 通过主键查询单条数据
     *
     * @return 单条数据
     */
    /*@GetMapping("{id}")
    public ResponseEntity<TestPlanTestinTask> queryById(String testPlanId) {
        return ResponseEntity.ok(this.testPlanTestinTaskService.queryById(testPlanId));
    }*/

    /**
     * 新增数据
     *
     * @param testPlanTestinTask 实体
     * @return 新增结果
     */
    /*@PostMapping
    public ResponseEntity<TestPlanTestinTask> add(TestPlanTestinTask testPlanTestinTask) {
        return ResponseEntity.ok(this.testPlanTestinTaskService.insert(testPlanTestinTask));
    }*/
    @PostMapping("/startTheTestInTask/{testPlanId}")
    public ResponseEntity StartTheTestInTask(@PathVariable String testPlanId, @RequestBody EmailDto emailDto) {
//        return ResponseEntity.ok(this.testPlanTestinTaskService.insert(testPlanTestinTask));
        TestPlanTestInTaskTokenReqIdCallbackUrlVo testPlanTestInTaskTokenReqIdCallbackUrlVo = this.testPlanTestinTaskService.getTestPlanTestInTaskTokenReqIdCallbackUrlVo(testPlanId,emailDto);
        if (testPlanTestInTaskTokenReqIdCallbackUrlVo != null) {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("data", testPlanTestInTaskTokenReqIdCallbackUrlVo);
            parameters.put("message", "启动成功");
            return ResponseEntity.ok(parameters);
        }else {
            return ResponseEntity.badRequest().body("该测试计划所属项目未关联TestIn项目组");
        }
    }
    @PostMapping("/callback")
    public ResponseEntity callback(CallBackTaskTestingOrCompletionMessageRequestDto callBackTaskTestingOrCompletionMessageRequestDto) {
        return ResponseEntity.ok(this.testPlanTestinTaskService.callback(callBackTaskTestingOrCompletionMessageRequestDto));
//        return ResponseEntity.ok(this.testPlanTestinTaskService.update(callBackTaskTestingOrCompletionMessageRequestDto));
    }

    /**
     * 编辑数据
     *
     * @param testPlanTestinTask 实体
     * @return 编辑结果
     */
    /*@PutMapping
    public ResponseEntity<TestPlanTestinTask> edit(TestPlanTestinTask testPlanTestinTask) {
        return ResponseEntity.ok(this.testPlanTestinTaskService.update(testPlanTestinTask));
    }*/

    /**
     * 删除数据
     *
     * @param  主键
     * @return 删除是否成功
     */
    /*@DeleteMapping
    public ResponseEntity<Boolean> deleteById() {
        return ResponseEntity.ok(this.testPlanTestinTaskService.deleteById());
    }*/

}

