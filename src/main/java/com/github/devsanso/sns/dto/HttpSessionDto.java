package com.github.devsanso.sns.dto;

import com.github.devsanso.sns.vo.HttpSessionInfoVO;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpSession;

@AllArgsConstructor
public class HttpSessionDto {
    private HttpSession session;

    public HttpSessionInfoVO toInfo() {
        if(session == null)return null;

        var ip = (String)session.getAttribute("clientIp");
        var userUUID = (String)session.getAttribute("userUUID");
        if(ip == null && userUUID == null)
            throw new NullPointerException();

        return HttpSessionInfoVO.builder()
                .ip(ip)
                .userUUID(userUUID)
                .build();
    }
}
