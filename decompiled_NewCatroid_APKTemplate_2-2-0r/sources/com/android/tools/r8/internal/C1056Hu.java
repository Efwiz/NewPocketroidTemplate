package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Hu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1056Hu extends com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.l implements InterfaceC0848Ay {
    private int c;
    private com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.e d;
    private com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j e;
    private com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j f;
    private com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j g;
    private com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j h;

    public C1056Hu() {
        super(0);
    }

    public final void a(InterfaceC2653ny interfaceC2653ny) {
        InterfaceC0874By interfaceC0874By = (InterfaceC0874By) interfaceC2653ny;
        if (!(interfaceC0874By instanceof com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.l)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.l lVar = (com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.l) interfaceC0874By;
        lVar.a(this.c, this.d, this.e, this.f);
        lVar.a(this.g);
        lVar.b(this.h);
        lVar.b();
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.l
    public final void b(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar) {
        this.h = jVar;
    }

    public final com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.e c() {
        return this.d;
    }

    public final com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j d() {
        return this.e;
    }

    public final int e() {
        return this.c;
    }

    public final com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j f() {
        return this.f;
    }

    public final com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j g() {
        return this.g;
    }

    public final com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j h() {
        return this.h;
    }

    public final void c(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar) {
        this.e = jVar;
    }

    public final void d(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar) {
        this.f = jVar;
    }

    public final void e(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar) {
        this.g = jVar;
    }

    public final void f(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar) {
        this.h = jVar;
    }

    public final void a(int i) {
        this.c = i;
    }

    public final void a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.e eVar) {
        this.d = eVar;
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.l
    public final void a(int i, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.e eVar, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar2) {
        this.c = i;
        this.d = eVar;
        this.e = jVar;
        this.f = jVar2;
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.l
    public final void a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar) {
        this.g = jVar;
    }
}
