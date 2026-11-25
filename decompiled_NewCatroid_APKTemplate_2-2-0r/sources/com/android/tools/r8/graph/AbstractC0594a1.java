package com.android.tools.r8.graph;

import java.util.Collection;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.graph.a1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0594a1 {
    public static void a(final com.android.tools.r8.dex.V v, AbstractC0594a1[] abstractC0594a1Arr) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.graph.a1$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((AbstractC0594a1) obj).a(v);
            }
        };
        if (abstractC0594a1Arr == null) {
            return;
        }
        for (AbstractC0594a1 abstractC0594a1 : abstractC0594a1Arr) {
            if (abstractC0594a1 != null) {
                consumer.accept(abstractC0594a1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void a(com.android.tools.r8.dex.V v);

    public String q0() {
        return toString();
    }

    public String r0() {
        return toString();
    }

    public static void a(final com.android.tools.r8.dex.V v, Collection collection) {
        collection.forEach(new Consumer() { // from class: com.android.tools.r8.graph.a1$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((AbstractC0594a1) obj).a(v);
            }
        });
    }
}
