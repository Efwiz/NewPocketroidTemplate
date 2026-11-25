package com.android.tools.r8.internal;

import com.android.tools.r8.references.ClassReference;
import java.util.OptionalInt;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class BT {
    private ClassReference a;
    private OptionalInt b;

    private BT() {
        this.b = OptionalInt.empty();
    }

    public final BT a(ClassReference classReference) {
        this.a = classReference;
        return this;
    }

    public final BT b() {
        this.b = OptionalInt.empty();
        return this;
    }

    public final BT a(OptionalInt optionalInt) {
        this.b = optionalInt;
        return this;
    }

    public final CT a() {
        return new CT(this.a, this.b);
    }
}
