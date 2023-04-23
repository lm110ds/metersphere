package io.metersphere.testin.bo;

import io.metersphere.testin.dto.BaseDto;
import lombok.*;

import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryTheInitializationDataRequestForTheTestingTaskTestinBo extends BaseDto{
    private String apikey;
    private String meky;
    private String op;
    private String action;
    private String msg;
    private int code;
    private QueryTheInitializationDataRequestForTheTestingTaskTestinResultData data;
    @Data
    @Builder
    public static class QueryTheInitializationDataRequestForTheTestingTaskTestinResultData {

        private String result ;

    }
    public boolean isSuccess() {
        return Objects.equals(code, 0);
    }

}
