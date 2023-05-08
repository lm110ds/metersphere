package io.metersphere.testin.dto.faceMsFront;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * (TestCaseScriptInformation)实体类
 *
 * @author makejava
 * @since 2023-04-20 11:47:52
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto implements Serializable {
    //2.4 test ms front
    private String email;
    private static final long serialVersionUID = 385794188289146681L;


}

