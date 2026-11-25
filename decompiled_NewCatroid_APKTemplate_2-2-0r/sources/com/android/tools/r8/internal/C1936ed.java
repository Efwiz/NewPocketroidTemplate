package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0594a1;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.Y$$ExternalSyntheticLambda1;
import com.android.tools.r8.utils.C3932m1;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ed, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1936ed extends VY {
    static final /* synthetic */ boolean n = true;
    private final com.android.tools.r8.graph.Y0 l;
    private final InterfaceC2415kr m;

    public C1936ed(InterfaceC2415kr interfaceC2415kr, C0684j2 c0684j2, com.android.tools.r8.graph.Y0 y0, AbstractC1915eM abstractC1915eM, C0684j2 c0684j3) {
        super(c0684j2.e, c0684j2, abstractC1915eM, c0684j3);
        this.m = interfaceC2415kr;
        this.l = y0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(int i, C2102gl c2102gl) {
        c2102gl.b(i, g(), this.l);
    }

    final void a(final C0684j2 c0684j2) {
        a(new Consumer() { // from class: com.android.tools.r8.internal.ed$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1936ed.a(c0684j2, (C2102gl) obj);
            }
        });
    }

    final void i(final int i) {
        if (!n && this.l == null) {
            throw new AssertionError();
        }
        final int iA = a(EnumC1812d20.INT);
        a(new Consumer() { // from class: com.android.tools.r8.internal.ed$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1936ed.b(iA, i, (C2102gl) obj);
            }
        });
        j(iA);
    }

    final void j(final int i) {
        if (!n && this.l == null) {
            throw new AssertionError();
        }
        a(new Consumer() { // from class: com.android.tools.r8.internal.ed$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(i, (C2102gl) obj);
            }
        });
    }

    protected final void k() {
        int size = this.m.size();
        final int iH = h(((C0684j2) this.m.values().iterator().next()).F0());
        if (this.l != null) {
            j(iH);
        }
        int i = size - 1;
        final int[] iArr = new int[i];
        final int[] iArr2 = new int[i];
        final C3932m1 c3932m1 = new C3932m1();
        final int iH2 = h();
        a(new Consumer() { // from class: com.android.tools.r8.internal.ed$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1936ed.a(iH, iArr, c3932m1, iArr2, (C2102gl) obj);
            }
        }, new Predicate() { // from class: com.android.tools.r8.internal.ed$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a(iH2, c3932m1, iArr2, (C2102gl) obj);
            }
        });
        YJ it = this.m.c().iterator();
        int i2 = 0;
        while (it.hasNext()) {
            InterfaceC0934Dq interfaceC0934Dq = (InterfaceC0934Dq) it.next();
            int iA = interfaceC0934Dq.a();
            C0684j2 c0684j2 = (C0684j2) interfaceC0934Dq.getValue();
            if (i2 == 0) {
                c3932m1.e(i());
            } else {
                int i3 = i2 - 1;
                iArr[i3] = iA;
                iArr2[i3] = i();
            }
            a(c0684j2);
            a(new Y$$ExternalSyntheticLambda1(), VY.j);
            i2++;
        }
    }

    protected final void l() {
        InterfaceC0934Dq interfaceC0934Dq = (InterfaceC0934Dq) this.m.c().first();
        if (this.l != null) {
            i(interfaceC0934Dq.a());
        }
        a((C0684j2) interfaceC0934Dq.getValue());
        a(new Y$$ExternalSyntheticLambda1(), VY.j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0684j2 c0684j2, C2102gl c2102gl) {
        ArrayList arrayList = new ArrayList(c0684j2.F0() + 1);
        arrayList.add(c2102gl.k());
        for (int i = 0; i < c0684j2.F0(); i++) {
            arrayList.add((S10) c2102gl.e().get(i));
        }
        c2102gl.a(EnumC1953et.c, (AbstractC0594a1) c0684j2, c0684j2.h, arrayList, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(int i, int i2, C2102gl c2102gl) {
        c2102gl.getClass();
        c2102gl.a(new C0978Fc(c2102gl.a(i, AbstractC3472z00.k(), 1), i2));
    }

    @Override // com.android.tools.r8.internal.VY
    protected final void j() {
        if (this.m.size() > 1) {
            k();
        } else {
            l();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(int i, int[] iArr, C3932m1 c3932m1, int[] iArr2, C2102gl c2102gl) {
        c2102gl.a(i, iArr, c3932m1.a(), iArr2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(int i, C3932m1 c3932m1, int[] iArr, C2102gl c2102gl) {
        int iA = c3932m1.a();
        for (int i2 : iArr) {
            c2102gl.d(i, i2);
        }
        c2102gl.d(i, iA);
        return true;
    }
}
