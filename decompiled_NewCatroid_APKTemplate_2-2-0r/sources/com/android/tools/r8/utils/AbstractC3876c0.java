package com.android.tools.r8.utils;

import com.android.tools.r8.ClassFileResourceProvider;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.C1714bm;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.utils.c0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class AbstractC3876c0 {
    private final com.android.tools.r8.graph.I a;

    AbstractC3876c0(com.android.tools.r8.graph.I i) {
        this.a = i;
    }

    public static AbstractC3876c0 a(com.android.tools.r8.graph.I i, ClassFileResourceProvider classFileResourceProvider, com.android.tools.r8.graph.M3 m3) {
        return new Y(i, classFileResourceProvider, m3);
    }

    public abstract Collection a();

    public abstract void a(C0767v2 c0767v2, Consumer consumer);

    final com.android.tools.r8.graph.I b() {
        return this.a;
    }

    public C3866a0 a(Set set) {
        return new C3866a0(this.a, this, (AbstractC1048Hm) set);
    }

    public static AbstractC3876c0 a(com.android.tools.r8.graph.I i, ArrayList arrayList) {
        return new Z(i, arrayList);
    }

    public static AbstractC3876c0 a(com.android.tools.r8.graph.I i, ConcurrentLinkedQueue concurrentLinkedQueue) {
        C1714bm c1714bm = new C1714bm();
        Iterator it = concurrentLinkedQueue.iterator();
        while (it.hasNext()) {
            AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) it.next();
            c1714bm.a(abstractC0727q0.d, abstractC0727q0);
        }
        return new C3871b0(i, c1714bm.a());
    }
}
