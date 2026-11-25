package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class XL {
    static final /* synthetic */ boolean a = true;

    private static void a(final AbstractC2309jV abstractC2309jV, LinkedList linkedList) {
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            C2688oE c2688oE = (C2688oE) it.next();
            boolean zL = c2688oE.l();
            int size = c2688oE.size();
            c2688oE.removeIf(new Predicate() { // from class: com.android.tools.r8.internal.XL$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return XL.a(abstractC2309jV, (C0729q2) obj);
                }
            });
            if (!a) {
                abstractC2309jV.a(zL, size, AbstractC1639am.c(c2688oE));
            }
            if (c2688oE.m()) {
                it.remove();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(C2688oE c2688oE, C2688oE c2688oE2) {
        c2688oE2.getClass();
        c2688oE.getClass();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(AbstractC2309jV abstractC2309jV, C0729q2 c0729q2) {
        return !abstractC2309jV.a(c0729q2);
    }

    private static LinkedList a(final SF sf, LinkedList linkedList) {
        final LinkedList linkedList2 = new LinkedList();
        linkedList.forEach(new Consumer() { // from class: com.android.tools.r8.internal.XL$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                XL.a(sf, linkedList2, (C2688oE) obj);
            }
        });
        return linkedList2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(SF sf, LinkedList linkedList, final C2688oE c2688oE) {
        boolean zL = c2688oE.l();
        int size = c2688oE.size();
        Collection collectionA = sf.a(c2688oE);
        collectionA.forEach(new Consumer() { // from class: com.android.tools.r8.internal.XL$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                XL.a(c2688oE, (C2688oE) obj);
            }
        });
        if (!a) {
            sf.a(zL, size, collectionA);
        }
        linkedList.addAll(collectionA);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C2688oE c2688oE, C2688oE c2688oE2) {
        c2688oE2.getClass();
        c2688oE.getClass();
    }

    private static LinkedList a(final TF tf, LinkedList linkedList, ExecutorService executorService) {
        final Object objA = tf.a(linkedList, executorService);
        final LinkedList linkedList2 = new LinkedList();
        linkedList.forEach(new Consumer() { // from class: com.android.tools.r8.internal.XL$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                XL.a(tf, objA, linkedList2, (C2688oE) obj);
            }
        });
        return linkedList2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(TF tf, Object obj, LinkedList linkedList, final C2688oE c2688oE) {
        boolean zL = c2688oE.l();
        int size = c2688oE.size();
        Collection collectionA = tf.a(c2688oE, obj);
        collectionA.forEach(new Consumer() { // from class: com.android.tools.r8.internal.XL$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj2) {
                XL.b(c2688oE, (C2688oE) obj2);
            }
        });
        if (!a) {
            tf.a(zL, size, collectionA);
        }
        linkedList.addAll(collectionA);
    }

    public static LinkedList a(LinkedList linkedList, List list, ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            WL wl = (WL) it.next();
            if (!wl.j()) {
                y3Var.a(wl.e());
                if (wl.i()) {
                    a(wl.c(), linkedList);
                } else if (wl.g()) {
                    linkedList = a(wl.a(), linkedList);
                } else {
                    if (!a && !wl.h()) {
                        throw new AssertionError();
                    }
                    linkedList = a(wl.b(), linkedList, executorService);
                }
                y3Var.c();
                wl.d();
                if (linkedList.isEmpty()) {
                    break;
                }
                if (!a && !linkedList.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.internal.XL$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return XL.a((C2688oE) obj);
                    }
                })) {
                    throw new AssertionError();
                }
            }
        }
        return linkedList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C2688oE c2688oE) {
        return c2688oE.size() >= 2;
    }
}
