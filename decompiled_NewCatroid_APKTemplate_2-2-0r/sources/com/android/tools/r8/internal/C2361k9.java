package com.android.tools.r8.internal;

import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: renamed from: com.android.tools.r8.internal.k9, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2361k9 extends C2994sV {
    private static final C2361k9 e = new C2361k9();

    static C2361k9 S() {
        return e;
    }

    @Override // com.android.tools.r8.internal.C2994sV, com.android.tools.r8.internal.XM
    public final String P() {
        return "C";
    }

    @Override // com.android.tools.r8.internal.C2994sV, com.android.tools.r8.internal.XM
    public final String Q() {
        return PsiKeyword.CHAR;
    }

    @Override // com.android.tools.r8.internal.C2994sV, com.android.tools.r8.internal.AbstractC3472z00
    public final boolean equals(Object obj) {
        return this == obj;
    }

    @Override // com.android.tools.r8.internal.C2994sV, com.android.tools.r8.internal.AbstractC3472z00
    public final int hashCode() {
        return System.identityHashCode(e);
    }

    @Override // com.android.tools.r8.internal.C2994sV, com.android.tools.r8.internal.AbstractC3472z00
    public final String toString() {
        return "BYTE";
    }
}
