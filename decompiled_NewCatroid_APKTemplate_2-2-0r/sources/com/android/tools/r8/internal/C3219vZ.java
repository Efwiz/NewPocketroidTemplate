package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.LinkedList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vZ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3219vZ implements Serializable {
    private final Object a;
    private final Object b;
    private final Object c;

    public C3219vZ(LinkedList linkedList, LinkedList linkedList2, Boolean bool) {
        this.a = linkedList;
        this.b = linkedList2;
        this.c = bool;
    }

    public final Object a() {
        return this.a;
    }

    public final Object b() {
        return this.b;
    }

    public final Object c() {
        return this.c;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C3219vZ)) {
            return false;
        }
        C3219vZ c3219vZ = (C3219vZ) obj;
        return AbstractC0936Ds.a(this.a, c3219vZ.a) && AbstractC0936Ds.a(this.b, c3219vZ.b) && AbstractC0936Ds.a(this.c, c3219vZ.c);
    }

    public final int hashCode() {
        Object obj = this.a;
        int iHashCode = (obj == null ? 0 : obj.hashCode()) * 31;
        Object obj2 = this.b;
        int iHashCode2 = (iHashCode + (obj2 == null ? 0 : obj2.hashCode())) * 31;
        Object obj3 = this.c;
        return iHashCode2 + (obj3 != null ? obj3.hashCode() : 0);
    }

    public final String toString() {
        return "(" + this.a + ", " + this.b + ", " + this.c + ')';
    }
}
