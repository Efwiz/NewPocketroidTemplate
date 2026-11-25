package org.jetbrains.kotlin.scripting.compiler.plugin;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.jvm.JvmScriptingHostConfigurationKt;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.extensions.ReplFactoryExtension;
import org.jetbrains.kotlin.cli.common.extensions.ScriptEvaluationExtension;
import org.jetbrains.kotlin.cli.common.extensions.ShellExtension;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.com.intellij.mock.MockProject;
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.extensions.CollectAdditionalSourcesExtension;
import org.jetbrains.kotlin.extensions.CompilerConfigurationExtension;
import org.jetbrains.kotlin.extensions.ProcessSourcesBeforeCompilingExtension;
import org.jetbrains.kotlin.resolve.extensions.ExtraImportsProviderExtension;
import org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension;
import org.jetbrains.kotlin.scripting.compiler.plugin.definitions.CliScriptDefinitionProvider;
import org.jetbrains.kotlin.scripting.compiler.plugin.definitions.CliScriptDependenciesProvider;
import org.jetbrains.kotlin.scripting.compiler.plugin.definitions.CliScriptReportSink;
import org.jetbrains.kotlin.scripting.compiler.plugin.extensions.JvmStandardReplFactoryExtension;
import org.jetbrains.kotlin.scripting.compiler.plugin.extensions.ScriptingCollectAdditionalSourcesExtension;
import org.jetbrains.kotlin.scripting.compiler.plugin.extensions.ScriptingProcessSourcesBeforeCompilingExtension;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinitionProvider;
import org.jetbrains.kotlin.scripting.definitions.ScriptDependenciesProvider;
import org.jetbrains.kotlin.scripting.extensions.ScriptExtraImportsProviderExtension;
import org.jetbrains.kotlin.scripting.extensions.ScriptingResolveExtension;
import org.jetbrains.kotlin.scripting.resolve.ScriptReportSink;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: pluginRegisrar.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/ScriptingCompilerConfigurationComponentRegistrar;", "Lorg/jetbrains/kotlin/compiler/plugin/ComponentRegistrar;", "()V", "supportsK2", "", "getSupportsK2", "()Z", "registerProjectComponents", "", "project", "Lorg/jetbrains/kotlin/com/intellij/mock/MockProject;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptingCompilerConfigurationComponentRegistrar implements ComponentRegistrar {
    @Override // org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
    public boolean getSupportsK2() {
        return true;
    }

    @Override // org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
    public void registerProjectComponents(MockProject project, CompilerConfiguration configuration) {
        NoClassDefFoundError noClassDefFoundError;
        ArrayList arrayList;
        URL[] uRLs;
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        MessageCollector messageCollector = (MessageCollector) configuration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        try {
            CompilerConfigurationExtension.INSTANCE.registerExtension(project, new ScriptingCompilerConfigurationExtension(project, new ScriptingHostConfiguration(new ScriptingHostConfiguration[]{JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration()}, new Function1<ScriptingHostConfiguration.Builder, Unit>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.ScriptingCompilerConfigurationComponentRegistrar$registerProjectComponents$hostConfiguration$1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ScriptingHostConfiguration.Builder $receiver) {
                    Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ScriptingHostConfiguration.Builder builder) {
                    invoke2(builder);
                    return Unit.INSTANCE;
                }
            })));
            CollectAdditionalSourcesExtension.INSTANCE.registerExtension(project, new ScriptingCollectAdditionalSourcesExtension(project));
            ProcessSourcesBeforeCompilingExtension.INSTANCE.registerExtension(project, new ScriptingProcessSourcesBeforeCompilingExtension(project));
            PluginRegisrarKt.registerExtensionIfRequired(ScriptEvaluationExtension.INSTANCE, project, new JvmCliScriptEvaluationExtension());
            PluginRegisrarKt.registerExtensionIfRequired(ShellExtension.INSTANCE, project, new JvmCliReplShellExtension());
            PluginRegisrarKt.registerExtensionIfRequired(ReplFactoryExtension.INSTANCE, project, new JvmStandardReplFactoryExtension());
            project.registerService((Class<CliScriptDefinitionProvider>) ScriptDefinitionProvider.class, new CliScriptDefinitionProvider());
            project.registerService((Class<CliScriptDependenciesProvider>) ScriptDependenciesProvider.class, new CliScriptDependenciesProvider(project));
            SyntheticResolveExtension.INSTANCE.registerExtension(project, new ScriptingResolveExtension());
            ExtraImportsProviderExtension.INSTANCE.registerExtension(project, new ScriptExtraImportsProviderExtension());
            if (messageCollector != null) {
                project.registerService((Class<CliScriptReportSink>) ScriptReportSink.class, new CliScriptReportSink(messageCollector));
            }
            noClassDefFoundError = null;
        } catch (ClassNotFoundException e) {
            noClassDefFoundError = e;
        } catch (NoClassDefFoundError e2) {
            noClassDefFoundError = e2;
        }
        if (noClassDefFoundError != null) {
            ClassLoader classLoader = ScriptingCompilerConfigurationComponentRegistrar.class.getClassLoader();
            URLClassLoader uRLClassLoader = classLoader instanceof URLClassLoader ? (URLClassLoader) classLoader : null;
            if (uRLClassLoader == null || (uRLs = uRLClassLoader.getURLs()) == null) {
                arrayList = null;
            } else {
                Intrinsics.checkNotNull(uRLs);
                ArrayList arrayList2 = new ArrayList(uRLs.length);
                for (URL url : uRLs) {
                    arrayList2.add(url.getPath());
                }
                arrayList = arrayList2;
            }
            String str = "Error registering scripting services: " + noClassDefFoundError + "\n  current classpath:\n    " + (arrayList != null ? CollectionsKt.joinToString$default(arrayList, "\n    ", null, null, 0, null, null, 62, null) : null);
            if (messageCollector != null) {
                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.STRONG_WARNING, str, null, 4, null);
            } else {
                System.err.println(str);
            }
        }
    }
}
