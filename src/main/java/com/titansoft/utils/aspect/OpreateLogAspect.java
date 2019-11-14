package com.titansoft.utils.aspect;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.titansoft.entity.cadre.Cadre;
import com.titansoft.entity.log.DataLog;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.entity.system.Privilege;
import com.titansoft.mapper.CadreMapper;
import com.titansoft.mapper.DataLogMapper;
import com.titansoft.utils.annotation.OpreateLog;
import com.titansoft.utils.config.PrivilegeConfig;
import com.titansoft.utils.util.CommonUtil;
import com.titansoft.utils.util.DateTools;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * 档案操作日志切面
 *
 * @Author: Kevin
 * @Date: 2019/8/17 10:03
 */
@Aspect
@Component
public class OpreateLogAspect {
    private static final Logger log = LoggerFactory.getLogger(OpreateLogAspect.class);

    @Autowired
    DataLogMapper dataLogMapper;

    @Autowired
    CadreMapper cadreMapper;

    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation(com.titansoft.utils.annotation.OpreateLog)")
    public void opreateLogPoinCut() {
    }


    @AfterReturning(returning = "ret", pointcut = "opreateLogPoinCut()")// returning的值和doAfterReturning的参数名一致
    public void doAfterReturning(JoinPoint joinPoint, Object ret) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //读取session中的用户信息
        Logininfo logininfo = (Logininfo) session.getAttribute("logininfo");
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        //获取参数名
        String[] strings = methodSignature.getParameterNames();
        String modulecode = "";
        String idnumber = "";
        //判断参数名是否有modulecode
        for (int i = 0; i < strings.length; i++) {
            if ("modulecode".equals(strings[i])) {
                Object[] params = joinPoint.getArgs();
                modulecode = params[i].toString();
            }
            if ("idnumber".equals(strings[i])) {
                Object[] params = joinPoint.getArgs();
                idnumber = params[i].toString();
            }
        }


        Object[] array = joinPoint.getArgs();
        for (int i = 0; i < array.length; i++) {
            if (array[i] instanceof Map) {
                Map o = (Map) array[i];
                if (o.get("modulecode") != null) {
                    modulecode = o.get("modulecode").toString();
                }
                if (o.get("idnumber") != null) {
                    idnumber = o.get("idnumber").toString();
                }
            }
        }
        if (!"".equals(modulecode)) {
            Privilege privilege = PrivilegeConfig.privilegeMap.get(modulecode);
            DataLog dataLog = new DataLog();
            dataLog.setId(CommonUtil.getGuid());
            dataLog.setFunctionname(privilege.getName());
            dataLog.setIpaddress(CommonUtil.getLocalIpAddr());
            dataLog.setDotime(DateTools.pdateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
            dataLog.setUsername(logininfo.getUsername());
            dataLog.setRealname(logininfo.getRealname());
            dataLog.setUserorganid(logininfo.getUnitid());
            dataLog.setUserorgan(logininfo.getUnitname());
            dataLog.setDodesc(getMethodDescription(joinPoint));
            dataLog.setPosition(privilege.getFullname());
            if (!"".equals(idnumber)) {
                Cadre cadre = cadreMapper.selectOne(new QueryWrapper<Cadre>().eq("idnumber", idnumber));
                dataLog.setDodesc("被操作的干部：" + idnumber + "-" + cadre.getName());
            }
            dataLogMapper.insert(dataLog);
        } else {
            log.error("没有配置modulecode");
        }
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
                    description = method.getAnnotation(OpreateLog.class).description();
                    break;
                }
            }
        }
        return description;
    }
}
