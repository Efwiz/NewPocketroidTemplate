package org.jetbrains.kotlin.ir.interpreter.exceptions;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: InterpreterError.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/exceptions/InterpreterError;", "Lorg/jetbrains/kotlin/ir/interpreter/exceptions/InterpreterException;", CrashHianalyticsData.MESSAGE, "", "(Ljava/lang/String;)V", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class InterpreterError extends InterpreterException {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InterpreterError(String message) {
        super(message);
        Intrinsics.checkNotNullParameter(message, "message");
    }
}
