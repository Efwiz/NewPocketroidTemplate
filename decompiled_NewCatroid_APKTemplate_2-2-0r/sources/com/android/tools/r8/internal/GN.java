package com.android.tools.r8.internal;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.naming.C3605b;
import com.android.tools.r8.naming.C3634k;
import com.android.tools.r8.naming.MapVersion;
import com.android.tools.r8.retrace.MappingPartition;
import com.android.tools.r8.retrace.MappingPartitionMetadata;
import com.android.tools.r8.retrace.ProguardMapPartitioner;
import com.android.tools.r8.retrace.ProguardMapPartitionerBuilder;
import com.android.tools.r8.retrace.ProguardMapProducer;
import com.android.tools.r8.retrace.internal.RetracePartitionException;
import com.android.tools.r8.utils.ExceptionDiagnostic;
import com.android.tools.r8.utils.StringDiagnostic;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class GN implements ProguardMapPartitioner {
    private final ProguardMapProducer a;
    private final Consumer b;
    private final DiagnosticsHandler c;
    private final boolean d;
    private final boolean e;
    private final EnumC3490zD f;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a implements ProguardMapPartitionerBuilder<a, GN> {
        protected ProguardMapProducer a;
        protected Consumer b;
        protected final DiagnosticsHandler c;
        protected boolean d = false;
        protected boolean e = false;

        public a(DiagnosticsHandler diagnosticsHandler) {
            this.c = diagnosticsHandler;
        }

        public a a(Consumer<MappingPartition> consumer) {
            this.b = consumer;
            return this;
        }

        @Override // com.android.tools.r8.retrace.ProguardMapPartitionerBuilder
        public final ProguardMapPartitionerBuilder setAllowEmptyMappedRanges(boolean z) {
            this.d = z;
            return this;
        }

        @Override // com.android.tools.r8.retrace.ProguardMapPartitionerBuilder
        public final ProguardMapPartitionerBuilder setAllowExperimentalMapping(boolean z) {
            this.e = z;
            return this;
        }

        @Override // com.android.tools.r8.retrace.ProguardMapPartitionerBuilder
        public final /* bridge */ /* synthetic */ ProguardMapPartitionerBuilder setPartitionConsumer(Consumer consumer) {
            return a((Consumer<MappingPartition>) consumer);
        }

        @Override // com.android.tools.r8.retrace.ProguardMapPartitionerBuilder
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public a setProguardMapProducer(ProguardMapProducer proguardMapProducer) {
            this.a = proguardMapProducer;
            return this;
        }

        @Override // com.android.tools.r8.retrace.ProguardMapPartitionerBuilder
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public GN build() {
            return new GN(this.a, this.b, this.c, this.d, this.e, EnumC3490zD.b);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class b extends a {
        private EnumC3490zD f;

        public b(DiagnosticsHandler diagnosticsHandler) {
            super(diagnosticsHandler);
            this.f = EnumC3490zD.c;
        }

        public b a(EnumC3490zD enumC3490zD) {
            this.f = enumC3490zD;
            return this;
        }

        @Override // com.android.tools.r8.internal.GN.a, com.android.tools.r8.retrace.ProguardMapPartitionerBuilder
        /* JADX INFO: renamed from: a */
        public final GN build() {
            return new GN(this.a, this.b, this.c, this.d, this.e, this.f);
        }
    }

    private GN(ProguardMapProducer proguardMapProducer, Consumer consumer, DiagnosticsHandler diagnosticsHandler, boolean z, boolean z2, EnumC3490zD enumC3490zD) {
        this.a = proguardMapProducer;
        this.b = consumer;
        this.c = diagnosticsHandler;
        this.d = z;
        this.e = z2;
        this.f = enumC3490zD;
    }

    private C3605b a(final com.android.tools.r8.utils.F3 f3) {
        final C3605b c3605bA = ((HN) this.a).a();
        c3605bA.b().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.GN$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                GN.a(f3, c3605bA, (String) obj, (C3634k) obj2);
            }
        });
        return c3605bA;
    }

    private C3605b b(final com.android.tools.r8.utils.F3 f3) {
        FN fn = new FN(this.a.isFileBacked() ? new KN(this.a.getPath(), CM.b(), true) : new JN(this.a.get(), CM.b(), true));
        final C3605b c3605bA = C3605b.a(this.c, fn, MapVersion.MAP_VERSION_UNKNOWN, this.d, this.e);
        fn.a(new BiConsumer() { // from class: com.android.tools.r8.internal.GN$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(f3, c3605bA, (String) obj, (List) obj2);
            }
        });
        return c3605bA;
    }

    @Override // com.android.tools.r8.retrace.ProguardMapPartitioner
    public MappingPartitionMetadata run() throws IOException {
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        com.android.tools.r8.utils.F3 f3 = new com.android.tools.r8.utils.F3() { // from class: com.android.tools.r8.internal.GN$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.F3
            public final void a(Object obj, Object obj2, Object obj3) {
                this.f$0.a(linkedHashSet, (C3605b) obj, (C3634k) obj2, (String) obj3);
            }
        };
        C3605b c3605bA = this.a instanceof HN ? a(f3) : b(f3);
        MapVersion mapVersionQ = MapVersion.MAP_VERSION_UNKNOWN;
        com.android.tools.r8.naming.mappinginformation.b bVarC = c3605bA.c();
        if (bVarC != null) {
            mapVersionQ = bVarC.q();
        }
        EnumC3490zD enumC3490zD = this.f;
        if (enumC3490zD == EnumC3490zD.b) {
            return AD.a(mapVersionQ);
        }
        if (enumC3490zD == EnumC3490zD.c) {
            return BD.a(mapVersionQ, C3491zE.a(linkedHashSet));
        }
        RetracePartitionException retracePartitionException = new RetracePartitionException("Unknown mapping partitioning strategy");
        this.c.error(new ExceptionDiagnostic(retracePartitionException));
        throw retracePartitionException;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.utils.F3 f3, C3605b c3605b, String str, C3634k c3634k) {
        f3.a(c3605b, c3634k, c3634k.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.utils.F3 f3, C3605b c3605b, String str, List list) {
        try {
            String strA = com.android.tools.r8.utils.Y2.a("\n", list);
            AbstractC2178hm abstractC2178hmB = C3605b.a(strA, (DiagnosticsHandler) null, this.d, this.e, false).b();
            if (abstractC2178hmB.size() != 1) {
                this.c.error(new StringDiagnostic("Multiple class names in payload\n: " + strA));
            } else {
                f3.a(c3605b, (C3634k) abstractC2178hmB.values().iterator().next(), strA);
            }
        } catch (IOException e) {
            this.c.error(new ExceptionDiagnostic(e));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(HashSet hashSet, final C3605b c3605b, C3634k c3634k, String str) {
        final HashSet hashSet2 = new HashSet();
        final StringBuilder sb = new StringBuilder();
        final Map<String, String> map = c3605b.e().b;
        c3634k.a(new Consumer() { // from class: com.android.tools.r8.internal.GN$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(hashSet2, sb, map, c3605b, (String) obj);
            }
        });
        sb.append(str);
        this.b.accept(new C3421yD(c3634k.b, sb.toString().getBytes(StandardCharsets.UTF_8)));
        hashSet.add(c3634k.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Set set, StringBuilder sb, Map map, C3605b c3605b, String str) {
        if (set.add(str)) {
            sb.append(a(str, (String) map.get(str), c3605b.c(str)));
        }
    }

    private static String a(String str, String str2, String str3) {
        if (str3 == null) {
            return "";
        }
        if (str2 == null) {
            str2 = str;
        }
        return str + " -> " + str2 + ":\n  # " + com.android.tools.r8.naming.mappinginformation.a.a(str3).p() + "\n";
    }
}
