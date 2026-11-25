package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ix, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2267ix extends AbstractC2190hx {
    static final /* synthetic */ boolean b = true;
    private final List a;

    private C2267ix(ArrayList arrayList) {
        boolean z = b;
        if (!z && arrayList == null) {
            throw new AssertionError();
        }
        if (!z && arrayList.isEmpty()) {
            throw new AssertionError();
        }
        this.a = arrayList;
    }

    @Override // com.android.tools.r8.internal.AbstractC2190hx
    public final void a(Consumer consumer) {
        this.a.forEach(consumer);
    }

    @Override // com.android.tools.r8.internal.AbstractC2190hx
    public final boolean c() {
        return false;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C2267ix.class != obj.getClass()) {
            return false;
        }
        return this.a.equals(((C2267ix) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return this.a.toString();
    }
}
