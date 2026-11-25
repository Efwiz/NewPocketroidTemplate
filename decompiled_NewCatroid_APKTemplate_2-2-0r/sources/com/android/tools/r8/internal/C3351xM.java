package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xM, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final enum C3351xM extends BM {
    C3351xM() {
        super("ALWAYS_TRUE", 0, 0);
    }

    @Override // com.android.tools.r8.internal.InterfaceC3205vM
    public final boolean apply(Object obj) {
        return true;
    }

    @Override // java.lang.Enum
    public final String toString() {
        return "Predicates.alwaysTrue()";
    }
}
