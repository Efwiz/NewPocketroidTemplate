package com.android.tools.r8.dex.code;

import com.android.tools.r8.internal.EnumC1076Il;
import com.android.tools.r8.internal.EnumC1968f20;

/* JADX INFO: renamed from: com.android.tools.r8.dex.code.q1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0491q1 extends N0 {
    C0491q1(int i, InterfaceC0409a interfaceC0409a) {
        super(i, interfaceC0409a);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "if-nez";
    }

    @Override // com.android.tools.r8.dex.code.N0
    protected final EnumC1968f20 N() {
        return EnumC1968f20.INT_OR_FLOAT_OR_OBJECT;
    }

    @Override // com.android.tools.r8.dex.code.N0
    public final EnumC1076Il O() {
        return EnumC1076Il.NE;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "IfNez";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 57;
    }

    public C0491q1(int i, int i2) {
        super(i, i2);
    }
}
