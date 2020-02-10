/*
 * This file is part of the WannaGo distribution (https://github.com/wannago).
 * Copyright (c) [2019] - [2020].
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */


package org.wannagoframework.backend.aop.logging;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.wannagoframework.commons.utils.HasLogger;
import org.wannagoframework.commons.utils.SpringProfileConstants;

/**
 * Aspect for logging execution of service and repository Spring components.
 *
 * By default, it only runs with the "dev" profile.
 */
public class LoggingAspect implements HasLogger {

  private final Environment env;

  public LoggingAspect(Environment env) {
    this.env = env;
  }

  /**
   * Pointcut that matches all repositories, services and Web REST endpoints.
   */
  @Pointcut("within(@org.springframework.stereotype.Repository *)" +
      " || within(@org.springframework.stereotype.Service *)" +
      " || within(@org.springframework.web.bind.annotation.RestController *)")
  public void springBeanPointcut() {
    // Method is empty as this is just a Pointcut, the implementations are in the advices.
  }

  /**
   * Pointcut that matches all Spring beans in the application's main packages.
   */
  @Pointcut("within(org.wannagoframework.backend.service..*)" +
      " || within(org.wannagoframework.backend.endpoint..*)")
  public void serviceOrEnpointPointcut() {
    // Method is empty as this is just a Pointcut, the implementations are in the advices.
  }

  @Pointcut("within(org.wannagoframework.backend.repository..*)")
  public void repositoryPointcut() {
    // Method is empty as this is just a Pointcut, the implementations are in the advices.
  }

  @Pointcut("!execution(@org.wannagoframework.backend.aop.logging.NoLog * *(..))")
  public void methodAnnotatedWithNoLog() {}

  /**
   * Advice that logs methods throwing exceptions.
   *
   * @param joinPoint join point for advice.
   * @param e exception.
   */
  @AfterThrowing(pointcut = "serviceOrEnpointPointcut() && springBeanPointcut()", throwing = "e")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
    String loggerPrefix = getLoggerPrefix(joinPoint.getSignature().getName());
    Class sourceClass = joinPoint.getSignature().getDeclaringType();

    if (env.acceptsProfiles(Profiles.of(SpringProfileConstants.SPRING_PROFILE_DEVELOPMENT))) {
      logger(sourceClass)
          .error(loggerPrefix + ">>> Exception in {} with cause = \'{}\' and exception = \'{}\'",
              joinPoint.getSignature().toShortString(),
              e.getCause() != null ? e.getCause() : "NULL",
              e.getMessage(), e);

    } else {
      logger(sourceClass).error(loggerPrefix + ">>> Exception in {} with cause = {}",
          joinPoint.getSignature().toShortString(), e.getCause() != null ? e.getCause() : "NULL");
    }
  }

  /**
   * Advice that logs when a method is entered and exited.
   *
   * @param joinPoint join point for advice.
   * @return result.
   * @throws Throwable throws {@link IllegalArgumentException}.
   */
  @Around("serviceOrEnpointPointcut() && springBeanPointcut() && methodAnnotatedWithNoLog()")
  public Object logAroundServiceOrEndpoint(ProceedingJoinPoint joinPoint) throws Throwable {
    String loggerPrefix = getLoggerPrefix(joinPoint.getSignature().getName());
    Class sourceClass = joinPoint.getSignature().getDeclaringType();

    // TODO : Add an endpoint to activate/deactivate at runtime
    if (logger(sourceClass).isTraceEnabled()) {
      logger(sourceClass).debug(loggerPrefix + ">>> Enter with argument[s] = {}",
          Arrays.toString(joinPoint.getArgs()));
    } else if (logger().isDebugEnabled()) {
      logger(sourceClass).debug(loggerPrefix + ">>> Enter");
    }
    try {
      long start = System.currentTimeMillis();
      Object result = joinPoint.proceed();
      long end = System.currentTimeMillis();
      long duration = end - start;
      if (duration > 1000) {
        logger(sourceClass).warn(
            loggerPrefix + ">>> Service or EndPoint Method: " + joinPoint.getSignature().getName() + " took long ... "
                + duration + " ms");
      }
      if (logger().isTraceEnabled()) {
        logger(sourceClass)
            .debug(loggerPrefix + ">>> Exit, duration {} ms with result = {}", duration, result);
      } else if (logger().isDebugEnabled()) {
        logger(sourceClass).debug(loggerPrefix + ">>> Exit, duration {} ms", duration);
      }
      return result;
    } catch (IllegalArgumentException e) {
      logger(sourceClass).error(loggerPrefix + ">>> Illegal argument: {} in {}",
          Arrays.toString(joinPoint.getArgs()),
          joinPoint.getSignature().toShortString());

      throw e;
    }
  }

  @Around("repositoryPointcut() && springBeanPointcut() && methodAnnotatedWithNoLog()")
  public Object logAroundRepository(ProceedingJoinPoint joinPoint) throws Throwable {
    String loggerPrefix = getLoggerPrefix(joinPoint.getSignature().getName());
    Class sourceClass = joinPoint.getSignature().getDeclaringType();

    try {
      long start = System.currentTimeMillis();
      Object result = joinPoint.proceed();
      long end = System.currentTimeMillis();
      long duration = end - start;
      if (duration > 1000) {
        logger(sourceClass).warn(
            loggerPrefix + ">>> Repository Method: " + joinPoint.getSignature().getName() + " took long ... "
                + duration + " ms");
      }
      return result;
    } catch (IllegalArgumentException e) {
      logger(sourceClass).error(loggerPrefix + ">>> Illegal argument: {} in {}",
          Arrays.toString(joinPoint.getArgs()),
          joinPoint.getSignature().toShortString());

      throw e;
    }
  }
}
