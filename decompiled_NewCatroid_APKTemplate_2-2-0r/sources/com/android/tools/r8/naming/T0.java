package com.android.tools.r8.naming;

import com.android.tools.r8.SourceFileEnvironment;
import com.android.tools.r8.SourceFileProvider;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class T0 implements SourceFileProvider {
    final /* synthetic */ String a;
    final /* synthetic */ boolean b;

    T0(String str, boolean z) {
        this.a = str;
        this.b = z;
    }

    @Override // com.android.tools.r8.SourceFileProvider
    public final boolean allowDiscardingSourceFile() {
        return this.b;
    }

    @Override // com.android.tools.r8.SourceFileProvider
    public final String get(SourceFileEnvironment sourceFileEnvironment) {
        return this.a;
    }
}
