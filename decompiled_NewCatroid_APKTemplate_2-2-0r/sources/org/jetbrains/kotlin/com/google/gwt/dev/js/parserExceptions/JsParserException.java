package org.jetbrains.kotlin.com.google.gwt.dev.js.parserExceptions;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.CodePosition;

/* JADX INFO: compiled from: parserExceptions.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/com/google/gwt/dev/js/parserExceptions/JsParserException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", CrashHianalyticsData.MESSAGE, "", "position", "Lorg/jetbrains/kotlin/com/google/gwt/dev/js/rhino/CodePosition;", "(Ljava/lang/String;Lcom/google/gwt/dev/js/rhino/CodePosition;)V", "getPosition", "()Lcom/google/gwt/dev/js/rhino/CodePosition;", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsParserException extends RuntimeException {
    private final CodePosition position;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsParserException(String message, CodePosition position) {
        super(message + " at " + position);
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(position, "position");
        this.position = position;
    }
}
