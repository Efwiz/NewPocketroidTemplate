package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uA, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3111uA {
    private static final C3111uA b = new C3111uA(AbstractC1639am.i());
    private final List a;

    private C3111uA(AbstractC1639am abstractC1639am) {
        this.a = abstractC1639am;
    }

    static C3111uA a(ArrayList arrayList) {
        return arrayList.isEmpty() ? b : new C3111uA(AbstractC1639am.a(arrayList));
    }

    final boolean a(Consumer consumer) {
        if (this == b) {
            return false;
        }
        consumer.accept(this.a);
        return false;
    }
}
