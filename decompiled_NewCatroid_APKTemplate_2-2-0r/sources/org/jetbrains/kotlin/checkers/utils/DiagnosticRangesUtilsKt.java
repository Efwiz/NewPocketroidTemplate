package org.jetbrains.kotlin.checkers.utils;

import java.io.File;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: diagnosticRangesUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005¨\u0006\u0007"}, d2 = {"clearFileFromDiagnosticMarkup", "", "file", "Ljava/io/File;", "clearTextFromDiagnosticMarkup", "", "text", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DiagnosticRangesUtilsKt {
    public static final void clearFileFromDiagnosticMarkup(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        FilesKt.writeText$default(file, clearTextFromDiagnosticMarkup(FilesKt.readText$default(file, null, 1, null)), null, 2, null);
    }

    public static final String clearTextFromDiagnosticMarkup(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        String strReplaceAll = CheckerTestUtil.INSTANCE.getRangeStartOrEndPattern().matcher(text).replaceAll("");
        Intrinsics.checkNotNullExpressionValue(strReplaceAll, "replaceAll(...)");
        return strReplaceAll;
    }
}
