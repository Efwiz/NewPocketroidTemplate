package com.android.tools.r8.internal;

import java.util.Set;
import java.util.SortedMap;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class M0 extends L0 implements SortedMap {
    protected M0() {
    }

    @Override // com.android.tools.r8.internal.L0
    /* JADX INFO: renamed from: e */
    public final InterfaceC2529mK entrySet() {
        DI di = (DI) this;
        if (di.l == null) {
            di.l = new AI(di, 0);
        }
        return di.l;
    }

    @Override // com.android.tools.r8.internal.L0, java.util.Map
    public final Set entrySet() {
        DI di = (DI) this;
        if (di.l == null) {
            di.l = new AI(di, 0);
        }
        return di.l;
    }
}
