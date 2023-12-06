package com.itzc.rear_end.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.itzc.rear_end.common.Constants;
import com.itzc.rear_end.config.AuthAccess;
import com.itzc.rear_end.entity.SysUser;
import com.itzc.rear_end.exception.ServiceException;
import com.itzc.rear_end.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//jwt拦截器

public class Jwtlnterceptor implements HandlerInterceptor {

    @Autowired
    private ISysUserService iSysUserService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token=request.getHeader("token");
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)){
            return  true;
        }else {
            HandlerMethod h=(HandlerMethod) handler;
            AuthAccess authAccess=h.getMethodAnnotation(AuthAccess.class);
            if (authAccess!=null){
                return true;
            }
        }
        //执行认证
        if (StrUtil.isBlank(token)){
            throw new ServiceException(Constants.CODE_401,"无token,请重新登录");
        }
        //获取token中的 user id
        String userId;
        try {
            userId= JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException j){
            throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
        }
        //根据数据框中的userid查找数据库
        SysUser sysUser= iSysUserService.getById(userId);
        if (sysUser==null){
            throw new ServiceException(Constants.CODE_401,"用户不存在,请重新登录");
        }

        //用户密码加签验证token
        JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(sysUser.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        }catch (JWTVerificationException e){
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录用户");
        }
        return true;
    }
}
