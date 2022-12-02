package com.guney.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {

	// setup logger
	private Logger myLogger = Logger.getLogger(getClass().getName());

	// setup pointcut declarations
	@Pointcut("execution(* com.guney.springdemo.controller.*.*(..))")
	private void forControllerPackage() {
	}

	@Pointcut("execution(* com.guney.springdemo.service.*.*(..))")
	private void forServicePackage() {
	}

	@Pointcut("execution(* com.guney.springdemo.dao.*.*(..))")
	private void forDaoPackage() {
	}

	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {

	}

	// add @Before advice
	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoint) {
		// display method we are calling
		String theMethod = theJoinPoint.getSignature().toShortString();

		myLogger.info("===>>> in @Before: calling method: " + theMethod);

		// display the arguments to the method
		// get the arguments
		Object[] args = theJoinPoint.getArgs();

		// loop thru and display args
		for (Object object : args) {
			myLogger.info("=====>>>>> argument: " + object);
		}

	}

	// add æAfterReturning advice
	@AfterReturning(pointcut = "forAppFlow()", returning = "result")
	public void afterReturning(JoinPoint theJoinPoint, Object result) {

		// display method we are returning from
		String theMethod = theJoinPoint.getSignature().toShortString();

		myLogger.info("===>>> in @AfterReturning: from method: " + theMethod);

		// display data returned
		myLogger.info("=====>>>>> result: " + result);
	}

}