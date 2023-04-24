package io.metersphere.testin.dto.faceMsFront;

import com.google.gson.annotations.SerializedName;
import io.metersphere.testin.dto.BaseDto;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheListOfTestingScriptsWithEmailDto extends BaseDto {
    //2.2 test ms front
    private String apikey;

    private String mkey;

    private String sid;

    private String op;

    private String action;

    private Long timestamp;

    private String email;

    private String scriptDesc;

    private int startPageNo;

    private int pageSize;
    @SerializedName(value = "projectId", alternate = {"testInProjectId"})
    private int projectId;


}
