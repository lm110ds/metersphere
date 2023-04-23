package io.metersphere.testin.service;

import io.metersphere.testin.dto.faceMsFront.TestCaseScriptInformationWithEmailDto;
import io.metersphere.testin.entity.TestCaseScriptInformation;
import io.metersphere.testin.vo.TestCaseScriptInformationCombinVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * (TestCaseScriptInformation)表服务接口
 *
 * @author makejava
 * @since 2023-04-20 11:47:52
 */
public interface TestCaseScriptInformationService {

    /**
     * 通过ID查询单条数据
     *
     * @param 主键
     * @return 实例对象
     */
    TestCaseScriptInformation queryById();

    /**
     * 分页查询
     *
     * @param testCaseScriptInformation 筛选条件
     * @param pageRequest               分页对象
     * @return 查询结果
     */
    Page<TestCaseScriptInformation> queryByPage(TestCaseScriptInformation testCaseScriptInformation, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param testCaseScriptInformation 实例对象
     * @return 实例对象
     */
    //TestCaseScriptInformation
    Boolean insert(TestCaseScriptInformation testCaseScriptInformation);

    /**
     * 修改数据
     *
     * @param testCaseScriptInformation 实例对象
     * @return 实例对象
     */
    //TestCaseScriptInformation
    Boolean update(TestCaseScriptInformation testCaseScriptInformation);

    /**
     * 通过主键删除数据
     *
     * @param 主键
     * @param testCaseId
     * @return 是否成功
     */
    boolean deleteById(String testCaseId);


    List<TestCaseScriptInformationCombinVo> listTestCaseScriptInformation(int goPage, int pageSize
            , TestCaseScriptInformationWithEmailDto request);

    List<TestCaseScriptInformation> queryAll(TestCaseScriptInformation testCaseScriptInformation);
}
