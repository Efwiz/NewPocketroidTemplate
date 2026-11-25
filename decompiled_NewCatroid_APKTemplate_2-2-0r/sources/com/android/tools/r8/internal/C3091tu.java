package com.android.tools.r8.internal;

import java.io.Serializable;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3091tu extends com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.l {
    private int c;
    private C1280Pu d;
    final /* synthetic */ C3317wu e;
    final /* synthetic */ Q20 f;
    final /* synthetic */ EO g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C3091tu(C3317wu c3317wu, Q20 q20, EO eo) {
        super(0);
        this.e = c3317wu;
        this.f = q20;
        this.g = eo;
        Object objA = FO.m().a(AbstractC1543Yu.e);
        AbstractC0936Ds.b(objA, "getDefaultInstance().get…ension(JvmProtoBuf.flags)");
        this.c = ((Number) objA).intValue();
    }

    private final C1280Pu c() {
        C1280Pu c1280Pu = this.d;
        if (c1280Pu != null) {
            return c1280Pu;
        }
        C1280Pu c1280PuS = C1307Qu.s();
        this.d = c1280PuS;
        return c1280PuS;
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.l
    public final void a(int i, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.e eVar, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar2) {
        this.c = i;
        if (eVar != null) {
            C1280Pu c1280PuC = c();
            C1115Ju c1115JuM = C1143Ku.m();
            Q20 q20 = this.f;
            c1115JuM.b(q20.a(eVar.c()));
            c1115JuM.a(q20.a(eVar.b()));
            C1143Ku c1143KuG = c1115JuM.g();
            if (!c1143KuG.a()) {
                throw new C1659b10();
            }
            c1280PuC.a(c1143KuG);
        }
        if (jVar != null) {
            c().b(C3317wu.a(this.e, jVar, this.f));
        }
        if (jVar2 != null) {
            c().c(C3317wu.a(this.e, jVar2, this.f));
        }
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.l
    public final void b(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar) {
        if (jVar == null) {
            return;
        }
        c().a(C3317wu.a(this.e, jVar, this.f));
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.l
    public final void b() {
        int i = this.c;
        FO foM = FO.m();
        C1012Gj c1012Gj = AbstractC1543Yu.e;
        Integer num = (Integer) foM.a(c1012Gj);
        if (num == null || i != num.intValue()) {
            this.g.a(c1012Gj, Integer.valueOf(this.c));
        }
        if (this.d != null) {
            EO eo = this.g;
            C1012Gj c1012Gj2 = AbstractC1543Yu.d;
            C1307Qu c1307QuG = c().g();
            if (!c1307QuG.a()) {
                throw new C1659b10();
            }
            eo.a(c1012Gj2, (Serializable) c1307QuG);
        }
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.l
    public final void a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar) {
        if (jVar == null) {
            return;
        }
        c().d(C3317wu.a(this.e, jVar, this.f));
    }
}
