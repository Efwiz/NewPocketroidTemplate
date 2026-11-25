package com.android.tools.r8.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicIntegerArray;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class HZ extends EZ {
    HZ() {
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) throws IOException {
        AtomicIntegerArray atomicIntegerArray = (AtomicIntegerArray) obj;
        c1799cu.d();
        int length = atomicIntegerArray.length();
        for (int i = 0; i < length; i++) {
            c1799cu.a(atomicIntegerArray.get(i));
        }
        c1799cu.f();
    }

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) throws IOException {
        ArrayList arrayList = new ArrayList();
        c1486Wt.c();
        while (c1486Wt.i()) {
            try {
                arrayList.add(Integer.valueOf(c1486Wt.n()));
            } catch (NumberFormatException e) {
                throw new C1542Yt(e);
            }
        }
        c1486Wt.g();
        int size = arrayList.size();
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(size);
        for (int i = 0; i < size; i++) {
            atomicIntegerArray.set(i, ((Integer) arrayList.get(i)).intValue());
        }
        return atomicIntegerArray;
    }
}
