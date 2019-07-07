package com.nsystem.lib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Smart used to generate new similar class prepended with "Smart"
 * @Smart consist of @Getter and @Setter for its local variable if specified
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Smart {
}
