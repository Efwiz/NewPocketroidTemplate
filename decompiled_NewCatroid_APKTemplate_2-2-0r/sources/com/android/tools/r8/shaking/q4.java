package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.C1301Qo;
import com.android.tools.r8.internal.InterfaceC1050Ho;
import com.android.tools.r8.internal.MR;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class q4 {
    static final /* synthetic */ boolean h = true;
    private final IdentityHashMap a;
    private final MR b;
    private final MR c;
    private final C0767v2 d;
    private final C0767v2 e;
    private final Collection f;
    final /* synthetic */ x4 g;

    private q4(x4 x4Var, C0767v2 c0767v2, C0767v2 c0767v3) {
        this.g = x4Var;
        this.a = new IdentityHashMap();
        Collection collectionA = x4Var.a();
        this.f = collectionA;
        this.d = c0767v2;
        this.e = c0767v3;
        ArrayList arrayList = (ArrayList) collectionA;
        MR mr = new MR(arrayList.size() / 2);
        this.b = mr;
        mr.e(Integer.MIN_VALUE);
        MR mr2 = new MR(arrayList.size() / 2);
        this.c = mr2;
        mr2.e(Integer.MIN_VALUE);
    }

    private void a() {
        for (C0684j2 c0684j2 : this.f) {
            C0737r2 c0737r2 = c0684j2.h;
            int length = c0737r2.f.a.length;
            int iA = a(c0737r2, this.e, this.b);
            if (iA != 0) {
                InterfaceC1050Ho interfaceC1050Ho = (InterfaceC1050Ho) this.a.computeIfAbsent(c0684j2.f, new Function() { // from class: com.android.tools.r8.shaking.q4$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return q4.a((C0759u2) obj);
                    }
                });
                int i = interfaceC1050Ho.get(length);
                if (i == Integer.MIN_VALUE) {
                    i = 0;
                }
                interfaceC1050Ho.b(length, iA | i);
            }
        }
    }

    final boolean b() {
        boolean z;
        int i;
        this.g.h.a("collision detection");
        a();
        if (this.a.isEmpty()) {
            z = false;
        } else {
            for (C0684j2 c0684j2 : this.f) {
                InterfaceC1050Ho interfaceC1050Ho = (InterfaceC1050Ho) this.a.get(c0684j2.f);
                if (interfaceC1050Ho != null && (i = interfaceC1050Ho.get(c0684j2.F0())) != Integer.MIN_VALUE) {
                    if (!h && i == 0) {
                        throw new AssertionError();
                    }
                    if ((a(c0684j2.h, this.d, this.c) & i) != 0) {
                        z = true;
                    }
                }
            }
            z = false;
        }
        this.g.h.c();
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ InterfaceC1050Ho a(C0759u2 c0759u2) {
        C1301Qo c1301Qo = new C1301Qo();
        c1301Qo.e(Integer.MIN_VALUE);
        return c1301Qo;
    }

    private int a(C0737r2 c0737r2, C0767v2 c0767v2, MR mr) {
        int iA = mr.a(c0737r2);
        if (iA != Integer.MIN_VALUE) {
            return iA;
        }
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        for (C0767v2 c0767v3 : c0737r2.f.a) {
            C0767v2 c0767v2B = c0767v3.b(this.g.c.a());
            i <<= 1;
            i2++;
            if (((C0767v2) this.g.m.getOrDefault(c0767v2B, c0767v2B)) == c0767v2) {
                i |= 1;
            }
            if (i2 == 31) {
                i3 |= i;
                i = 0;
                i2 = 0;
            }
        }
        C0767v2 c0767v2B2 = c0737r2.e.b(this.g.c.a());
        int i4 = i << 1;
        if (((C0767v2) this.g.m.getOrDefault(c0767v2B2, c0767v2B2)) == c0767v2) {
            i4 |= 1;
        }
        int i5 = i3 | i4;
        mr.a(i5, c0737r2);
        return i5;
    }
}
