package org.jetbrains.kotlin.org.fusesource.hawtjni.runtime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: classes4.dex */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JniMethod {
    String accessor() default "";

    JniArg[] callbackArgs() default {};

    String cast() default "";

    String conditional() default "";

    String copy() default "";

    MethodFlag[] flags() default {};
}
