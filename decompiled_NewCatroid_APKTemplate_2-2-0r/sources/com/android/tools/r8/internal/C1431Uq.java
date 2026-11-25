package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Uq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1431Uq extends F implements Cloneable {
    C1431Uq c;
    C1431Uq d;
    int e;

    C1431Uq() {
        super(0, null);
    }

    final void a(C1431Uq c1431Uq) {
        this.e |= 1073741824;
        this.c = c1431Uq;
    }

    final void b(boolean z) {
        if (z) {
            this.e |= 1073741824;
        } else {
            this.e &= -1073741825;
        }
    }

    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public final C1431Uq clone() {
        try {
            C1431Uq c1431Uq = (C1431Uq) super.clone();
            c1431Uq.a = this.a;
            c1431Uq.b = this.b;
            c1431Uq.e = this.e;
            return c1431Uq;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    final boolean d() {
        return (this.e & 1073741824) != 0;
    }

    final C1431Uq e() {
        C1431Uq c1431Uq = this.c;
        if ((this.e & 1073741824) == 0) {
            while ((c1431Uq.e & Integer.MIN_VALUE) == 0) {
                c1431Uq = c1431Uq.d;
            }
        }
        return c1431Uq;
    }

    @Override // com.android.tools.r8.internal.F, java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return this.a == ((Integer) entry.getKey()).intValue() && this.b == entry.getValue();
    }

    final boolean f() {
        return (this.e & Integer.MIN_VALUE) != 0;
    }

    @Override // com.android.tools.r8.internal.F, java.util.Map.Entry
    public final int hashCode() {
        int i = this.a;
        Object obj = this.b;
        return i ^ (obj == null ? 0 : System.identityHashCode(obj));
    }

    @Override // com.android.tools.r8.internal.F, java.util.Map.Entry
    public final Object setValue(Object obj) {
        Object obj2 = this.b;
        this.b = obj;
        return obj2;
    }

    @Override // com.android.tools.r8.internal.F
    public final String toString() {
        return this.a + "=>" + this.b;
    }

    C1431Uq(int i, Object obj) {
        super(i, obj);
        this.e = -1073741824;
    }

    final void a(boolean z) {
        if (z) {
            this.e |= 1;
        } else {
            this.e &= -2;
        }
    }

    final void b(C1431Uq c1431Uq) {
        this.e |= Integer.MIN_VALUE;
        this.d = c1431Uq;
    }

    final boolean b() {
        return (this.e & 1) != 0;
    }
}
