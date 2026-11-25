package org.jetbrains.kotlin.incremental.util;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;

/* JADX INFO: compiled from: messageCollectorUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"reportException", "", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "e", "", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/util/ExceptionLocation;", "incremental-compilation-impl"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class MessageCollectorUtilKt {
    public static final void reportException(MessageCollector messageCollector, Throwable e, ExceptionLocation location) {
        Intrinsics.checkNotNullParameter(messageCollector, "<this>");
        Intrinsics.checkNotNullParameter(e, "e");
        Intrinsics.checkNotNullParameter(location, "location");
        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.EXCEPTION, location.getReadableName() + " failed: " + e.getMessage() + '\n' + ExceptionsKt.stackTraceToString(e), null, 4, null);
    }
}
