package com.artemis.injection;

import com.artemis.utils.reflect.Field;

/* JADX INFO: loaded from: classes8.dex */
public class CachedField {
    public final boolean failOnNull;
    public final Field field;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public final String f81name;
    public final WireType wireType;

    public CachedField(Field field, WireType wireType, String str, boolean z) {
        this.field = field;
        this.wireType = wireType;
        this.f81name = str;
        this.failOnNull = z;
    }
}
