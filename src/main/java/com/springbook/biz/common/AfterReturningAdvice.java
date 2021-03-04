package com.springbook.biz.common;

import com.springbook.biz.user.UserVO;
import org.aspectj.lang.JoinPoint;

/**
 * AfterAdvice 설정
 */
public class AfterReturningAdvice {
    public void afterLog(JoinPoint jp, Object returnObj) {
        String method = jp.getSignature().getName();

        if(returnObj instanceof UserVO) {
            UserVO user = (UserVO) returnObj;
            if(user.getRole().equals("Admin")) {
                System.out.println(user.getName() + " 로그인(Admin)");
            }
        }

        System.out.println("[사후 처리] " + method + "() 메서드 리턴 값 : " + returnObj.toString());
    }
}
