package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.inspector.Inspector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Dn, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0931Dn implements Inspector {
    private final Collection a;

    public C0931Dn(Collection collection) {
        this.a = collection;
    }

    public static List a(Collection collection) {
        if (collection == null || collection.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            final Consumer consumer = (Consumer) it.next();
            Objects.requireNonNull(consumer);
            arrayList.add(new Consumer() { // from class: com.android.tools.r8.internal.Dn$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    consumer.accept((C0931Dn) obj);
                }
            });
        }
        return arrayList;
    }

    @Override // com.android.tools.r8.inspector.Inspector
    public final void forEachClass(Consumer consumer) {
        Iterator it = this.a.iterator();
        while (it.hasNext()) {
            consumer.accept(new C2315ja((C0729q2) it.next()));
        }
    }

    public static void a(List list, Collection collection) {
        if (list == null || list.isEmpty()) {
            return;
        }
        C0931Dn c0931Dn = new C0931Dn(collection);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((Consumer) it.next()).accept(c0931Dn);
        }
    }
}
