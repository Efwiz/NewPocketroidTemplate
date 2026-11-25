package com.android.tools.r8.internal;

import java.util.Comparator;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pG, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2767pG implements Comparator {
    public static final C2767pG a = new C2767pG();

    private C2767pG() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        Comparable comparable = (Comparable) obj;
        Comparable comparable2 = (Comparable) obj2;
        AbstractC0936Ds.c(comparable, "a");
        AbstractC0936Ds.c(comparable2, JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME);
        return comparable.compareTo(comparable2);
    }

    @Override // java.util.Comparator
    public final Comparator reversed() {
        return C1768cU.a;
    }
}
