package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Collections;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yE, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3422yE extends C3491zE {
    private final byte[] b;
    private final int c;
    private final int d;
    private C3491zE e;

    public C3422yE(byte[] bArr, int i, int i2) {
        super(Collections.emptyList());
        this.e = null;
        this.b = bArr;
        this.c = i;
        this.d = i2;
    }

    @Override // com.android.tools.r8.internal.C3491zE
    public final Collection a() {
        if (this.e == null) {
            byte[] bArr = this.b;
            int i = this.c;
            this.e = new C3491zE(com.android.tools.r8.utils.Y2.a(new String(bArr, i, this.d - i), ';'));
        }
        return this.e.a();
    }
}
