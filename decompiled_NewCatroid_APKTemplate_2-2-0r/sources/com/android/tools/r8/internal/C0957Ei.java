package com.android.tools.r8.internal;

import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ei, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0957Ei extends AbstractC0984Fi {
    final /* synthetic */ Iterable[] b;

    C0957Ei(Iterable[] iterableArr) {
        this.b = iterableArr;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return new C3393xt(new C0926Di(this, this.b.length));
    }
}
