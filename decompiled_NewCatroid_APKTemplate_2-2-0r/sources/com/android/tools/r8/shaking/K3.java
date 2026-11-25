package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1639am;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class K3 extends G3 {
    private final String a;
    private final O3 b;
    static final /* synthetic */ boolean e = true;
    private static final G3 c = new K3("**");
    private static final G3 d = new K3("*");

    private K3(String str) {
        this(str, new O3.b(str));
    }

    @Override // com.android.tools.r8.shaking.G3
    protected final G3 a(C0722p1 c0722p1) {
        return new K3(this.a, this.b.f());
    }

    @Override // com.android.tools.r8.shaking.G3
    public final boolean b(C0767v2 c0767v2) {
        if (!c0767v2.O0()) {
            return false;
        }
        this.b.a(c0767v2.r0());
        return true;
    }

    @Override // com.android.tools.r8.shaking.G3
    protected final Iterable d() {
        return AbstractC1639am.c(this.b);
    }

    @Override // com.android.tools.r8.shaking.G3
    public final boolean equals(Object obj) {
        return (obj instanceof K3) && this.a.equals(((K3) obj).a);
    }

    @Override // com.android.tools.r8.shaking.G3
    public final int hashCode() {
        return this.a.hashCode();
    }

    @Override // com.android.tools.r8.shaking.G3
    public final String toString() {
        return this.a;
    }

    private K3(String str, O3 o3) {
        super();
        if (!e && !str.equals("*") && !str.equals("**")) {
            throw new AssertionError();
        }
        this.a = str;
        this.b = o3;
    }
}
