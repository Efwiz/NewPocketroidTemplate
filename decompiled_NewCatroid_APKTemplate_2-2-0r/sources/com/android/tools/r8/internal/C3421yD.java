package com.android.tools.r8.internal;

import com.android.tools.r8.retrace.MappingPartition;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yD, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3421yD implements MappingPartition {
    private final String a;
    private final byte[] b;

    public C3421yD(String str, byte[] bArr) {
        this.a = str;
        this.b = bArr;
    }

    @Override // com.android.tools.r8.retrace.MappingPartition
    public final String getKey() {
        return this.a;
    }

    @Override // com.android.tools.r8.retrace.MappingPartition
    public final byte[] getPayload() {
        return this.b;
    }
}
