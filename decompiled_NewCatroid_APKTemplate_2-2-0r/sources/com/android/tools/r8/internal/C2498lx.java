package com.android.tools.r8.internal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lx, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2498lx {
    static final /* synthetic */ boolean d = true;
    final C2574mx a;
    final HashSet b;
    final HashMap c;

    private C2498lx(C2574mx c2574mx) {
        this.b = new HashSet();
        this.c = new HashMap();
        this.a = c2574mx;
    }

    public final void a(C2814px c2814px) {
        if (!d && !c2814px.b().c()) {
            throw new AssertionError();
        }
        ((Set) this.c.computeIfAbsent(c2814px.c(), new Function() { // from class: com.android.tools.r8.internal.lx$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2498lx.a((C1651ax) obj);
            }
        })).add(c2814px.b().a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set a(C1651ax c1651ax) {
        return new HashSet();
    }
}
