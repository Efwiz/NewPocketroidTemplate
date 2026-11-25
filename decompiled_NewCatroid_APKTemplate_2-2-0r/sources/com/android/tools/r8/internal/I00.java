package com.android.tools.r8.internal;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class I00 {
    final Class a;
    final Type b;
    final int c;

    protected I00() {
        Type typeB = b(getClass());
        this.b = typeB;
        this.a = AbstractC1879e.c(typeB);
        this.c = typeB.hashCode();
    }

    static Type b(Class cls) {
        Type genericSuperclass = cls.getGenericSuperclass();
        if (genericSuperclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        return AbstractC1879e.a(((ParameterizedType) genericSuperclass).getActualTypeArguments()[0]);
    }

    public final Class a() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof I00) && AbstractC1879e.a(this.b, ((I00) obj).b);
    }

    public final int hashCode() {
        return this.c;
    }

    public final String toString() {
        return AbstractC1879e.d(this.b);
    }

    public static I00 a(Type type) {
        return new I00(type);
    }

    public static I00 a(Class cls) {
        return new I00(cls);
    }

    I00(Type type) {
        Type typeA = AbstractC1879e.a((Type) AbstractC1575a.a(type));
        this.b = typeA;
        this.a = AbstractC1879e.c(typeA);
        this.c = typeA.hashCode();
    }

    public final Type b() {
        return this.b;
    }
}
