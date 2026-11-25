package com.android.tools.r8.internal;

import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.el, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1945el {
    private final ArrayList a;

    private C1945el() {
        this.a = new ArrayList();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof C1945el)) {
            return false;
        }
        C1945el c1945el = (C1945el) obj;
        if (c1945el.a.size() != this.a.size()) {
            return false;
        }
        for (int i = 0; i < this.a.size(); i++) {
            if (this.a.get(i) != c1945el.a.get(i)) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }
}
