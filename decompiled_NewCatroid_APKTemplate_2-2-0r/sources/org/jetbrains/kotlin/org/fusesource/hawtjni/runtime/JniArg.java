package org.jetbrains.kotlin.org.fusesource.hawtjni.runtime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: classes4.dex */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface JniArg {
    String cast() default "";

    ArgFlag[] flags() default {};
}
