package com.android.tools.r8.utils;

import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.utils.b1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class AbstractC3872b1 {
    public static boolean a(Consumer consumer, final Predicate predicate) {
        final K k = new K(true);
        consumer.accept(new Consumer() { // from class: com.android.tools.r8.utils.b1$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AbstractC3872b1.a(k, predicate, obj);
            }
        });
        return k.e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(K k, Predicate predicate, Object obj) {
        k.a(predicate.test(obj));
    }
}
