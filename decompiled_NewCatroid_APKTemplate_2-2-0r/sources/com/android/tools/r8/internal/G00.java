package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class G00 {
    public abstract C0767v2 a(C0767v2 c0767v2);

    public abstract void a(C0767v2 c0767v2, C0767v2 c0767v3);

    public abstract void a(Consumer consumer);

    public abstract boolean a();

    public final boolean a(C0702m c0702m, C0767v2 c0767v2) {
        return b(c0702m, c0767v2) != null;
    }

    public abstract C0767v2 b(C0702m c0702m, C0767v2 c0767v2);

    public final boolean a(C0737r2 c0737r2, C0702m c0702m) {
        if (a(c0702m, c0737r2.e)) {
            return true;
        }
        for (C0767v2 c0767v2 : c0737r2.f.a) {
            if (a(c0702m, c0767v2)) {
                return true;
            }
        }
        return false;
    }
}
