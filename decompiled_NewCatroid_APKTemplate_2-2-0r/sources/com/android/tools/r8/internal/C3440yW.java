package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.startup.StartupMethodBuilder;
import com.android.tools.r8.utils.C3889e2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yW, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3440yW implements InterfaceC2125h1, StartupMethodBuilder {
    static final /* synthetic */ boolean c = true;
    private final C0722p1 a;
    private C0684j2 b;

    C3440yW(C0722p1 c0722p1) {
        this.a = c0722p1;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2125h1
    public final InterfaceC2125h1 a() {
        return this;
    }

    public final C3440yW b(C0684j2 c0684j2) {
        this.b = c0684j2;
        return this;
    }

    @Override // com.android.tools.r8.startup.StartupMethodBuilder
    public final StartupMethodBuilder setMethodReference(MethodReference methodReference) {
        if (!c && this.a == null) {
            throw new AssertionError();
        }
        this.b = C3889e2.a(methodReference, this.a);
        return this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2125h1
    public final InterfaceC2125h1 a(InterfaceC2125h1 interfaceC2125h1) {
        return this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2125h1
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final C3510zW d() {
        return new C3510zW(this.b);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2125h1
    public final InterfaceC2125h1 a(InterfaceC2125h1 interfaceC2125h1, Runnable runnable) {
        return this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2125h1
    public final InterfaceC2125h1 a(InterfaceC2197i1 interfaceC2197i1) {
        return this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2125h1
    public final InterfaceC2125h1 a(C0684j2 c0684j2) {
        this.b = c0684j2;
        return this;
    }
}
