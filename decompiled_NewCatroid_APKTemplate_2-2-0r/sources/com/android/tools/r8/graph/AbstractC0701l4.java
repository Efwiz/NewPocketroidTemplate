package com.android.tools.r8.graph;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.graph.l4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0701l4 {

    /* JADX INFO: renamed from: com.android.tools.r8.graph.l4$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a extends AbstractC0701l4 {
        private static final a e = new a(new IdentityHashMap(), Collections.emptyList(), Collections.emptyList(), 2);
        private final Map a;
        private final List b;
        private final List c;
        private int d;

        private a(Map map, List list, List list2, int i) {
            this.a = map;
            this.b = list;
            this.c = list2;
            this.d = i;
        }

        public static C0693k4 f() {
            return new C0693k4();
        }

        @Override // com.android.tools.r8.graph.AbstractC0701l4
        public final a a() {
            return this;
        }

        @Override // com.android.tools.r8.graph.AbstractC0701l4
        public final boolean e() {
            return true;
        }

        public final InterfaceC0707m4 g() {
            if (!j()) {
                if (this.b.size() + this.a.size() <= 1) {
                    if (this.a.size() == 1) {
                        return (InterfaceC0707m4) this.a.values().iterator().next();
                    }
                    if (this.b.size() == 1) {
                        return (InterfaceC0707m4) this.b.get(0);
                    }
                }
            }
            return null;
        }

        public boolean h() {
            return !this.b.isEmpty();
        }

        public boolean i() {
            return this.d == 1;
        }

        public boolean j() {
            return this.d == 2;
        }

        public final void k() {
            this.d = 2;
        }

        @Override // com.android.tools.r8.graph.AbstractC0701l4
        public void a(final Consumer<? super InterfaceC0669h4> consumer, Consumer<? super C0662g4> consumer2) {
            this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.graph.l4$a$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    consumer.accept((InterfaceC0669h4) obj2);
                }
            });
            this.b.forEach(consumer2);
        }

        @Override // com.android.tools.r8.graph.AbstractC0701l4
        public final void a(Consumer consumer) {
            this.c.forEach(consumer);
        }

        public boolean a(W0 w0) {
            return this.a.containsKey(w0.getReference());
        }
    }

    public static C0686j4 b() {
        return C0686j4.a;
    }

    public static a c() {
        return a.e;
    }

    public a a() {
        return null;
    }

    public abstract void a(Consumer consumer);

    public abstract void a(Consumer<? super InterfaceC0669h4> consumer, Consumer<? super C0662g4> consumer2);

    public boolean d() {
        return false;
    }

    public boolean e() {
        return false;
    }

    public static a a(Map map, List list, List list2, int i) {
        return new a(map, list, list2, i);
    }
}
