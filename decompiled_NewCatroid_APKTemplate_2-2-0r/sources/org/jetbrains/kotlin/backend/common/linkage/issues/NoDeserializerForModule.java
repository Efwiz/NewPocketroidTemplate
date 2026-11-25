package org.jetbrains.kotlin.backend.common.linkage.issues;

import com.huawei.hms.mlplugin.asr.MLAsrCaptureConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IdSignatureRendererKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: KotlinIrLinkerIssues.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/issues/NoDeserializerForModule;", "Lorg/jetbrains/kotlin/backend/common/linkage/issues/KotlinIrLinkerIssue;", "moduleName", "Lorg/jetbrains/kotlin/name/Name;", "idSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "(Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/ir/util/IdSignature;)V", MLAsrCaptureConstants.ASR_ERROR_MESSAGE, "", "getErrorMessage", "()Ljava/lang/String;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NoDeserializerForModule extends KotlinIrLinkerIssue {
    private final String errorMessage;

    public NoDeserializerForModule(Name moduleName, IdSignature idSignature) {
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        StringBuilder sb = new StringBuilder();
        sb.append("Could not load module " + moduleName.asString());
        if (idSignature != null) {
            sb.append(" in an attempt to find deserializer for symbol " + IdSignatureRendererKt.render$default(idSignature, null, 1, null) + '.');
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        this.errorMessage = string;
    }

    @Override // org.jetbrains.kotlin.backend.common.linkage.issues.KotlinIrLinkerIssue
    protected String getErrorMessage() {
        return this.errorMessage;
    }
}
