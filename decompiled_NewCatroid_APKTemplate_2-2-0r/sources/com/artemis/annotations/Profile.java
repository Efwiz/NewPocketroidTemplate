package com.artemis.annotations;

import com.artemis.utils.ArtemisProfiler;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: classes8.dex */
@Target({ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.CLASS)
public @interface Profile {
    boolean enabled() default true;

    Class<? extends ArtemisProfiler> using();
}
