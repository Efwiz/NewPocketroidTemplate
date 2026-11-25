package com.android.tools.r8.errors;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class DexFileOverflowDiagnostic implements Diagnostic {
    private final boolean a;
    private final long b;
    private final long c;

    public DexFileOverflowDiagnostic(boolean z, long j, long j2) {
        this.a = z;
        this.b = j;
        this.c = j2;
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        StringBuilder sb = new StringBuilder("Cannot fit requested classes in ");
        sb.append(hasMainDexSpecification() ? "the main-" : "a single ").append("dex file (");
        if (getNumberOfMethods() > getMaximumNumberOfMethods()) {
            sb.append("# methods: ").append(getNumberOfMethods()).append(" > ").append(getMaximumNumberOfMethods());
            if (getNumberOfFields() > getMaximumNumberOfFields()) {
                sb.append(" ; ");
            }
        }
        if (getNumberOfFields() > getMaximumNumberOfFields()) {
            sb.append("# fields: ").append(getNumberOfFields()).append(" > ").append(getMaximumNumberOfFields());
        }
        return sb.append(Constant.AFTER_QUTO).toString();
    }

    public long getMaximumNumberOfFields() {
        return 65536L;
    }

    public long getMaximumNumberOfMethods() {
        return 65536L;
    }

    public long getNumberOfFields() {
        return this.c;
    }

    public long getNumberOfMethods() {
        return this.b;
    }

    @Override // com.android.tools.r8.Diagnostic
    public Origin getOrigin() {
        return Origin.unknown();
    }

    @Override // com.android.tools.r8.Diagnostic
    public Position getPosition() {
        return null;
    }

    public boolean hasMainDexSpecification() {
        return this.a;
    }
}
