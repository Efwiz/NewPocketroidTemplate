package com.android.tools.r8.naming;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.HN;
import com.android.tools.r8.retrace.MappingPartition;
import com.android.tools.r8.retrace.MappingPartitionMetadata;
import com.android.tools.r8.retrace.ProguardMapPartitioner;
import java.io.IOException;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class D0 extends com.android.tools.r8.T {
    private final Consumer a;
    private final Consumer b;
    private final Runnable c;
    private final DiagnosticsHandler d;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        private Consumer a;
        private Consumer b;
        private Runnable c;
        private DiagnosticsHandler d;

        public a a(Consumer<MappingPartition> consumer) {
            this.a = consumer;
            return this;
        }

        public a b(Consumer<MappingPartitionMetadata> consumer) {
            this.b = consumer;
            return this;
        }

        public a a(Runnable runnable) {
            this.c = runnable;
            return this;
        }

        public a a(DiagnosticsHandler diagnosticsHandler) {
            this.d = diagnosticsHandler;
            return this;
        }

        public D0 a() {
            return new D0(this.a, this.b, this.c, this.d);
        }
    }

    private D0(Consumer consumer, Consumer consumer2, Runnable runnable, DiagnosticsHandler diagnosticsHandler) {
        this.a = consumer;
        this.b = consumer2;
        this.c = runnable;
        this.d = diagnosticsHandler;
    }

    @Override // com.android.tools.r8.T
    public final void a(y0 y0Var, C3605b c3605b) {
        try {
            this.b.accept(ProguardMapPartitioner.builder(this.d).setProguardMapProducer(new HN(c3605b)).setPartitionConsumer(this.a).setAllowEmptyMappedRanges(false).setAllowExperimentalMapping(false).build().run());
        } catch (IOException unused) {
            throw new C3405y10("IOExceptions should only occur when parsing");
        }
    }

    @Override // com.android.tools.r8.J
    public final void finished(DiagnosticsHandler diagnosticsHandler) {
        this.c.run();
    }

    public static a a() {
        return new a();
    }
}
