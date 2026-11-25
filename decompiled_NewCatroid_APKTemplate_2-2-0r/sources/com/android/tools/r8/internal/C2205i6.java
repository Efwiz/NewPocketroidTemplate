package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* JADX INFO: renamed from: com.android.tools.r8.internal.i6, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2205i6 extends AbstractC2283j6<C2768pH> {
    static final /* synthetic */ boolean b = true;
    public static final /* synthetic */ int c = 0;

    C2205i6(Map map) {
        super(map);
    }

    public static C2205i6 a(Collection<C2768pH> collection) {
        return new C2205i6((Map) collection.stream().collect(Collectors.toMap(new Function() { // from class: com.android.tools.r8.internal.i6$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2205i6.a((C2768pH) obj);
            }
        }, Function.identity())));
    }

    public CN c() {
        Predicate predicate = new Predicate() { // from class: com.android.tools.r8.internal.i6$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((C2768pH) obj).i();
            }
        };
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.i6$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((C2768pH) obj).d();
            }
        };
        CN cnB = CN.b();
        Set setC = OU.c();
        Iterator it = this.a.values().iterator();
        while (it.hasNext()) {
            C2768pH c2768pH = (C2768pH) it.next();
            if (predicate.test(c2768pH)) {
                cnB.add(c2768pH.b());
                it.remove();
                setC.add(c2768pH);
            }
        }
        setC.forEach(consumer);
        if (b || !cnB.isEmpty()) {
            return cnB;
        }
        throw new AssertionError();
    }

    public CN d() {
        Predicate predicate = new Predicate() { // from class: com.android.tools.r8.internal.i6$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((C2768pH) obj).j();
            }
        };
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.i6$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((C2768pH) obj).c();
            }
        };
        CN cnB = CN.b();
        Set setC = OU.c();
        Iterator it = this.a.values().iterator();
        while (it.hasNext()) {
            C2768pH c2768pH = (C2768pH) it.next();
            if (predicate.test(c2768pH)) {
                cnB.add(c2768pH.b());
                it.remove();
                setC.add(c2768pH);
            }
        }
        setC.forEach(consumer);
        if (b || !cnB.isEmpty()) {
            return cnB;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0684j2 a(C2768pH c2768pH) {
        return c2768pH.b().getReference();
    }

    public final AbstractC2755p6 a(C0702m c0702m) {
        if (c0702m.Y().o1()) {
            return new C2589n6(c0702m, this);
        }
        return AbstractC2755p6.a();
    }
}
