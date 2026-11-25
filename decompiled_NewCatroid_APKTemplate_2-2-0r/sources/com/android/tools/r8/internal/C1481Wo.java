package com.android.tools.r8.internal;

import androidx.core.view.InputDeviceCompat;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Wo, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1481Wo extends A implements Cloneable {
    C1481Wo c;
    C1481Wo d;
    int e;

    C1481Wo() {
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

    final C1481Wo d() {
        C1481Wo c1481Wo = this.c;
        if ((this.e & 1073741824) == 0) {
            while ((c1481Wo.e & Integer.MIN_VALUE) == 0) {
                c1481Wo = c1481Wo.d;
            }
        }
        return c1481Wo;
    }

    final boolean e() {
        return (this.e & Integer.MIN_VALUE) != 0;
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

    C1481Wo(int i, Object obj) {
        super(i, obj);
        this.e = -1073741824;
    }

    final void a(C1481Wo c1481Wo) {
        this.e |= 1073741824;
        this.c = c1481Wo;
    }

    final void b(C1481Wo c1481Wo) {
        this.e |= Integer.MIN_VALUE;
        this.d = c1481Wo;
    }

    final void a(int i) {
        this.e = (i & 255) | (this.e & InputDeviceCompat.SOURCE_ANY);
    }

    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final C1481Wo clone() {
        try {
            C1481Wo c1481Wo = (C1481Wo) super.clone();
            c1481Wo.a = this.a;
            c1481Wo.b = this.b;
            c1481Wo.e = this.e;
            return c1481Wo;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }
}
