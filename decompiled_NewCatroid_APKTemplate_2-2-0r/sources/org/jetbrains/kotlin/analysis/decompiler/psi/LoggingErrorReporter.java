package org.jetbrains.kotlin.analysis.decompiler.psi;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.serialization.deserialization.ErrorReporter;

/* JADX INFO: compiled from: LoggingErrorReporter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u001e\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/psi/LoggingErrorReporter;", "Lorg/jetbrains/kotlin/serialization/deserialization/ErrorReporter;", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "(Lcom/intellij/openapi/diagnostic/Logger;)V", "reportCannotInferVisibility", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "reportIncompleteHierarchy", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "unresolvedSuperClasses", "", "", "decompiler-to-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LoggingErrorReporter implements ErrorReporter {
    private final Logger log;

    @Override // org.jetbrains.kotlin.serialization.deserialization.ErrorReporter
    public void reportIncompleteHierarchy(ClassDescriptor descriptor, List<String> unresolvedSuperClasses) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(unresolvedSuperClasses, "unresolvedSuperClasses");
    }

    public LoggingErrorReporter(Logger log) {
        Intrinsics.checkNotNullParameter(log, "log");
        this.log = log;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.ErrorReporter
    public void reportCannotInferVisibility(CallableMemberDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        this.log.error("Could not infer visibility for " + descriptor);
    }
}
