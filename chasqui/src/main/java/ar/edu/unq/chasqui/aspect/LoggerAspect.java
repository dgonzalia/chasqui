package ar.edu.unq.chasqui.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LoggerAspect {

	
	@Around("@annotation(Auditada)")
	public Object logear(ProceedingJoinPoint pointcut)throws Throwable{
		System.out.println("HOLAAAAA");
		System.out.println(pointcut.getSignature());
		return pointcut.proceed();
	}
	
	
}
