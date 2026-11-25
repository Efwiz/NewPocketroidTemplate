package com.android.tools.r8.internal;

import com.reandroid.dex.sections.Marker;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Rt, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1333Rt extends AbstractC1249Ot {
    private final JB a = new JB();

    public final boolean b(String str) {
        return this.a.containsKey(str);
    }

    public final Set entrySet() {
        return this.a.entrySet();
    }

    public final boolean equals(Object obj) {
        return obj == this || ((obj instanceof C1333Rt) && ((C1333Rt) obj).a.equals(this.a));
    }

    public final C1333Rt g() {
        return (C1333Rt) this.a.get("positions");
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final void a(Long l) {
        this.a.put(Marker.MIN_API, l == null ? C1306Qt.a : new C1434Ut((Object) l));
    }

    public final void a(String str, AbstractC1249Ot abstractC1249Ot) {
        if (abstractC1249Ot == null) {
            abstractC1249Ot = C1306Qt.a;
        }
        this.a.put(str, abstractC1249Ot);
    }

    public final void a(String str, Boolean bool) {
        this.a.put(str, bool == null ? C1306Qt.a : new C1434Ut((Object) bool));
    }

    public final void a(String str, String str2) {
        this.a.put(str, str2 == null ? C1306Qt.a : new C1434Ut((Object) str2));
    }

    public final AbstractC1249Ot a(String str) {
        return (AbstractC1249Ot) this.a.get(str);
    }
}
