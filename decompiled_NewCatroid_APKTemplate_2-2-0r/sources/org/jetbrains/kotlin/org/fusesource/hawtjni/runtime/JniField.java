package org.jetbrains.kotlin.org.fusesource.hawtjni.runtime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: classes4.dex */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JniField {
    String accessor() default "";

    String cast() default "";

    String conditional() default "";

    FieldFlag[] flags() default {};
}
