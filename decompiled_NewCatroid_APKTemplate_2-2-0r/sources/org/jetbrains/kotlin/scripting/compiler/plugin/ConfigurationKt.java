package org.jetbrains.kotlin.scripting.compiler.plugin;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRootsKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt;
import org.jetbrains.kotlin.scripting.configuration.ScriptingConfigurationKeys;
import org.jetbrains.kotlin.scripting.definitions.ScriptiDefinitionsFromClasspathDiscoverySourceKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: configuration.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a4\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"KOTLIN_SCRIPTING_PLUGIN_ID", "", "configureScriptDefinitions", "", "scriptTemplates", "", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "baseClassloader", "Ljava/lang/ClassLoader;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConfigurationKt {
    public static final String KOTLIN_SCRIPTING_PLUGIN_ID = "kotlin.scripting";

    public static final void configureScriptDefinitions(List<String> scriptTemplates, CompilerConfiguration configuration, ClassLoader baseClassloader, MessageCollector messageCollector, ScriptingHostConfiguration hostConfiguration) {
        Intrinsics.checkNotNullParameter(scriptTemplates, "scriptTemplates");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(baseClassloader, "baseClassloader");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
        configuration.addAll(ScriptingConfigurationKeys.INSTANCE.getSCRIPT_DEFINITIONS(), SequencesKt.toList(ScriptiDefinitionsFromClasspathDiscoverySourceKt.loadScriptTemplatesFromClasspath(scriptTemplates, JvmContentRootsKt.getJvmClasspathRoots(configuration), CollectionsKt.emptyList(), baseClassloader, hostConfiguration, ErrorReportingKt.getReporter(messageCollector))));
    }
}
