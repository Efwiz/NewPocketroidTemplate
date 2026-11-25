package com.android.tools.r8.internal;

import androidx.core.view.InputDeviceCompat;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1718bq extends F implements Cloneable {
    C1718bq c;
    C1718bq d;
    int e;

    C1718bq() {
        super(0, null);
    }

    final void a(boolean z) {
        if (z) {
            this.e |= 1073741824;
        } else {
            this.e &= -1073741825;
        }
    }

    final void b(boolean z) {
        if (z) {
            this.e |= Integer.MIN_VALUE;
        } else {
            this.e &= Integer.MAX_VALUE;
        }
    }

    final boolean c() {
        return (this.e & 1073741824) != 0;
    }

    final C1718bq d() {
        C1718bq c1718bq = this.c;
        if ((this.e & 1073741824) == 0) {
            while ((c1718bq.e & Integer.MIN_VALUE) == 0) {
                c1718bq = c1718bq.d;
            }
        }
        return c1718bq;
    }

    final boolean e() {
        return (this.e & Integer.MIN_VALUE) != 0;
    }

    @Override // com.android.tools.r8.internal.F, java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return this.a == ((Integer) entry.getKey()).intValue() && this.b == entry.getValue();
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

    C1718bq(int i, Object obj) {
        super(i, obj);
        this.e = -1073741824;
    }

    final void a(C1718bq c1718bq) {
        this.e |= 1073741824;
        this.c = c1718bq;
    }

    final void b(C1718bq c1718bq) {
        this.e |= Integer.MIN_VALUE;
        this.d = c1718bq;
    }

    final void a(int i) {
        this.e = (i & 255) | (this.e & InputDeviceCompat.SOURCE_ANY);
    }

    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final C1718bq clone() {
        try {
            C1718bq c1718bq = (C1718bq) super.clone();
            c1718bq.a = this.a;
            c1718bq.b = this.b;
            c1718bq.e = this.e;
            return c1718bq;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }
}
