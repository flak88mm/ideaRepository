package com.brt.device.exception;

import com.brt.device.rest.vo.ResEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: ExceptionAdvice 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author Zeng Nan
 * @date 2019年10月14日 下午10:17:41
 */
@ControllerAdvice
@ResponseBody
public class ExceptionAdvice {
    @ExceptionHandler(CustomBusinessException.class)
    public ResEntity handlePoliceCodeNotUniqueException(HttpServletResponse re, CustomBusinessException e) {
        re.setStatus(801);
        return new ResEntity().failure(e.getLocalizedMessage());
    }

    @ExceptionHandler(java.lang.Exception.class)
    public ResEntity handleJavaLangException(HttpServletResponse re,CustomBusinessException e) {
        re.setStatus(802);
        return new ResEntity().failure(e.getLocalizedMessage());
    }

}

