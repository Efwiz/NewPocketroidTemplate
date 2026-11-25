package com.android.tools.r8;

import com.android.tools.r8.origin.Origin;
import java.io.InputStream;

/* JADX INFO: renamed from: com.android.tools.r8.v, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3999v implements DataEntryResource {
    private final String a;
    private final DataEntryResource b;

    public C3999v(String str, DataEntryResource dataEntryResource) {
        this.a = str;
        this.b = dataEntryResource;
    }

    @Override // com.android.tools.r8.DataEntryResource
    public final InputStream getByteStream() {
        return this.b.getByteStream();
    }

    @Override // com.android.tools.r8.DataResource
    public final String getName() {
        String str = this.a;
        return str != null ? str : this.b.getName();
    }

    @Override // com.android.tools.r8.Resource
    public final Origin getOrigin() {
        return this.b.getOrigin();
    }
}
