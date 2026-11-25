package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Jk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1105Jk implements InterfaceC2765pE {
    static final /* synthetic */ boolean b = true;
    private final L4 a;

    public C1105Jk(L4 l4) {
        this.a = l4;
    }

    public static C1105Jk a() {
        return new C1105Jk(new C1371Tf());
    }

    public C0767v2 b(C0767v2 c0767v2) {
        return (C0767v2) this.a.getOrDefault(c0767v2, c0767v2);
    }

    public Set<C0767v2> c() {
        return this.a.keySet();
    }

    public Set<C0767v2> d() {
        return this.a.mo1078values();
    }

    public final boolean e() {
        return this.a.isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C1105Jk c1105Jk, final C1073Ik c1073Ik, Set set, C0767v2 c0767v2) {
        final C0767v2 c0767v2B = c1105Jk.b(c0767v2);
        set.forEach(new Consumer() { // from class: com.android.tools.r8.internal.Jk$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1105Jk.a(c1073Ik, c0767v2B, (C0767v2) obj);
            }
        });
    }

    final Map b() {
        return this.a.e();
    }

    public Set<C0767v2> c(C0767v2 c0767v2) {
        return this.a.a(c0767v2);
    }

    public boolean d(C0767v2 c0767v2) {
        return a(c0767v2) || e(c0767v2);
    }

    public boolean e(C0767v2 c0767v2) {
        return this.a.containsValue(c0767v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(C1073Ik c1073Ik, C0767v2 c0767v2, C0767v2 c0767v3) {
        if (!C1073Ik.b && c1073Ik.a.containsKey(c0767v3)) {
            throw new AssertionError();
        }
        c1073Ik.a.a(c0767v3, c0767v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final C1073Ik c1073Ik, Set set, final C0767v2 c0767v2) {
        set.forEach(new Consumer() { // from class: com.android.tools.r8.internal.Jk$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1105Jk.b(c1073Ik, c0767v2, (C0767v2) obj);
            }
        });
    }

    public void a(BiConsumer<Set<C0767v2>, C0767v2> biConsumer) {
        this.a.a((BiConsumer) biConsumer);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2765pE
    public boolean a(C0767v2 c0767v2) {
        return this.a.containsKey(c0767v2);
    }

    public final C1105Jk a(final C1105Jk c1105Jk) {
        if (this.a.isEmpty()) {
            return c1105Jk;
        }
        if (c1105Jk.a.isEmpty()) {
            return this;
        }
        final C1073Ik c1073Ik = new C1073Ik();
        a(new BiConsumer() { // from class: com.android.tools.r8.internal.Jk$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1105Jk.a(this.f$0, c1073Ik, (Set) obj, (C0767v2) obj2);
            }
        });
        c1105Jk.a(new BiConsumer() { // from class: com.android.tools.r8.internal.Jk$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1105Jk.a(c1073Ik, (Set) obj, (C0767v2) obj2);
            }
        });
        return c1073Ik.a();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2765pE
    public final void a(C0702m c0702m) {
        for (C0767v2 c0767v2 : this.a.keySet()) {
            if (!b && !((C3733i) c0702m.f()).p(c0767v2)) {
                throw new AssertionError("Expected horizontally merged lambda class `" + c0767v2.r0() + "` to be absent");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C1073Ik c1073Ik, C0767v2 c0767v2, C0767v2 c0767v3) {
        if (!C1073Ik.b && c1073Ik.a.containsKey(c0767v3)) {
            throw new AssertionError();
        }
        c1073Ik.a.a(c0767v3, c0767v2);
    }
}
