package com.android.tools.r8.internal;

import java.util.HashMap;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2187hv {
    private final HashMap a = new HashMap();

    public final void a(String str, C0846Aw c0846Aw) {
        if (str == null || c0846Aw == null) {
            throw new C2883qv("Invalid binding of '" + str + FormatHelper.QUOTE);
        }
        if (((C0846Aw) this.a.put(str, c0846Aw)) != null) {
            throw new C2883qv("Multiple definitions for binding '" + str + FormatHelper.QUOTE);
        }
    }

    public final C2264iv a() {
        if (this.a.isEmpty()) {
            return C2264iv.b;
        }
        HashMap map = new HashMap(this.a.size());
        for (String str : this.a.keySet()) {
            map.put(str, a(str));
        }
        return new C2264iv(map);
    }

    private C2117gv a(String str) {
        C0846Aw c0846Aw = (C0846Aw) this.a.get(str);
        for (String str2 : c0846Aw.c()) {
            if (!str2.equals(str)) {
                if (!this.a.containsKey(str2)) {
                    throw new C2883qv("Undefined binding for name '" + str2 + "' referenced in binding of '" + str + FormatHelper.QUOTE);
                }
            } else {
                throw new C2883qv("Recursive binding for name '" + str2 + FormatHelper.QUOTE);
            }
        }
        return new C2117gv(c0846Aw);
    }
}
