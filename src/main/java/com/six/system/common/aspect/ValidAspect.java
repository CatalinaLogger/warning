package com.six.system.common.aspect;

import com.six.system.common.exception.SixException;
import com.six.system.common.pojo.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Slf4j
@Aspect
@Component
public class ValidAspect {

    @Around("execution(public * com.six.*.controller.*.*(..)) && args(..,bindingResult)")
    public Object doAround(ProceedingJoinPoint point, BindingResult bindingResult) throws Throwable {
        if (bindingResult.hasErrors()) {
            log.error(ResultEnum.ERROR_PARAM.getMsg());
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), bindingResult.getFieldError().getDefaultMessage());
        } else {
            return point.proceed();
        }
    }
}
