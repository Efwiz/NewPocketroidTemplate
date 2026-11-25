package com.android.tools.r8;

import com.android.tools.r8.utils.O2;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class FeatureSplit {
    public static final FeatureSplit BASE = new H();
    public static final FeatureSplit BASE_STARTUP = new I();
    private final ProgramConsumer a;
    private final List b;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class Builder {
        private ProgramConsumer a;
        private final ArrayList b;
        private final DiagnosticsHandler c;

        private Builder(O2 o2) {
            this.b = new ArrayList();
            this.c = o2;
        }

        public Builder addProgramResourceProvider(ProgramResourceProvider programResourceProvider) {
            this.b.add(programResourceProvider);
            return this;
        }

        public FeatureSplit build() {
            return new FeatureSplit(this.a, this.b);
        }

        public Builder setProgramConsumer(ProgramConsumer programConsumer) {
            this.a = programConsumer;
            return this;
        }
    }

    private FeatureSplit(ProgramConsumer programConsumer, ArrayList arrayList) {
        this.a = programConsumer;
        this.b = arrayList;
    }

    static Builder a(O2 o2) {
        return new Builder(o2);
    }

    public ProgramConsumer getProgramConsumer() {
        return this.a;
    }

    public List<ProgramResourceProvider> getProgramResourceProviders() {
        return this.b;
    }

    public boolean isBase() {
        return false;
    }

    public boolean isStartupBase() {
        return false;
    }
}
