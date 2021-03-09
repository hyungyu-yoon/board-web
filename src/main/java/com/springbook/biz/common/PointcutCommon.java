package com.springbook.biz.common;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 공통 포인트컷 설정
 * 사용하는 곳의 어드바이스에서 클래스.메서드로 사용한다.
 */
@Aspect
public class PointcutCommon {
    @Pointcut("execution(* com.springbook.biz..*Impl.*(..))")
    public void allPointcut() {}

    @Pointcut("execution(* com.springbook.biz..*Impl.get*(..))")
    public void getPointcut() {}
}
