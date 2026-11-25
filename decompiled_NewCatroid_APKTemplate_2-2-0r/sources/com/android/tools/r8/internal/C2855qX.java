package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qX, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2855qX extends AbstractC3327x1 {
    private static final C2855qX f = new C2855qX();
    static final /* synthetic */ boolean g = true;
    private final Map a;
    private final Map b;
    private final Set c;
    private final Set d;
    private final Set e;

    private C2855qX() {
        this.a = Collections.emptyMap();
        this.b = Collections.emptyMap();
        this.c = Collections.emptySet();
        this.d = Collections.emptySet();
        this.e = Collections.emptySet();
    }

    public static C2855qX e() {
        return f;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2998sZ
    public final AbstractC3327x1 a() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC3327x1
    public final /* bridge */ /* synthetic */ AbstractC3327x1 b(C0702m c0702m, AbstractC3327x1 abstractC3327x1) {
        return a((C2855qX) abstractC3327x1);
    }

    /* JADX WARN: Code duplicated, block: B:23:0x004d  */
    /* JADX WARN: Code duplicated, block: B:26:0x0058  */
    /* JADX WARN: Code duplicated, block: B:29:0x0066  */
    /* JADX WARN: Code duplicated, block: B:32:0x007f  */
    /* JADX WARN: Code duplicated, block: B:34:0x0089  */
    /* JADX WARN: Code duplicated, block: B:43:0x0094 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:44:? A[LOOP:1: B:27:0x0060->B:44:?, LOOP_END, SYNTHETIC] */
    @Override // com.android.tools.r8.internal.AbstractC3327x1
    public final boolean equals(Object obj) {
        Map map;
        Map map2;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C2855qX)) {
            return false;
        }
        C2855qX c2855qX = (C2855qX) obj;
        Map map3 = this.a;
        Map map4 = c2855qX.a;
        if (map3 == map4) {
            map = this.b;
            map2 = c2855qX.b;
            if (map != map2) {
                if (map.size() == map2.size()) {
                    for (Map.Entry entry : map.entrySet()) {
                        if (!entry.getValue().equals(map2.get(entry.getKey()))) {
                        }
                    }
                    if (this.c.equals(c2855qX.c)) {
                        return true;
                    }
                }
            } else if (this.c.equals(c2855qX.c)) {
                return true;
            }
        } else if (map3.size() == map4.size()) {
            for (Map.Entry entry2 : map3.entrySet()) {
                if (!entry2.getValue().equals(map4.get(entry2.getKey()))) {
                }
            }
            map = this.b;
            map2 = c2855qX.b;
            if (map != map2) {
                if (map.size() == map2.size()) {
                    while (r1.hasNext()) {
                        if (!entry.getValue().equals(map2.get(entry.getKey()))) {
                        }
                    }
                    if (this.c.equals(c2855qX.c) && this.d.equals(c2855qX.d)) {
                        return true;
                    }
                }
            } else if (this.c.equals(c2855qX.c)) {
                return true;
            }
        }
        return false;
    }

    public final Map f() {
        return this.a;
    }

    public final Map g() {
        return this.b;
    }

    public final Set h() {
        return this.c;
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.b, this.c, this.d);
    }

    public final Set i() {
        return this.d;
    }

    public final Set j() {
        return this.e;
    }

    public final boolean a(S10 s10) {
        return this.c.contains(s10);
    }

    public final boolean b(S10 s10) {
        return this.d.contains(s10);
    }

    public final C2855qX a(C2855qX c2855qX) {
        C2855qX c2855qX2 = f;
        if (this == c2855qX2) {
            return c2855qX;
        }
        c2855qX.getClass();
        if (c2855qX == c2855qX2) {
            return this;
        }
        final C2784pX c2784pX = new C2784pX(this);
        Set set = c2855qX.c;
        if (set != null) {
            if (c2784pX.c == c2784pX.f.c) {
                c2784pX.c = new HashSet(c2784pX.c);
            }
            c2784pX.c.addAll(set);
        }
        Set set2 = c2855qX.d;
        if (c2784pX.d == c2784pX.f.d) {
            c2784pX.d = new HashSet(c2784pX.d);
        }
        c2784pX.d.addAll(set2);
        c2855qX.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.qX$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                c2784pX.a((S10) obj, (Set) obj2);
            }
        });
        c2855qX.b.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.qX$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                c2784pX.b((S10) obj, (Set) obj2);
            }
        });
        return c2784pX.a();
    }

    public C2855qX(Map map, Map map2, Set set, Set set2, HashSet hashSet) {
        if (!g && map.isEmpty() && set.isEmpty() && map2.isEmpty() && set2.isEmpty()) {
            throw new AssertionError("Creating an instance of BOTTOM");
        }
        this.a = map;
        this.b = map2;
        this.c = set;
        this.d = set2;
        this.e = hashSet;
    }
}
