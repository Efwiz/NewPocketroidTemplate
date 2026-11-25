package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.utils.InterfaceC3905h3;
import java.util.Collection;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC3231vl extends AbstractC2438l6 {
    static final /* synthetic */ boolean d = true;

    AbstractC3231vl(C0702m c0702m) {
        super(c0702m);
    }

    public C2205i6 a(ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var) throws Throwable {
        y3Var.a("Build IR processing order constraints");
        y3Var.a("Build call graph");
        a(executorService);
        if (!d) {
            for (C2768pH c2768pH : this.b.values()) {
                for (C2768pH c2768pH2 : c2768pH.g()) {
                    if (!d && c2768pH.c(c2768pH2)) {
                        throw new AssertionError();
                    }
                }
            }
        }
        y3Var.c();
        boolean z = d;
        if (!z) {
            a();
        }
        this.a.c(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.vl$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                this.f$0.a((C1132Kj) obj);
            }
        });
        y3Var.a("Cycle elimination");
        Collection collectionValues = this.b.values();
        TreeSet treeSet = new TreeSet();
        AbstractC2734ot.a((Iterable) collectionValues, (Collection) treeSet);
        C3144ud c3144ud = new C3144ud();
        c3144ud.a(treeSet);
        y3Var.c();
        y3Var.c();
        if (z || c3144ud.a(treeSet).a() == 0) {
            return new C2205i6(this.b);
        }
        throw new AssertionError();
    }

    abstract void a();

    abstract void a(ExecutorService executorService);

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C1132Kj c1132Kj) {
        c1132Kj.a(this.b);
    }

    @Override // com.android.tools.r8.internal.AbstractC2438l6
    protected final AbstractC2839qH a(com.android.tools.r8.graph.o5 o5Var) {
        return new C2768pH(o5Var);
    }
}
