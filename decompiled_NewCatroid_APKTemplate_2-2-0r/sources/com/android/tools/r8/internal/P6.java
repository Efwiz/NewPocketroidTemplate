package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.utils.C3879c3;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class P6 extends Q6 {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final Collection b;

    public P6(C0702m c0702m, ArrayList arrayList) {
        if (!c && arrayList.isEmpty()) {
            throw new AssertionError();
        }
        this.a = c0702m;
        this.b = arrayList;
    }

    @Override // com.android.tools.r8.internal.Q6
    public final void a(ExecutorService executorService, final S6 s6) {
        if (!c && ((Set) this.b.stream().map(new Function() { // from class: com.android.tools.r8.internal.P6$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((N6) obj).a();
            }
        }).collect(Collectors.toSet())).size() != this.b.size()) {
            throw new AssertionError();
        }
        final C1257Pb c1257PbP = this.a.p();
        C3879c3.a(this.b, new Consumer() { // from class: com.android.tools.r8.internal.P6$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                P6.a(c1257PbP, s6, (N6) obj);
            }
        }, executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C1257Pb c1257Pb, S6 s6, N6 n6) {
        n6.a(c1257Pb.a(n6), s6);
    }
}
