package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Jm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1107Jm implements Comparator {
    final /* synthetic */ Comparator a;

    C1107Jm(OK ok) {
        this.a = ok;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return this.a.compare(((Map.Entry) obj).getKey(), ((Map.Entry) obj2).getKey());
    }
}
