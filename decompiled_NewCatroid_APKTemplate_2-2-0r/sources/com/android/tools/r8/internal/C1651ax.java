package com.android.tools.r8.internal;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ax, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1651ax {
    private static final C1651ax b = new C1651ax(AbstractC1048Hm.k());
    private final AbstractC1048Hm a;

    /* JADX INFO: renamed from: com.android.tools.r8.internal.ax$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public enum a {
        SHRINKING,
        OPTIMIZING,
        OBFUSCATING,
        ACCESS_MODIFICATION,
        ANNOTATION_REMOVAL
    }

    private C1651ax(AbstractC1048Hm abstractC1048Hm) {
        this.a = abstractC1048Hm;
    }

    public static C1571Zw a() {
        return new C1571Zw(true);
    }

    public static C1571Zw b() {
        return new C1571Zw(false);
    }

    public static C1651ax d() {
        return b;
    }

    public final boolean c() {
        return this.a.isEmpty();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C1651ax.class != obj.getClass()) {
            return false;
        }
        return this.a.equals(((C1651ax) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return "KeepOptions{" + ((String) this.a.stream().map(new Function() { // from class: com.android.tools.r8.internal.ax$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Objects.toString((C1651ax.a) obj);
            }
        }).collect(Collectors.joining(", "))) + "}";
    }

    public static C1651ax a(a... aVarArr) {
        C1571Zw c1571ZwA = a();
        c1571ZwA.b.addAll(Arrays.asList(aVarArr));
        return c1571ZwA.a();
    }

    public static C1651ax b(a... aVarArr) {
        C1571Zw c1571ZwB = b();
        c1571ZwB.b.addAll(Arrays.asList(aVarArr));
        return c1571ZwB.a();
    }

    public final boolean a(a aVar) {
        return this.a.contains(aVar);
    }
}
