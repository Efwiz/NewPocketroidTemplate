package org.jetbrains.kotlin.com.google.gwt.dev.js;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.google.gwt.dev.js.parserExceptions.JsParserException;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.CodePosition;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.ErrorReporter;

/* JADX INFO: compiled from: ThrowExceptionReporter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0016J \u0010\n\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/com/google/gwt/dev/js/ThrowExceptionOnErrorReporter;", "Lorg/jetbrains/kotlin/com/google/gwt/dev/js/rhino/ErrorReporter;", "()V", "error", "", CrashHianalyticsData.MESSAGE, "", "startPosition", "Lorg/jetbrains/kotlin/com/google/gwt/dev/js/rhino/CodePosition;", "endPosition", CompilerOptions.WARNING, "", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ThrowExceptionOnErrorReporter implements ErrorReporter {
    public static final ThrowExceptionOnErrorReporter INSTANCE = new ThrowExceptionOnErrorReporter();

    @Override // org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.ErrorReporter
    public void warning(String message, CodePosition startPosition, CodePosition endPosition) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(startPosition, "startPosition");
        Intrinsics.checkNotNullParameter(endPosition, "endPosition");
    }

    private ThrowExceptionOnErrorReporter() {
    }

    @Override // org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.ErrorReporter
    /* JADX INFO: renamed from: error, reason: merged with bridge method [inline-methods] */
    public Void mo6456error(String message, CodePosition startPosition, CodePosition endPosition) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(startPosition, "startPosition");
        Intrinsics.checkNotNullParameter(endPosition, "endPosition");
        throw new JsParserException(message, startPosition);
    }
}
