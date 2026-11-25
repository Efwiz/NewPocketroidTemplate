package com.android.tools.r8.keepanno.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
@Retention(RetentionPolicy.CLASS)
public @interface KeepEdge {
    KeepBinding[] bindings() default {};

    KeepTarget[] consequences();

    String description() default "";

    KeepCondition[] preconditions() default {};
}
