package com.android.tools.r8;

import com.android.tools.r8.origin.Origin;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class r implements DataDirectoryResource {
    static final /* synthetic */ boolean c = true;
    private final String a;
    private final Origin b;

    private r(String str, Origin origin) {
        boolean z = c;
        if (!z && str == null) {
            throw new AssertionError();
        }
        if (!z && origin == null) {
            throw new AssertionError();
        }
        this.a = str;
        this.b = origin;
    }

    @Override // com.android.tools.r8.DataResource
    public final String getName() {
        return this.a;
    }

    @Override // com.android.tools.r8.Resource
    public final Origin getOrigin() {
        return this.b;
    }
}
