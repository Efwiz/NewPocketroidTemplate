package com.android.tools.r8.utils;

import com.android.tools.r8.DataResourceProvider;
import com.android.tools.r8.ProgramResourceProvider;
import com.android.tools.r8.internal.AbstractC1639am;
import java.util.Collection;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.utils.o, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3940o implements ProgramResourceProvider {
    final /* synthetic */ List a;
    final /* synthetic */ List b;

    C3940o(AbstractC1639am abstractC1639am, AbstractC1639am abstractC1639am2) {
        this.a = abstractC1639am;
        this.b = abstractC1639am2;
    }

    @Override // com.android.tools.r8.ProgramResourceProvider
    public final DataResourceProvider getDataResourceProvider() {
        if (this.b.isEmpty()) {
            return null;
        }
        return new C3935n(this);
    }

    @Override // com.android.tools.r8.ProgramResourceProvider
    public final Collection getProgramResources() {
        return this.a;
    }
}
