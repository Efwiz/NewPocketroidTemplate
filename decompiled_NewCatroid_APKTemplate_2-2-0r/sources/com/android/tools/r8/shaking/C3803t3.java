package com.android.tools.r8.shaking;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.t3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3803t3 extends AbstractC3815v3 {
    private final String c;
    private final List d;

    C3803t3(N2.a aVar) {
        super();
        this.c = aVar.a;
        this.d = aVar.b;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3815v3
    public final boolean a(String str) {
        boolean zA = AbstractC3815v3.a(this.c, 0, str, 0, this.d, 0);
        if (!zA) {
            this.d.forEach(new L3$$ExternalSyntheticLambda0());
        }
        return zA;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.tools.r8.shaking.AbstractC3815v3
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public final C3803t3 b() {
        return new C3803t3(new N2.a(this.c, (List) this.d.stream().map(new Function() { // from class: com.android.tools.r8.shaking.t3$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((O3) obj).f();
            }
        }).collect(Collectors.toList())));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C3803t3) && this.c.equals(((C3803t3) obj).c);
    }

    public final int hashCode() {
        return this.c.hashCode();
    }

    public final String toString() {
        return this.c;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3815v3
    protected final Iterable a() {
        return this.d;
    }
}
