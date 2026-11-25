package com.android.tools.r8.internal;

import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.b9, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1670b9 extends AbstractC1744c9 {
    static final C1670b9 b = new C1670b9();

    private C1670b9() {
        super("CharMatcher.any()");
    }

    @Override // com.android.tools.r8.internal.AbstractC1893e9
    public final boolean a() {
        return true;
    }

    @Override // java.util.function.Predicate
    public final Predicate negate() {
        return C1820d9.b;
    }
}
