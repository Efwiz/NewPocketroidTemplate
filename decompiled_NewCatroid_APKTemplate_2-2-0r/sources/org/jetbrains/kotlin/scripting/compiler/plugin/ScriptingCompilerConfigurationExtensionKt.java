package org.jetbrains.kotlin.scripting.compiler.plugin;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRootsKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt;
import org.jetbrains.kotlin.scripting.configuration.ScriptingConfigurationKeys;
import org.jetbrains.kotlin.scripting.definitions.AutoloadedScriptDefinitions;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinition;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinitionsFromClasspathDiscoverySource;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinitionsSource;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptingCompilerConfigurationExtension.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0000¨\u0006\b"}, d2 = {"configureScriptDefinitions", "", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "classLoader", "Ljava/lang/ClassLoader;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ScriptingCompilerConfigurationExtensionKt {
    public static final void configureScriptDefinitions(CompilerConfiguration configuration, ScriptingHostConfiguration hostConfiguration, ClassLoader classLoader) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        MessageCollector none = (MessageCollector) configuration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        if (none == null) {
            none = MessageCollector.INSTANCE.getNONE();
        }
        Intrinsics.checkNotNull(none);
        List list = configuration.getList(ScriptingConfigurationKeys.INSTANCE.getSCRIPT_DEFINITIONS_CLASSES());
        Intrinsics.checkNotNullExpressionValue(list, "getList(...)");
        if (!list.isEmpty()) {
            ConfigurationKt.configureScriptDefinitions(list, configuration, classLoader, none, hostConfiguration);
        }
        if (!configuration.getBoolean(JVMConfigurationKeys.DISABLE_STANDARD_SCRIPT_DEFINITION)) {
            List list2 = configuration.getList(ScriptingConfigurationKeys.INSTANCE.getSCRIPT_DEFINITIONS());
            Intrinsics.checkNotNullExpressionValue(list2, "getList(...)");
            List list3 = list2;
            if ((list3 instanceof Collection) && list3.isEmpty()) {
                configuration.add(ScriptingConfigurationKeys.INSTANCE.getSCRIPT_DEFINITIONS(), ScriptDefinition.INSTANCE.getDefault(hostConfiguration));
                break;
            }
            Iterator it = list3.iterator();
            do {
                if (!it.hasNext()) {
                    configuration.add(ScriptingConfigurationKeys.INSTANCE.getSCRIPT_DEFINITIONS(), ScriptDefinition.INSTANCE.getDefault(hostConfiguration));
                    break;
                }
            } while (!((ScriptDefinition) it.next()).getIsDefault());
        }
        configuration.addAll(ScriptingConfigurationKeys.INSTANCE.getSCRIPT_DEFINITIONS_SOURCES(), CollectionsKt.listOfNotNull((Object[]) new ScriptDefinitionsSource[]{configuration.getBoolean(ScriptingConfigurationKeys.INSTANCE.getDISABLE_SCRIPT_DEFINITIONS_FROM_CLASSPATH_OPTION()) ? null : new ScriptDefinitionsFromClasspathDiscoverySource(JvmContentRootsKt.getJvmClasspathRoots(configuration), hostConfiguration, ErrorReportingKt.getReporter(none)), configuration.getBoolean(ScriptingConfigurationKeys.INSTANCE.getDISABLE_SCRIPT_DEFINITIONS_AUTOLOADING_OPTION()) ? null : new AutoloadedScriptDefinitions(hostConfiguration, classLoader, ErrorReportingKt.getReporter(none))}));
    }
}
