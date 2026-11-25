package com.android.tools.r8.tracereferences;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.StringConsumer;
import com.android.tools.r8.internal.C2392kZ;
import java.nio.file.Path;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class TraceReferencesKeepRules extends TraceReferencesConsumer.ForwardingConsumer {
    private final C2392kZ c;
    private final StringConsumer d;
    private final boolean e;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class Builder {
        private StringConsumer a;
        private boolean b;

        public TraceReferencesKeepRules build() {
            return new TraceReferencesKeepRules(new C2392kZ(), this.a, this.b);
        }

        public Builder setAllowObfuscation(boolean z) {
            this.b = z;
            return this;
        }

        public Builder setOutputConsumer(StringConsumer stringConsumer) {
            this.a = stringConsumer;
            return this;
        }

        public Builder setOutputPath(Path path) {
            this.a = new StringConsumer.FileConsumer(path);
            return this;
        }
    }

    private TraceReferencesKeepRules(C2392kZ c2392kZ, StringConsumer stringConsumer, boolean z) {
        super(c2392kZ);
        this.c = c2392kZ;
        this.d = stringConsumer;
        this.e = z;
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean allowObfuscation() {
        return this.e;
    }

    @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer.ForwardingConsumer, com.android.tools.r8.tracereferences.TraceReferencesConsumer
    public void finished(DiagnosticsHandler diagnosticsHandler) {
        super.finished(diagnosticsHandler);
        b bVar = new b(this.e);
        bVar.a(this.c.a());
        this.d.accept(bVar.a(), diagnosticsHandler);
        this.d.finished(diagnosticsHandler);
    }
}
