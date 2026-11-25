package com.android.tools.r8.internal;

import com.android.tools.r8.AbstractC0403b;
import java.io.IOException;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xK, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3349xK extends EZ {
    public static final FZ b = new C3273wK();
    private final C1862dk a;

    C3349xK(C1862dk c1862dk) {
        this.a = c1862dk;
    }

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) throws IOException {
        int iB = AbstractC0403b.b(c1486Wt.t());
        if (iB == 0) {
            ArrayList arrayList = new ArrayList();
            c1486Wt.c();
            while (c1486Wt.i()) {
                arrayList.add(a(c1486Wt));
            }
            c1486Wt.g();
            return arrayList;
        }
        if (iB == 2) {
            JB jb = new JB(0);
            c1486Wt.d();
            while (c1486Wt.i()) {
                jb.put(c1486Wt.p(), a(c1486Wt));
            }
            c1486Wt.h();
            return jb;
        }
        if (iB == 5) {
            return c1486Wt.r();
        }
        if (iB == 6) {
            return Double.valueOf(c1486Wt.m());
        }
        if (iB == 7) {
            return Boolean.valueOf(c1486Wt.l());
        }
        if (iB != 8) {
            throw new IllegalStateException();
        }
        c1486Wt.q();
        return null;
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) throws IOException {
        if (obj == null) {
            c1799cu.l();
            return;
        }
        C1862dk c1862dk = this.a;
        Class<?> cls = obj.getClass();
        c1862dk.getClass();
        EZ ezA = c1862dk.a(I00.a((Class) cls));
        if (ezA instanceof C3349xK) {
            c1799cu.e();
            c1799cu.g();
        } else {
            ezA.a(c1799cu, obj);
        }
    }
}
