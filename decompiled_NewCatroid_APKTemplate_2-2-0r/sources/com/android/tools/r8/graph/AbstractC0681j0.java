package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.naming.C3605b;
import com.android.tools.r8.synthesis.C3842a;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.graph.j0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0681j0 implements Q0 {
    static final /* synthetic */ boolean h = true;
    public final AbstractC1639am a;
    private final C3605b b;
    public final com.android.tools.r8.utils.y3 c;
    public final com.android.tools.r8.utils.A1 d;
    public final C0722p1 e;
    private final C0696l0 f;
    public final C0759u2 g;

    AbstractC0681j0(C3605b c3605b, C0696l0 c0696l0, AbstractC1639am abstractC1639am, com.android.tools.r8.utils.A1 a1, C0759u2 c0759u2, com.android.tools.r8.utils.y3 y3Var) {
        this.b = c3605b;
        this.f = c0696l0;
        this.a = abstractC1639am;
        this.d = a1;
        this.e = a1.a;
        this.g = c0759u2;
        this.c = y3Var;
    }

    @Override // com.android.tools.r8.graph.Q0
    public final C0722p1 a() {
        return this.e;
    }

    public abstract void a(Consumer consumer);

    public N2 b() {
        throw new C3405y10("Cannot use a LazyDexApplication where a DirectDexApplication is expected.");
    }

    public abstract void b(Consumer consumer);

    public abstract AbstractC0674i0 c();

    @Override // com.android.tools.r8.graph.Q0
    public abstract AbstractC0727q0 c(C0767v2 c0767v2);

    public abstract C0729q2 d(C0767v2 c0767v2);

    public Collection<C0729q2> d() {
        Collection<C0729q2> collectionH = h();
        if (h || com.android.tools.r8.utils.A1.O1) {
            return collectionH;
        }
        ArrayList arrayList = new ArrayList(collectionH);
        Collections.shuffle(arrayList);
        return AbstractC1639am.a(arrayList);
    }

    public Collection<C0729q2> e() {
        return a(new ArrayList(h()));
    }

    public final C0696l0 f() {
        return this.f;
    }

    public C3605b g() {
        return this.b;
    }

    abstract Collection h();

    public abstract N2 i();

    public final Q0 a(C3842a c3842a) {
        return new C0665h0(c3842a, this);
    }

    public static List a(ArrayList arrayList) {
        arrayList.sort(Comparator.comparing(new Function() { // from class: com.android.tools.r8.graph.j0$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((F) obj).getType();
            }
        }));
        return arrayList;
    }

    public static C0612b4.a a(com.android.tools.r8.utils.A1 a1, com.android.tools.r8.utils.y3 y3Var) {
        return new C0612b4.a(a1, y3Var);
    }
}
