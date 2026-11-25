package org.jetbrains.kotlin.org.fusesource.hawtjni.runtime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: classes4.dex */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JniClass {
    String conditional() default "";

    ClassFlag[] flags() default {};

    String name() default "";
}
