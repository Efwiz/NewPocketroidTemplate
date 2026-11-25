package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.regex.Pattern;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Kt, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1142Kt extends AbstractC1032Hb implements Serializable {
    private final Pattern a;

    C1142Kt(Pattern pattern) {
        this.a = (Pattern) AbstractC3123uM.a(pattern);
    }

    public final C1114Jt a() {
        return new C1114Jt(this.a.matcher(""));
    }

    public final String toString() {
        return this.a.toString();
    }
}
