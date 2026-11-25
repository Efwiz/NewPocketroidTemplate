package com.android.tools.r8.keepanno.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
@Retention(RetentionPolicy.CLASS)
public @interface KeepBinding {
    String bindingName();

    Class<?> classConstant() default Object.class;

    String classFromBinding() default "";

    String className() default "";

    Class<?> extendsClassConstant() default Object.class;

    String extendsClassName() default "";

    String fieldName() default "";

    String fieldType() default "";

    KeepItemKind kind() default KeepItemKind.DEFAULT;

    String methodName() default "";

    String[] methodParameters() default {""};

    String methodReturnType() default "";
}
