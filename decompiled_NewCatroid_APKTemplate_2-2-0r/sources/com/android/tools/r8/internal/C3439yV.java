package com.android.tools.r8.internal;

import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yV, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3439yV extends AbstractC1214Nl {
    final transient Object e;
    final transient Object f;
    private final transient AbstractC1214Nl g;
    private transient AbstractC1214Nl h;

    C3439yV(Object obj, Object obj2) {
        AbstractC2625nb.a(obj, obj2);
        this.e = obj;
        this.f = obj2;
        this.g = null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm, java.util.Map
    public final boolean containsKey(Object obj) {
        return this.e.equals(obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm, java.util.Map
    public final boolean containsValue(Object obj) {
        return this.f.equals(obj);
    }

    @Override // java.util.Map
    public final void forEach(BiConsumer biConsumer) {
        int i = AbstractC3123uM.a;
        biConsumer.getClass();
        biConsumer.accept(this.e, this.f);
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm, java.util.Map
    public final Object get(Object obj) {
        if (this.e.equals(obj)) {
            return this.f;
        }
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final AbstractC1048Hm k() {
        C1325Rl c1325Rl = new C1325Rl(this.e, this.f);
        int i = AbstractC1048Hm.c;
        return new AV(c1325Rl);
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final AbstractC1048Hm l() {
        Object obj = this.e;
        int i = AbstractC1048Hm.c;
        return new AV(obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final boolean o() {
        return false;
    }

    @Override // java.util.Map
    public final int size() {
        return 1;
    }

    @Override // com.android.tools.r8.internal.AbstractC1214Nl
    /* JADX INFO: renamed from: u */
    public final AbstractC1214Nl g() {
        AbstractC1214Nl abstractC1214Nl = this.g;
        if (abstractC1214Nl != null) {
            return abstractC1214Nl;
        }
        AbstractC1214Nl abstractC1214Nl2 = this.h;
        if (abstractC1214Nl2 != null) {
            return abstractC1214Nl2;
        }
        C3439yV c3439yV = new C3439yV(this.f, this.e, this);
        this.h = c3439yV;
        return c3439yV;
    }

    private C3439yV(Object obj, Object obj2, AbstractC1214Nl abstractC1214Nl) {
        this.e = obj;
        this.f = obj2;
        this.g = abstractC1214Nl;
    }
}
