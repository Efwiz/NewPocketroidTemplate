package com.android.tools.r8.internal;

import java.util.HashSet;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C9 {
    private final String a;
    private final HashSet b = new HashSet();

    C9(String str) {
        this.a = str;
    }

    final void a(String str) {
        if (!this.b.add(str)) {
            throw new IllegalArgumentException(this.a + " '" + str + "' already declared");
        }
    }
}
