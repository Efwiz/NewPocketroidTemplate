package com.android.tools.r8.utils;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class Q0 {
    public static <K, V> Consumer<Map.Entry<K, V>> a(final BiConsumer<K, V> biConsumer) {
        return new Consumer() { // from class: com.android.tools.r8.utils.Q0$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Q0.a(biConsumer, (Map.Entry) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(BiConsumer biConsumer, Map.Entry entry) {
        biConsumer.accept(entry.getKey(), entry.getValue());
    }
}
