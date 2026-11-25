package com.android.tools.r8.internal;

import java.util.IdentityHashMap;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class P4 extends O4 implements InterfaceC2370kG {
    static final /* synthetic */ boolean d = true;
    private final IdentityHashMap c = new IdentityHashMap();

    @Override // com.android.tools.r8.internal.J4
    public final Object b(Object obj) {
        Set setE = e(obj);
        if (setE.isEmpty()) {
            return null;
        }
        return setE.size() == 1 ? setE.iterator().next() : this.c.get(obj);
    }

    @Override // com.android.tools.r8.internal.O4
    public final Object g(Object obj) {
        Object objG = super.g(obj);
        if (e(objG).size() <= 1 || b(objG) == obj) {
            this.c.remove(objG);
        }
        return objG;
    }

    public final void b(Object obj, Object obj2) {
        boolean z = d;
        if (!z && e(obj).size() <= 1) {
            throw new AssertionError();
        }
        if (!z && !e(obj).contains(obj2)) {
            throw new AssertionError();
        }
        this.c.put(obj, obj2);
    }
}
