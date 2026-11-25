package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lt, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2493lt extends AbstractC0984Fi {
    final /* synthetic */ Iterable b;
    final /* synthetic */ InterfaceC3010sj c;

    C2493lt(Iterable iterable, InterfaceC3010sj interfaceC3010sj) {
        this.b = iterable;
        this.c = interfaceC3010sj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Consumer consumer, InterfaceC3010sj interfaceC3010sj, Object obj) {
        consumer.accept(interfaceC3010sj.apply(obj));
    }

    @Override // java.lang.Iterable
    public final void forEach(final Consumer consumer) {
        int i = AbstractC3123uM.a;
        consumer.getClass();
        Iterable iterable = this.b;
        final InterfaceC3010sj interfaceC3010sj = this.c;
        iterable.forEach(new Consumer() { // from class: com.android.tools.r8.internal.lt$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2493lt.a(consumer, interfaceC3010sj, obj);
            }
        });
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        Iterator it = this.b.iterator();
        InterfaceC3010sj interfaceC3010sj = this.c;
        int i = AbstractC3123uM.a;
        interfaceC3010sj.getClass();
        return new C3090tt(it, interfaceC3010sj);
    }

    @Override // java.lang.Iterable
    public final Spliterator spliterator() {
        return AbstractC3142ub.a(this.b.spliterator(), this.c);
    }
}
