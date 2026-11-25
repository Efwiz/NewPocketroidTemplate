package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.utils.C3879c3;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.es, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1952es implements InterfaceC2135h8 {
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final EnumC2029fs b;
    private final C2569ms c;
    private final C1002Ga d;

    C1952es(C0702m c0702m, Predicate predicate, C2569ms c2569ms, int i) {
        EnumC2029fs enumC2029fs = EnumC2029fs.b;
        this.a = c0702m;
        this.b = enumC2029fs;
        if (!e && c2569ms == null) {
            throw new AssertionError();
        }
        this.c = c2569ms;
        this.d = new C1002Ga(c0702m, predicate, i);
    }

    private void a(Collection collection, final InterfaceC2262is interfaceC2262is, ExecutorService executorService) {
        C3879c3.a(com.android.tools.r8.utils.V1.a(collection, new Predicate() { // from class: com.android.tools.r8.internal.es$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a((C0729q2) obj);
            }
        }), new Consumer() { // from class: com.android.tools.r8.internal.es$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(interfaceC2262is, (C0729q2) obj);
            }
        }, executorService);
        this.d.a(interfaceC2262is, executorService);
        this.c.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(C0729q2 c0729q2) {
        return !this.a.b(c0729q2) && (!c0729q2.g2() || this.b == EnumC2029fs.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(InterfaceC2262is interfaceC2262is, C0729q2 c0729q2) {
        this.d.a(interfaceC2262is, c0729q2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2135h8
    public final void a(Collection collection, AbstractC2591n8 abstractC2591n8, ExecutorService executorService) {
        a(collection, (InterfaceC2262is) abstractC2591n8, executorService);
    }
}
