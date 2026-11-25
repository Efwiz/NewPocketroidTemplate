package org.jetbrains.kotlin.scripting.compiler.plugin;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar;
import org.jetbrains.kotlin.scripting.compiler.plugin.services.Fir2IrScriptConfiguratorExtensionImpl;
import org.jetbrains.kotlin.scripting.compiler.plugin.services.FirScriptConfiguratorExtensionImpl;
import org.jetbrains.kotlin.scripting.compiler.plugin.services.FirScriptDefinitionProviderService;
import org.jetbrains.kotlin.scripting.configuration.ScriptingConfigurationKeys;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: FirScriptingCompilerExtensionRegistrar.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b*\u00060\tR\u00020\u0001H\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/FirScriptingCompilerExtensionRegistrar;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionRegistrar;", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "compilerConfiguration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "(Lkotlin/script/experimental/host/ScriptingHostConfiguration;Lorg/jetbrains/kotlin/config/CompilerConfiguration;)V", "configurePlugin", "", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionRegistrar$ExtensionRegistrarContext;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirScriptingCompilerExtensionRegistrar extends FirExtensionRegistrar {
    private final CompilerConfiguration compilerConfiguration;
    private final ScriptingHostConfiguration hostConfiguration;

    public FirScriptingCompilerExtensionRegistrar(ScriptingHostConfiguration hostConfiguration, CompilerConfiguration compilerConfiguration) {
        Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
        Intrinsics.checkNotNullParameter(compilerConfiguration, "compilerConfiguration");
        this.hostConfiguration = hostConfiguration;
        this.compilerConfiguration = compilerConfiguration;
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar
    protected void configurePlugin(FirExtensionRegistrar.ExtensionRegistrarContext extensionRegistrarContext) {
        Intrinsics.checkNotNullParameter(extensionRegistrarContext, "<this>");
        if (this.compilerConfiguration.getBoolean(ScriptingConfigurationKeys.INSTANCE.getDISABLE_SCRIPTING_PLUGIN_OPTION())) {
            return;
        }
        CompilerConfiguration compilerConfiguration = this.compilerConfiguration;
        ScriptingHostConfiguration scriptingHostConfiguration = this.hostConfiguration;
        ClassLoader classLoader = extensionRegistrarContext.getClass().getClassLoader();
        Intrinsics.checkNotNullExpressionValue(classLoader, "getClassLoader(...)");
        ScriptingCompilerConfigurationExtensionKt.configureScriptDefinitions(compilerConfiguration, scriptingHostConfiguration, classLoader);
        List list = this.compilerConfiguration.getList(ScriptingConfigurationKeys.INSTANCE.getSCRIPT_DEFINITIONS_SOURCES());
        Intrinsics.checkNotNullExpressionValue(list, "getList(...)");
        List list2 = this.compilerConfiguration.getList(ScriptingConfigurationKeys.INSTANCE.getSCRIPT_DEFINITIONS());
        Intrinsics.checkNotNullExpressionValue(list2, "getList(...)");
        if (!list.isEmpty() || !list2.isEmpty()) {
            extensionRegistrarContext.plusExtensionSessionComponent(FirScriptDefinitionProviderService.Companion.getFactory$default(FirScriptDefinitionProviderService.INSTANCE, list2, list, null, null, 12, null));
        }
        extensionRegistrarContext.plusScriptConfiguratorExtension(FirScriptConfiguratorExtensionImpl.INSTANCE.getFactory(this.hostConfiguration));
        extensionRegistrarContext.plusFir2IrScriptConfiguratorExtension(Fir2IrScriptConfiguratorExtensionImpl.INSTANCE.getFactory(this.hostConfiguration));
    }
}
