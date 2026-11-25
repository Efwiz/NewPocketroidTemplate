package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class FL {
    static final /* synthetic */ boolean a = true;

    public static Predicate a(final Predicate predicate) {
        return new Predicate() { // from class: com.android.tools.r8.internal.FL$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return FL.a(predicate, (AbstractC2412ko) obj);
            }
        };
    }

    public static int b(AbstractC2412ko abstractC2412ko) {
        S10 s10B = abstractC2412ko.b();
        if (s10B instanceof C2708oW) {
            return ((C2708oW) s10B).j0().length;
        }
        return (s10B == null || !s10B.T()) ? 0 : 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Predicate predicate, AbstractC2412ko abstractC2412ko) {
        return predicate.test(abstractC2412ko) && !abstractC2412ko.b1() && (abstractC2412ko.b() == null || !abstractC2412ko.b().A());
    }

    public static void a(C3486zB c3486zB, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            c3486zB.previous();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(List list, AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko == list.get(list.size() - 1);
    }

    public static int a(AbstractC2412ko abstractC2412ko) {
        int i = 0;
        for (int size = abstractC2412ko.h1().size() - 1; size >= 0; size--) {
            if (((S10) abstractC2412ko.h1().get(size)).T()) {
                i++;
            }
        }
        return i;
    }

    public static void a(C3486zB c3486zB, final List list) {
        if (!a && list.isEmpty()) {
            throw new AssertionError();
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AbstractC2412ko abstractC2412ko = (AbstractC2412ko) it.next();
            Iterator it2 = ((ArrayList) abstractC2412ko.h1()).iterator();
            while (it2.hasNext()) {
                ((S10) it2.next()).b(abstractC2412ko);
            }
            c3486zB.add(abstractC2412ko);
        }
        AbstractC2412ko next = (AbstractC2412ko) c3486zB.a(new Predicate() { // from class: com.android.tools.r8.internal.FL$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return FL.a(list, (AbstractC2412ko) obj);
            }
        });
        for (int i = 0; i < list.size(); i++) {
            if (!a && next != list.get(i)) {
                throw new AssertionError();
            }
            c3486zB.s();
            next = c3486zB.next();
        }
        c3486zB.c(new Predicate() { // from class: com.android.tools.r8.internal.FL$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return FL.b(list, (AbstractC2412ko) obj);
            }
        });
        c3486zB.next();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(List list, AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko == list.get(0);
    }
}
