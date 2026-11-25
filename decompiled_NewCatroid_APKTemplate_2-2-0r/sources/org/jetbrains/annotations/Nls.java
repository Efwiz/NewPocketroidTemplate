package org.jetbrains.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/annotations-13.0.jar:org/jetbrains/annotations/Nls.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE, ElementType.TYPE, ElementType.PACKAGE})
@Documented
@Retention(RetentionPolicy.CLASS)
public @interface Nls {

    public enum Capitalization {
        NotSpecified,
        Title,
        Sentence
    }

    Capitalization capitalization() default Capitalization.NotSpecified;
}
