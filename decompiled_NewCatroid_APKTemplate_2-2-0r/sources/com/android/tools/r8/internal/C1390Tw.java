package com.android.tools.r8.internal;

import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Tw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1390Tw extends AbstractC1437Uw {
    private final List a;
    static final /* synthetic */ boolean c = true;
    private static final C1390Tw b = new C1390Tw(Collections.emptyList());

    private C1390Tw(List list) {
        super();
        if (!c && list == null) {
            throw new AssertionError();
        }
        this.a = list;
    }

    @Override // com.android.tools.r8.internal.AbstractC1437Uw
    public final List b() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C1390Tw.class != obj.getClass()) {
            return false;
        }
        return this.a.equals(((C1390Tw) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }
}
