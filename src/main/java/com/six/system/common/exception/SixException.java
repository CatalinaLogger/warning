package com.six.system.common.exception;


import com.six.system.common.pojo.ResultEnum;

public class SixException extends  RuntimeException {

    private Integer code;

    public SixException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public SixException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
