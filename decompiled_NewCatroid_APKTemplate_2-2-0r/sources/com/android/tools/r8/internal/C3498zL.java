package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.C3879c3;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zL, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3498zL extends AbstractC3231vl {
    static final /* synthetic */ boolean f = true;
    private final CN e;

    public C3498zL(C0702m<C3733i> c0702m, CN cn) {
        super(c0702m);
        if (!f && (cn == null || cn.isEmpty())) {
            throw new AssertionError();
        }
        this.e = cn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(com.android.tools.r8.graph.o5 o5Var) {
        C0702m c0702m = this.a;
        C2768pH c2768pH = (C2768pH) b(o5Var);
        Function function = new Function() { // from class: com.android.tools.r8.internal.zL$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (C2768pH) this.f$0.b((com.android.tools.r8.graph.o5) obj);
            }
        };
        ConcurrentHashMap concurrentHashMap = this.c;
        final CN cn = this.e;
        Objects.requireNonNull(cn);
        o5Var.a(new C3308wl(c0702m, c2768pH, function, concurrentHashMap, new Predicate() { // from class: com.android.tools.r8.internal.zL$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return cn.b((com.android.tools.r8.graph.o5) obj);
            }
        }));
    }

    @Override // com.android.tools.r8.internal.AbstractC3231vl
    public /* bridge */ /* synthetic */ C2205i6 a(ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var) throws ExecutionException {
        return super.a(executorService, y3Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC3231vl
    final void a(ExecutorService executorService) {
        C3879c3.a(this.e, new Consumer() { // from class: com.android.tools.r8.internal.zL$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.c((com.android.tools.r8.graph.o5) obj);
            }
        }, executorService);
    }

    @Override // com.android.tools.r8.internal.AbstractC3231vl
    final void a() {
        for (com.android.tools.r8.graph.o5 o5Var : this.e) {
            if (!f) {
                if (o5Var.e().q1() != (this.b.get(o5Var.getReference()) != null)) {
                    throw new AssertionError();
                }
            }
        }
    }
}
