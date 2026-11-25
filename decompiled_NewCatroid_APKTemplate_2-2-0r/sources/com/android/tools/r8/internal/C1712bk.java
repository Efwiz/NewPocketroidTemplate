package com.android.tools.r8.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLongArray;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1712bk extends EZ {
    final /* synthetic */ EZ a;

    C1712bk(EZ ez) {
        this.a = ez;
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) throws IOException {
        AtomicLongArray atomicLongArray = (AtomicLongArray) obj;
        c1799cu.d();
        int length = atomicLongArray.length();
        for (int i = 0; i < length; i++) {
            this.a.a(c1799cu, Long.valueOf(atomicLongArray.get(i)));
        }
        c1799cu.f();
    }

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) throws IOException {
        ArrayList arrayList = new ArrayList();
        c1486Wt.c();
        while (c1486Wt.i()) {
            arrayList.add(Long.valueOf(((Number) this.a.a(c1486Wt)).longValue()));
        }
        c1486Wt.g();
        int size = arrayList.size();
        AtomicLongArray atomicLongArray = new AtomicLongArray(size);
        for (int i = 0; i < size; i++) {
            atomicLongArray.set(i, ((Long) arrayList.get(i)).longValue());
        }
        return atomicLongArray;
    }
}
