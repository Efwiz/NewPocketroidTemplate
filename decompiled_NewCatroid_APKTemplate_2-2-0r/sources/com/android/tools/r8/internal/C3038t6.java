package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.t6, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3038t6 implements Iterable {
    public static final C3038t6 d = new C3038t6();
    public static final C3038t6 e = new C3038t6();
    static final /* synthetic */ boolean f = true;
    private final AbstractC1639am a;
    private final AbstractC1639am b;
    private AbstractC1048Hm c;

    private C3038t6() {
        this.a = AbstractC1639am.i();
        this.b = AbstractC1639am.i();
    }

    public final boolean a(C0722p1 c0722p1) {
        if (this.a.size() > 0) {
            AbstractC1639am abstractC1639am = this.a;
            if (abstractC1639am.get(abstractC1639am.size() - 1) == c0722p1.m3) {
                return true;
            }
        }
        return false;
    }

    public final List b() {
        return this.a;
    }

    public final Set c() {
        if (this.c == null) {
            this.c = AbstractC1048Hm.a(this.b);
        }
        return this.c;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C3038t6)) {
            return false;
        }
        C3038t6 c3038t6 = (C3038t6) obj;
        return this.a.equals(c3038t6.a) && this.b.equals(c3038t6.b);
    }

    public final int hashCode() {
        return this.b.hashCode() + (this.a.hashCode() * 31);
    }

    public final boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return new C2898r6(this);
    }

    public final C0767v2 j(int i) {
        return (C0767v2) this.a.get(i);
    }

    public final int size() {
        if (f || this.a.size() == this.b.size()) {
            return this.a.size();
        }
        throw new AssertionError();
    }

    public C3038t6(List list, List list2) {
        boolean z = f;
        if (!z && list.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && list.size() != list2.size()) {
            throw new AssertionError();
        }
        this.a = AbstractC1639am.a(list);
        this.b = AbstractC1639am.a(list2);
    }

    public final List a() {
        return this.b;
    }

    public final C3038t6 a(C0767v2 c0767v2, Integer num) {
        if (f || !this.a.contains(c0767v2)) {
            return new C3038t6(AbstractC1639am.g().a((Iterable) this.a).a(c0767v2).a(), AbstractC1639am.g().a((Iterable) this.b).a(num).a());
        }
        throw new AssertionError();
    }

    public final C3038t6 a(final C0767v2 c0767v2) {
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        a(new BiConsumer() { // from class: com.android.tools.r8.internal.t6$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3038t6.a(c0767v2, arrayList, arrayList2, (C0767v2) obj, obj2);
            }
        });
        return new C3038t6(arrayList, arrayList2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0767v2 c0767v2, List list, List list2, C0767v2 c0767v3, Object obj) {
        if (c0767v3 != c0767v2) {
            list.add(c0767v3);
            list2.add(obj);
        }
    }

    public final void a(BiConsumer biConsumer) {
        for (int i = 0; i < size(); i++) {
            biConsumer.accept((C0767v2) this.a.get(i), this.b.get(i));
        }
    }
}
