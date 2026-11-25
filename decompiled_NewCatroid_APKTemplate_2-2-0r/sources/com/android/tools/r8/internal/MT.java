package com.android.tools.r8.internal;

import com.android.tools.r8.retrace.RetraceUnknownJsonMappingInformationResult;
import com.android.tools.r8.retrace.RetraceUnknownMappingInformationElement;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class MT implements RetraceUnknownMappingInformationElement {
    private final RetraceUnknownJsonMappingInformationResult a;
    private final C2431l10 b;

    MT(RetraceUnknownJsonMappingInformationResult retraceUnknownJsonMappingInformationResult, C2431l10 c2431l10) {
        this.a = retraceUnknownJsonMappingInformationResult;
        this.b = c2431l10;
    }

    @Override // com.android.tools.r8.retrace.RetraceUnknownMappingInformationElement
    public final String getIdentifier() {
        return this.b.q();
    }

    @Override // com.android.tools.r8.retrace.RetraceUnknownMappingInformationElement
    public final String getPayLoad() {
        return this.b.r();
    }

    @Override // com.android.tools.r8.retrace.RetraceUnknownMappingInformationElement
    public final RetraceUnknownJsonMappingInformationResult getRetraceResultContext() {
        return this.a;
    }
}
