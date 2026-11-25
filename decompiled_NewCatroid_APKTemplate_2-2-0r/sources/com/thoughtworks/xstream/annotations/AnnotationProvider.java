package com.thoughtworks.xstream.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/annotations/AnnotationProvider.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
@Deprecated
public class AnnotationProvider {
    @Deprecated
    public <T extends Annotation> T getAnnotation(Field field, Class<T> cls) {
        return (T) field.getAnnotation(cls);
    }
}
