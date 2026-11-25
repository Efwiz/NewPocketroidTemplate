package com.android.tools.r8.internal;

import java.util.function.BiConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class KS extends AbstractC1214Nl {
    final /* synthetic */ LS e;

    private KS(LS ls) {
        this.e = ls;
    }

    @Override // java.util.Map
    public final void forEach(final BiConsumer biConsumer) {
        int i = AbstractC3123uM.a;
        biConsumer.getClass();
        this.e.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.KS$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                biConsumer.accept(obj2, obj);
            }
        });
    }

    @Override // com.android.tools.r8.internal.AbstractC1214Nl, com.android.tools.r8.internal.G4
    public final G4 g() {
        return this.e;
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm, java.util.Map
    public final Object get(Object obj) {
        if (obj != null && this.e.f != null) {
            for (C2410km c2410kmC = this.e.f[AbstractC0900Ck.a(obj.hashCode()) & this.e.h]; c2410kmC != null; c2410kmC = c2410kmC.c()) {
                if (obj.equals(c2410kmC.b)) {
                    return c2410kmC.a;
                }
            }
        }
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final AbstractC1048Hm k() {
        return new JS(this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final AbstractC1048Hm l() {
        return new C2638nm(this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final boolean o() {
        return false;
    }

    @Override // java.util.Map
    public final int size() {
        return this.e.g.length;
    }

    @Override // com.android.tools.r8.internal.AbstractC1214Nl
    /* JADX INFO: renamed from: u */
    public final AbstractC1214Nl g() {
        return this.e;
    }

    /* synthetic */ KS(LS ls, int i) {
        this(ls);
    }
}
