package com.android.tools.r8.internal;

import java.util.HashSet;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2422kw implements JK {
    public static int h = 589824;

    public static Set<C2736ov> a(byte[] bArr) {
        int length = bArr.length;
        C1031Ha c1031Ha = new C1031Ha(bArr, 0);
        final HashSet hashSet = new HashSet();
        c1031Ha.a(1, new C1388Tv(new InterfaceC2118gw() { // from class: com.android.tools.r8.internal.kw$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC2118gw
            public final void accept(Object obj) {
                hashSet.add((C2736ov) obj);
            }
        }));
        return hashSet;
    }
}
