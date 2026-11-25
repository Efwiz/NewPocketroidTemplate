package org.jetbrains.kotlin.scripting.compiler.plugin.services;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ErrorHandlingKt;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.SourceCode;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinition;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinitionProvider;
import org.jetbrains.kotlin.scripting.definitions.ScriptDependenciesProvider;
import org.jetbrains.kotlin.scripting.resolve.KtFileScriptSource;
import org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptConfigurationUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a3\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u001b\b\u0002\u0010\u0005\u001a\u0015\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0006¢\u0006\u0002\b\b¨\u0006\t"}, d2 = {"getScriptCompilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "Lorg/jetbrains/kotlin/fir/FirSession;", "sourceCode", "Lkotlin/script/experimental/api/SourceCode;", "getDefault", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/services/FirScriptDefinitionProviderService;", "Lkotlin/ExtensionFunctionType;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ScriptConfigurationUtilsKt {
    public static /* synthetic */ ScriptCompilationConfiguration getScriptCompilationConfiguration$default(FirSession firSession, SourceCode sourceCode, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            function1 = new Function1<FirScriptDefinitionProviderService, ScriptCompilationConfiguration>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.services.ScriptConfigurationUtilsKt.getScriptCompilationConfiguration.1
                @Override // kotlin.jvm.functions.Function1
                public final ScriptCompilationConfiguration invoke(FirScriptDefinitionProviderService firScriptDefinitionProviderService) {
                    ScriptDefinition defaultDefinition;
                    Intrinsics.checkNotNullParameter(firScriptDefinitionProviderService, "$this$null");
                    ScriptDefinitionProvider definitionProvider = firScriptDefinitionProviderService.getDefinitionProvider();
                    if (definitionProvider == null || (defaultDefinition = definitionProvider.getDefaultDefinition()) == null) {
                        return null;
                    }
                    return defaultDefinition.getCompilationConfiguration();
                }
            };
        }
        return getScriptCompilationConfiguration(firSession, sourceCode, function1);
    }

    public static final ScriptCompilationConfiguration getScriptCompilationConfiguration(FirSession firSession, SourceCode sourceCode, Function1<? super FirScriptDefinitionProviderService, ? extends ScriptCompilationConfiguration> getDefault) {
        KtFile ktFile;
        ScriptCompilationConfiguration scriptCompilationConfigurationInvoke;
        ResultWithDiagnostics<ScriptCompilationConfigurationWrapper> scriptConfigurationResult;
        ScriptCompilationConfigurationWrapper scriptCompilationConfigurationWrapper;
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        Intrinsics.checkNotNullParameter(getDefault, "getDefault");
        FirScriptDefinitionProviderService scriptDefinitionProviderService = FirScriptDefinitionProviderServiceKt.getScriptDefinitionProviderService(firSession);
        if (scriptDefinitionProviderService == null) {
            return null;
        }
        if (sourceCode != null) {
            KtFileScriptSource ktFileScriptSource = sourceCode instanceof KtFileScriptSource ? (KtFileScriptSource) sourceCode : null;
            if (ktFileScriptSource == null || (ktFile = ktFileScriptSource.getKtFile()) == null) {
                throw new IllegalStateException("only PSI scripts are supported at the moment".toString());
            }
            ScriptDependenciesProvider configurationProvider = scriptDefinitionProviderService.getConfigurationProvider();
            if (configurationProvider == null || (scriptConfigurationResult = configurationProvider.getScriptConfigurationResult(ktFile)) == null || (scriptCompilationConfigurationWrapper = (ScriptCompilationConfigurationWrapper) ErrorHandlingKt.valueOrNull(scriptConfigurationResult)) == null || (scriptCompilationConfigurationInvoke = scriptCompilationConfigurationWrapper.getConfiguration()) == null) {
                scriptCompilationConfigurationInvoke = getDefault.invoke(scriptDefinitionProviderService);
            }
            if (scriptCompilationConfigurationInvoke != null) {
                return scriptCompilationConfigurationInvoke;
            }
        }
        return getDefault.invoke(scriptDefinitionProviderService);
    }
}
