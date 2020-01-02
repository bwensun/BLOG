package com.bwensun.blog.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 返回实体
 *
 * @author 郑建雄
 * @date 2019/12/26
 */
@Getter
@Setter
public class Result {

    @ApiModelProperty("返回状态码")
    private String code;

    @ApiModelProperty("返回信息")
    private String message;

    @ApiModelProperty("返回数据")
    private Object data;

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result success(){
        return new Result("200", "成功");
    }

    public static Result success(String message){
        return new Result("200", message);
    }

    public static Result success(Object data){
        return new Result("200", "成功", data);
    }

    public static Result error(String code, String message){
        return new Result(code, message);
    }
}
