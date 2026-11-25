package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1256Pa;
import com.android.tools.r8.internal.AbstractC1899eF;
import com.android.tools.r8.internal.C0869Bt;
import com.android.tools.r8.internal.C2778pR;
import com.android.tools.r8.internal.CR;
import com.android.tools.r8.origin.Origin;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class U3 extends AbstractC1256Pa {
    private final AbstractC0727q0 c;
    private final BiFunction d;
    private final M3 e;
    private boolean f;
    private final Origin g;
    private final V3 h;
    private C2778pR i;

    U3(AbstractC0727q0 abstractC0727q0, BiFunction biFunction, M3 m3, boolean z, Origin origin, V3 v3) {
        super(589824);
        this.c = abstractC0727q0;
        this.d = biFunction;
        this.e = m3;
        this.f = z;
        this.g = origin;
        this.h = v3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CR b() {
        if (this.i == null) {
            this.i = new C2778pR();
        }
        return this.i;
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final AbstractC1899eF a(int i, String str, String str2, String str3, String[] strArr) {
        Z3 z3;
        C0725p4 c0725p4A = T3.a(i, str);
        if (c0725p4A.W() || c0725p4A.Z() || (z3 = (Z3) this.d.apply(str, str2)) == null) {
            return null;
        }
        Y3 y3 = new Y3(this.e, this.e.b(this.c.d, str, str2), z3, this.g, this.h, new Supplier() { // from class: com.android.tools.r8.graph.U3$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.b();
            }
        });
        return !this.f ? y3 : new C0869Bt(y3, i, str, str2, strArr);
    }
}
