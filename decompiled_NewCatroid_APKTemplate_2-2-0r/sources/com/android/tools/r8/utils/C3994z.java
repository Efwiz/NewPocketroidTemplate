package com.android.tools.r8.utils;

import com.android.tools.r8.ByteDataView;

/* JADX INFO: renamed from: com.android.tools.r8.utils.z, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3994z implements Comparable {
    public final String a;
    public final ByteDataView b;
    public final boolean c;

    private C3994z(String str, ByteDataView byteDataView, boolean z) {
        this.a = str;
        this.b = byteDataView;
        this.c = z;
    }

    public static C3994z a(String str, ByteDataView byteDataView) {
        return new C3994z(str, byteDataView, false);
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        C3994z c3994z = (C3994z) obj;
        return c3994z == null ? this.a.compareTo((String) null) : this.a.compareTo(c3994z.a);
    }
}
