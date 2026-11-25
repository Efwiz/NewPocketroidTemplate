package com.android.tools.r8.internal;

import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.w4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3257w4 extends AbstractC2251ih {
    static final /* synthetic */ boolean a = true;

    private C3257w4() {
    }

    @Override // com.android.tools.r8.internal.AbstractC2251ih
    protected final boolean a(Object obj, Object obj2) {
        ML ml = (ML) obj;
        ML ml2 = (ML) obj2;
        if (!a && ml.h() != ml2.h()) {
            throw new AssertionError();
        }
        for (int i = 0; i < ml.k0().size(); i++) {
            if (ml.c(i) != ml2.c(i)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2251ih
    protected final int a(Object obj) {
        Iterator<S10> it = ((ML) obj).k0().iterator();
        int iHashCode = 0;
        while (it.hasNext()) {
            iHashCode = (iHashCode * 13) + it.next().hashCode();
        }
        return iHashCode;
    }
}
