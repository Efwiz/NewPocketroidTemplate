package com.android.tools.r8.graph;

import com.android.tools.r8.internal.JA;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.graph.g4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0662g4 implements InterfaceC0707m4 {
    static final /* synthetic */ boolean b = true;
    private final AbstractC0748t0 a;

    public C0662g4(JA ja, AbstractC0748t0 abstractC0748t0) {
        if (!b && ja == null) {
            throw new AssertionError();
        }
        this.a = abstractC0748t0;
    }

    @Override // com.android.tools.r8.graph.InterfaceC0707m4
    public final void a(Consumer consumer, Consumer consumer2) {
        consumer2.accept(this);
    }

    public final AbstractC0748t0 b() {
        return this.a;
    }

    @Override // com.android.tools.r8.graph.InterfaceC0707m4
    public final boolean n() {
        return true;
    }

    @Override // com.android.tools.r8.graph.InterfaceC0707m4
    public final C0662g4 o() {
        return this;
    }
}
