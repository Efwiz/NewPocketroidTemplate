package com.android.tools.r8.shaking;

import com.android.tools.r8.experimental.graphinfo.AnnotationGraphNode;
import com.android.tools.r8.experimental.graphinfo.ClassGraphNode;
import com.android.tools.r8.experimental.graphinfo.FieldGraphNode;
import com.android.tools.r8.experimental.graphinfo.GraphConsumer;
import com.android.tools.r8.experimental.graphinfo.GraphNode;
import com.android.tools.r8.experimental.graphinfo.KeepRuleGraphNode;
import com.android.tools.r8.experimental.graphinfo.MethodGraphNode;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.graph.m5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C1349Sj;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.utils.AbstractC3962s0;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class M0 {
    static final /* synthetic */ boolean k = true;
    private final C0702m a;
    private final com.android.tools.r8.utils.A1 b;
    private final GraphConsumer c;
    private final C3798t d;
    private final IdentityHashMap e = new IdentityHashMap();
    private final IdentityHashMap f = new IdentityHashMap();
    private final IdentityHashMap g = new IdentityHashMap();
    private final IdentityHashMap h = new IdentityHashMap();
    private final IdentityHashMap i = new IdentityHashMap();
    private final IdentityHashMap j = new IdentityHashMap();

    M0(C0702m c0702m, GraphConsumer graphConsumer) {
        this.a = c0702m;
        this.b = c0702m.Y();
        if (!c0702m.Y().n1.x0) {
            this.d = null;
            this.c = graphConsumer;
        } else {
            C3798t c3798t = new C3798t(graphConsumer);
            this.d = c3798t;
            this.c = c3798t;
        }
    }

    public static L0 a() {
        return L0.a;
    }

    public static L0 b() {
        return L0.a;
    }

    public final L0 c(C0729q2 c0729q2, o5 o5Var) {
        if (o5Var != null) {
            if (!k && !o5Var.e().u1()) {
                throw new AssertionError();
            }
            if (this.c != null) {
                a(a(c0729q2.d), a(o5Var.getReference()), C1349Sj.a.ReachableFromLiveType);
                return L0.a;
            }
        } else if (!k && c0729q2.p1()) {
            throw new AssertionError();
        }
        return L0.a;
    }

    public final boolean a(C0729q2 c0729q2) {
        if (!k && this.d == null) {
            throw new AssertionError();
        }
        ClassGraphNode classGraphNodeA = a(c0729q2.d);
        Set setC = OU.c();
        ArrayDeque arrayDequeA = AbstractC3962s0.a(classGraphNodeA);
        while (!arrayDequeA.isEmpty()) {
            GraphNode graphNode = (GraphNode) arrayDequeA.pop();
            if ((graphNode instanceof KeepRuleGraphNode) && ((KeepRuleGraphNode) graphNode).getPreconditions().isEmpty()) {
                return true;
            }
            if (setC.add(graphNode)) {
                Map<GraphNode, Set<C1349Sj>> mapA = this.d.a(graphNode);
                boolean z = k;
                if (!z && mapA == null) {
                    throw new AssertionError("No sources set for " + graphNode);
                }
                if (!z && mapA.isEmpty()) {
                    throw new AssertionError("Empty sources set for " + graphNode);
                }
                arrayDequeA.addAll(mapA.keySet());
            }
        }
        if (k) {
            return false;
        }
        throw new AssertionError("No rooted path to " + c0729q2.d);
    }

    public final L0 b(C0729q2 c0729q2, o5 o5Var) {
        if (this.c != null) {
            a(a(o5Var.getReference()), a(c0729q2.d), C1349Sj.a.CompatibilityRule);
        }
        return L0.a;
    }

    public final void b(C0633d0 c0633d0, InterfaceC0694k5 interfaceC0694k5) {
        if (this.c != null) {
            a(a(c0633d0, interfaceC0694k5), AbstractC3837z1.a(interfaceC0694k5.e()));
            a(a(c0633d0.t0()), AbstractC3837z1.a(c0633d0, interfaceC0694k5));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ClassGraphNode b(C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0D = this.a.f().d(c0767v2);
        return new ClassGraphNode(abstractC0727q0D != null && abstractC0727q0D.I1(), Reference.classFromDescriptor(c0767v2.b1()));
    }

    final L0 a(com.android.tools.r8.graph.O0 o0, Collection collection, C0729q2 c0729q2) {
        C1349Sj.a aVar;
        if (!k && collection.isEmpty() && this.b.o1()) {
            throw new AssertionError();
        }
        if (this.c != null) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                C3737i3 c3737i3 = (C3737i3) it.next();
                if (this.c != null) {
                    KeepRuleGraphNode keepRuleGraphNodeA = a(o0, c3737i3);
                    if (keepRuleGraphNodeA.getPreconditions().isEmpty()) {
                        aVar = C1349Sj.a.KeepRule;
                    } else {
                        Iterator<GraphNode> it2 = keepRuleGraphNodeA.getPreconditions().iterator();
                        while (it2.hasNext()) {
                            a(it2.next(), keepRuleGraphNodeA, C1349Sj.a.KeepRulePrecondition);
                        }
                        aVar = C1349Sj.a.ConditionalKeepRule;
                    }
                    a(keepRuleGraphNodeA, a(c0729q2.d), aVar);
                }
            }
        }
        return L0.a;
    }

    final L0 a(com.android.tools.r8.graph.O0 o0, Set set, com.android.tools.r8.graph.W0 w0) {
        C1349Sj.a aVar;
        if (!k && set.isEmpty() && this.b.o1()) {
            throw new AssertionError();
        }
        if (this.c != null) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                C3737i3 c3737i3 = (C3737i3) it.next();
                if (this.c != null) {
                    KeepRuleGraphNode keepRuleGraphNodeA = a(o0, c3737i3);
                    if (keepRuleGraphNodeA.getPreconditions().isEmpty()) {
                        aVar = C1349Sj.a.KeepRule;
                    } else {
                        Iterator<GraphNode> it2 = keepRuleGraphNodeA.getPreconditions().iterator();
                        while (it2.hasNext()) {
                            a(it2.next(), keepRuleGraphNodeA, C1349Sj.a.KeepRulePrecondition);
                        }
                        aVar = C1349Sj.a.ConditionalKeepRule;
                    }
                    a(keepRuleGraphNodeA, a(w0.getReference()), aVar);
                }
            }
        }
        return L0.a;
    }

    final L0 a(com.android.tools.r8.graph.O0 o0, HashSet hashSet, Set set, com.android.tools.r8.graph.T0 t0) {
        C1349Sj.a aVar;
        if (!k && hashSet.isEmpty() && set.isEmpty() && this.b.o1()) {
            throw new AssertionError();
        }
        if (this.c != null) {
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                a(t0, (AbstractC3837z1) it.next());
            }
            Iterator it2 = set.iterator();
            while (it2.hasNext()) {
                C3737i3 c3737i3 = (C3737i3) it2.next();
                if (this.c != null) {
                    KeepRuleGraphNode keepRuleGraphNodeA = a(o0, c3737i3);
                    if (keepRuleGraphNodeA.getPreconditions().isEmpty()) {
                        aVar = C1349Sj.a.KeepRule;
                    } else {
                        Iterator<GraphNode> it3 = keepRuleGraphNodeA.getPreconditions().iterator();
                        while (it3.hasNext()) {
                            a(it3.next(), keepRuleGraphNodeA, C1349Sj.a.KeepRulePrecondition);
                        }
                        aVar = C1349Sj.a.ConditionalKeepRule;
                    }
                    a(keepRuleGraphNodeA, a(t0.getReference()), aVar);
                }
            }
        }
        return L0.a;
    }

    public final L0 a(o5 o5Var) {
        if (!k) {
            C0729q2 c0729q2A = o5Var.a();
            c0729q2A.getClass();
            if (c0729q2A.a(C0767v2.g) != o5Var.e()) {
                throw new AssertionError();
            }
        }
        if (this.c != null) {
            a(a(o5Var.t()), a(o5Var.getReference()), C1349Sj.a.CompatibilityRule);
        }
        return L0.a;
    }

    public final L0 a(C0729q2 c0729q2, InterfaceC0694k5 interfaceC0694k5) {
        if (interfaceC0694k5.e0()) {
            return a(c0729q2, interfaceC0694k5.a0());
        }
        if (interfaceC0694k5.l0()) {
            m5 m5VarQ = interfaceC0694k5.Q();
            if (this.c != null) {
                a(a(m5VarQ.getReference()), a(c0729q2.d), C1349Sj.a.ReferencedFrom);
                return L0.a;
            }
            return L0.a;
        }
        if (k || interfaceC0694k5.k()) {
            return a(c0729q2, interfaceC0694k5.g0());
        }
        throw new AssertionError();
    }

    public final L0 a(C0729q2 c0729q2, C0729q2 c0729q3) {
        if (this.c != null) {
            a(a(c0729q3.d), a(c0729q2.d), C1349Sj.a.ReferencedFrom);
            return L0.a;
        }
        return L0.a;
    }

    public final L0 a(C0729q2 c0729q2, o5 o5Var) {
        if (this.c != null) {
            a(a(o5Var.getReference()), a(c0729q2.d), C1349Sj.a.ReferencedFrom);
            return L0.a;
        }
        return L0.a;
    }

    public final L0 a(C0684j2 c0684j2, o5 o5Var) {
        if (this.c != null && c0684j2 != o5Var.e().getReference()) {
            a(a(c0684j2), a(o5Var.e().getReference()), C1349Sj.a.OverridingMethod);
            return L0.a;
        }
        return L0.a;
    }

    public final L0 a(U0 u0, o5 o5Var) {
        if (this.c != null) {
            u0.getClass();
            if (u0 instanceof S0) {
                a(a(u0.a().d), a(o5Var.e().getReference()), C1349Sj.a.IsLibraryMethod);
                return L0.a;
            }
        }
        return L0.a;
    }

    private void a(GraphNode graphNode, GraphNode graphNode2, C1349Sj.a aVar) {
        if (!k && this.c == null) {
            throw new AssertionError();
        }
        this.c.acceptEdge(graphNode, graphNode2, a(aVar));
    }

    private boolean a(AbstractC3837z1 abstractC3837z1) {
        boolean z = k;
        if (!z && abstractC3837z1 == null) {
            throw new AssertionError();
        }
        if (abstractC3837z1 == L0.a) {
            return true;
        }
        if (z || abstractC3837z1.a(this) != null) {
            return !(this.c != null);
        }
        throw new AssertionError();
    }

    public final L0 a(C0729q2 c0729q2, AbstractC3837z1 abstractC3837z1) {
        if (a(abstractC3837z1)) {
            return L0.a;
        }
        a(a(c0729q2.d), abstractC3837z1);
        return L0.a;
    }

    public final L0 a(com.android.tools.r8.graph.W0 w0, AbstractC3837z1 abstractC3837z1) {
        if (a(abstractC3837z1)) {
            return L0.a;
        }
        if (abstractC3837z1.a() == C1349Sj.a.IsLibraryMethod) {
            AbstractC0727q0 abstractC0727q0C = this.a.c(w0.J0());
            if (abstractC0727q0C == null || abstractC0727q0C.I1()) {
                return L0.a;
            }
        }
        a(a(w0.getReference()), abstractC3837z1);
        return L0.a;
    }

    public final L0 a(com.android.tools.r8.graph.T0 t0, AbstractC3837z1 abstractC3837z1) {
        if (a(abstractC3837z1)) {
            return L0.a;
        }
        a(a(t0.getReference()), abstractC3837z1);
        return L0.a;
    }

    private void a(GraphNode graphNode, AbstractC3837z1 abstractC3837z1) {
        if (!k && a(abstractC3837z1)) {
            throw new AssertionError();
        }
        GraphNode graphNodeA = abstractC3837z1.a(this);
        if (graphNodeA.isLibraryNode()) {
            return;
        }
        this.c.acceptEdge(graphNodeA, graphNode, a(abstractC3837z1.a()));
    }

    public final GraphNode a(AbstractC0744s2 abstractC0744s2) {
        abstractC0744s2.getClass();
        if (abstractC0744s2 instanceof C0767v2) {
            return a(abstractC0744s2.w0());
        }
        if (abstractC0744s2.y0()) {
            return a(abstractC0744s2.v0());
        }
        if (abstractC0744s2.x0()) {
            return a(abstractC0744s2.t0());
        }
        throw new C3405y10();
    }

    final C1349Sj a(C1349Sj.a aVar) {
        return (C1349Sj) this.j.computeIfAbsent(aVar, new Function() { // from class: com.android.tools.r8.shaking.M0$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new C1349Sj((C1349Sj.a) obj);
            }
        });
    }

    final AnnotationGraphNode a(final C0633d0 c0633d0, final InterfaceC0694k5 interfaceC0694k5) {
        return (AnnotationGraphNode) this.e.computeIfAbsent(c0633d0, new Function() { // from class: com.android.tools.r8.shaking.M0$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(interfaceC0694k5, c0633d0, (C0633d0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ AnnotationGraphNode a(InterfaceC0694k5 interfaceC0694k5, C0633d0 c0633d0, C0633d0 c0633d1) {
        return new AnnotationGraphNode((GraphNode) interfaceC0694k5.getReference().a(new Function() { // from class: com.android.tools.r8.shaking.M0$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((C0767v2) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.shaking.M0$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((com.android.tools.r8.graph.Y0) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.shaking.M0$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((C0684j2) obj);
            }
        }), a(c0633d0.t0()));
    }

    final ClassGraphNode a(C0767v2 c0767v2) {
        return (ClassGraphNode) this.f.computeIfAbsent(c0767v2, new Function() { // from class: com.android.tools.r8.shaking.M0$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b((C0767v2) obj);
            }
        });
    }

    final MethodGraphNode a(final C0684j2 c0684j2) {
        return (MethodGraphNode) this.g.computeIfAbsent(c0684j2, new Function() { // from class: com.android.tools.r8.shaking.M0$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(c0684j2, (C0684j2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MethodGraphNode a(C0684j2 c0684j2, C0684j2 c0684j3) {
        AbstractC0727q0 abstractC0727q0D = this.a.f().d(c0684j2.e);
        C1507Xl c1507XlG = AbstractC1639am.g();
        boolean z = false;
        for (C0767v2 c0767v2 : c0684j3.h.f.a) {
            c1507XlG.a(Reference.typeFromDescriptor(c0767v2.b1()));
        }
        if (abstractC0727q0D != null && abstractC0727q0D.I1()) {
            z = true;
        }
        return new MethodGraphNode(z, Reference.method(Reference.classFromDescriptor(c0684j3.e.b1()), c0684j3.f.toString(), c1507XlG.a(), c0684j3.h.e.Y0() ? null : Reference.typeFromDescriptor(c0684j3.h.e.b1())));
    }

    final FieldGraphNode a(final com.android.tools.r8.graph.Y0 y0) {
        return (FieldGraphNode) this.h.computeIfAbsent(y0, new Function() { // from class: com.android.tools.r8.shaking.M0$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(y0, (com.android.tools.r8.graph.Y0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public FieldGraphNode a(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
        AbstractC0727q0 abstractC0727q0D = this.a.f().d(y0.e);
        return new FieldGraphNode(abstractC0727q0D != null && abstractC0727q0D.I1(), Reference.field(Reference.classFromDescriptor(y1.e.b1()), y1.f.toString(), Reference.typeFromDescriptor(y1.h.b1())));
    }

    final KeepRuleGraphNode a(com.android.tools.r8.graph.O0 o0, final C3737i3 c3737i3) {
        final Set setEmptySet;
        if (c3737i3 instanceof C3732h3) {
            if (o0 != null) {
                setEmptySet = Collections.singleton(a(o0.getReference()));
            } else {
                setEmptySet = Collections.emptySet();
            }
            return (KeepRuleGraphNode) this.i.computeIfAbsent(c3737i3, new Function() { // from class: com.android.tools.r8.shaking.M0$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return M0.a(c3737i3, setEmptySet, (C3737i3) obj);
                }
            });
        }
        if (c3737i3 instanceof C3712e3) {
            final C3712e3 c3712e3 = (C3712e3) c3737i3;
            if (k || !c3712e3.J().isEmpty()) {
                return (KeepRuleGraphNode) this.i.computeIfAbsent(c3712e3, new Function() { // from class: com.android.tools.r8.shaking.M0$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return this.f$0.a(c3712e3, (C3737i3) obj);
                    }
                });
            }
            throw new AssertionError();
        }
        throw new C3405y10("Unexpected type of keep rule: " + c3737i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ KeepRuleGraphNode a(C3737i3 c3737i3, Set set, C3737i3 c3737i4) {
        return new KeepRuleGraphNode(c3737i3, set);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ KeepRuleGraphNode a(C3712e3 c3712e3, C3737i3 c3737i3) {
        HashSet hashSet = new HashSet(c3712e3.J().size());
        Iterator it = c3712e3.J().iterator();
        while (it.hasNext()) {
            hashSet.add(a((AbstractC0744s2) it.next()));
        }
        return new KeepRuleGraphNode(c3712e3, hashSet);
    }
}
