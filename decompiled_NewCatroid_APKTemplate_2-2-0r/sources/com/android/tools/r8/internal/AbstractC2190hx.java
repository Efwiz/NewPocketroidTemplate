package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hx, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2190hx {

    /* JADX INFO: renamed from: com.android.tools.r8.internal.hx$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        private ArrayList a;

        private a() {
            this.a = new ArrayList();
        }

        public a a(C2572mv c2572mv) {
            this.a.add(c2572mv);
            return this;
        }

        public AbstractC2190hx a() {
            return this.a.isEmpty() ? C2119gx.a : new C2267ix(this.a);
        }
    }

    public static AbstractC2190hx a() {
        return C2119gx.a;
    }

    public static a b() {
        return new a();
    }

    public abstract void a(Consumer consumer);

    public abstract boolean c();
}
