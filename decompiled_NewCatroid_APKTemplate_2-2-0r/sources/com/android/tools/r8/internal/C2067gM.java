package com.android.tools.r8.internal;

import com.android.tools.r8.ResourceException;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.C3979v2;
import java.util.IdentityHashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gM, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2067gM implements InterfaceC2229iM {
    static final /* synthetic */ boolean h = true;
    private final C0702m a;
    private final C0722p1 b;
    private final IdentityHashMap c;
    private final com.android.tools.r8.utils.T d;
    private final InterfaceC2229iM e;
    private com.android.tools.r8.graph.W0 f;
    private C2444lA.b g;

    private C2067gM(C0702m c0702m, InterfaceC2229iM interfaceC2229iM, com.android.tools.r8.utils.T t) {
        this.c = new IdentityHashMap();
        this.g = null;
        this.a = c0702m;
        this.b = c0702m.a();
        this.e = interfaceC2229iM;
        this.d = t;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2229iM
    public final C3979v2 a(AbstractC1915eM abstractC1915eM) {
        C2444lA.b bVar;
        if (!h && this.f == null) {
            throw new AssertionError();
        }
        int iE = abstractC1915eM.e();
        C0767v2 c0767v2 = abstractC1915eM.g().e;
        if (this.g == null) {
            this.g = (C2444lA.b) this.c.get(c0767v2);
        }
        if (this.g != null || this.c.containsKey(c0767v2)) {
            bVar = this.g;
        } else {
            com.android.tools.r8.graph.B2.j jVarA = this.a.a(this.a.c(this.f.J0()));
            if (jVarA != null) {
                this.g = C2444lA.c(((C0759u2) jVarA.b).toString());
            }
            this.c.put(c0767v2, this.g);
            bVar = this.g;
        }
        if (bVar == null) {
            return this.e.a(abstractC1915eM);
        }
        Map.Entry<Integer, C2444lA.a> entryB = bVar.b(iE);
        if (entryB == null) {
            return this.e.a(abstractC1915eM);
        }
        int iIntValue = entryB.getValue().a().a + (iE - entryB.getKey().intValue());
        try {
            String strB = entryB.getValue().b().b();
            String strA = this.d.a(iIntValue, strB);
            if (strA == null) {
                return this.e.a(abstractC1915eM);
            }
            String strL = C3970u0.l(strB);
            String strB2 = com.android.tools.r8.utils.T.b(strA);
            String strA2 = com.android.tools.r8.utils.T.a(strA);
            String strU = C3970u0.u(strA2);
            String[] strArrE = C3970u0.e(strA2);
            C0759u2[] c0759u2Arr = new C0759u2[strArrE.length];
            for (int i = 0; i < strArrE.length; i++) {
                c0759u2Arr[i] = this.b.c(strArrE[i]);
            }
            C0722p1 c0722p1 = this.b;
            C0684j2 c0684j2A = c0722p1.a(c0722p1.c(strL), this.b.c(strB2), this.b.c(strU), c0759u2Arr);
            if (!c0684j2A.equals(abstractC1915eM.g())) {
                Map.Entry entryA = bVar.a(iE);
                if (entryA != null) {
                    abstractC1915eM = abstractC1915eM.a().a(((C2444lA.a) entryA.getValue()).a().a).a();
                }
                return this.e.a(((AbstractC1915eM.b.a) AbstractC1915eM.b.x().a(iIntValue).a(c0684j2A).a(abstractC1915eM)).a());
            }
            return this.e.a(abstractC1915eM);
        } catch (ResourceException unused) {
        }
    }

    public final void a(com.android.tools.r8.graph.W0 w0) {
        this.f = w0;
        this.g = null;
    }
}
