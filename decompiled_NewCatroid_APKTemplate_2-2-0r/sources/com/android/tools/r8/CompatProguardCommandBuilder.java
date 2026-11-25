package com.android.tools.r8;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class CompatProguardCommandBuilder extends R8Command.Builder {
    public CompatProguardCommandBuilder() {
        this(true);
    }

    public CompatProguardCommandBuilder(boolean z, DiagnosticsHandler diagnosticsHandler) {
        super(diagnosticsHandler);
        setProguardCompatibility(z);
    }

    public CompatProguardCommandBuilder(boolean z) {
        this(z, false);
    }

    public CompatProguardCommandBuilder(boolean z, boolean z2) {
        setProguardCompatibility(z);
        c(z2);
    }
}
