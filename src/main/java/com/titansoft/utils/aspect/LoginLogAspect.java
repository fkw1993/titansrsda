package com.titansoft.utils.aspect;

import com.titansoft.entity.system.Logininfo;
import com.titansoft.mapper.LoginLogMapper;
import com.titansoft.utils.annotation.LoginLog;
import com.titansoft.utils.util.CommonUtil;
import com.titansoft.utils.util.DateTools;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 登录日志切面
 *
 * @Author: Kevin
 * @Date: 2019/8/15 17:50
 */

@Aspect
@Component
public class LoginLogAspect {
    @Autowired
    LoginLogMapper loginLogMapper;


    @Pointcut("@annotation(com.titansoft.utils.annotation.LoginLog)")
    public void loginAspect() {
    }

    @Before("loginAspect()")
    public void doBefore(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

    }

    @AfterReturning(returning = "ret", pointcut = "loginAspect()")// returning的值和doAfterReturning的参数名一致
    public void doAfterReturning(JoinPoint joinPoint, Object ret) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        System.out.println(ret);
        //读取session中的用户信息
        Logininfo logininfo = (Logininfo) session.getAttribute("logininfo");
        com.titansoft.entity.log.LoginLog loginLog = new com.titansoft.entity.log.LoginLog(
                CommonUtil.getGuid(),
                logininfo.getUsername(),
                logininfo.getRealname(),
                logininfo.getIpaddress(),
                DateTools.pdateToString(new Date(), "yyyy-MM-dd HH:mm:ss"),
                getMethodDescription(joinPoint),
                getMethodDescription(joinPoint),
                getMethodDescription(joinPoint),
                logininfo.getUnitid(),
                logininfo.getUnitname()
        );
        loginLogMapper.insert(loginLog);
    }


    /**
     * @param * @param joinPoint:
     * @return
     * @description 获取注解上的描述
     * @author Fkw
     * @date 2019/8/15
     */
    public static String getMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(LoginLog.class).description();
                    break;
                }
            }
        }
        return description;
    }


}
