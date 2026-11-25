package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.OU;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class U1 {
    private final Set a;

    private U1(Set set) {
        this.a = set;
    }

    public static U1 b() {
        return new U1(OU.c());
    }

    public final T1 a() {
        return new T1(this.a);
    }

    public final void a(Consumer consumer) {
        this.a.forEach(consumer);
    }

    public final boolean a(C0767v2 c0767v2) {
        return this.a.contains(c0767v2);
    }
}
