package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class UH extends B1 {
    private final B1 c;

    private UH(B1 b1) {
        this.c = b1;
    }

    public static B1 a(B1 b1) {
        b1.getClass();
        return ((b1 instanceof H5) || b1.isUnknown() || b1.x() || (b1 instanceof UH)) ? b1 : new UH(b1);
    }

    public final B1 F() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.B1
    public final B1 b(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2) {
        return new UH(this.c.b(c0702m, abstractC1476Wj, abstractC1476Wj2));
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean equals(Object obj) {
        return obj != null && UH.class == obj.getClass() && this.c.equals(((UH) obj).c);
    }

    @Override // com.android.tools.r8.internal.B1
    public final int hashCode() {
        return this.c.hashCode() * 7;
    }

    @Override // com.android.tools.r8.internal.B1
    public final UH l() {
        return this;
    }

    @Override // com.android.tools.r8.internal.B1
    public final String toString() {
        return "Null or " + this.c.toString();
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean y() {
        return true;
    }
}
