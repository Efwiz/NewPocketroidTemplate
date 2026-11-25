package com.android.tools.r8.internal;

import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.en, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1947en implements Iterable, InterfaceC2032fv {
    private final InterfaceC1423Ui a;

    public C1947en(InterfaceC1423Ui interfaceC1423Ui) {
        this.a = interfaceC1423Ui;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return new C2024fn((Iterator) this.a.a());
    }
}
