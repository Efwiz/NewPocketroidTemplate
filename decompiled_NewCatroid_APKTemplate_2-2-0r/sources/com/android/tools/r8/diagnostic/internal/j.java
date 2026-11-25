package com.android.tools.r8.diagnostic.internal;

import com.android.tools.r8.diagnostic.MissingDefinitionInfo;
import com.android.tools.r8.internal.AbstractC1639am;
import java.util.Collection;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class j implements MissingDefinitionInfo {
    final Collection a;

    j(AbstractC1639am abstractC1639am) {
        this.a = abstractC1639am;
    }

    @Override // com.android.tools.r8.diagnostic.MissingDefinitionInfo
    public final String getDiagnosticMessage() {
        StringBuilder sb = new StringBuilder();
        k.a(sb, this);
        return sb.toString();
    }

    @Override // com.android.tools.r8.diagnostic.MissingDefinitionInfo
    public final Collection getReferencedFromContexts() {
        return this.a;
    }
}
