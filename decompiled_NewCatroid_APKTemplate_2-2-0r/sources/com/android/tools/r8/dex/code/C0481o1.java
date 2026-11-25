package com.android.tools.r8.dex.code;

import com.android.tools.r8.internal.EnumC1076Il;
import com.android.tools.r8.internal.EnumC1968f20;

/* JADX INFO: renamed from: com.android.tools.r8.dex.code.o1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0481o1 extends N0 {
    C0481o1(int i, InterfaceC0409a interfaceC0409a) {
        super(i, interfaceC0409a);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "if-ltz";
    }

    @Override // com.android.tools.r8.dex.code.N0
    protected final EnumC1968f20 N() {
        return EnumC1968f20.INT;
    }

    @Override // com.android.tools.r8.dex.code.N0
    public final EnumC1076Il O() {
        return EnumC1076Il.LT;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "IfLtz";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 58;
    }

    public C0481o1(int i, int i2) {
        super(i, i2);
    }
}
