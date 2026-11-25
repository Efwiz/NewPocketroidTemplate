package com.android.tools.r8.naming;

import com.android.tools.r8.internal.AbstractC2178hm;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class T {
    final Map a;

    private T(Map map) {
        this.a = map;
    }

    public static T a() {
        return new T(AbstractC2178hm.s());
    }
}
