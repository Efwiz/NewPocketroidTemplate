package com.android.tools.r8.graph;

import com.android.tools.r8.internal.B7;
import com.android.tools.r8.internal.Z00;

/* JADX INFO: renamed from: com.android.tools.r8.graph.z, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0813z implements com.android.tools.r8.utils.structural.u {
    final /* synthetic */ C a;

    C0813z(C c) {
        this.a = c;
    }

    @Override // com.android.tools.r8.utils.structural.v
    public final int a(Object obj, Object obj2, com.android.tools.r8.utils.structural.a aVar) {
        B7 b7 = (B7) obj;
        B7 b8 = (B7) obj2;
        C c = this.a;
        int iA = aVar.a(b7.z(), b8.z());
        return iA != 0 ? iA : b7.a(b8, aVar, c);
    }

    @Override // com.android.tools.r8.utils.structural.w
    public final void a(Object obj, com.android.tools.r8.utils.structural.o oVar) {
        throw new Z00();
    }
}
