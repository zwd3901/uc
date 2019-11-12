package com.sxkj.uc.auth;

import com.sxkj.uc.auth.aop.Permit;
import com.sxkj.uc.jwt.JwtConfig;
import com.sxkj.uc.service.UserAppService;
import com.sxkj.uc.service.UserService;
import com.sxkj.uc.util.CustomResultUtil;
import com.sxkj.uc.util.code.CustomResultCodeEnum;
import io.jsonwebtoken.Claims;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zwd
 */
@Aspect
@Slf4j
@Component
public class AuthorizationAop {
    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private JwtUtil requestUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private UserAppService userAppService;

    @Pointcut("@annotation(com.sxkj.uc.auth.aop.Permit)")
    public void permit() {
    }


    @Before("permit()")
    public void checkPermit(JoinPoint joinPoint){
        String token = requestUtil.getToken();
        System.err.println(token);
        Claims claims = jwtConfig.getTokenClaims(token);
        String userId = claims.getSubject();
        List<Map<String, Object>> appList = userAppService.findAppByUserId(userId);
        if (appList == null || appList.isEmpty()) {
            throw new RuntimeException(CustomResultCodeEnum.NO_PERMIT.getCode());
        }

        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Permit permit = method.getAnnotation(Permit.class);
        String value = permit.value();
        System.err.println(value);
        boolean pass = false;
        for(Map<String,Object> map : appList){
            if (map.get("name") != null&&value.equals(map.get("name").toString())) {
                pass = true;
                break;
            }
        }
        if (!pass){
            throw new RuntimeException(CustomResultCodeEnum.NO_PERMIT.getCode());
        }
    }
    private HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }

    private void getControllerInf(JoinPoint joinPoint) {
        Class clazz = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        try {
            Method method = methodSignature.getMethod();//clazz.getMethod(methodName);
            Permit permit = method.getAnnotation(Permit.class);
            if (permit != null) {
                String value = permit.value();
                System.err.println(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e.getCause());
        }


    }

    private Map<String, Object> getParameters(Class cls, String clazzName, String methodName, Object[] args) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);
        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
        }
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < cm.getParameterTypes().length; i++) {
            map.put(attr.variableName(i + pos), args[i]);//paramNames即参数名    
        }
        return map;
    }

}
