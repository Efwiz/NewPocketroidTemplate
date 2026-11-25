package com.android.tools.r8.internal;

import java.util.Arrays;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class XY {
    private final AbstractC0859Bj a;
    private final byte[] b;
    private final int c;

    public XY(C2461lP c2461lP) {
        this.a = c2461lP;
        byte[] bArrF = c2461lP.c().f();
        this.b = bArrF;
        this.c = Arrays.hashCode(bArrF);
    }

    public final AbstractC0859Bj a() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof XY) && Arrays.equals(this.b, ((XY) obj).b);
    }

    public final int hashCode() {
        return this.c;
    }
}
