package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.naming.AbstractC3649o0;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: renamed from: com.android.tools.r8.internal.a9, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1592a9 extends AbstractC2926rV {
    static final C1592a9 c = new C1592a9();

    private C1592a9() {
    }

    @Override // com.android.tools.r8.internal.AbstractC2926rV
    public final boolean T() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final C0767v2 a(C0722p1 c0722p1) {
        return c0722p1.x1;
    }

    @Override // com.android.tools.r8.internal.VM
    public final String getTypeName() {
        return PsiKeyword.CHAR;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1348Si
    public final Object a(AbstractC1476Wj abstractC1476Wj, AbstractC3649o0 abstractC3649o0) {
        throw new C3405y10("Unexpected value type: " + this);
    }
}
