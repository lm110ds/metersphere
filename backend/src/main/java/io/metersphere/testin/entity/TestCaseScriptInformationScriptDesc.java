package io.metersphere.testin.entity;

import lombok.*;

import java.io.Serializable;

/**
 * (TestCaseScriptInformation)实体类
 *
 * @author makejava
 * @since 2023-04-20 11:47:52
 */

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseScriptInformationScriptDesc extends TestCaseScriptInformation{
    private String scriptDesc;
}