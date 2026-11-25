package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import java.util.ListIterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.w8, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3261w8 extends M6 {
    public C3261w8(C0767v2 c0767v2) {
        super(c0767v2, true);
    }

    @Override // com.android.tools.r8.internal.M6
    final void a(C2102gl c2102gl, H8 h8) {
        c2102gl.e(h8.a, getType());
    }

    @Override // com.android.tools.r8.internal.M6, com.android.tools.r8.internal.B7
    final void a(com.android.tools.r8.graph.J5 j5, ListIterator listIterator) {
        j5.e(getType());
    }

    @Override // com.android.tools.r8.internal.M6, com.android.tools.r8.internal.X8
    public final B7 a(C0767v2 c0767v2) {
        return new C3261w8(c0767v2);
    }
}
