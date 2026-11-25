package com.android.tools.r8.internal;

import com.android.tools.r8.utils.C3888e1;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pX, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2784pX {
    static final /* synthetic */ boolean g = true;
    private Map a;
    private Map b;
    private Set c;
    private Set d;
    private final HashSet e = new HashSet();
    private final C2855qX f;

    public static /* synthetic */ HashSet $r8$lambda$j35xzDqY00kBEc7DMQyJC7sG_u4() {
        return new HashSet();
    }

    public C2784pX(C2855qX c2855qX) {
        this.a = c2855qX.a;
        this.b = c2855qX.b;
        this.c = c2855qX.c;
        this.d = c2855qX.d;
        this.f = c2855qX;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(S10 s10, Set set) {
        this.a.put(s10, OU.a((Iterable) set));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(S10 s10, Set set) {
        this.b.put(s10, OU.a((Iterable) set));
    }

    public final C2784pX a(S10 s10, Set set) {
        b();
        ((Set) this.a.computeIfAbsent(s10, C3888e1.a(new pX$$ExternalSyntheticLambda1()))).addAll(set);
        return this;
    }

    public final C2784pX b(S10 s10, Set set) {
        c();
        ((Set) this.b.computeIfAbsent(s10, C3888e1.a(new pX$$ExternalSyntheticLambda1()))).addAll(set);
        return this;
    }

    private void c() {
        if (this.b == this.f.b) {
            this.b = new HashMap(this.f.b.size() + 1);
            this.f.b.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.pX$$ExternalSyntheticLambda3
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.d((S10) obj, (Set) obj2);
                }
            });
        }
    }

    public final Set d() {
        return this.d;
    }

    private void b() {
        if (this.a == this.f.a) {
            this.a = new HashMap(this.f.a.size() + 1);
            this.f.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.pX$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.c((S10) obj, (Set) obj2);
                }
            });
        }
    }

    public final void a(S10 s10, S10 s11) {
        b();
        c();
        ((Set) this.a.computeIfAbsent(s10, C3888e1.a(new pX$$ExternalSyntheticLambda1()))).add(s11);
        ((Set) this.b.computeIfAbsent(s11, C3888e1.a(new pX$$ExternalSyntheticLambda1()))).add(s10);
    }

    public final void b(S10 s10) {
        if (this.d == this.f.d) {
            this.d = new HashSet(this.d);
        }
        this.d.add(s10);
    }

    public final void a(S10 s10) {
        if (this.c == this.f.c) {
            this.c = new HashSet(this.c);
        }
        if (this.c.add(s10)) {
            this.e.add(s10);
        }
    }

    public final C2855qX a() {
        boolean z = g;
        if (!z && !this.d.containsAll(this.c)) {
            throw new AssertionError("Escaping is not a subset of live string builders");
        }
        if (!z && !this.d.containsAll(this.a.keySet())) {
            throw new AssertionError("Aliases is not a subset of live string builders");
        }
        if (!z && !this.c.containsAll(this.e)) {
            throw new AssertionError("Unexpected value in newlyEscaping not in escaping");
        }
        if (!z && !this.d.containsAll(this.b.keySet())) {
            throw new AssertionError("Escaped definitions should all be live");
        }
        if (!z && !this.b.values().stream().allMatch(new Predicate() { // from class: com.android.tools.r8.internal.pX$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a((Set) obj);
            }
        })) {
            throw new AssertionError("All known escaping definitions should be live string builders");
        }
        C2855qX c2855qX = this.f;
        if (c2855qX.d == this.d && c2855qX.c == this.c && c2855qX.a == this.a && c2855qX.b == this.b) {
            c2855qX.e.clear();
            return this.f;
        }
        return new C2855qX(this.a, this.b, this.c, this.d, this.e);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(Set set) {
        return this.d.containsAll(set);
    }
}
