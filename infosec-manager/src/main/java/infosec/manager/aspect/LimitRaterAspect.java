package infosec.manager.aspect;

import infosec.manager.annotation.LimitRater;
import infosec.manager.util.DigestLoggerUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class LimitRaterAspect {

    @Resource
    private RedisScript<Boolean> rateScript;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Pointcut("@annotation(infosec.manager.annotation.LimitRater)")
    public void digestLogger() {
    }

    @Around("digestLogger()")
    public Object before(ProceedingJoinPoint joinPoint) {

        try {

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            LimitRater annotation = AnnotationUtils.findAnnotation(signature.getMethod(), LimitRater.class);
            if (annotation == null) {
                return joinPoint.proceed();
            }
            int rate = annotation.rate();
            if (rate < 0) {
                return joinPoint.proceed();
            }
            TimeUnit timeUnit = annotation.timeUnit();
            long rateSeconds = timeUnit.toSeconds(rate);
            Boolean result = redisTemplate.execute(rateScript, List.of(annotation.key()), rateSeconds, annotation.capacity(), annotation.acquire());
            if (result == null || !result) {
                return null;
            }
            return joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            DigestLoggerUtil.clear();
        }
    }
}
