package com.android.tools.r8;

import com.android.tools.r8.origin.Origin;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/* JADX INFO: renamed from: com.android.tools.r8.t, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3863t implements DataEntryResource {
    private final byte[] a;
    private final String b;
    private final Origin c;

    public C3863t(byte[] bArr, String str, Origin origin) {
        this.a = bArr;
        this.b = str;
        this.c = origin;
    }

    @Override // com.android.tools.r8.DataEntryResource
    public final InputStream getByteStream() {
        return new ByteArrayInputStream(this.a);
    }

    @Override // com.android.tools.r8.DataResource
    public final String getName() {
        return this.b;
    }

    @Override // com.android.tools.r8.Resource
    public final Origin getOrigin() {
        return this.c;
    }
}
