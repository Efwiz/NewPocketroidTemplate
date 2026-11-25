package com.android.tools.r8.utils;

import com.android.tools.r8.DataDirectoryResource;
import com.android.tools.r8.DataEntryResource;
import com.android.tools.r8.DataResource;
import com.android.tools.r8.DataResourceProvider;

/* JADX INFO: renamed from: com.android.tools.r8.utils.n, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3935n implements DataResourceProvider {
    static final /* synthetic */ boolean b = true;
    final /* synthetic */ C3940o a;

    C3935n(C3940o c3940o) {
        this.a = c3940o;
    }

    @Override // com.android.tools.r8.DataResourceProvider
    public final void accept(DataResourceProvider.Visitor visitor) {
        for (DataResource dataResource : this.a.b) {
            if (dataResource instanceof DataEntryResource) {
                visitor.visit((DataEntryResource) dataResource);
            } else {
                if (!b && !(dataResource instanceof DataDirectoryResource)) {
                    throw new AssertionError();
                }
                visitor.visit((DataDirectoryResource) dataResource);
            }
        }
    }
}
