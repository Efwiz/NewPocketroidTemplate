package com.artemis.injection;

import com.artemis.annotations.Wire;
import com.artemis.utils.reflect.Field;

/* JADX INFO: loaded from: classes8.dex */
public class CachedClass {
    public Field[] allFields;
    public Class<?> clazz;
    public boolean failOnNull;
    public boolean injectInherited;
    public Wire wireAnnotation;
    public WireType wireType;

    public CachedClass(Class<?> cls) {
        this.clazz = cls;
    }
}
