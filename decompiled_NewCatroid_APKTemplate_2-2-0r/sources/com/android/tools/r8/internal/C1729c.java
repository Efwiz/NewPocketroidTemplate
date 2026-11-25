package com.android.tools.r8.internal;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/* JADX INFO: renamed from: com.android.tools.r8.internal.c, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1729c implements ParameterizedType, Serializable {
    private final Type a;
    private final Type b;
    private final Type[] c;

    public C1729c(Type type, Type type2, Type... typeArr) {
        int i = 0;
        if (type2 instanceof Class) {
            Class cls = (Class) type2;
            boolean z = true;
            boolean z2 = Modifier.isStatic(cls.getModifiers()) || cls.getEnclosingClass() == null;
            if (type == null && !z2) {
                z = false;
            }
            AbstractC1575a.a(z);
        }
        this.a = type == null ? null : AbstractC1879e.a(type);
        this.b = AbstractC1879e.a(type2);
        this.c = (Type[]) typeArr.clone();
        while (true) {
            Type[] typeArr2 = this.c;
            if (i >= typeArr2.length) {
                return;
            }
            AbstractC1575a.a(typeArr2[i]);
            AbstractC1879e.b(this.c[i]);
            Type[] typeArr3 = this.c;
            typeArr3[i] = AbstractC1879e.a(typeArr3[i]);
            i++;
        }
    }

    public final boolean equals(Object obj) {
        return (obj instanceof ParameterizedType) && AbstractC1879e.a(this, (ParameterizedType) obj);
    }

    @Override // java.lang.reflect.ParameterizedType
    public final Type[] getActualTypeArguments() {
        return (Type[]) this.c.clone();
    }

    @Override // java.lang.reflect.ParameterizedType
    public final Type getOwnerType() {
        return this.a;
    }

    @Override // java.lang.reflect.ParameterizedType
    public final Type getRawType() {
        return this.b;
    }

    public final int hashCode() {
        int iHashCode = Arrays.hashCode(this.c) ^ this.b.hashCode();
        Type type = this.a;
        return iHashCode ^ (type != null ? type.hashCode() : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder((this.c.length + 1) * 30);
        sb.append(AbstractC1879e.d(this.b));
        if (this.c.length == 0) {
            return sb.toString();
        }
        sb.append("<").append(AbstractC1879e.d(this.c[0]));
        for (int i = 1; i < this.c.length; i++) {
            sb.append(", ").append(AbstractC1879e.d(this.c[i]));
        }
        return sb.append(">").toString();
    }
}
