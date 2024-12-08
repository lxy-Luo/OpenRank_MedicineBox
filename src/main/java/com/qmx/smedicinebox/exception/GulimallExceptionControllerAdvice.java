package com.qmx.smedicinebox.exception;



import com.qmx.smedicinebox.constant.BizCodeEnume;
import com.qmx.smedicinebox.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@Slf4j
@RestControllerAdvice(basePackages = "com.qmx.smedicinebox.sys.controller")
public class GulimallExceptionControllerAdvice {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e){
        log.error("数据校验出现问题{}，异常类型：{}",e.getMessage(),e.getClass());

        BindingResult bindingResult = e.getBindingResult();
        HashMap<String, String> errormap = new HashMap<>();

        bindingResult.getFieldErrors().forEach((item)->{
            String defaultMessage = item.getDefaultMessage();
            String field = item.getField();
            errormap.put(field,defaultMessage);
        });

        return R.error(BizCodeEnume.VALID_EXCEPTION.getCode(),BizCodeEnume.VALID_EXCEPTION.getMsg()).put("data",errormap);

    }
//    @ExceptionHandler(value = Throwable.class)
//    public R handleException(Throwable e){
//       return R.error(BizCodeEnume.UNKNOW_EXCEPTION.getCode(), BizCodeEnume.UNKNOW_EXCEPTION.getMsg());
//    }


}
