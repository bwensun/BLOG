package com.bwensun.blog.base.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 郑建雄
 * @date 2020/1/3
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {
    private String code;
    private String message;

    public BusinessException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
