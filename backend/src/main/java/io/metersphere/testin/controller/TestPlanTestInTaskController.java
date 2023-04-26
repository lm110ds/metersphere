package io.metersphere.testin.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import io.metersphere.commons.utils.PageUtils;
import io.metersphere.commons.utils.Pager;
import io.metersphere.testin.bo.QueryToObtainTheExecutionDetailsOfTheTestingReportGenerateBo;
import io.metersphere.testin.dto.faceMsFront.EmailDto;
import io.metersphere.testin.dto.faceMsFront.ToObtainTheExecutionDetailsOfTheTestingReportGenerateDto;
import io.metersphere.testin.dto.faceTestInFront.CallBackTaskTestingOrCompletionMessageRequestDto;
import io.metersphere.testin.service.TestPlanTestinTaskService;
import io.metersphere.testin.vo.TestPlanTestInTaskTokenReqIdCallbackUrlVo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;

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
    private static final Logger logger = LoggerFactory.getLogger(TestPlanTestInTaskController.class);

/*    public static void main(String[] args) {
        logger.info("This is a log message.");
        logger.error("This is an error message.", new Exception("Something wrong happened."));}*/

    /**
     * 分页查询
     *
     * @param goPage 筛选条件
     * @param pageSize        分页对象
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
     * @param testPlanId 实体
     * @return 新增结果
     */
    /*@PostMapping
    public ResponseEntity<TestPlanTestinTask> add(TestPlanTestinTask testPlanTestinTask) {
        return ResponseEntity.ok(this.testPlanTestinTaskService.insert(testPlanTestinTask));
    }*/
    @PostMapping("/startTheTestInTask/{testPlanId}")
    public TestPlanTestInTaskTokenReqIdCallbackUrlVo StartTheTestInTask(@PathVariable String testPlanId, @RequestBody EmailDto emailDto) throws UnsupportedEncodingException {
//        return ResponseEntity.ok(this.testPlanTestinTaskService.insert(testPlanTestinTask));
        return this.testPlanTestinTaskService.getTestPlanTestInTaskTokenReqIdCallbackUrlVo(testPlanId,emailDto);
//        TestPlanTestInTaskTokenReqIdCallbackUrlVo testPlanTestInTaskTokenReqIdCallbackUrlVo = this.testPlanTestinTaskService.getTestPlanTestInTaskTokenReqIdCallbackUrlVo(testPlanId,emailDto);
        /*if (testPlanTestInTaskTokenReqIdCallbackUrlVo != null) {
            return ApiResult.success(200,"启动成功",testPlanTestInTaskTokenReqIdCallbackUrlVo);
        }else {
            return ApiResult.error("该测试计划所属项目未关联TestIn项目组");
//            return ResponseEntity.badRequest().body("该测试计划所属项目未关联TestIn项目组");
        }*/
    }
    @PostMapping("/callback")
    public Object callback(@RequestBody CallBackTaskTestingOrCompletionMessageRequestDto callBackTaskTestingOrCompletionMessageRequestDto) {
        logger.info("来自testIn的回调: {}",JSON.toJSONString(callBackTaskTestingOrCompletionMessageRequestDto));
        return this.testPlanTestinTaskService.callback(callBackTaskTestingOrCompletionMessageRequestDto);
//        return ApiResult.success(this.testPlanTestinTaskService.callback(callBackTaskTestingOrCompletionMessageRequestDto));
//        return ResponseEntity.ok(this.testPlanTestinTaskService.callback(callBackTaskTestingOrCompletionMessageRequestDto));
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

