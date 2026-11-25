package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kt, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2417kt extends AbstractC0984Fi {
    final /* synthetic */ Iterable b;
    final /* synthetic */ InterfaceC3205vM c;

    C2417kt(Iterable iterable, InterfaceC3205vM interfaceC3205vM) {
        this.b = iterable;
        this.c = interfaceC3205vM;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(InterfaceC3205vM interfaceC3205vM, Consumer consumer, Object obj) {
        if (interfaceC3205vM.test(obj)) {
            consumer.accept(obj);
        }
    }

    @Override // java.lang.Iterable
    public final void forEach(final Consumer consumer) {
        int i = AbstractC3123uM.a;
        consumer.getClass();
        Iterable iterable = this.b;
        final InterfaceC3205vM interfaceC3205vM = this.c;
        iterable.forEach(new Consumer() { // from class: com.android.tools.r8.internal.kt$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2417kt.a(interfaceC3205vM, consumer, obj);
            }
        });
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        Iterator it = this.b.iterator();
        InterfaceC3205vM interfaceC3205vM = this.c;
        int i = AbstractC3123uM.a;
        it.getClass();
        interfaceC3205vM.getClass();
        return new C3020st(it, interfaceC3205vM);
    }

    @Override // java.lang.Iterable
    public final Spliterator spliterator() {
        Spliterator spliterator = this.b.spliterator();
        InterfaceC3205vM interfaceC3205vM = this.c;
        int i = AbstractC3123uM.a;
        spliterator.getClass();
        interfaceC3205vM.getClass();
        return new C2792pb(spliterator, interfaceC3205vM);
    }
}
