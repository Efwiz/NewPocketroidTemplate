package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0767v2;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class EY {
    private final Map a;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a extends b {
        private static final a e = new a(-1, -1, false);

        a(int i, int i2, boolean z) {
            super(i, i2, z);
        }

        public static a a(int i) {
            return new a(i, i, true);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static abstract class b {
        static final /* synthetic */ boolean d = true;
        final boolean a;
        final int b;
        final int c;

        b(int i, int i2, boolean z) {
            this.a = z;
            this.b = i;
            this.c = i2;
        }

        public int a() {
            return this.c;
        }

        public int b() {
            return this.b;
        }

        public boolean c() {
            return this.a;
        }

        final int a(b bVar) {
            int i;
            int i2;
            boolean z = this.a;
            if (!z && !bVar.a) {
                i2 = -1;
                i = -1;
            } else if (z && bVar.a) {
                int i3 = this.c;
                i = bVar.b;
                if (i3 == i - 1) {
                    int i4 = this.b;
                    i2 = bVar.c;
                    i = i4;
                } else {
                    i2 = bVar.c;
                    int i5 = this.b;
                    if (i2 == i5 - 1) {
                        i2 = i3;
                    } else if (i3 == 19 && i == 21) {
                        i = i5;
                    } else {
                        if (i2 != 19 || i5 != 21) {
                            throw new RuntimeException("Cannot merge ranges.");
                        }
                        i2 = i3;
                    }
                }
            } else {
                i = z ? this.b : bVar.b;
                i2 = z ? this.c : bVar.c;
            }
            if (d || (i2 < 32768 && i < 32768)) {
                return (i2 << 16) + i;
            }
            throw new AssertionError();
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class c extends b {
        private static final c h = new c(false, false, true, false, -1, -1);
        private static final c i = new c(false, false, false, false, -1, -1);
        private static final c j = new c(true, false, false, false, -1, -1);
        private static final c k = new c(false, true, false, false, -1, -1);
        final boolean e;
        final boolean f;
        final boolean g;

        c(boolean z, boolean z2, boolean z3, boolean z4, int i2, int i3) {
            super(i2, i3, z4);
            this.e = z;
            this.f = z2;
            this.g = z3;
        }

        public static c a(int i2) {
            return new c(false, false, false, true, i2, i2);
        }

        public static c d() {
            return h;
        }

        public static c e() {
            return k;
        }

        public static c f() {
            return j;
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class d {
        private final AbstractC0727q0 a;
        private final DY b;
        private final SortedMap c;
        private final SortedMap d;
        private final Map e;
        private final Map f;

        private d(AbstractC0727q0 abstractC0727q0, DY dy, C1188Mm c1188Mm, C1188Mm c1188Mm2, HashMap map, HashMap map2) {
            this.a = abstractC0727q0;
            this.b = dy;
            this.c = c1188Mm;
            this.d = c1188Mm2;
            this.e = map;
            this.f = map2;
        }

        public final DY a() {
            return this.b;
        }

        public final AbstractC0727q0 b() {
            return this.a;
        }

        public final Collection c() {
            return this.c.values();
        }

        public final C0767v2 d() {
            return this.a.d;
        }

        public void a(BiConsumer<com.android.tools.r8.graph.T0, a> biConsumer) {
            for (com.android.tools.r8.graph.T0 t0 : this.d.values()) {
                biConsumer.accept(t0, (a) this.f.get(t0.getReference()));
            }
        }

        public void b(BiConsumer<com.android.tools.r8.graph.W0, c> biConsumer) {
            for (com.android.tools.r8.graph.W0 w0 : this.c.values()) {
                biConsumer.accept(w0, (c) this.e.get(w0.getReference()));
            }
        }
    }

    EY(C1188Mm c1188Mm) {
        this.a = c1188Mm;
    }

    public void a(Consumer<d> consumer) {
        this.a.values().forEach(consumer);
    }

    static CY a() {
        return new CY();
    }
}
