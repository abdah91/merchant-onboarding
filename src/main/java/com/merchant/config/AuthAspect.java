package com.merchant.config;

import com.merchant.service.impl.AuthorizationImpl;
import org.apache.tomcat.util.codec.binary.Base64;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthAspect {

    @Value("${authentication.user.name}")
    String username;
    @Value("${authentication.user.[password]}")
    String password;

    @Around("@annotation(Authorized)")
    public Object authorize(ProceedingJoinPoint joinPoint) throws Throwable {

        //BEFORE METHOD EXECUTION
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[1];

        if(request.getHeader("Authorization")!=null){
            String header = request.getHeader("Authorization");
            String pair=new String(Base64.decodeBase64(header.substring(6)));
            String userName=pair.split(":")[0];
            String password=pair.split(":")[1];
            if(!userName.equals(this.username) || !password.equals(this.password)){
                throw new RuntimeException("Invalid username or password");
            }

        }else {
            throw new RuntimeException("auth error..!!!");
        }

        //This is where ACTUAL METHOD will get invoke
        Object result = joinPoint.proceed();

        return result;
    }

}
