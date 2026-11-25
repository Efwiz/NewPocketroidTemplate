package org.jetbrains.kotlin.com.intellij.util.xmlb.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.kotlin.com.intellij.util.xmlb.Converter;

/* JADX INFO: loaded from: classes3.dex */
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Attribute {
    Class<? extends Converter> converter() default Converter.class;

    String value() default "";
}
