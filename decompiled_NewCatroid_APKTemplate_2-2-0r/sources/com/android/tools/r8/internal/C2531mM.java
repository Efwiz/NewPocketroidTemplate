package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0772w0;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mM, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2531mM implements InterfaceC2606nM {
    static final /* synthetic */ boolean b = true;
    private final ArrayList a = new ArrayList();

    @Override // com.android.tools.r8.internal.InterfaceC2606nM
    public final int a(int i) {
        if (b || i >= 0) {
            return i + 1;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2606nM
    public final void a(com.android.tools.r8.graph.o5 o5Var, int i) {
        boolean z = b;
        if (!z && !o5Var.e().a1().B0()) {
            throw new AssertionError();
        }
        int size = o5Var.D().size();
        C0772w0 c0772w0P = o5Var.e().a1().P();
        if (!z) {
            C1525Yd.a(c0772w0P, i);
        }
        this.a.add(new C2457lM(c0772w0P, size, i));
    }

    @Override // com.android.tools.r8.internal.InterfaceC2606nM
    public final void a() {
        final HashMap map = new HashMap();
        this.a.forEach(new Consumer() { // from class: com.android.tools.r8.internal.mM$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2531mM.a(map, (C2457lM) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, C2457lM c2457lM) {
        boolean z = b;
        if (!z) {
            C1525Yd.a(c2457lM.a, c2457lM.c);
        }
        com.android.tools.r8.graph.J0 j0 = (com.android.tools.r8.graph.J0) map.computeIfAbsent(c2457lM, new Function() { // from class: com.android.tools.r8.internal.mM$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2531mM.a((C2457lM) obj);
            }
        });
        if (!z && j0.u0().B0() != c2457lM.c) {
            throw new AssertionError();
        }
        c2457lM.a.a(j0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.tools.r8.graph.J0 a(C2457lM c2457lM) {
        return new com.android.tools.r8.graph.K0(c2457lM.b, c2457lM.c);
    }
}
