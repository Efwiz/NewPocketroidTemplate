package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.utils.C3926l1;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bM, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1687bM extends AbstractC1915eM {
    private final C3926l1 g;
    private final C0684j2 h;
    private final boolean i;

    private C1687bM(int i, C0684j2 c0684j2, AbstractC1915eM abstractC1915eM, boolean z, boolean z2, C3926l1 c3926l1, C0684j2 c0684j3, boolean z3) {
        super(i, c0684j2, abstractC1915eM, z, z2);
        this.g = c3926l1;
        this.h = c0684j3;
        this.i = z3;
    }

    public static C1607aM x() {
        return new C1607aM();
    }

    @Override // com.android.tools.r8.internal.AbstractC1915eM, com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC1915eM
    public final AbstractC1915eM.a a() {
        C1607aM c1607aM = (C1607aM) ((C1607aM) new C1607aM().a(this.a)).a(this.b);
        c1607aM.c = this.c;
        C1607aM c1607aM2 = (C1607aM) c1607aM.c();
        c1607aM2.i = this.h;
        c1607aM2.j = this.i;
        c1607aM2.d = ((AbstractC1915eM) this).d;
        C1607aM c1607aM3 = (C1607aM) c1607aM2.c();
        c1607aM3.e = ((AbstractC1915eM) this).e;
        final C1607aM c1607aM4 = (C1607aM) c1607aM3.c();
        this.g.a(new BiConsumer() { // from class: com.android.tools.r8.internal.bM$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                c1607aM4.a(((Integer) obj).intValue(), (AbstractC1915eM) obj2);
            }
        });
        return c1607aM4;
    }

    @Override // com.android.tools.r8.internal.AbstractC1915eM
    public final int c() {
        return 4;
    }

    @Override // com.android.tools.r8.internal.AbstractC1915eM
    public final C0684j2 i() {
        return this.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC1915eM
    public final C3926l1 j() {
        return this.g;
    }

    @Override // com.android.tools.r8.internal.AbstractC1915eM
    public final boolean o() {
        return false;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.internal.bM$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                C1687bM.c(zVar);
            }
        };
    }

    @Override // com.android.tools.r8.internal.AbstractC1915eM
    public final boolean q() {
        return this.i;
    }

    public static void c(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.internal.bM$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar2) {
                AbstractC1915eM.m1379$$Nest$sma(zVar2);
            }
        }).b(new Predicate() { // from class: com.android.tools.r8.internal.bM$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AbstractC1915eM) obj).q();
            }
        }).c(new Function() { // from class: com.android.tools.r8.internal.bM$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((AbstractC1915eM) obj).i();
            }
        }).c(new Function() { // from class: com.android.tools.r8.internal.bM$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((AbstractC1915eM) obj).j();
            }
        });
    }
}
