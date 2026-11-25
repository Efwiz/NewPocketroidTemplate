package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vn, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3233vn {
    static final /* synthetic */ boolean a = true;

    public static void a(C2409kl c2409kl, Set set, Set set2) {
        if (!a && !c2409kl.t().z()) {
            throw new AssertionError();
        }
        Iterator it = c2409kl.b((Predicate) new C3234vn$$ExternalSyntheticLambda0()).iterator();
        while (it.hasNext()) {
            a(((C3344xF) it.next()).e().m(), set, set2);
        }
    }

    public static void a(S10 s10, Set set, Set set2) {
        if (!a && s10.m() != s10) {
            throw new AssertionError();
        }
        if (!s10.i()) {
            AbstractC2412ko abstractC2412ko = s10.b;
            abstractC2412ko.getClass();
            if (abstractC2412ko instanceof C3446yc) {
                set.add(s10.b.A().K2());
                return;
            }
        }
        set2.add(s10);
    }
}
