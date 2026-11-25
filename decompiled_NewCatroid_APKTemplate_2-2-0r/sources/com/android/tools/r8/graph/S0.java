package com.android.tools.r8.graph;

import java.util.Arrays;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class S0 extends AbstractC0594a1 {
    public final B2[] a;

    public S0(B2[] b2Arr) {
        this.a = b2Arr;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public final void a(com.android.tools.r8.dex.V v) {
        v.a(this);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return (obj instanceof S0) && Arrays.equals(((S0) obj).a, this.a);
    }

    public final int hashCode() {
        return Arrays.hashCode(this.a);
    }

    public final String toString() {
        return "EncodedArray " + Arrays.toString(this.a);
    }
}
