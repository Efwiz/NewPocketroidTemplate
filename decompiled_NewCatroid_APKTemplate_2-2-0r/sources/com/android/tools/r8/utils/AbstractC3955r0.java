package com.android.tools.r8.utils;

import com.android.tools.r8.internal.C3332x4;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.utils.r0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class AbstractC3955r0 {
    static final /* synthetic */ boolean c = true;
    private final ArrayDeque a = new ArrayDeque();
    private final IdentityHashMap b = new IdentityHashMap();

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object a(List list) {
        return list.get(0);
    }

    abstract D3 a(C3931m0 c3931m0);

    abstract C3931m0 a(Object obj);

    protected abstract List a(Collection collection);

    abstract D3 b(C3931m0 c3931m0);

    final C3931m0 b(Object obj) {
        C3931m0 c3931m0 = (C3931m0) this.b.computeIfAbsent(obj, new Function() { // from class: com.android.tools.r8.utils.r0$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return this.f$0.a(obj2);
            }
        });
        if (c3931m0.b == 1) {
            this.a.addLast(c3931m0);
        }
        return c3931m0;
    }

    public final D3 a(C3332x4 c3332x4) {
        return b((Collection) Collections.singletonList(c3332x4)).a(Function.identity(), new Function() { // from class: com.android.tools.r8.utils.r0$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC3955r0.a((List) obj);
            }
        });
    }

    public final D3 b(Collection collection) {
        D3 d3B;
        collection.forEach(new Consumer() { // from class: com.android.tools.r8.utils.r0$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(obj);
            }
        });
        while (!this.a.isEmpty()) {
            C3931m0 c3931m0 = (C3931m0) this.a.removeLast();
            int i = c3931m0.b;
            if (i != 3) {
                if (i == 1) {
                    this.a.addLast(c3931m0);
                    c3931m0.b = 2;
                    d3B = b(c3931m0);
                } else {
                    if (!c && !c3931m0.b()) {
                        throw new AssertionError();
                    }
                    D3 d3A = a(c3931m0);
                    if (!C3931m0.c && c3931m0.b == 3) {
                        throw new AssertionError();
                    }
                    c3931m0.b = 3;
                    d3B = d3A;
                }
                if (d3B.f()) {
                    return D3.a(d3B.a().i());
                }
            }
        }
        return D3.b(a(collection));
    }
}
