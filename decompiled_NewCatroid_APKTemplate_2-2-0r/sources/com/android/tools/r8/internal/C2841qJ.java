package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qJ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2841qJ extends O0 implements Cloneable {
    C2841qJ c;
    C2841qJ d;
    int e;

    C2841qJ() {
        super(null, null);
    }

    final void a(C2841qJ c2841qJ) {
        this.e |= 1073741824;
        this.c = c2841qJ;
    }

    final void b(boolean z) {
        if (z) {
            this.e |= 1073741824;
        } else {
            this.e &= -1073741825;
        }
    }

    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public final C2841qJ clone() {
        try {
            C2841qJ c2841qJ = (C2841qJ) super.clone();
            c2841qJ.a = this.a;
            c2841qJ.b = this.b;
            c2841qJ.e = this.e;
            return c2841qJ;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    final boolean d() {
        return (this.e & 1073741824) != 0;
    }

    final C2841qJ e() {
        C2841qJ c2841qJ = this.c;
        if ((this.e & 1073741824) == 0) {
            while ((c2841qJ.e & Integer.MIN_VALUE) == 0) {
                c2841qJ = c2841qJ.d;
            }
        }
        return c2841qJ;
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        Object obj2 = this.a;
        if (obj2 == null) {
            if (entry.getKey() != null) {
                return false;
            }
        } else if (!obj2.equals(entry.getKey())) {
            return false;
        }
        Object obj3 = this.b;
        Object value = entry.getValue();
        if (obj3 == null) {
            if (value != null) {
                return false;
            }
        } else if (!obj3.equals(value)) {
            return false;
        }
        return true;
    }

    final boolean f() {
        return (this.e & Integer.MIN_VALUE) != 0;
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        int iHashCode = this.a.hashCode();
        Object obj = this.b;
        return iHashCode ^ (obj == null ? 0 : obj.hashCode());
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        Object obj2 = this.b;
        this.b = obj;
        return obj2;
    }

    public final String toString() {
        return this.a + "=>" + this.b;
    }

    C2841qJ(Object obj, Object obj2) {
        super(obj, obj2);
        this.e = -1073741824;
    }

    final void a(boolean z) {
        if (z) {
            this.e |= 1;
        } else {
            this.e &= -2;
        }
    }

    final void b(C2841qJ c2841qJ) {
        this.e |= Integer.MIN_VALUE;
        this.d = c2841qJ;
    }

    final boolean b() {
        return (this.e & 1) != 0;
    }
}
