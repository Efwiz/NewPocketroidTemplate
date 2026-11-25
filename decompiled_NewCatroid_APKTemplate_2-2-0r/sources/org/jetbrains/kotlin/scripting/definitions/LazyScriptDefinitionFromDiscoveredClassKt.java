package org.jetbrains.kotlin.scripting.definitions;

import kotlin.Metadata;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: LazyScriptDefinitionFromDiscoveredClass.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"InvalidScriptDefinition", "Lkotlin/script/experimental/host/ScriptDefinition;", "getInvalidScriptDefinition", "()Lkotlin/script/experimental/host/ScriptDefinition;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LazyScriptDefinitionFromDiscoveredClassKt {
    private static final kotlin.script.experimental.host.ScriptDefinition InvalidScriptDefinition = new kotlin.script.experimental.host.ScriptDefinition(new ScriptCompilationConfiguration(null, 1, null), new ScriptEvaluationConfiguration(null, 1, null));

    public static final kotlin.script.experimental.host.ScriptDefinition getInvalidScriptDefinition() {
        return InvalidScriptDefinition;
    }
}
