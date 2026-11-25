package com.android.tools.r8.internal;

import java.util.logging.Logger;
import java.util.regex.Pattern;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class UL {
    private static final SL a;

    static {
        Logger.getLogger(UL.class.getName());
        a = new SL(0);
    }

    static C1142Kt a() {
        int i = AbstractC3123uM.a;
        a.getClass();
        return new C1142Kt(Pattern.compile("\r\n|\n|\r"));
    }
}
