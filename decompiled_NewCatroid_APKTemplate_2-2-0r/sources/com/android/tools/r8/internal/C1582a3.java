package com.android.tools.r8.internal;

import java.util.Collection;

/* JADX INFO: renamed from: com.android.tools.r8.internal.a3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1582a3 extends AbstractC1663b3 {
    transient int g;

    private C1582a3() {
        this(0);
    }

    public static C1582a3 j() {
        return new C1582a3();
    }

    @Override // com.android.tools.r8.internal.VF
    public final boolean a(Object obj, Object obj2) {
        Collection collection = (Collection) b().get(obj);
        return collection != null && collection.contains(obj2);
    }

    public final int hashCode() {
        return b().hashCode();
    }

    @Override // com.android.tools.r8.internal.VF
    public final boolean remove(Object obj, Object obj2) {
        Collection collection = (Collection) super.b().get(obj);
        return collection != null && collection.remove(obj2);
    }

    public final String toString() {
        return b().toString();
    }

    private C1582a3(int i) {
        super(TL.a());
        AbstractC2625nb.a(3, "expectedValuesPerKey");
        this.g = 3;
    }
}
