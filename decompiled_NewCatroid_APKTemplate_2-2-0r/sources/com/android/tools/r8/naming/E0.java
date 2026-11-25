package com.android.tools.r8.naming;

import com.android.tools.r8.position.Position;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class E0 implements Position {
    private final int a;

    E0(int i) {
        this.a = i;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return (obj instanceof E0) && this.a == ((E0) obj).a;
    }

    @Override // com.android.tools.r8.position.Position
    public final String getDescription() {
        return "line " + this.a;
    }

    public final int hashCode() {
        return this.a;
    }
}
