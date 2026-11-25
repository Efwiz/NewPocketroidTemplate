package com.android.tools.r8.dex;

import com.android.tools.r8.SourceFileEnvironment;
import com.android.tools.r8.naming.J0;

/* JADX INFO: renamed from: com.android.tools.r8.dex.g, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0544g implements SourceFileEnvironment {
    final /* synthetic */ J0 a;

    C0544g(J0 j0) {
        this.a = j0;
    }

    @Override // com.android.tools.r8.SourceFileEnvironment
    public final String getMapHash() {
        return this.a.a();
    }

    @Override // com.android.tools.r8.SourceFileEnvironment
    public final String getMapId() {
        return this.a.b();
    }
}
