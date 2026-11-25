package com.android.tools.r8.dex;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.M2;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AN;
import com.android.tools.r8.internal.InterfaceC3506zS;
import com.android.tools.r8.naming.C3605b;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.dex.v, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0584v extends W {
    static final /* synthetic */ boolean d = true;
    final C0702m b;
    final I c;

    public C0584v(C0702m c0702m, I i) {
        this.b = c0702m;
        this.c = i;
    }

    @Override // com.android.tools.r8.dex.W
    public Collection a() {
        return this.c.c();
    }

    @Override // com.android.tools.r8.dex.W
    public Collection b() {
        return this.c.f();
    }

    @Override // com.android.tools.r8.dex.W
    public Collection c() {
        return this.c.e();
    }

    @Override // com.android.tools.r8.dex.W
    public Collection d() {
        return this.c.d();
    }

    @Override // com.android.tools.r8.dex.W
    public Collection e() {
        return this.c.g();
    }

    @Override // com.android.tools.r8.dex.W
    public Collection f() {
        return a(this.c.g());
    }

    @Override // com.android.tools.r8.dex.W
    public Collection g() {
        return this.c.i();
    }

    @Override // com.android.tools.r8.dex.W
    public Collection h() {
        return this.c.j();
    }

    @Override // com.android.tools.r8.dex.W
    public Collection i() {
        return this.c.k();
    }

    final ArrayList a(InterfaceC3506zS interfaceC3506zS) {
        final AN anC = AN.c();
        final ArrayList arrayList = new ArrayList();
        Iterator<E> it = interfaceC3506zS.iterator();
        while (it.hasNext()) {
            ((C0729q2) it.next()).g(new Consumer() { // from class: com.android.tools.r8.dex.v$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(arrayList, anC, (o5) obj);
                }
            }, new C0572k$$ExternalSyntheticLambda8());
        }
        arrayList.sort(Comparator.comparing(new Function() { // from class: com.android.tools.r8.dex.v$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (String) anC.b((o5) obj);
            }
        }));
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list, AN an, o5 o5Var) {
        M2 m2C1 = o5Var.e().c1();
        if (!d && m2C1 == null && !o5Var.e().X1()) {
            throw new AssertionError();
        }
        if (m2C1 != null) {
            list.add(o5Var);
            an.a(o5Var, a(o5Var, this.b.e().g()));
        }
    }

    private static String a(o5 o5Var, C3605b c3605b) {
        com.android.tools.r8.naming.Q.b bVarA;
        String strR0;
        if (c3605b != null) {
            bVarA = c3605b.a(o5Var.getReference());
            strR0 = c3605b.a(o5Var.t());
        } else {
            bVarA = com.android.tools.r8.naming.Q.b.a(o5Var.getReference());
            strR0 = o5Var.t().r0();
        }
        return strR0 + bVarA;
    }
}
