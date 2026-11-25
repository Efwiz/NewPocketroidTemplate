package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class GA {
    static final /* synthetic */ boolean e = true;
    final C0684j2 a;
    final EnumC1953et b;
    final boolean c;
    private boolean d;

    GA(C0684j2 c0684j2, EnumC1953et enumC1953et, boolean z) {
        if (!e && c0684j2 == null) {
            throw new AssertionError();
        }
        this.a = c0684j2;
        this.b = enumC1953et;
        this.c = z;
    }

    public final C0684j2 a() {
        return this.a;
    }

    abstract com.android.tools.r8.graph.o5 a(InterfaceC1011Gi interfaceC1011Gi, Consumer consumer);

    public final void b(InterfaceC1011Gi interfaceC1011Gi, Consumer consumer) {
        if (this.d) {
            return;
        }
        a(interfaceC1011Gi, consumer);
        this.d = true;
    }

    public final EnumC1953et c() {
        return this.b;
    }

    public final boolean d() {
        return this.c;
    }

    public C0684j2 b() {
        return this.a;
    }
}
