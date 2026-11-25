package com.android.tools.r8.internal;

import com.android.tools.r8.TextInputStream;
import com.android.tools.r8.TextOutputStream;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.android.tools.r8.profile.art.ArtProfileBuilder;
import com.android.tools.r8.profile.art.ArtProfileConsumer;
import com.android.tools.r8.profile.art.ArtProfileProvider;
import com.android.tools.r8.profile.art.ArtProfileRuleConsumer;
import com.android.tools.r8.profile.art.diagnostic.HumanReadableArtProfileParserErrorDiagnostic;
import com.android.tools.r8.utils.InterfaceC3905h3;
import com.android.tools.r8.utils.InterfaceC3910i3;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.l3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2434l3 implements InterfaceC1882e1 {
    static final /* synthetic */ boolean b = true;
    private final Map a;

    C2434l3(LinkedHashMap linkedHashMap) {
        this.a = linkedHashMap;
    }

    public static a a() {
        return new a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(A3 a3, C3.a aVar) {
        C3 c3F = a3.f();
        aVar.getClass();
        if (c3F.isHot()) {
            aVar.d();
        }
        if (c3F.isStartup()) {
            aVar.f();
        }
        if (c3F.isPostStartup()) {
            aVar.e();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void d(A3 a3, C3.a aVar) {
        C3 c3F = a3.f();
        aVar.getClass();
        if (c3F.isHot()) {
            aVar.d();
        }
        if (c3F.isStartup()) {
            aVar.f();
        }
        if (c3F.isPostStartup()) {
            aVar.e();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void e(A3 a3, C3.a aVar) {
        C3 c3F = a3.f();
        aVar.getClass();
        if (c3F.isHot()) {
            aVar.d();
        }
        if (c3F.isStartup()) {
            aVar.f();
        }
        if (c3F.isPostStartup()) {
            aVar.e();
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC1882e1
    public final InterfaceC2197i1 b(C0684j2 c0684j2) {
        return (A3) this.a.get(c0684j2);
    }

    /* JADX INFO: renamed from: com.android.tools.r8.internal.l3$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a implements ArtProfileBuilder, InterfaceC1809d1 {
        private final ArtProfileProvider a;
        private final C0722p1 b;
        private com.android.tools.r8.utils.O2 c;
        private final LinkedHashMap d;

        a() {
            this.d = new LinkedHashMap();
            this.a = null;
            this.b = null;
            this.c = null;
        }

        @Override // com.android.tools.r8.profile.art.ArtProfileBuilder
        public final ArtProfileBuilder addClassRule(Consumer consumer) {
            C2895r3 c2895r3 = new C2895r3(this.b);
            consumer.accept(c2895r3);
            C2966s3 c2966s3Build = c2895r3.build();
            this.d.put(c2966s3Build.f(), c2966s3Build);
            return this;
        }

        @Override // com.android.tools.r8.profile.art.ArtProfileBuilder
        public final ArtProfileBuilder addMethodRule(Consumer consumer) {
            C0722p1 c0722p1 = this.b;
            boolean z = A3.c;
            A3.a aVar = new A3.a(c0722p1);
            consumer.accept(aVar);
            return a(aVar.build());
        }

        @Override // com.android.tools.r8.internal.InterfaceC1809d1
        public final InterfaceC1809d1 a(InterfaceC2275j1 interfaceC2275j1) {
            interfaceC2275j1.getClass();
            return a((K3) interfaceC2275j1);
        }

        @Override // com.android.tools.r8.internal.InterfaceC1809d1
        public final a a(C2966s3 c2966s3) {
            this.d.put(c2966s3.f(), c2966s3);
            return this;
        }

        @Override // com.android.tools.r8.internal.InterfaceC1809d1
        public final a a(final A3 a3) {
            this.d.compute(a3.e(), new BiFunction() { // from class: com.android.tools.r8.internal.l3$a$$ExternalSyntheticLambda0
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return C2434l3.a.a(a3, (AbstractC0744s2) obj, (K3) obj2);
                }
            });
            return this;
        }

        a(ArtProfileProvider artProfileProvider, com.android.tools.r8.utils.A1 a1) {
            this.d = new LinkedHashMap();
            this.a = artProfileProvider;
            this.b = a1.j0();
            this.c = a1.e;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ K3 a(A3 a3, AbstractC0744s2 abstractC0744s2, K3 k3) {
            if (k3 == null) {
                return a3;
            }
            return A3.d().b(a3.e()).a(a3).a((A3) k3).build();
        }

        public final a a(K3 k3) {
            return (a) k3.a(new InterfaceC3910i3() { // from class: com.android.tools.r8.internal.l3$a$$ExternalSyntheticLambda2
                @Override // com.android.tools.r8.utils.InterfaceC3910i3
                public final Object apply(Object obj) {
                    return this.f$0.a((C2966s3) obj);
                }
            }, new InterfaceC3910i3() { // from class: com.android.tools.r8.internal.l3$a$$ExternalSyntheticLambda3
                @Override // com.android.tools.r8.utils.InterfaceC3910i3
                public final Object apply(Object obj) {
                    return this.f$0.a((A3) obj);
                }
            });
        }

        public final a a(Collection collection) {
            collection.forEach(new Consumer() { // from class: com.android.tools.r8.internal.l3$a$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((J3) obj);
                }
            });
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(J3 j3) {
            a(j3.d());
        }

        @Override // com.android.tools.r8.profile.art.ArtProfileBuilder
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public final a addHumanReadableArtProfile(TextInputStream textInputStream, Consumer consumer) {
            C1269Pk.a aVarA = C1269Pk.a();
            final com.android.tools.r8.utils.O2 o2 = this.c;
            Objects.requireNonNull(o2);
            C1269Pk.a aVarA2 = aVarA.a(new Consumer() { // from class: com.android.tools.r8.internal.l3$a$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    o2.info((HumanReadableArtProfileParserErrorDiagnostic) obj);
                }
            }).a(this.c).a(this);
            consumer.accept(aVarA2);
            aVarA2.a().a(textInputStream, this.a.getOrigin());
            return this;
        }

        @Override // com.android.tools.r8.internal.InterfaceC1809d1
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public C2434l3 build() {
            return new C2434l3(this.d);
        }
    }

    public static a a(ArtProfileProvider artProfileProvider, com.android.tools.r8.utils.A1 a1) {
        return new a(artProfileProvider, a1);
    }

    public int b() {
        return this.a.size();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(A3 a3, C3.a aVar) {
        C3 c3F = a3.f();
        aVar.getClass();
        if (c3F.isHot()) {
            aVar.d();
        }
        if (c3F.isStartup()) {
            aVar.f();
        }
        if (c3F.isPostStartup()) {
            aVar.e();
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC1882e1
    public final boolean a(C0767v2 c0767v2) {
        return this.a.containsKey(c0767v2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1882e1
    public final boolean a(C0684j2 c0684j2) {
        return this.a.containsKey(c0684j2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1882e1
    public <E1 extends Exception, E2 extends Exception> void a(InterfaceC3905h3<? super C2966s3, E1> interfaceC3905h3, InterfaceC3905h3<? super A3, E2> interfaceC3905h4) throws Exception {
        Iterator it = this.a.values().iterator();
        while (it.hasNext()) {
            ((K3) it.next()).a(interfaceC3905h3, interfaceC3905h4);
        }
    }

    public final C2434l3 a(C0702m c0702m, final AbstractC1476Wj abstractC1476Wj) {
        abstractC1476Wj.getClass();
        if (abstractC1476Wj instanceof C1319Rg) {
            return a(c0702m, abstractC1476Wj.a());
        }
        return a(new BiConsumer() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda14
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C2434l3.a(abstractC1476Wj, (C2966s3) obj, (Consumer) obj2);
            }
        }, new com.android.tools.r8.utils.F3() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda15
            @Override // com.android.tools.r8.utils.F3
            public final void a(Object obj, Object obj2, Object obj3) {
                C2434l3.a(abstractC1476Wj, (A3) obj, (Consumer) obj2, (Function) obj3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ A3.a c(C0684j2 c0684j2) {
        return A3.d().b(c0684j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(AbstractC1476Wj abstractC1476Wj, C2966s3 c2966s3, Consumer consumer) {
        C0767v2 c0767v2A = AbstractC2964s2.a(abstractC1476Wj, c2966s3.f());
        if (!b && !c0767v2A.O0()) {
            throw new AssertionError();
        }
        consumer.accept(c0767v2A);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C2895r3 b(C0767v2 c0767v2) {
        return C2966s3.d().a(c0767v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(AbstractC1476Wj abstractC1476Wj, final A3 a3, Consumer consumer, Function function) {
        ((A3.a) function.apply(abstractC1476Wj.c((AbstractC1476Wj) null, a3.e()))).a(new Consumer() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2434l3.a(a3, (C3.a) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(A3 a3, C3.a aVar) {
        C3 c3F = a3.f();
        aVar.getClass();
        if (c3F.isHot()) {
            aVar.d();
        }
        if (c3F.isStartup()) {
            aVar.f();
        }
        if (c3F.isPostStartup()) {
            aVar.e();
        }
    }

    public final C2434l3 a(final C0702m c0702m, final C1319Rg c1319Rg) {
        return a(new BiConsumer() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda22
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C2434l3.a(c1319Rg, (C2966s3) obj, (Consumer) obj2);
            }
        }, new com.android.tools.r8.utils.F3() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda23
            @Override // com.android.tools.r8.utils.F3
            public final void a(Object obj, Object obj2, Object obj3) {
                C2434l3.a(c1319Rg, c0702m, (A3) obj, (Consumer) obj2, (Function) obj3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C1319Rg c1319Rg, C2966s3 c2966s3, Consumer consumer) {
        C0767v2 c0767v2F = c2966s3.f();
        c1319Rg.getClass();
        C0767v2 c0767v2A = c1319Rg.a(AbstractC1476Wj.e(), c0767v2F);
        if (c0767v2A.O0()) {
            consumer.accept(c0767v2A);
        } else if (!b && !c0767v2A.R0()) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C1319Rg c1319Rg, C0702m c0702m, final A3 a3, Consumer consumer, Function function) {
        C0684j2 c0684j2C = c1319Rg.c((AbstractC1476Wj) null, a3.e());
        if (c0684j2C.B0() != a3.e().B0()) {
            if (!b && !c0702m.D().a(c0684j2C.B0(), new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda12
                @Override // com.android.tools.r8.synthesis.C
                public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                    return l.i;
                }
            })) {
                throw new AssertionError();
            }
            consumer.accept(c0684j2C.B0());
        }
        ((A3.a) function.apply(c0684j2C)).a(new Consumer() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2434l3.b(a3, (C3.a) obj);
            }
        });
    }

    public final C2434l3 a(final AbstractC3649o0 abstractC3649o0, final C0722p1 c0722p1) {
        if (b || !abstractC3649o0.c()) {
            return a(new BiConsumer() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda24
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    C2434l3.a(abstractC3649o0, c0722p1, (C2966s3) obj, (Consumer) obj2);
                }
            }, new com.android.tools.r8.utils.F3() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda1
                @Override // com.android.tools.r8.utils.F3
                public final void a(Object obj, Object obj2, Object obj3) {
                    C2434l3.a(abstractC3649o0, c0722p1, (A3) obj, (Consumer) obj2, (Function) obj3);
                }
            });
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(AbstractC3649o0 abstractC3649o0, C0722p1 c0722p1, C2966s3 c2966s3, Consumer consumer) {
        consumer.accept(abstractC3649o0.a(c0722p1, c2966s3.f()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(AbstractC3649o0 abstractC3649o0, C0722p1 c0722p1, final A3 a3, Consumer consumer, Function function) {
        ((A3.a) function.apply(abstractC3649o0.a(c0722p1, a3.e()))).a(new Consumer() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2434l3.c(a3, (C3.a) obj);
            }
        });
    }

    public final C2434l3 a(C0702m c0702m) {
        final C0664h c0664hF = c0702m.f();
        return a(new BiConsumer() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda9
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C2434l3.a(c0664hF, (C2966s3) obj, (Consumer) obj2);
            }
        }, new com.android.tools.r8.utils.F3() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda10
            @Override // com.android.tools.r8.utils.F3
            public final void a(Object obj, Object obj2, Object obj3) {
                C2434l3.a(c0664hF, (A3) obj, (Consumer) obj2, (Function) obj3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0664h c0664h, C2966s3 c2966s3, Consumer consumer) {
        if (c0664h.d(c2966s3.f()) != null) {
            consumer.accept(c2966s3.f());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0664h c0664h, final A3 a3, Consumer consumer, Function function) {
        if (a3.e().c(c0664h.d(a3.e().B0())) != null) {
            ((A3.a) function.apply(a3.e())).a(new Consumer() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda18
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2434l3.d(a3, (C3.a) obj);
                }
            });
        }
    }

    public final C2434l3 a(final com.android.tools.r8.graph.u5 u5Var) {
        return a(new BiConsumer() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda11
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C2434l3.a(u5Var, (C2966s3) obj, (Consumer) obj2);
            }
        }, new com.android.tools.r8.utils.F3() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda17
            @Override // com.android.tools.r8.utils.F3
            public final void a(Object obj, Object obj2, Object obj3) {
                C2434l3.a(u5Var, (A3) obj, (Consumer) obj2, (Function) obj3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.u5 u5Var, C2966s3 c2966s3, Consumer consumer) {
        if (u5Var.a(c2966s3.f())) {
            return;
        }
        consumer.accept(c2966s3.f());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.u5 u5Var, final A3 a3, Consumer consumer, Function function) {
        if (u5Var.a(a3.e())) {
            return;
        }
        ((A3.a) function.apply(a3.e())).a(new Consumer() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2434l3.e(a3, (C3.a) obj);
            }
        });
    }

    private C2434l3 a(final BiConsumer biConsumer, final com.android.tools.r8.utils.F3 f3) throws Exception {
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        final Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2434l3.a(linkedHashMap, (C0767v2) obj);
            }
        };
        final Function function = new Function() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2434l3.a(linkedHashMap, (C0684j2) obj);
            }
        };
        a(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                biConsumer.accept((C2966s3) obj, consumer);
            }
        }, new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                f3.a((A3) obj, consumer, function);
            }
        });
        return new a().a(linkedHashMap.values()).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, final C0767v2 c0767v2) {
        ((J3) map.computeIfAbsent(c0767v2, com.android.tools.r8.utils.Z1.a(new Supplier() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda21
            @Override // java.util.function.Supplier
            public final Object get() {
                return C2434l3.b(c0767v2);
            }
        }))).b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ A3.a a(Map map, final C0684j2 c0684j2) {
        return ((J3) map.computeIfAbsent(c0684j2, com.android.tools.r8.utils.Z1.a(new Supplier() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda20
            @Override // java.util.function.Supplier
            public final Object get() {
                return C2434l3.c(c0684j2);
            }
        }))).c();
    }

    public void a(ArtProfileConsumer artProfileConsumer, com.android.tools.r8.utils.O2 o2) throws Exception {
        if (artProfileConsumer != null) {
            TextOutputStream humanReadableArtProfileConsumer = artProfileConsumer.getHumanReadableArtProfileConsumer();
            if (humanReadableArtProfileConsumer != null) {
                a(humanReadableArtProfileConsumer);
            }
            ArtProfileRuleConsumer ruleConsumer = artProfileConsumer.getRuleConsumer();
            if (ruleConsumer != null) {
                a(ruleConsumer);
            }
            artProfileConsumer.finished(o2);
        }
    }

    private void a(TextOutputStream textOutputStream) {
        try {
            final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(textOutputStream.getOutputStream(), textOutputStream.getCharset());
            try {
                InterfaceC3905h3 interfaceC3905h3 = new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda6
                    @Override // com.android.tools.r8.utils.InterfaceC3905h3
                    public final void accept(Object obj) throws IOException {
                        C2434l3.a(outputStreamWriter, (K3) obj);
                    }
                };
                Iterator it = this.a.values().iterator();
                while (it.hasNext()) {
                    interfaceC3905h3.accept((K3) it.next());
                }
                outputStreamWriter.close();
            } catch (Throwable th) {
                try {
                    outputStreamWriter.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(OutputStreamWriter outputStreamWriter, K3 k3) throws IOException {
        k3.a(outputStreamWriter);
        outputStreamWriter.write(10);
    }

    private void a(final ArtProfileRuleConsumer artProfileRuleConsumer) throws Exception {
        a(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                C2434l3.a(artProfileRuleConsumer, (C2966s3) obj);
            }
        }, new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.l3$$ExternalSyntheticLambda8
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                C2434l3.a(artProfileRuleConsumer, (A3) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(ArtProfileRuleConsumer artProfileRuleConsumer, C2966s3 c2966s3) {
        artProfileRuleConsumer.acceptClassRule(c2966s3.e(), C3035t3.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(ArtProfileRuleConsumer artProfileRuleConsumer, A3 a3) {
        artProfileRuleConsumer.acceptMethodRule(a3.getMethodReference(), a3.f());
    }
}
