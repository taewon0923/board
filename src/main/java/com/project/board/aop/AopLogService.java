package com.project.board.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

//Aspect를 통해 AOP 적용
@Slf4j
@Aspect
//Component를 통해 spring bean을 만드는 것
@Component
public class AopLogService {
//    에러가 났을때, 사용자가 어떤 url로 호출했는지, 어떤 parameter넘겼는지 이런 정보를 log로 남기고 싶다면?

    @Around("execution(* com.project.board.author.controller..*.*(..))")
    public Object controllerLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        JSONObject jsonObject = new JSONObject();
//        사용자의 request정보를 Servlet을 통해 가져온다.
        HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        jsonObject.put("method name", proceedingJoinPoint.getSignature().getName());
        jsonObject.put("CRUD name", req.getMethod());
        Enumeration<String> req_body = req.getParameterNames();
        while(req_body.hasMoreElements()){
            String body = req_body.nextElement();
            jsonObject.put(body, req.getParameter(body));
        }
        log.info("request 정보"+jsonObject);
        return proceedingJoinPoint.proceed();
    }
}