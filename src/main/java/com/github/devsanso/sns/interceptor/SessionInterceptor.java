package com.github.devsanso.sns.interceptor;

import com.github.devsanso.sns.dto.HttpSessionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    @Qualifier("excludeHttpMethod")
    HashMap<String, String[]> excludeHttpMethod;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getRequestURI().equals("/")) {
            throw new IllegalArgumentException("session interceptor not support / uri");
        }
        var session = request.getSession(false);
        var url = "/"+request.getRequestURI().split("/")[1];
        var excludes = excludeHttpMethod.get(url);


        if(excludes != null) {
            var existMethod = Arrays.stream(excludes).anyMatch(s-> s.equals(request.getMethod()));
            if(existMethod) return true;
        }


        if(session == null) {
            response.setStatus(403);
            return false;
        }


        var dto = new HttpSessionDto(session);

        if(!request.getRemoteAddr().equals(dto.toInfo().getIp())) {
            response.setStatus(403);
            return false;
        }

        return true;
    }
}
