package com.reandroid.jcommand.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: classes11.dex */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandOptions {
    String[] alternates() default {};

    String description();

    String[] examples() default {};

    String name();

    String usage() default "";
}
