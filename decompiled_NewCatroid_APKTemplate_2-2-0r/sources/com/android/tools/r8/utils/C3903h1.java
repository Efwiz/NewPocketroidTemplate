package com.android.tools.r8.utils;

import com.android.tools.r8.internal.AbstractC2763pC;
import java.util.ArrayList;
import java.util.Collections;

/* JADX INFO: renamed from: com.android.tools.r8.utils.h1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class C3903h1 implements InterfaceC3908i1 {
    private static final C3903h1 a = new C3903h1();

    private C3903h1() {
    }

    public static C3903h1 a() {
        return a;
    }

    @Override // com.android.tools.r8.utils.InterfaceC3908i1
    public final Iterable a(Iterable iterable) {
        ArrayList arrayListA = AbstractC2763pC.a(iterable);
        Collections.shuffle(arrayListA);
        return arrayListA;
    }
}
