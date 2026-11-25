package com.android.tools.r8.internal;

import com.android.tools.r8.utils.EnumC3891f;
import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.internal.q2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2822q2 {
    private final EnumC3891f a;
    private final EnumC3891f b;

    public C2822q2(int i) {
        this(EnumC3891f.c(i), null);
    }

    public final int a() {
        return this.a.d();
    }

    public final int b() {
        return this.b.d();
    }

    public final boolean c() {
        return this.b != null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C2822q2)) {
            return false;
        }
        C2822q2 c2822q2 = (C2822q2) obj;
        return this.a.equals(c2822q2.a) && Objects.equals(this.b, c2822q2.b);
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.b);
    }

    public C2822q2(EnumC3891f enumC3891f, EnumC3891f enumC3891f2) {
        this.a = enumC3891f;
        this.b = enumC3891f2;
    }

    public final int a(C2822q2 c2822q2) {
        int iCompareTo = this.a.compareTo(c2822q2.a);
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        EnumC3891f enumC3891f = this.b;
        if (enumC3891f == null) {
            return c2822q2.b == null ? 0 : 1;
        }
        EnumC3891f enumC3891f2 = c2822q2.b;
        if (enumC3891f2 == null) {
            return -1;
        }
        return enumC3891f.compareTo(enumC3891f2);
    }
}
