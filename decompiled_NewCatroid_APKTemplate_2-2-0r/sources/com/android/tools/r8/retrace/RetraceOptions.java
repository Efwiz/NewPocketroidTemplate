package com.android.tools.r8.retrace;

import com.android.tools.r8.DiagnosticsHandler;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class RetraceOptions {
    static final /* synthetic */ boolean f = true;
    private final boolean a;
    private final boolean b;
    private final String c;
    private final DiagnosticsHandler d;
    private final MappingSupplier e;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class Builder {
        private boolean a;
        private boolean b;
        private final DiagnosticsHandler c;
        private MappingSupplier d;
        private String e = RetraceOptions.defaultRegularExpression();

        Builder(DiagnosticsHandler diagnosticsHandler) {
            this.c = diagnosticsHandler;
        }

        public RetraceOptions build() {
            if (this.c == null) {
                throw new RuntimeException("DiagnosticsHandler not specified");
            }
            if (this.d == null) {
                throw new RuntimeException("ProguardMapSupplier not specified");
            }
            if (this.e != null) {
                return new RetraceOptions(this.e, this.c, this.d, this.a, this.b);
            }
            throw new RuntimeException("Regular expression not specified");
        }

        public Builder setMappingSupplier(MappingSupplier<?> mappingSupplier) {
            this.d = mappingSupplier;
            return this;
        }

        public Builder setProguardMapProducer(ProguardMapProducer proguardMapProducer) {
            return setMappingSupplier(ProguardMappingSupplier.builder().setProguardMapProducer(proguardMapProducer).build());
        }

        public Builder setRegularExpression(String str) {
            this.e = str;
            return this;
        }

        public Builder setVerbose(boolean z) {
            this.a = z;
            return this;
        }

        public Builder setVerifyMappingFileHash(boolean z) {
            this.b = z;
            return this;
        }
    }

    private RetraceOptions(String str, DiagnosticsHandler diagnosticsHandler, MappingSupplier mappingSupplier, boolean z, boolean z2) {
        this.c = str;
        this.d = diagnosticsHandler;
        this.e = mappingSupplier;
        this.a = z;
        this.b = z2;
        boolean z3 = f;
        if (!z3 && diagnosticsHandler == null) {
            throw new AssertionError();
        }
        if (!z3 && mappingSupplier == null) {
            throw new AssertionError();
        }
    }

    public static Builder builder() {
        return builder(new g());
    }

    public static String defaultRegularExpression() {
        return "(?:.*?\\bat\\s+%c\\.%m\\s*\\(%S\\)\\p{Z}*(?:~\\[.*\\])?)|(?:(?:(?:%c|.*)?[:\"]\\s+)?%c(?::.*)?)";
    }

    public DiagnosticsHandler getDiagnosticsHandler() {
        return this.d;
    }

    public MappingSupplier<?> getMappingSupplier() {
        return this.e;
    }

    public String getRegularExpression() {
        return this.c;
    }

    public boolean isVerbose() {
        return this.a;
    }

    public boolean isVerifyMappingFileHash() {
        return this.b;
    }

    public static Builder builder(DiagnosticsHandler diagnosticsHandler) {
        return new Builder(diagnosticsHandler);
    }
}
