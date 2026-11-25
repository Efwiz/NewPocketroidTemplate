package org.jetbrains.kotlin.scripting.definitions;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptDiagnostic;
import org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: dynamicConfigurations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u0001*\f\u0012\u0004\u0012\u00020\u00010\u0002j\u0002`\u0003H\u0002¨\u0006\u0004"}, d2 = {"valueOrError", "Lorg/jetbrains/kotlin/scripting/resolve/ScriptCompilationConfigurationWrapper;", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lorg/jetbrains/kotlin/scripting/resolve/ScriptCompilationConfigurationResult;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DynamicConfigurationsKt {
    private static final ScriptCompilationConfigurationWrapper valueOrError(ResultWithDiagnostics<? extends ScriptCompilationConfigurationWrapper> resultWithDiagnostics) {
        Object next;
        if (!(resultWithDiagnostics instanceof ResultWithDiagnostics.Success)) {
            if (!(resultWithDiagnostics instanceof ResultWithDiagnostics.Failure)) {
                throw new NoWhenBranchMatchedException();
            }
            ResultWithDiagnostics.Failure failure = (ResultWithDiagnostics.Failure) resultWithDiagnostics;
            Iterator<T> it = failure.getReports().iterator();
            boolean z = false;
            Object obj = null;
            while (true) {
                if (!it.hasNext()) {
                    if (!z) {
                        break;
                    }
                    break;
                }
                Object next2 = it.next();
                if (((ScriptDiagnostic) next2).getSeverity() == ScriptDiagnostic.Severity.ERROR) {
                    if (!z) {
                        z = true;
                        obj = next2;
                    }
                }
                obj = null;
                break;
            }
            ScriptDiagnostic scriptDiagnostic = (ScriptDiagnostic) obj;
            if (scriptDiagnostic != null) {
                throw new IllegalStateException(scriptDiagnostic.getMessage(), scriptDiagnostic.getException());
            }
            String str = "Error retrieving script compilation configuration: " + CollectionsKt.joinToString$default(failure.getReports(), null, null, null, 0, null, new Function1<ScriptDiagnostic, CharSequence>() { // from class: org.jetbrains.kotlin.scripting.definitions.DynamicConfigurationsKt$valueOrError$1$1
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(ScriptDiagnostic it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    return it2.getMessage();
                }
            }, 31, null);
            Iterator<T> it2 = failure.getReports().iterator();
            do {
                if (!it2.hasNext()) {
                    next = null;
                    break;
                }
                next = it2.next();
            } while (((ScriptDiagnostic) next).getException() == null);
            ScriptDiagnostic scriptDiagnostic2 = (ScriptDiagnostic) next;
            throw new IllegalStateException(str, scriptDiagnostic2 != null ? scriptDiagnostic2.getException() : null);
        }
        return (ScriptCompilationConfigurationWrapper) ((ResultWithDiagnostics.Success) resultWithDiagnostics).getValue();
    }
}
