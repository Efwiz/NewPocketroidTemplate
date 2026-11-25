package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC2509m2;
import com.android.tools.r8.internal.C1549Za;
import com.android.tools.r8.internal.CQ;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class v5 implements com.android.tools.r8.utils.structural.x<v5> {
    static final /* synthetic */ boolean d = true;
    private final Y0 a;
    private final C0706m3.e b;
    private final List c;

    public v5(Y0 y0, C0706m3.e eVar, List list) {
        boolean z = d;
        if (!z && y0 == null) {
            throw new AssertionError();
        }
        if (!z && eVar == null) {
            throw new AssertionError();
        }
        if (!z && list == null) {
            throw new AssertionError();
        }
        this.a = y0;
        this.b = eVar;
        this.c = list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.c(new Function() { // from class: com.android.tools.r8.graph.v5$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((v5) obj).c();
            }
        }).c(new Function() { // from class: com.android.tools.r8.graph.v5$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((v5) obj).e();
            }
        });
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    public final Y0 b() {
        return this.a;
    }

    public C0759u2 c() {
        return this.a.C0();
    }

    public C0706m3.e d() {
        return this.b;
    }

    public C0767v2 e() {
        return this.a.getType();
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.v5$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                v5.a(zVar);
            }
        };
    }

    public List<C0633d0> a() {
        return this.c;
    }

    public final void a(C1549Za c1549Za, AbstractC3649o0 abstractC3649o0, Predicate predicate, BiConsumer biConsumer) {
        String string;
        String string2 = abstractC3649o0.a(this.a).toString();
        String string3 = abstractC3649o0.c(e()).toString();
        C0706m3.e eVar = this.b;
        if (eVar.a()) {
            string = null;
        } else {
            A3 a3 = new A3(abstractC3649o0, predicate);
            a3.a((C0706m3.i) eVar);
            string = a3.toString();
        }
        CQ cqB = c1549Za.b(string2, string3, string);
        for (C0633d0 c0633d0 : this.c) {
            if (c0633d0.a != 2) {
                AbstractC2509m2 abstractC2509m2A = cqB.a(abstractC3649o0.c(c0633d0.t0()).toString(), c0633d0.a == 1);
                if (abstractC2509m2A != null) {
                    biConsumer.accept(abstractC2509m2A, c0633d0.b);
                    abstractC2509m2A.a();
                }
            }
        }
    }
}
