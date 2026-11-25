package com.android.tools.r8.internal;

import java.util.ListIterator;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lY, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC2470lY {
    final C2409kl a;
    final ListIterator b;
    final Set c;
    final AbstractC1915eM d;
    final S10 e;

    private AbstractC2470lY(C2409kl c2409kl, B4 b4, C1620aY c1620aY, Set set) {
        this.a = c2409kl;
        this.b = b4;
        this.c = set;
        this.d = c1620aY.Y0();
        this.e = c1620aY.value();
    }

    abstract void a();
}
