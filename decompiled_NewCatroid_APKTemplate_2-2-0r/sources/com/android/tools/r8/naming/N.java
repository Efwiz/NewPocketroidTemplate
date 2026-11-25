package com.android.tools.r8.naming;

import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class N {
    static final /* synthetic */ boolean a = true;

    static boolean a(int i, List list) {
        N0 n0;
        int i2 = i + 1;
        return i2 < list.size() && (n0 = ((C3634k.b) list.get(i)).a) != null && n0.equals(((C3634k.b) list.get(i2)).a);
    }
}
