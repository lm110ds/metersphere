package io.metersphere.testin.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import io.metersphere.commons.utils.PageUtils;
import io.metersphere.commons.utils.Pager;
import io.metersphere.controller.handler.annotation.NoResultHolder;
import io.metersphere.testin.bo.QueryToObtainTheExecutionDetailsOfTheTestingReportGenerateBo;
import io.metersphere.testin.dto.faceMsFront.EmailDto;
import io.metersphere.testin.dto.faceMsFront.ToObtainTheExecutionDetailsOfTheTestingReportGenerateDto;
import io.metersphere.testin.dto.faceTestInFront.CallBackTaskTestingOrCompletionMessageRequestDto;
import io.metersphere.testin.service.TestPlanTestinTaskService;
import io.metersphere.testin.util.ResponseEntity;
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


    /**
     * 分页查询
     *
     * @param goPage 筛选条件
     * @param pageSize        分页对象
     * @return 查询结果
     */
    @PostMapping("/GetReportList/{goPage}/{pageSize}")
    public Pager<List<QueryToObtainTheExecutionDetailsOfTheTestingReportGenerateBo.TestInProjectGroupTask>> list(@PathVariable Integer goPage, @PathVariable Integer pageSize, @RequestBody ToObtainTheExecutionDetailsOfTheTestingReportGenerateDto toObtainTheExecutionDetailsOfTheTestingReportGenerateDto) {
        com.github.pagehelper.Page<Object> page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, testPlanTestinTaskService.listQueryToObtainTheExecutionDetailsOfTheTestingReport(goPage,pageSize,toObtainTheExecutionDetailsOfTheTestingReportGenerateDto));
    }


    /**
     * 新增数据
     *
     * @param testPlanId 实体
     * @return 新增结果
     */
    @PostMapping("/startTheTestInTask/{testPlanId}")
    @NoResultHolder
    public ResponseEntity StartTheTestInTask(@PathVariable String testPlanId, @RequestBody EmailDto emailDto) throws UnsupportedEncodingException {
        TestPlanTestInTaskTokenReqIdCallbackUrlVo testPlanTestInTaskTokenReqIdCallbackUrlVo = this.testPlanTestinTaskService.getTestPlanTestInTaskTokenReqIdCallbackUrlVo(testPlanId,emailDto);
        if (testPlanTestInTaskTokenReqIdCallbackUrlVo != null) {
            return ResponseEntity.success("启动成功",testPlanTestInTaskTokenReqIdCallbackUrlVo);
        }else {
            return ResponseEntity.error("该测试计划所属项目未关联TestIn项目组");
        }
    }
    @PostMapping("/callback")
    public Object callback(@RequestBody CallBackTaskTestingOrCompletionMessageRequestDto callBackTaskTestingOrCompletionMessageRequestDto) {
        logger.info("来自testIn的回调: {}",JSON.toJSONString(callBackTaskTestingOrCompletionMessageRequestDto));
        return this.testPlanTestinTaskService.callback(callBackTaskTestingOrCompletionMessageRequestDto);
    }

}

