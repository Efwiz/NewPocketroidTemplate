package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ok, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2725ok extends OD implements G4, Serializable {
    final /* synthetic */ C3011sk a;

    private C2725ok(C3011sk c3011sk) {
        this.a = c3011sk;
    }

    @Override // com.android.tools.r8.internal.G4
    public final Object a(Object obj, Object obj2) {
        return C3011sk.a(this.a, obj, obj2, true);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsKey(Object obj) {
        return this.a.containsValue(obj);
    }

    @Override // com.android.tools.r8.internal.OD
    final Iterator e() {
        return new C2484lk(this);
    }

    @Override // java.util.Map
    public final void forEach(final BiConsumer biConsumer) {
        int i = AbstractC3123uM.a;
        biConsumer.getClass();
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.ok$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                biConsumer.accept(obj2, obj);
            }
        });
    }

    @Override // com.android.tools.r8.internal.G4
    public final G4 g() {
        return this.a;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object get(Object obj) {
        return RD.a(this.a.b(AbstractC0900Ck.a(obj), obj));
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set keySet() {
        return new C2636nk(this);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object put(Object obj, Object obj2) {
        return C3011sk.a(this.a, obj, obj2, false);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object remove(Object obj) {
        C2328jk c2328jkB = this.a.b(AbstractC0900Ck.a(obj), obj);
        if (c2328jkB == null) {
            return null;
        }
        this.a.a(c2328jkB);
        c2328jkB.h = null;
        c2328jkB.g = null;
        return c2328jkB.a;
    }

    @Override // java.util.Map
    public final void replaceAll(BiFunction biFunction) {
        int i = AbstractC3123uM.a;
        biFunction.getClass();
        this.a.clear();
        for (C2328jk c2328jk = this.a.c; c2328jk != null; c2328jk = c2328jk.g) {
            Object obj = c2328jk.b;
            put(obj, biFunction.apply(obj, c2328jk.a));
        }
    }

    @Override // com.android.tools.r8.internal.OD, java.util.AbstractMap, java.util.Map
    public final int size() {
        return this.a.e;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Collection values() {
        return this.a.keySet();
    }

    /* synthetic */ C2725ok(C3011sk c3011sk, int i) {
        this(c3011sk);
    }

    @Override // java.util.AbstractMap, java.util.Map, com.android.tools.r8.internal.G4
    public final Set values() {
        C3011sk c3011sk = this.a;
        c3011sk.getClass();
        return new C2942rk(c3011sk);
    }
}
