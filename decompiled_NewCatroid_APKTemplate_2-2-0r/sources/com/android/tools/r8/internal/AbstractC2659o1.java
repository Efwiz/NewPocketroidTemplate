package com.android.tools.r8.internal;

import java.util.Set;
import java.util.SortedMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.o1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2659o1 extends AbstractC2581n1 implements SortedMap {
    protected AbstractC2659o1() {
    }

    @Override // com.android.tools.r8.internal.AbstractC2581n1
    /* JADX INFO: renamed from: e */
    public final InterfaceC2529mK entrySet() {
        return ((C3505zR) this).f();
    }

    @Override // com.android.tools.r8.internal.AbstractC2581n1, java.util.Map
    public final Set entrySet() {
        return ((C3505zR) this).f();
    }
}
