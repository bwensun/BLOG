package com.bwensun.commons.config;

import com.bwensun.blog.base.Result;
import com.bwensun.blog.base.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常处理
 *
 * @author 郑建雄
 * @date 2019/12/26
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result defaultExceptionHandler(BusinessException e){
        //记录堆栈信息
        log.error("全局异常捕获", e);
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result defaultExceptionHandler(Exception e){
//        if (e instanceof HTTPException && HttpStatus.NOT_FOUND.value() == (((HTTPException) e).getStatusCode())){
//            return new Result("404", "资源不存在");
//        }
        //记录堆栈信息
        log.error("全局异常捕获", e);
        return Result.error("500", "内部服务器错误");
    }
}
