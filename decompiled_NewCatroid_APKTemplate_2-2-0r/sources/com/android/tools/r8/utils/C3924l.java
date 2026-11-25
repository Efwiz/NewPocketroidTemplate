package com.android.tools.r8.utils;

import com.android.tools.r8.DataEntryResource;
import com.android.tools.r8.DataResourceProvider;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.utils.l, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3924l implements DataResourceProvider {
    final /* synthetic */ C3930m a;

    C3924l(C3930m c3930m) {
        this.a = c3930m;
    }

    @Override // com.android.tools.r8.DataResourceProvider
    public final void accept(DataResourceProvider.Visitor visitor) {
        Iterator it = this.a.b.iterator();
        while (it.hasNext()) {
            visitor.visit((DataEntryResource) it.next());
        }
    }
}
