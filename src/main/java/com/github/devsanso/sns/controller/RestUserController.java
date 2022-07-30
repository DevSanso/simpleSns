package com.github.devsanso.sns.controller;


import com.github.devsanso.sns.dto.HttpSessionDto;
import com.github.devsanso.sns.dto.RestUserPatchRequestDto;
import com.github.devsanso.sns.dto.UserInfoVODto;
import com.github.devsanso.sns.model.UserInfoModel;
import com.github.devsanso.sns.service.SelectInfoService;
import com.github.devsanso.sns.service.UserManageService;
import com.github.devsanso.sns.vo.RestUserPatchRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class RestUserController {
    final private SelectInfoService selectInfoService;
    final private UserManageService userManageService;

    @GetMapping("/{userUUID}")
    public UserInfoModel get(@PathVariable("userUUID") String userUUID,HttpServletResponse response) {
        var vo = selectInfoService.selectUserInfo(UUID.fromString(userUUID));
        if (vo == null) {
            response.setStatus(404);
            return null;
        }
        var dto = new UserInfoVODto(vo);
        return dto.toModel();
    }

    @PatchMapping("")
    public void patch(
            @RequestBody RestUserPatchRequestVO body,
            HttpServletRequest request,
            HttpServletResponse response) {


        var session = request.getSession(false);
        if(session == null) {
            response.setStatus(403);
            return;
        }


        try {
            var sessionDto = new HttpSessionDto(session);
            var sessionInfo = sessionDto.toInfo();

            if(!request.getRemoteAddr().equals(sessionInfo.getIp()))
                throw new SecurityException();

            var dto = new RestUserPatchRequestDto(body);
            userManageService.amend(dto.toUserAmendmentVO(UUID.fromString(sessionInfo.getUserUUID())));
        }
        catch(SecurityException e) {
            response.setStatus(403);
            return;
        }
        catch(NoSuchElementException e) {
            response.setStatus(404);
            return;
        }
        catch(Exception e) {
            response.setStatus(500);
            return;
        }

        response.setStatus(200);

    }

    @DeleteMapping("")
    public void delete(
            HttpServletRequest request,
            HttpServletResponse response) {
        var session = request.getSession(false);
        if(session == null) {
            response.setStatus(403);
            return;
        }


        try {
            var sessionDto = new HttpSessionDto(session);
            var sessionInfo = sessionDto.toInfo();

            if(!request.getRemoteAddr().equals(sessionInfo.getIp()))
                throw new SecurityException();

            userManageService.deleteUser(UUID.fromString(sessionInfo.getUserUUID()));
        }
        catch(SecurityException | NoSuchElementException e) {
            response.setStatus(403);
            return;
        } catch(Exception e) {
            response.setStatus(500);
            return;
        }

        response.setStatus(200);
    }
}
