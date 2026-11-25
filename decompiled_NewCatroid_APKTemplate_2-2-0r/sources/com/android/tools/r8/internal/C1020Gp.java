package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Gp, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1020Gp extends A implements Cloneable {
    C1020Gp c;
    C1020Gp d;
    int e;

    C1020Gp() {
        super(0, null);
    }

    final void a(C1020Gp c1020Gp) {
        this.e |= 1073741824;
        this.c = c1020Gp;
    }

    final void b(boolean z) {
        if (z) {
            this.e |= 1073741824;
        } else {
            this.e &= -1073741825;
        }
    }

    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public final C1020Gp clone() {
        try {
            C1020Gp c1020Gp = (C1020Gp) super.clone();
            c1020Gp.a = this.a;
            c1020Gp.b = this.b;
            c1020Gp.e = this.e;
            return c1020Gp;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    final boolean d() {
        return (this.e & 1073741824) != 0;
    }

    final C1020Gp e() {
        C1020Gp c1020Gp = this.c;
        if ((this.e & 1073741824) == 0) {
            while ((c1020Gp.e & Integer.MIN_VALUE) == 0) {
                c1020Gp = c1020Gp.d;
            }
        }
        return c1020Gp;
    }

    @Override // com.android.tools.r8.internal.A, java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (this.a != ((Integer) entry.getKey()).intValue()) {
            return false;
        }
        Object obj2 = this.b;
        Object value = entry.getValue();
        if (obj2 == null) {
            if (value != null) {
                return false;
            }
        } else if (!obj2.equals(value)) {
            return false;
        }
        return true;
    }

    final boolean f() {
        return (this.e & Integer.MIN_VALUE) != 0;
    }

    @Override // com.android.tools.r8.internal.A, java.util.Map.Entry
    public final int hashCode() {
        int i = this.a;
        Object obj = this.b;
        return i ^ (obj == null ? 0 : obj.hashCode());
    }

    @Override // com.android.tools.r8.internal.A, java.util.Map.Entry
    public final Object setValue(Object obj) {
        Object obj2 = this.b;
        this.b = obj;
        return obj2;
    }

    @Override // com.android.tools.r8.internal.A
    public final String toString() {
        return this.a + "=>" + this.b;
    }

    C1020Gp(int i, Object obj) {
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

    final void b(C1020Gp c1020Gp) {
        this.e |= Integer.MIN_VALUE;
        this.d = c1020Gp;
    }

    final boolean b() {
        return (this.e & 1) != 0;
    }
}
