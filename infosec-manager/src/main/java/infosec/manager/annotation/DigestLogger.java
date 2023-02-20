/*
 * Ant Group
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package infosec.manager.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yunzhi
 * @version DigestLogger.java, v 0.1 2023年02月20日 16:42 yunzhi
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DigestLogger {

    String loggerName();

    String pattern();
}