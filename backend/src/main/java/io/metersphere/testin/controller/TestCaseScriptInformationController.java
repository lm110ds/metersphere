package io.metersphere.testin.controller;

import com.github.pagehelper.PageHelper;
import io.metersphere.commons.constants.PermissionConstants;
import io.metersphere.commons.exception.MSException;
import io.metersphere.commons.utils.PageUtils;
import io.metersphere.commons.utils.Pager;
import io.metersphere.controller.handler.annotation.NoResultHolder;
import io.metersphere.testin.dto.faceMsFront.TestCaseScriptInformationWithEmailDto;
import io.metersphere.testin.entity.TestCaseScriptInformation;
import io.metersphere.testin.service.TestCaseScriptInformationService;
import io.metersphere.testin.util.ResponseEntity;
import io.metersphere.testin.vo.MsProjectTestinProjectTeamCombinVo;
import io.metersphere.testin.vo.TestCaseScriptInformationCombinVo;
import io.metersphere.track.dto.TestCaseDTO;
import io.metersphere.track.request.testcase.QueryTestCaseRequest;
import io.metersphere.track.service.TestCaseService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

//import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (TestCaseScriptInformation)表控制层
 *
 * @author makejava
 * @since 2023-04-20 11:47:52
 */
@RestController
@RequestMapping("/testCaseScriptInformation")
public class TestCaseScriptInformationController {
    /**
     * 服务对象
     */
    @Resource
    private TestCaseScriptInformationService testCaseScriptInformationService;


    @PostMapping("/list/{goPage}/{pageSize}")
    public Pager<List<TestCaseScriptInformationCombinVo>> list(@PathVariable int goPage, @PathVariable int pageSize, @Valid @RequestBody TestCaseScriptInformationWithEmailDto request) {
        com.github.pagehelper.Page<Object> page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, testCaseScriptInformationService.listTestCaseScriptInformation(goPage, pageSize, request));
    }
    @PostMapping("/listWithAppNameOrScript/{goPage}/{pageSize}")
    @NoResultHolder
    public Map<String, Object> listWithAppNameOrScript(@PathVariable int goPage, @PathVariable int pageSize, @Valid @RequestBody TestCaseScriptInformationWithEmailDto request) {
        List<TestCaseScriptInformationCombinVo> testCaseScriptInformationCombinVos = testCaseScriptInformationService.listWithAppNameOrScriptNameTestCaseScriptInformation(goPage, pageSize, request);
        List<TestCaseScriptInformationCombinVo> collect = testCaseScriptInformationCombinVos.parallelStream().filter(p ->
                (StringUtils.isBlank(request.getScriptName()) ||
                        (StringUtils.isNotBlank(request.getScriptName())&&StringUtils.isNotBlank(p.getScriptName())&&p.getScriptName().contains(request.getScriptName()))
                )
                &&
                (StringUtils.isBlank(request.getAppName()) ||
                        (StringUtils.isNotBlank(request.getAppName())&&StringUtils.isNotBlank(p.getAppName())&&p.getAppName().contains(request.getAppName()))
                )
        ).collect(Collectors.toList());

        int totalItems = collect.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        int fromIndex = Math.min((goPage-1) * pageSize, totalItems);
        int toIndex = Math.min(goPage * pageSize, totalItems);
        List<TestCaseScriptInformationCombinVo> pagedItems = collect.subList(fromIndex, toIndex);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("totalPages", totalPages);
        responseBody.put("pageNumber", goPage);
        responseBody.put("items", pagedItems);
        responseBody.put("data", collect);
        responseBody.put("allCount", totalItems);
        responseBody.put("pageSize", pageSize);
        return responseBody;
    }

    /**
     * 通过主键查询单条数据
     *
     * @param  testCaseId
     * @return 单条数据
     */
    @GetMapping("/GetTestCaseScriptInformation/{testCaseId}")
    public TestCaseScriptInformation getTestCaseScriptInformation(@PathVariable String testCaseId) {
        return this.testCaseScriptInformationService.queryTestCaseScriptInformationByTestCaseId(testCaseId);
    }
    @PostMapping("/updateOrInsertTestCaseScriptInformation")
    public Boolean updateOrInsertTestCaseScriptInformation(@RequestBody TestCaseScriptInformation testCaseScriptInformation) {
        if (CollectionUtils.isNotEmpty(this.testCaseScriptInformationService.queryAll(testCaseScriptInformation))){
            return this.testCaseScriptInformationService.update(testCaseScriptInformation);
        }
        return this.testCaseScriptInformationService.insert(testCaseScriptInformation);
    }
    /**
     * 新增数据
     *
     * @param testCaseScriptInformation 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Boolean add(@RequestBody TestCaseScriptInformation testCaseScriptInformation) {
        if (StringUtils.isBlank(testCaseScriptInformation.getTestCaseId())
                ||testCaseScriptInformation.getScriptNo()==null
                ||testCaseScriptInformation.getCleanData()==null
                ||testCaseScriptInformation.getKeepApp()==null
                ||testCaseScriptInformation.getCoverInstall()==null
        ) MSException.throwException("testCaseId、scriptNo、cleanData、keepApp、coverInstall不能为空");
        if (CollectionUtils.isNotEmpty(this.testCaseScriptInformationService.queryAll(testCaseScriptInformation))){
            return false;
        }
        return this.testCaseScriptInformationService.insert(testCaseScriptInformation);
    }

    /**
     * 编辑数据
     *
     * @param testCaseScriptInformation 实体
     * @return 编辑结果
     */
    @PostMapping("/edit")
    public Boolean edit(@RequestBody TestCaseScriptInformation testCaseScriptInformation) {
        if (StringUtils.isBlank(testCaseScriptInformation.getTestCaseId())
                ||testCaseScriptInformation.getScriptNo()==null
        ) MSException.throwException("testCaseId、scriptNo不能为空");
        return this.testCaseScriptInformationService.update(testCaseScriptInformation);
    }

    /**
     * 删除数据
     *
     * @param testCaseId 主键
     * @return 删除是否成功
     */
    @PostMapping("/delete/{testCaseId}")
    public Boolean deleteById(@PathVariable String testCaseId) {
        return this.testCaseScriptInformationService.deleteById(testCaseId);
    }

}

