package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0767v2;
import java.util.Comparator;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.m3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2511m3 extends AbstractC2069gN {
    public C2511m3(C2434l3 c2434l3) {
        super(c2434l3);
    }

    @Override // com.android.tools.r8.internal.AbstractC2069gN
    public final AbstractC2069gN a() {
        return new C2511m3((C2434l3) this.a);
    }

    @Override // com.android.tools.r8.internal.AbstractC2069gN
    public final InterfaceC1965f1 b(C0767v2 c0767v2) {
        return C2966s3.d().a(c0767v2);
    }

    @Override // com.android.tools.r8.internal.AbstractC2069gN
    public final InterfaceC1809d1 c() {
        return C2434l3.a();
    }

    @Override // com.android.tools.r8.internal.AbstractC2069gN
    public final Comparator d() {
        return Comparator.comparing(new Function() { // from class: com.android.tools.r8.internal.m3$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((InterfaceC2275j1) obj).a();
            }
        });
    }

    @Override // com.android.tools.r8.internal.AbstractC2069gN
    public final AbstractC2069gN f() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2069gN
    public final InterfaceC2125h1 a(C0684j2 c0684j2) {
        return A3.d().b(c0684j2);
    }
}
