package com.android.tools.r8.utils;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.utils.q2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class C3954q2 {
    private final Origin a;
    private final Position b;

    public C3954q2(Origin origin, Position position) {
        this.a = origin;
        this.b = position;
    }

    public final Origin a() {
        return this.a;
    }

    public final Position b() {
        return this.b;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof C3954q2)) {
            return false;
        }
        C3954q2 c3954q2 = (C3954q2) obj;
        return Objects.equals(c3954q2.a, this.a) && Objects.equals(c3954q2.b, this.b);
    }

    public final int hashCode() {
        return this.b.hashCode() + (this.a.hashCode() * 13);
    }
}
