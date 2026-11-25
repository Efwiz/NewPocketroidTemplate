package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xY, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3364xY {
    private final C0702m a;
    private final ArrayList b = new ArrayList();
    private final IdentityHashMap c = new IdentityHashMap();

    public C3364xY(C0702m c0702m) {
        this.a = c0702m;
        a(((C0680j) c0702m.f()).d());
    }

    private void a(Collection collection) {
        collection.forEach(new Consumer() { // from class: com.android.tools.r8.internal.xY$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0729q2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List b(C0729q2 c0729q2) {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2) {
        if (c0729q2.isInterface()) {
            return;
        }
        C0702m c0702m = this.a;
        C0767v2 c0767v2 = c0729q2.f;
        c0702m.getClass();
        C0729q2 c0729q2A = C0729q2.a(c0767v2 == c0729q2.d ? c0729q2 : c0702m.a(c0767v2));
        if (c0729q2A == null) {
            this.b.add(c0729q2);
        } else {
            ((List) this.c.computeIfAbsent(c0729q2A, new Function() { // from class: com.android.tools.r8.internal.xY$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C3364xY.b((C0729q2) obj);
                }
            })).add(c0729q2);
        }
    }

    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final void a(Object obj, final BiFunction biFunction, C0729q2 c0729q2) {
        final Object objApply = biFunction.apply(c0729q2, obj);
        ((Collection) this.c.getOrDefault(c0729q2, Collections.emptyList())).forEach(new Consumer() { // from class: com.android.tools.r8.internal.xY$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj2) {
                this.f$0.a(objApply, biFunction, (C0729q2) obj2);
            }
        });
    }

    public final ArrayList a() {
        return this.b;
    }
}
