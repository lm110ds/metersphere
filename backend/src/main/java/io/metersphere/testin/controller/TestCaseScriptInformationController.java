package io.metersphere.testin.controller;

import com.github.pagehelper.PageHelper;
import io.metersphere.commons.constants.PermissionConstants;
import io.metersphere.commons.exception.MSException;
import io.metersphere.commons.utils.PageUtils;
import io.metersphere.commons.utils.Pager;
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

    @Resource
    private TestCaseService testCaseService;

/*
    @GetMapping
    public ResponseEntity<Page<TestCaseScriptInformation>> queryByPage(TestCaseScriptInformation testCaseScriptInformation, PageRequest pageRequest) {
        return ResponseEntity.ok(this.testCaseScriptInformationService.queryByPage(testCaseScriptInformation, pageRequest));
    }*/

    @PostMapping("/list/{goPage}/{pageSize}")
//    @RequiresPermissions(PermissionConstants.PROJECT_TRACK_CASE_READ)
    public Pager<List<TestCaseScriptInformationCombinVo>> list(@PathVariable int goPage, @PathVariable int pageSize, @Valid @RequestBody TestCaseScriptInformationWithEmailDto request) {
//    public Map<String, Object> list(@PathVariable int goPage, @PathVariable int pageSize, @Valid @RequestBody TestCaseScriptInformationWithEmailDto request) {
        com.github.pagehelper.Page<Object> page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, testCaseScriptInformationService.listTestCaseScriptInformation(goPage, pageSize, request));
        /*List<TestCaseScriptInformationCombinVo> testCaseScriptInformationCombinVos = testCaseScriptInformationService.listTestCaseScriptInformation(goPage, pageSize, request);
        List<TestCaseScriptInformationCombinVo> result3 = new ArrayList<>();
        int start = (goPage - 1) * pageSize;  // 计算起始记录位置
        if (StringUtils.isNotBlank(testCaseScriptInformationCombinVos.getNameOrDescr())){
            //List<MsProjectTestinProjectTeamCombinVo>
            result3 = testCaseScriptInformationCombinVos.stream()
                    .filter(p ->
                            (p.getName()).contains(msProjectTestinProjectTeamWithEmailDto.getNameOrDescr()) ||
                                    ( p.getDescr()).contains(msProjectTestinProjectTeamWithEmailDto.getNameOrDescr()))
                    .skip(start)     // 跳过前面的记录
                    .limit(pageSize)  // 取出指定数量的记录
                    .collect(Collectors.toList());
        }else {
            result3=testCaseScriptInformationCombinVos;
        }
        // 3. 模糊查询名字或性别包含 "五" 的Person


        int totalPages = (int) Math.ceil((double) result3.size() / pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put("data", result3);
        result.put("totalPages", totalPages);
        result.put("pageNum", goPage);
        result.put("pageSize", pageSize);
        return result;*/
//        return PageUtils.setPageInfo(page, testCaseScriptInformationService.listTestCaseScriptInformation(goPage, pageSize, request));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param  主键
     * @return 单条数据
     */
    @GetMapping("/GetTestCaseScriptInformation/{testCaseId}")
    public TestCaseScriptInformation getTestCaseScriptInformation(@PathVariable String testCaseId) {
//        TestCaseScriptInformation queryTestCaseScriptInformationByTestCaseId(String testCaseId)
        return this.testCaseScriptInformationService.queryTestCaseScriptInformationByTestCaseId(testCaseId);
    }
    @PostMapping("/updateOrInsertTestCaseScriptInformation")
    public Boolean updateOrInsertTestCaseScriptInformation(@RequestBody TestCaseScriptInformation testCaseScriptInformation) {
        /*if (StringUtils.isBlank(testCaseScriptInformation.getTestCaseId())
                ||testCaseScriptInformation.getScriptNo()==null
                ||testCaseScriptInformation.getCleanData()==null
                ||testCaseScriptInformation.getKeepApp()==null
                ||testCaseScriptInformation.getCoverInstall()==null
        ) MSException.throwException("testCaseId、scriptNo、cleanData、keepApp、coverInstall不能为空");*/
        if (CollectionUtils.isNotEmpty(this.testCaseScriptInformationService.queryAll(testCaseScriptInformation))){
            return this.testCaseScriptInformationService.update(testCaseScriptInformation);
        }
        return this.testCaseScriptInformationService.insert(testCaseScriptInformation);
        /*){
            return ResponseEntity.success("用例关联脚本成功",true);
        }else {
            return ResponseEntity.error("用例已关联脚本");
        }*/
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
        /*){
            return ResponseEntity.success("用例关联脚本成功",true);
        }else {
            return ResponseEntity.error("用例已关联脚本");
        }*/
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

