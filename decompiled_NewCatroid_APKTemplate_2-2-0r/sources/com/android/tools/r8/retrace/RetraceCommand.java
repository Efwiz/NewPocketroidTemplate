package com.android.tools.r8.retrace;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.utils.N;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class RetraceCommand {
    static final /* synthetic */ boolean d = true;
    private final StackTraceSupplier a;
    private final Consumer b;
    private final RetraceOptions c;

    private RetraceCommand(StackTraceSupplier stackTraceSupplier, Consumer consumer, RetraceOptions retraceOptions) {
        this.a = stackTraceSupplier;
        this.b = consumer;
        this.c = retraceOptions;
        boolean z = d;
        if (!z && stackTraceSupplier == null && !retraceOptions.isVerifyMappingFileHash()) {
            throw new AssertionError();
        }
        if (!z && consumer == null) {
            throw new AssertionError();
        }
    }

    public static Builder builder(DiagnosticsHandler diagnosticsHandler) {
        return new Builder(diagnosticsHandler);
    }

    public RetraceOptions getOptions() {
        return this.c;
    }

    public Consumer<List<String>> getRetracedStackTraceConsumer() {
        return this.b;
    }

    public StackTraceSupplier getStacktraceSupplier() {
        return this.a;
    }

    public boolean printMemory() {
        return System.getProperty("com.android.tools.r8.printmemory") != null;
    }

    public boolean printTimes() {
        return System.getProperty("com.android.tools.r8.printtimes") != null;
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class Builder {
        private boolean a;
        private final DiagnosticsHandler b;
        private MappingSupplier c;
        private String d;
        private StackTraceSupplier e;
        private Consumer f;
        private boolean g;

        private Builder(DiagnosticsHandler diagnosticsHandler) {
            this.d = "(?:.*?\\bat\\s+%c\\.%m\\s*\\(%S\\)\\p{Z}*(?:~\\[.*\\])?)|(?:(?:(?:%c|.*)?[:\"]\\s+)?%c(?::.*)?)";
            this.g = false;
            this.b = diagnosticsHandler;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ List a(N n) {
            return (List) n.b();
        }

        public RetraceCommand build() {
            DiagnosticsHandler diagnosticsHandler = this.b;
            if (diagnosticsHandler == null) {
                throw new RuntimeException("DiagnosticsHandler not specified");
            }
            if (this.c == null) {
                throw new RuntimeException("ProguardMapSupplier not specified");
            }
            if (this.e == null && !this.g) {
                throw new RuntimeException("StackTrace not specified");
            }
            if (this.f == null) {
                throw new RuntimeException("RetracedStackConsumer not specified");
            }
            return new RetraceCommand(this.e, this.f, RetraceOptions.builder(diagnosticsHandler).setRegularExpression(this.d).setMappingSupplier(this.c).setVerbose(this.a).setVerifyMappingFileHash(this.g).build());
        }

        public Builder setMappingSupplier(MappingSupplier<?> mappingSupplier) {
            this.c = mappingSupplier;
            return this;
        }

        public Builder setRegularExpression(String str) {
            this.d = str;
            return this;
        }

        public Builder setRetracedStackTraceConsumer(Consumer<List<String>> consumer) {
            this.f = consumer;
            return this;
        }

        public Builder setStackTrace(List<String> list) {
            final N n = new N(list);
            return setStackTrace(new StackTraceSupplier() { // from class: com.android.tools.r8.retrace.RetraceCommand$Builder$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.retrace.StackTraceSupplier
                public final List get() {
                    return RetraceCommand.Builder.a(n);
                }
            });
        }

        public Builder setVerbose(boolean z) {
            this.a = z;
            return this;
        }

        public Builder setVerifyMappingFileHash(boolean z) {
            this.g = z;
            return this;
        }

        public Builder setStackTrace(StackTraceSupplier stackTraceSupplier) {
            this.e = stackTraceSupplier;
            return this;
        }
    }

    public static Builder builder() {
        return new Builder(new f());
    }
}
