package org.jetbrains.kotlin.backend.common.linkage.issues;

import com.huawei.hms.mlplugin.asr.MLAsrCaptureConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analyzer.CompilationErrorException;
import org.jetbrains.kotlin.ir.util.IrMessageLogger;

/* JADX INFO: compiled from: KotlinIrLinkerIssues.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u0012\u0010\u0003\u001a\u00020\u0004X¤\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/issues/KotlinIrLinkerIssue;", "", "()V", MLAsrCaptureConstants.ASR_ERROR_MESSAGE, "", "getErrorMessage", "()Ljava/lang/String;", "raiseIssue", "", "messageLogger", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinIrLinkerIssue {
    protected abstract String getErrorMessage();

    public final Void raiseIssue(IrMessageLogger messageLogger) {
        Intrinsics.checkNotNullParameter(messageLogger, "messageLogger");
        messageLogger.report(IrMessageLogger.Severity.ERROR, getErrorMessage(), null);
        throw new CompilationErrorException(getErrorMessage());
    }
}
