package kotlin.script.experimental.jvm.util;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptDiagnostic;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: diagnosticsHelpers.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0016\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003\u001a\u0016\u0010\u0004\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003¨\u0006\u0005"}, d2 = {"isError", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "isIncomplete", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DiagnosticsHelpersKt {
    public static final <T> boolean isIncomplete(ResultWithDiagnostics<? extends T> resultWithDiagnostics) {
        Intrinsics.checkNotNullParameter(resultWithDiagnostics, "<this>");
        List<ScriptDiagnostic> reports = resultWithDiagnostics.getReports();
        if ((reports instanceof Collection) && reports.isEmpty()) {
            return false;
        }
        Iterator<T> it = reports.iterator();
        while (it.hasNext()) {
            if (((ScriptDiagnostic) it.next()).getCode() == -3) {
                return true;
            }
        }
        return false;
    }

    public static final <T> boolean isError(ResultWithDiagnostics<? extends T> resultWithDiagnostics) {
        Intrinsics.checkNotNullParameter(resultWithDiagnostics, "<this>");
        return resultWithDiagnostics instanceof ResultWithDiagnostics.Failure;
    }
}
