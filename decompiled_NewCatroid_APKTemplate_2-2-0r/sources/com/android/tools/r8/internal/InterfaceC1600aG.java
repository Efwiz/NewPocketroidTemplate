package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.aG, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface InterfaceC1600aG extends Collection {
    Set I();

    int a(Object obj);

    int a(Object obj, int i);

    default void a(final ObjIntConsumer objIntConsumer) {
        int i = AbstractC3123uM.a;
        objIntConsumer.getClass();
        entrySet().forEach(new Consumer() { // from class: com.android.tools.r8.internal.aG$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InterfaceC1600aG.a(objIntConsumer, (ZF) obj);
            }
        });
    }

    boolean a(int i, Object obj);

    int b(int i, Object obj);

    int b(Object obj);

    Set entrySet();

    @Override // com.android.tools.r8.internal.InterfaceC1600aG
    boolean equals(Object obj);

    default void forEach(final Consumer consumer) {
        int i = AbstractC3123uM.a;
        consumer.getClass();
        entrySet().forEach(new Consumer() { // from class: com.android.tools.r8.internal.aG$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InterfaceC1600aG.a(consumer, (ZF) obj);
            }
        });
    }

    @Override // com.android.tools.r8.internal.InterfaceC1600aG
    int hashCode();

    @Override // java.lang.Iterable, com.android.tools.r8.internal.InterfaceC1600aG
    default Spliterator spliterator() {
        return AbstractC1987fG.a(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ void a(ObjIntConsumer objIntConsumer, ZF zf) {
        objIntConsumer.accept(zf.b(), zf.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ void a(Consumer consumer, ZF zf) {
        Object objB = zf.b();
        int iA = zf.a();
        for (int i = 0; i < iA; i++) {
            consumer.accept(objB);
        }
    }
}
