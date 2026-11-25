package com.android.tools.r8.internal;

import java.util.Comparator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.cG, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1752cG implements Comparator {
    static final C1752cG a = new C1752cG();

    private C1752cG() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return ((ZF) obj2).a() - ((ZF) obj).a();
    }
}
