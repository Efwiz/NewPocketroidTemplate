package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.HashMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ws, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3315ws implements Comparator {
    final /* synthetic */ HashMap a;

    public C3315ws(HashMap map) {
        this.a = map;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        Integer num = (Integer) this.a.get(obj);
        Integer num2 = (Integer) this.a.get(obj2);
        if (num == num2) {
            return 0;
        }
        if (num == null) {
            return -1;
        }
        if (num2 == null) {
            return 1;
        }
        return num.compareTo(num2);
    }
}
