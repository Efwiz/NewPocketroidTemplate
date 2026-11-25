package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import java.util.List;
import java.util.Objects;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class P20 implements NV {
    private final List a;
    private final List b;
    private final boolean c;

    public P20(List list, List list2, boolean z) {
        this.a = list;
        this.b = list2;
        this.c = z;
    }

    public final List a() {
        return this.a;
    }

    public List<C0767v2> b() {
        return this.b;
    }

    public final boolean c() {
        return this.c;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof P20)) {
            return false;
        }
        P20 p20 = (P20) obj;
        return this.c == p20.c && Objects.equals(this.a, p20.a) && Objects.equals(this.b, p20.b);
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.b, Boolean.valueOf(this.c));
    }

    @Override // com.android.tools.r8.internal.NV
    public final Object[] a(NF nf) {
        return nf.a(this);
    }
}
