package io.metersphere.testin.util;


public class ResponseEntity<T> {
    /**
     * code
     */
    private Integer code;
    /**
     * msg
     */
    private String msg;
    /**
     * data
     */
    private T data;

    public ResponseEntity() {

    }

    public ResponseEntity(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <A> ResponseEntity<A> of(int code, String msg, A data){
        return new ResponseEntity<>(code, msg, data);
    }
    public static <A> ResponseEntity<A> success(String msg, A data){
        return ResponseEntity.of(200, msg, data);
    }
    public static <A> ResponseEntity<A> error(String desc) {
        return ResponseEntity.of(500, desc, null);
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}


