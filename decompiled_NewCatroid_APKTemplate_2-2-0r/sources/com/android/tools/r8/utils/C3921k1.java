package com.android.tools.r8.utils;

import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC3123uM;
import com.android.tools.r8.internal.C1507Xl;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.utils.k1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class C3921k1 {
    private final ArrayList a;
    private final C1507Xl b;

    private C3921k1() {
        this.a = new ArrayList();
        this.b = AbstractC1639am.g();
    }

    public final void a(int i, com.android.tools.r8.utils.structural.x xVar) {
        this.a.add(Integer.valueOf(i));
        this.b.a(xVar);
    }

    public final boolean b() {
        return this.a.isEmpty();
    }

    public final C3926l1 a() {
        Object[] array = this.a.toArray();
        int length = array.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            Object obj = array[i];
            int i2 = AbstractC3123uM.a;
            obj.getClass();
            iArr[i] = ((Number) obj).intValue();
        }
        return new C3926l1(iArr, this.b.a());
    }
}
