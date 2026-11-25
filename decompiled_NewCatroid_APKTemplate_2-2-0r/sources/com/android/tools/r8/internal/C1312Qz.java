package com.android.tools.r8.internal;

import com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.KotlinClassMetadata;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Qz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1312Qz implements InterfaceC3115uE {
    private static final String[] h = new String[0];
    private static final int[] i = new int[0];
    private final int a;
    private final int[] b;
    private final String[] c;
    private final String[] d;
    private final int e;
    private final String f;
    private final String g;

    public C1312Qz(Integer num, int[] iArr, String[] strArr, String[] strArr2, String str, String str2, Integer num2) {
        this.a = num == null ? 1 : num.intValue();
        this.b = iArr == null ? i : iArr;
        this.c = strArr == null ? h : strArr;
        this.d = strArr2 == null ? h : strArr2;
        this.f = str == null ? "" : str;
        this.g = str2 == null ? "" : str2;
        this.e = num2 == null ? 0 : num2.intValue();
    }

    public static C1312Qz a(KotlinClassMetadata kotlinClassMetadata) {
        InterfaceC3115uE interfaceC3115uEA = kotlinClassMetadata.a();
        return new C1312Qz(Integer.valueOf(interfaceC3115uEA.k()), interfaceC3115uEA.mv(), interfaceC3115uEA.d1(), interfaceC3115uEA.d2(), interfaceC3115uEA.xs(), interfaceC3115uEA.pn(), Integer.valueOf(interfaceC3115uEA.xi()));
    }

    @Override // java.lang.annotation.Annotation
    public final Class annotationType() {
        throw new C3405y10("Should never be called");
    }

    public String[] b() {
        return this.d;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3115uE
    public final int[] bv() {
        throw new C3405y10("Field is deprecated and should not be used");
    }

    public int c() {
        return this.a;
    }

    public String d() {
        return this.g;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3115uE
    public final String[] d1() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3115uE
    public final String[] d2() {
        return this.d;
    }

    @Override // java.lang.annotation.Annotation
    public final boolean equals(Object obj) {
        throw new C3405y10();
    }

    @Override // java.lang.annotation.Annotation
    public final int hashCode() {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.InterfaceC3115uE
    public final int k() {
        return this.a;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3115uE
    public final int[] mv() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3115uE
    public final String pn() {
        return this.g;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3115uE
    public final int xi() {
        return this.e;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3115uE
    public final String xs() {
        return this.f;
    }

    public String[] a() {
        return this.c;
    }
}
