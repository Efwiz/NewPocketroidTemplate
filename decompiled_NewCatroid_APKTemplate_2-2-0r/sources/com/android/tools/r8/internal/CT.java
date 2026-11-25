package com.android.tools.r8.internal;

import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.retrace.RetraceStackTraceContext;
import java.util.OptionalInt;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class CT implements RetraceStackTraceContext {
    private final ClassReference a;
    private final OptionalInt b;

    private CT(ClassReference classReference, OptionalInt optionalInt) {
        this.a = classReference;
        this.b = optionalInt;
    }

    public static BT b() {
        return new BT();
    }

    public final BT a() {
        BT bt = new BT();
        bt.a = this.a;
        bt.b = this.b;
        return bt;
    }

    public final int c() {
        return this.b.getAsInt();
    }

    public final ClassReference d() {
        return this.a;
    }

    public final boolean e() {
        return this.b.isPresent();
    }
}
