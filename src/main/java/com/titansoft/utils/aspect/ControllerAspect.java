package com.titansoft.utils.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Controller切面
 * @author lin
 *2018年7月14日
 */
@Aspect
@Order(1)  //定义切面的优先级，较小值的那个切面拥有较高优先级
@Component
public class ControllerAspect {
	private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

	@Pointcut("execution( * com.titansoft.controller.*.*(..))")//两个..代表所有子目录，最后括号里的两个..代表所有参数
	public void logPointCut() {
	}

	/**
	 * 接口请求前拦截
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Before("logPointCut()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		// 记录下请求内容
		logger.debug("-------------请求接口触发----------------");
		logger.debug("请求地址 : " + request.getRequestURL().toString());
		logger.debug("HTTP METHOD : " + request.getMethod());
		// 获取真实的ip地址
		//logger.info("IP : " + IPAddressUtil.getClientIpAddress(request));
		logger.debug("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
				+ joinPoint.getSignature().getName());
		logger.debug("参数 : " + Arrays.toString(joinPoint.getArgs()));

	}

	/**
	 * 方法正常完成后被织入
	 * @param ret
	 * @throws Throwable
	 */
	@AfterReturning(returning = "ret", pointcut = "logPointCut()")// returning的值和doAfterReturning的参数名一致
	public void doAfterReturning(Object ret) throws Throwable {
		// 处理完请求，返回内容(返回值太复杂时，打印的是物理存储空间的地址)
		logger.debug("返回值 : " + ret);




	}

	/**
	 * 方法的前后执行所需时间--@Around是可以同时在所拦截方法的前后执行一段逻辑
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("logPointCut()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object ob = pjp.proceed();// ob 为方法的返回值
		logger.debug("耗时 : " + (System.currentTimeMillis() - startTime));
		
		return ob;
	}



}