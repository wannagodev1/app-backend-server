package org.wannagoframework.backend.aop.logging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface NoLog {}
