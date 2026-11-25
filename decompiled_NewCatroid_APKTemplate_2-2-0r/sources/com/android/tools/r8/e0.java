package com.android.tools.r8;

import com.android.tools.r8.origin.Origin;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class e0 implements d0 {
    static final /* synthetic */ boolean c = true;
    private final Origin a;
    private final String b;

    private e0(Origin origin, String str) {
        boolean z = c;
        if (!z && origin == null) {
            throw new AssertionError();
        }
        if (!z && str == null) {
            throw new AssertionError();
        }
        this.a = origin;
        this.b = str;
    }

    @Override // com.android.tools.r8.d0
    public final String a() {
        return this.b;
    }

    @Override // com.android.tools.r8.Resource
    public final Origin getOrigin() {
        return this.a;
    }
}
