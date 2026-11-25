package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.c9, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC1744c9 extends AbstractC1893e9 {
    private final String a;

    AbstractC1744c9(String str) {
        this.a = (String) AbstractC3123uM.a(str);
    }

    @Override // com.android.tools.r8.internal.InterfaceC3205vM
    public final boolean apply(Object obj) {
        ((Character) obj).charValue();
        return a();
    }

    public final String toString() {
        return this.a;
    }
}
