package com.badlogic.gdx.utils.reflect;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/reflect/Annotation.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public final class Annotation {
    private java.lang.annotation.Annotation annotation;

    Annotation(java.lang.annotation.Annotation annotation) {
        this.annotation = annotation;
    }

    public <T extends java.lang.annotation.Annotation> T getAnnotation(Class<T> cls) {
        if (this.annotation.annotationType().equals(cls)) {
            return (T) this.annotation;
        }
        return null;
    }

    public Class<? extends java.lang.annotation.Annotation> getAnnotationType() {
        return this.annotation.annotationType();
    }
}
