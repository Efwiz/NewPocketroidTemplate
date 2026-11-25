package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yG, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3424yG extends DH {
    protected static final C1371Tf j = new C1371Tf();
    protected static final C1371Tf k = new C1371Tf();
    protected static final Map l = Collections.emptyMap();
    static final /* synthetic */ boolean m = true;
    protected final N4 f;
    protected final Function g;
    protected final Map h;
    protected J4 i;

    public AbstractC3424yG(C0702m c0702m) {
        this(c0702m, j, k, l);
    }

    protected com.android.tools.r8.graph.proto.k a(C0684j2 c0684j2, com.android.tools.r8.graph.proto.k kVar) {
        return kVar;
    }

    protected EnumC1953et b(C0684j2 c0684j2, C0684j2 c0684j3, EnumC1953et enumC1953et) {
        return enumC1953et;
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final com.android.tools.r8.graph.proto.k d(AbstractC1476Wj abstractC1476Wj, C0684j2 c0684j2) {
        if (this == abstractC1476Wj) {
            return AbstractC1476Wj.e().d(abstractC1476Wj, c0684j2);
        }
        return a(c0684j2, k().d(abstractC1476Wj, f(c0684j2)));
    }

    @Override // com.android.tools.r8.internal.DH
    protected final C0767v2 e(C0767v2 c0767v2) {
        return (C0767v2) this.h.getOrDefault(c0767v2, c0767v2);
    }

    @Override // com.android.tools.r8.internal.DH
    public C0684j2 f(C0684j2 c0684j2) {
        Object objC = this.i.c(c0684j2);
        Object obj = c0684j2;
        if (objC != null) {
            obj = objC;
        }
        return (C0684j2) obj;
    }

    protected Iterable g(C0767v2 c0767v2) {
        return com.android.tools.r8.utils.I1.b(c0767v2);
    }

    public C0684j2 h(C0684j2 c0684j2) {
        Object objB = this.i.b(c0684j2);
        Object obj = c0684j2;
        if (objB != null) {
            obj = objB;
        }
        return (C0684j2) obj;
    }

    protected boolean l() {
        return this instanceof C2416ks;
    }

    public final String toString() {
        final StringBuilder sb = new StringBuilder();
        this.h.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.yG$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                AbstractC3424yG.a(sb, (C0767v2) obj, (C0767v2) obj2);
            }
        });
        this.f.a(new BiConsumer() { // from class: com.android.tools.r8.internal.yG$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                AbstractC3424yG.a(sb, (Set) obj, (com.android.tools.r8.graph.Y0) obj2);
            }
        });
        sb.append(k().toString());
        return sb.toString();
    }

    public AbstractC3424yG(C0702m c0702m, N4 n4, N4 n5, Map map) {
        this(c0702m, n4, n5.e(), map, n5);
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public C0767v2 a(C0767v2 c0767v2) {
        return k().a(c0767v2);
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final Iterable b(C0767v2 c0767v2) {
        Iterable iterableG = g(c0767v2);
        final AbstractC1476Wj abstractC1476WjK = k();
        Objects.requireNonNull(abstractC1476WjK);
        return com.android.tools.r8.utils.I1.a(iterableG, new Function() { // from class: com.android.tools.r8.internal.yG$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return abstractC1476WjK.b((C0767v2) obj);
            }
        });
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public boolean e(C0684j2 c0684j2) {
        if (m || k().e(c0684j2)) {
            return true;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public boolean g() {
        return k().g();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AbstractC3424yG(C0702m c0702m, N4 n4, final Map map, Map map2, J4 j4) {
        this(c0702m, n4, new Function() { // from class: com.android.tools.r8.internal.yG$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (C0684j2) map.get((C0684j2) obj);
            }
        }, map2, j4);
        Objects.requireNonNull(map);
        if (!m && map2.isEmpty() && map.isEmpty() && n4.isEmpty() && !l()) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final com.android.tools.r8.graph.Y0 a(com.android.tools.r8.graph.Y0 y0) {
        Object objC = this.f.c(y0);
        Object obj = y0;
        if (objC != null) {
            obj = objC;
        }
        return k().a((com.android.tools.r8.graph.Y0) obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    /* JADX INFO: renamed from: b */
    public final C0684j2 c(AbstractC1476Wj abstractC1476Wj, C0684j2 c0684j2) {
        return this == abstractC1476Wj ? c0684j2 : h(k().c(abstractC1476Wj, c0684j2));
    }

    @Override // com.android.tools.r8.internal.DH
    protected C1237Oh b(C1237Oh c1237Oh) {
        if (c1237Oh.c()) {
            com.android.tools.r8.graph.Y0 y0 = (com.android.tools.r8.graph.Y0) c1237Oh.a(this.f);
            com.android.tools.r8.graph.Y0 y0B = c1237Oh.b() == c1237Oh.a() ? y0 : y0.b(e(((com.android.tools.r8.graph.Y0) c1237Oh.b()).B0()), j());
            C1210Nh c1210NhD = C1237Oh.d();
            c1210NhD.b = y0;
            c1210NhD.a = y0B;
            return c1210NhD.a(c1237Oh.b(new Function() { // from class: com.android.tools.r8.internal.yG$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.e((C0767v2) obj);
                }
            })).b(c1237Oh.c(new Function() { // from class: com.android.tools.r8.internal.yG$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.e((C0767v2) obj);
                }
            })).a();
        }
        com.android.tools.r8.graph.Y0 y1 = (com.android.tools.r8.graph.Y0) c1237Oh.b(this.f);
        C1210Nh c1210NhD2 = C1237Oh.d();
        c1210NhD2.a = y1;
        return c1210NhD2.a(c1237Oh.b(new Function() { // from class: com.android.tools.r8.internal.yG$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.e((C0767v2) obj);
            }
        })).b(c1237Oh.c(new Function() { // from class: com.android.tools.r8.internal.yG$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.e((C0767v2) obj);
            }
        })).a();
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    /* JADX INFO: renamed from: a */
    public final com.android.tools.r8.graph.Y0 b(AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.Y0 y0) {
        return this == abstractC1476Wj ? y0 : d(k().b(abstractC1476Wj, y0));
    }

    protected com.android.tools.r8.graph.Y0 d(com.android.tools.r8.graph.Y0 y0) {
        return (com.android.tools.r8.graph.Y0) this.f.getOrDefault(y0, y0);
    }

    public AbstractC3424yG(C0702m c0702m, N4 n4, Function function, Map map, J4 j4) {
        super(c0702m);
        this.f = n4;
        this.g = function;
        this.h = map;
        this.i = j4;
    }

    @Override // com.android.tools.r8.internal.DH
    public KE a(KE ke, C0684j2 c0684j2) {
        if (ke.c()) {
            C0684j2 c0684j3 = (C0684j2) ke.a(this.g);
            C0684j2 c0684j2B = ke.b() == ke.a() ? c0684j3 : c0684j3.b(e(((C0684j2) ke.b()).B0()), j());
            JE jeD = KE.d();
            jeD.a = c0684j2B;
            jeD.b = c0684j3;
            return jeD.a(a(c0684j3, ke.e())).a(b(c0684j3, (C0684j2) ke.b(), ke.f())).a();
        }
        C0684j2 c0684j4 = (C0684j2) this.g.apply((C0684j2) ke.b());
        if (c0684j4 == null) {
            c0684j4 = (C0684j2) ke.b();
        }
        com.android.tools.r8.graph.proto.k kVarA = a(c0684j4, ke.e());
        if (c0684j4 == ke.b() && kVarA == ke.e()) {
            return ke;
        }
        JE jeD2 = KE.d();
        jeD2.a = c0684j4;
        return jeD2.a(kVarA).a(b(c0684j4, (C0684j2) ke.b(), ke.f())).a();
    }

    public static EnumC1953et a(C0702m c0702m, C0684j2 c0684j2, C0684j2 c0684j3, EnumC1953et enumC1953et) {
        AbstractC0727q0 abstractC0727q0C;
        EnumC1953et enumC1953et2 = EnumC1953et.g;
        if ((enumC1953et != enumC1953et2 && enumC1953et != EnumC1953et.d) || (abstractC0727q0C = c0702m.c(c0684j2.B0())) == null) {
            return enumC1953et;
        }
        AbstractC0727q0 abstractC0727q0C2 = c0702m.c(c0684j3.B0());
        if (abstractC0727q0C2 != null) {
            boolean zIsInterface = abstractC0727q0C2.isInterface();
            EnumC1953et enumC1953et3 = EnumC1953et.d;
            if (zIsInterface ^ (enumC1953et == enumC1953et3)) {
                return abstractC0727q0C.e.W() ? enumC1953et2 : enumC1953et3;
            }
        }
        return abstractC0727q0C.e.W() ? EnumC1953et.d : enumC1953et2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(StringBuilder sb, C0767v2 c0767v2, C0767v2 c0767v3) {
        sb.append(c0767v2.L0()).append(" -> ").append(c0767v3.L0()).append(System.lineSeparator());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(StringBuilder sb, Set set, com.android.tools.r8.graph.Y0 y0) {
        sb.append((String) set.stream().map(new Function() { // from class: com.android.tools.r8.internal.yG$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((com.android.tools.r8.graph.Y0) obj).r0();
            }
        }).collect(Collectors.joining("," + System.lineSeparator())));
        sb.append(" -> ");
        sb.append(y0.r0()).append(System.lineSeparator());
    }
}
