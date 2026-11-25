package com.android.tools.r8.internal;

import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class VA extends com.android.tools.r8.utils.N {
    private final C2409kl b;

    public VA(C2409kl c2409kl) {
        this.b = c2409kl;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C0827Af e() {
        return new C0827Af(this.b, 2);
    }

    @Override // com.android.tools.r8.utils.N
    /* JADX INFO: renamed from: d, reason: merged with bridge method [inline-methods] */
    public final C0827Af a() {
        return (C0827Af) a(new Supplier() { // from class: com.android.tools.r8.internal.VA$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.e();
            }
        });
    }
}
