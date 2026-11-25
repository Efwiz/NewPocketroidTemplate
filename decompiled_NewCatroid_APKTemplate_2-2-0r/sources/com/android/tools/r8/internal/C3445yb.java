package com.android.tools.r8.internal;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3445yb extends EZ {
    private final EZ a;
    private final InterfaceC1833dK b;

    public C3445yb(C1862dk c1862dk, Type type, EZ ez, InterfaceC1833dK interfaceC1833dK) {
        this.a = new GZ(c1862dk, ez, type);
        this.b = interfaceC1833dK;
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) throws IOException {
        Collection collection = (Collection) obj;
        if (collection == null) {
            c1799cu.l();
            return;
        }
        c1799cu.d();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            this.a.a(c1799cu, it.next());
        }
        c1799cu.f();
    }

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) throws IOException {
        if (c1486Wt.t() == 9) {
            c1486Wt.q();
            return null;
        }
        Collection collection = (Collection) this.b.a();
        c1486Wt.c();
        while (c1486Wt.i()) {
            collection.add(this.a.a(c1486Wt));
        }
        c1486Wt.g();
        return collection;
    }
}
