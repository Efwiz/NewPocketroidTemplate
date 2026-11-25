package org.jetbrains.kotlin.utils.exceptions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.ControlFlowException;
import org.jetbrains.kotlin.com.intellij.openapi.project.IndexNotReadyException;

/* JADX INFO: compiled from: platformExceptionUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0003\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"}, d2 = {"shouldIjPlatformExceptionBeRethrown", "", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_EXCEPTION, "", "util"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PlatformExceptionUtilsKt {
    public static final boolean shouldIjPlatformExceptionBeRethrown(Throwable exception) {
        Intrinsics.checkNotNullParameter(exception, "exception");
        return (exception instanceof ControlFlowException) || (exception instanceof IndexNotReadyException);
    }
}
