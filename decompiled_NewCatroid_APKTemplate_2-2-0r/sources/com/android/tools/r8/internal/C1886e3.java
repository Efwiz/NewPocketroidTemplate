package com.android.tools.r8.internal;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.e3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1886e3 extends EZ {
    public static final FZ c = new C1813d3();
    private final Class a;
    private final EZ b;

    public C1886e3(C1862dk c1862dk, EZ ez, Class cls) {
        this.b = new GZ(c1862dk, ez, cls);
        this.a = cls;
    }

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) throws IOException {
        if (c1486Wt.t() == 9) {
            c1486Wt.q();
            return null;
        }
        ArrayList arrayList = new ArrayList();
        c1486Wt.c();
        while (c1486Wt.i()) {
            arrayList.add(this.b.a(c1486Wt));
        }
        c1486Wt.g();
        Object objNewInstance = Array.newInstance((Class<?>) this.a, arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            Array.set(objNewInstance, i, arrayList.get(i));
        }
        return objNewInstance;
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) throws IOException {
        if (obj == null) {
            c1799cu.l();
            return;
        }
        c1799cu.d();
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            this.b.a(c1799cu, Array.get(obj, i));
        }
        c1799cu.f();
    }
}
