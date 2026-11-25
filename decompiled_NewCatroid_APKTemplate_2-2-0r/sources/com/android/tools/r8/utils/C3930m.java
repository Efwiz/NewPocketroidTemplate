package com.android.tools.r8.utils;

import com.android.tools.r8.DataResourceProvider;
import com.android.tools.r8.ProgramResourceProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.utils.m, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3930m implements ProgramResourceProvider {
    final /* synthetic */ List a;
    final /* synthetic */ List b;

    C3930m(ArrayList arrayList, ArrayList arrayList2) {
        this.a = arrayList;
        this.b = arrayList2;
    }

    @Override // com.android.tools.r8.ProgramResourceProvider
    public final DataResourceProvider getDataResourceProvider() {
        if (this.b.isEmpty()) {
            return null;
        }
        return new C3924l(this);
    }

    @Override // com.android.tools.r8.ProgramResourceProvider
    public final Collection getProgramResources() {
        return this.a;
    }
}
