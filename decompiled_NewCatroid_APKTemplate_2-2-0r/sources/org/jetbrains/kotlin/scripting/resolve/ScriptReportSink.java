package org.jetbrains.kotlin.scripting.resolve;

import java.util.List;
import kotlin.Metadata;
import kotlin.script.experimental.api.ScriptDiagnostic;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptReportSink.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/scripting/resolve/ScriptReportSink;", "", "attachReports", "", "scriptFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "reports", "", "Lkotlin/script/experimental/api/ScriptDiagnostic;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ScriptReportSink {
    void attachReports(VirtualFile scriptFile, List<ScriptDiagnostic> reports);
}
