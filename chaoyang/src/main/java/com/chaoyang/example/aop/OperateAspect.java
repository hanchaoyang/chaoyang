package com.chaoyang.example.aop;

import com.chaoyang.example.annotation.OperateLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 操作日志切面
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class OperateAspect {

    @Before(value = "@annotation(com.chaoyang.example.annotation.OperateLog)", argNames = "joinPoint, operateLog")
    public void doBefore(JoinPoint joinPoint, OperateLog operateLog) {
        System.out.println(operateLog.value());

        re
    }

}