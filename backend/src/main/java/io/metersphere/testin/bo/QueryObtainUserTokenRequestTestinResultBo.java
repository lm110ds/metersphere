package io.metersphere.testin.bo;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryObtainUserTokenRequestTestinResultBo implements Serializable {
    // 2.4 response
    private String msg;

    private String apikey;
    private String mkey;

    private String op;

    private Integer code;

    private RequestTestInResultData data;

    private String action;
    @Data
    public static class RequestTestInResultData {
        private String result;
    }

    public boolean isSuccess() {
        return Objects.equals(code, 0);
    }
}






