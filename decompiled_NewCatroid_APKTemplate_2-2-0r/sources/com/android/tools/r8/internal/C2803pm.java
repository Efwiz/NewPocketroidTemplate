package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2803pm extends AbstractC1160Ll {
    final /* synthetic */ AbstractC1639am c;
    final /* synthetic */ C2872qm d;

    C2803pm(C2872qm c2872qm, AbstractC1639am abstractC1639am) {
        this.d = c2872qm;
        this.c = abstractC1639am;
    }

    @Override // java.util.List
    public final Object get(int i) {
        return ((Map.Entry) this.c.get(i)).getValue();
    }

    @Override // com.android.tools.r8.internal.AbstractC1160Ll
    final AbstractC1271Pl k() {
        return this.d;
    }
}
