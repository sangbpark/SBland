package com.sbland.aop.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class FindCacheAspect {
    private final CacheManager cacheManager;

    @Around("@annotation(FindCache)")
    public Object handleFindCache(ProceedingJoinPoint joinPoint, FindCache findCache) throws Throwable {
        String cacheName = findCache.value();
        String keyExpression = findCache.key();
        String key = resolveKey(joinPoint, keyExpression); 

        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache.get(key);
            if (valueWrapper != null) {
                return valueWrapper.get();
            }
        }

        return null;
    }

    private String resolveKey(ProceedingJoinPoint joinPoint, String keyExpression) {
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext(joinPoint);
        return parser.parseExpression(keyExpression).getValue(context, String.class);
    }
}
