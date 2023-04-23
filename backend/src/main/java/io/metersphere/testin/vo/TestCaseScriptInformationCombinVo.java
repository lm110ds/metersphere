package io.metersphere.testin.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.metersphere.testin.entity.MsProjectTestinProjectTeam;
import io.metersphere.testin.entity.TestCaseScriptInformation;
import io.metersphere.testin.util.JackJsonUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseScriptInformationCombinVo extends TestCaseScriptInformation {
    @ApiModelProperty("有关联的为true,未关联的为false")
    private Boolean flag;
/*
    public static void main(String[] args) {
        TestCaseScriptInformation testCaseScriptInformation;
        try {
//            File file = ResourceUtils.getFile("classpath:帮我导航到冰岛海鲜酒楼_9d00a498df8e3c7e_386816248_更改.json");
            File file = ResourceUtils.getFile("classpath:testCaScInV.json");
//            File file = ResourceUtils.getFile("classpath:导航去95号汽油加油站_f2b131d74c1e63a1_-1114375405.json");
            String json = FileUtils.readFileToString(file, "UTF-8");
            testCaseScriptInformation = JSON.parseObject(json, new TypeReference<TestCaseScriptInformation>() {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        TestCaseScriptInformationCombinVo testCaseScriptInformationCombinVo=new TestCaseScriptInformationCombinVo();
        testCaseScriptInformationCombinVo= JackJsonUtils.obj2pojo(testCaseScriptInformation, TestCaseScriptInformationCombinVo.class);
        System.out.println("testCaseScriptInformationCombinVo = " + JSON.toJSONString(testCaseScriptInformationCombinVo));
        System.out.println("testCaseScriptInformationCombinVo.getScriptId() = " + testCaseScriptInformationCombinVo.getScriptId());
        System.out.println("testCaseScriptInformationCombinVo.getFlag() = " + testCaseScriptInformationCombinVo.getFlag());
        *//*TestCaseScriptInformation testCaseScriptInformation=new TestCaseScriptInformation();
        //可以小转大,不能大转小
        testCaseScriptInformation= JackJsonUtils.obj2pojo(testCaseScriptInformationVo, TestCaseScriptInformation.class);
        System.out.println("testCaseScriptInformationCombin = " + JSON.toJSONString(testCaseScriptInformation));
        System.out.println("testCaseScriptInformationCombin.getScriptId() = " + testCaseScriptInformation.getScriptId());
//        System.out.println("testCaseScriptInformationCombin.getScriptId() = " + testCaseScriptInformation.getFlag());*//*
    }*/
}
