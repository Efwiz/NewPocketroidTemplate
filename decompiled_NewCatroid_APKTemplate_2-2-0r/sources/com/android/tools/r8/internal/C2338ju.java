package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ju, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2338ju extends com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.f implements InterfaceC2887qy {
    private com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j c;
    private String d;

    public C2338ju() {
        super(0);
    }

    public final void a(InterfaceC2653ny interfaceC2653ny) {
        InterfaceC2956ry interfaceC2956ry = (InterfaceC2956ry) interfaceC2653ny;
        if (!(interfaceC2956ry instanceof com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.f)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.f fVar = (com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.f) interfaceC2956ry;
        fVar.a(this.c);
        String str = this.d;
        if (str != null) {
            fVar.a(str);
        }
        fVar.a();
    }

    public final void b(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar) {
        this.c = jVar;
    }

    public final com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j c() {
        return this.c;
    }

    public final String b() {
        return this.d;
    }

    public final void b(String str) {
        this.d = str;
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.f
    public final void a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar) {
        this.c = jVar;
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.f
    public final void a(String str) {
        AbstractC0936Ds.c(str, "internalName");
        this.d = str;
    }
}
