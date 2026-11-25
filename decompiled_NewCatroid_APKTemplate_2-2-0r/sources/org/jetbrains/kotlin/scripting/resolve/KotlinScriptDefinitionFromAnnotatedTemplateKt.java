package org.jetbrains.kotlin.scripting.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.dependencies.ScriptDependenciesResolver;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: KotlinScriptDefinitionFromAnnotatedTemplate.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0001¨\u0006\u0002"}, d2 = {"unwrap", "Lkotlin/script/dependencies/ScriptDependenciesResolver;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinScriptDefinitionFromAnnotatedTemplateKt {
    public static final ScriptDependenciesResolver unwrap(ScriptDependenciesResolver scriptDependenciesResolver) {
        Intrinsics.checkNotNullParameter(scriptDependenciesResolver, "<this>");
        return scriptDependenciesResolver instanceof DependencyResolverWrapper ? unwrap(((DependencyResolverWrapper) scriptDependenciesResolver).getDelegate()) : scriptDependenciesResolver;
    }
}
