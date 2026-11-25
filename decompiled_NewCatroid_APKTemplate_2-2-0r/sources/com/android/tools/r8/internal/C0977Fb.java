package com.android.tools.r8.internal;

import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Fb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0977Fb extends AbstractC3416yA implements InterfaceC1423Ui {
    final /* synthetic */ Iterable a;

    C0977Fb(List list) {
        this.a = list;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1423Ui
    public final Object a() {
        return this.a.iterator();
    }
}
