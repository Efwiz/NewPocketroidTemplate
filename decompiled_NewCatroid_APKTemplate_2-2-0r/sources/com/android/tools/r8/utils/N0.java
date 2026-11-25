package com.android.tools.r8.utils;

import com.android.tools.r8.internal.C1036Hf;
import java.util.Map;
import java.util.regex.Pattern;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
abstract class N0 extends O0 {
    N0() {
    }

    @Override // com.android.tools.r8.utils.O0
    public final boolean a(C1036Hf c1036Hf) {
        for (Map.Entry entry : c1036Hf.c().entrySet()) {
            String property = System.getProperty("com.android.tools.r8.dump.filter.buildproperty." + ((String) entry.getKey()));
            if (property != null && !Pattern.matches(property, (CharSequence) entry.getValue())) {
                return false;
            }
        }
        return true;
    }
}
