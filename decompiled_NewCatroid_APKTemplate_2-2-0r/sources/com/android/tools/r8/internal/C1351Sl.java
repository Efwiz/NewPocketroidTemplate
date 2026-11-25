package com.android.tools.r8.internal;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Sl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1351Sl extends AbstractC2107gm {
    private final transient EnumMap e;

    private C1351Sl(EnumMap enumMap) {
        this.e = enumMap;
        AbstractC3123uM.a(!enumMap.isEmpty());
    }

    static AbstractC2178hm a(EnumMap enumMap) {
        int size = enumMap.size();
        if (size == 0) {
            return PS.h;
        }
        if (size != 1) {
            return new C1351Sl(enumMap);
        }
        Map.Entry entry = (Map.Entry) AbstractC2734ot.b(enumMap.entrySet());
        return new C3439yV((Enum) entry.getKey(), entry.getValue());
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm, java.util.Map
    public final boolean containsKey(Object obj) {
        return this.e.containsKey(obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm, java.util.Map
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof C1351Sl) {
            obj = ((C1351Sl) obj).e;
        }
        return this.e.equals(obj);
    }

    @Override // java.util.Map
    public final void forEach(BiConsumer biConsumer) {
        this.e.forEach(biConsumer);
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm, java.util.Map
    public final Object get(Object obj) {
        return this.e.get(obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final boolean o() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final AbstractC2963s10 p() {
        Iterator it = this.e.keySet().iterator();
        int i = AbstractC3123uM.a;
        it.getClass();
        return it instanceof AbstractC2963s10 ? (AbstractC2963s10) it : new C2810pt(it);
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final Spliterator r() {
        return this.e.keySet().spliterator();
    }

    @Override // java.util.Map
    public final int size() {
        return this.e.size();
    }

    @Override // com.android.tools.r8.internal.AbstractC2107gm
    final AbstractC2963s10 u() {
        return new ID(this.e.entrySet().iterator());
    }
}
