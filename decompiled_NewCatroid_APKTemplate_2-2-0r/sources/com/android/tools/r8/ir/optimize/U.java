package com.android.tools.r8.ir.optimize;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public enum U {
    NEVER(1),
    SAMECLASS(2),
    SAMENEST(4),
    PACKAGE(8),
    SUBCLASS(16),
    ALWAYS(32);

    int a;

    U(int i) {
        this.a = i;
    }

    final boolean c(int i) {
        return (i & this.a) != 0;
    }
}
