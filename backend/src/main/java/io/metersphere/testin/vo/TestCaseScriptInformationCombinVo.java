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

}
