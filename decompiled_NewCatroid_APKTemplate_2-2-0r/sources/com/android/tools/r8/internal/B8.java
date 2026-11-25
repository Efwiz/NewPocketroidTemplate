package com.android.tools.r8.internal;

import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class B8 {
    public static M7 a(List list) {
        B7 b7 = list.isEmpty() ? null : (B7) list.get(list.size() - 1);
        if (b7 != null && (b7 instanceof M7)) {
            return b7.q();
        }
        M7 m7 = new M7();
        list.add(m7);
        return m7;
    }
}
