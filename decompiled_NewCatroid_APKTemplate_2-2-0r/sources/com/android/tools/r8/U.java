package com.android.tools.r8;

import java.util.Collection;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class U implements ProgramResourceProvider {
    final /* synthetic */ ProgramResourceProvider a;

    U(ProgramResourceProvider programResourceProvider) {
        this.a = programResourceProvider;
    }

    @Override // com.android.tools.r8.ProgramResourceProvider
    public final DataResourceProvider getDataResourceProvider() {
        return null;
    }

    @Override // com.android.tools.r8.ProgramResourceProvider
    public final Collection getProgramResources() {
        return this.a.getProgramResources();
    }
}
