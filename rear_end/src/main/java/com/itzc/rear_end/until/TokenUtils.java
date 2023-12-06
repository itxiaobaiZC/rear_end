package com.itzc.rear_end.until;

import cn.hutool.core.date.DateUtil;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.itzc.rear_end.entity.SysUser;
import com.itzc.rear_end.service.ISysUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {

    private static ISysUserService staticiSysUserService;

    @Resource
    private ISysUserService iSysUserService;

    @PostConstruct
    public void setsysUserService(){
        //
        staticiSysUserService=iSysUserService;
    }

    public static String getToken(String userId,String sgin){
        return JWT.create().withAudience(userId)  //userId 保存在token里面作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))  //两小时过期
                .sign(Algorithm.HMAC256(sgin));
    }
    //获取当前登录的用户信息
    //在token中读取id 查找name返回user对象
    public static SysUser getCurrentsysUser(){
        //获取当前请求的request
        try {
            HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token=request.getHeader("token");
            if (StrUtil.isNotBlank(token)){
                String userId=JWT.decode(token).getAudience().get(0);
                return staticiSysUserService.getById(Integer.valueOf(userId));
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }
}
