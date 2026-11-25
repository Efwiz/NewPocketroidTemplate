package org.jetbrains.kotlin.scripting.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.script.experimental.dependencies.DependenciesResolver;
import kotlin.script.experimental.dependencies.ScriptReport;
import org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptContentLoader.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"asResolveFailure", "Lkotlin/script/experimental/dependencies/DependenciesResolver$ResolveResult$Failure;", "", "scriptDef", "Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ScriptContentLoaderKt {
    public static final DependenciesResolver.ResolveResult.Failure asResolveFailure(Throwable th, KotlinScriptDefinition scriptDef) {
        Intrinsics.checkNotNullParameter(th, "<this>");
        Intrinsics.checkNotNullParameter(scriptDef, "scriptDef");
        ScriptReport[] scriptReportArr = new ScriptReport[1];
        StringBuilder sbAppend = new StringBuilder().append(Reflection.getOrCreateKotlinClass(scriptDef.getDependencyResolver().getClass()).getSimpleName() + " threw exception " + Reflection.getOrCreateKotlinClass(th.getClass()).getSimpleName() + ":\n ");
        String message = th.getMessage();
        if (message == null) {
            message = "<no message>";
        }
        scriptReportArr[0] = new ScriptReport(sbAppend.append(message).toString(), ScriptReport.Severity.FATAL, null, 4, null);
        return new DependenciesResolver.ResolveResult.Failure(scriptReportArr);
    }
}
