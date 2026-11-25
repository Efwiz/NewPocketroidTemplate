package org.jetbrains.kotlin.cli.jvm.plugins;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.ExitCode;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.messages.MessageCollectorUtil;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.cli.plugins.PluginClasspathAndOptions;
import org.jetbrains.kotlin.cli.plugins.PluginsOptionsParserKt;
import org.jetbrains.kotlin.compiler.plugin.CliOptionProcessingException;
import org.jetbrains.kotlin.compiler.plugin.CliOptionValue;
import org.jetbrains.kotlin.compiler.plugin.CliOptionsKt;
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor;
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar;
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar;
import org.jetbrains.kotlin.compiler.plugin.PluginCliOptionProcessingException;
import org.jetbrains.kotlin.compiler.plugin.PluginProcessingException;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.util.ServiceLoaderLite;

/* JADX INFO: compiled from: PluginCliParser.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u001bB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002J0\u0010\b\u001a\u00020\t2\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\u0006\u0010\f\u001a\u00020\rH\u0003J \u0010\u000e\u001a\u00020\t2\u000e\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\u0006\u0010\f\u001a\u00020\rH\u0002JE\u0010\u0010\u001a\u00020\u00112\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00122\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00122\u000e\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00122\u0006\u0010\f\u001a\u00020\rH\u0007¢\u0006\u0002\u0010\u0014J:\u0010\u0010\u001a\u00020\u00112\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u00152\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\u00152\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00070\u00152\u0006\u0010\f\u001a\u00020\rH\u0007J\u001c\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002J(\u0010\u0019\u001a\u00020\t2\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001a\u001a\u00020\u0004H\u0002¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/plugins/PluginCliParser;", "", "()V", "createClassLoader", "Ljava/net/URLClassLoader;", ModuleXmlParser.CLASSPATH, "", "", "loadPluginsLegacyStyle", "", "pluginClasspaths", "pluginOptions", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "loadPluginsModernStyle", "rawPluginConfigurations", "loadPluginsSafe", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", "", "pluginConfigurations", "([Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;Lorg/jetbrains/kotlin/config/CompilerConfiguration;)Lorg/jetbrains/kotlin/cli/common/ExitCode;", "", "loadRegisteredPluginsInfo", "", "Lorg/jetbrains/kotlin/cli/jvm/plugins/PluginCliParser$RegisteredPluginInfo;", "processPluginOptions", "classLoader", "RegisteredPluginInfo", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PluginCliParser {
    public static final PluginCliParser INSTANCE = new PluginCliParser();

    private PluginCliParser() {
    }

    @JvmStatic
    public static final ExitCode loadPluginsSafe(String[] pluginClasspaths, String[] pluginOptions, String[] pluginConfigurations, CompilerConfiguration configuration) {
        List listEmptyList;
        List listEmptyList2;
        List listEmptyList3;
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        if (pluginClasspaths == null || (listEmptyList = ArraysKt.toList(pluginClasspaths)) == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List list = listEmptyList;
        if (pluginOptions == null || (listEmptyList2 = ArraysKt.toList(pluginOptions)) == null) {
            listEmptyList2 = CollectionsKt.emptyList();
        }
        List list2 = listEmptyList2;
        if (pluginConfigurations == null || (listEmptyList3 = ArraysKt.toList(pluginConfigurations)) == null) {
            listEmptyList3 = CollectionsKt.emptyList();
        }
        return loadPluginsSafe(list, list2, listEmptyList3, configuration);
    }

    @JvmStatic
    public static final ExitCode loadPluginsSafe(Collection<String> pluginClasspaths, Collection<String> pluginOptions, Collection<String> pluginConfigurations, CompilerConfiguration configuration) {
        Intrinsics.checkNotNullParameter(pluginClasspaths, "pluginClasspaths");
        Intrinsics.checkNotNullParameter(pluginOptions, "pluginOptions");
        Intrinsics.checkNotNullParameter(pluginConfigurations, "pluginConfigurations");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Object notNull = configuration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        MessageCollector messageCollector = (MessageCollector) notNull;
        try {
            PluginCliParser pluginCliParser = INSTANCE;
            loadPluginsLegacyStyle(pluginClasspaths, pluginOptions, configuration);
            pluginCliParser.loadPluginsModernStyle(pluginConfigurations, configuration);
            return ExitCode.OK;
        } catch (PluginCliOptionProcessingException e) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, e.getMessage() + "\n\n" + CliOptionsKt.cliPluginUsageString(e.getPluginId(), e.getOptions()), null, 4, null);
            return ExitCode.INTERNAL_ERROR;
        } catch (CliOptionProcessingException e2) {
            CompilerMessageSeverity compilerMessageSeverity = CompilerMessageSeverity.ERROR;
            String message = e2.getMessage();
            Intrinsics.checkNotNull(message);
            MessageCollector.report$default(messageCollector, compilerMessageSeverity, message, null, 4, null);
            return ExitCode.INTERNAL_ERROR;
        } catch (PluginProcessingException e3) {
            CompilerMessageSeverity compilerMessageSeverity2 = CompilerMessageSeverity.ERROR;
            String message2 = e3.getMessage();
            Intrinsics.checkNotNull(message2);
            MessageCollector.report$default(messageCollector, compilerMessageSeverity2, message2, null, 4, null);
            return ExitCode.INTERNAL_ERROR;
        } catch (Throwable th) {
            MessageCollectorUtil.reportException(messageCollector, th);
            return ExitCode.INTERNAL_ERROR;
        }
    }

    /* JADX INFO: compiled from: PluginCliParser.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B1\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/plugins/PluginCliParser$RegisteredPluginInfo;", "", "componentRegistrar", "Lorg/jetbrains/kotlin/compiler/plugin/ComponentRegistrar;", "compilerPluginRegistrar", "Lorg/jetbrains/kotlin/compiler/plugin/CompilerPluginRegistrar;", "commandLineProcessor", "Lorg/jetbrains/kotlin/compiler/plugin/CommandLineProcessor;", "pluginOptions", "", "Lorg/jetbrains/kotlin/compiler/plugin/CliOptionValue;", "(Lorg/jetbrains/kotlin/compiler/plugin/ComponentRegistrar;Lorg/jetbrains/kotlin/compiler/plugin/CompilerPluginRegistrar;Lorg/jetbrains/kotlin/compiler/plugin/CommandLineProcessor;Ljava/util/List;)V", "getCommandLineProcessor", "()Lorg/jetbrains/kotlin/compiler/plugin/CommandLineProcessor;", "getCompilerPluginRegistrar", "()Lorg/jetbrains/kotlin/compiler/plugin/CompilerPluginRegistrar;", "getComponentRegistrar", "()Lorg/jetbrains/kotlin/compiler/plugin/ComponentRegistrar;", "getPluginOptions", "()Ljava/util/List;", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class RegisteredPluginInfo {
        private final CommandLineProcessor commandLineProcessor;
        private final CompilerPluginRegistrar compilerPluginRegistrar;
        private final ComponentRegistrar componentRegistrar;
        private final List<CliOptionValue> pluginOptions;

        public RegisteredPluginInfo(ComponentRegistrar componentRegistrar, CompilerPluginRegistrar compilerPluginRegistrar, CommandLineProcessor commandLineProcessor, List<CliOptionValue> pluginOptions) {
            Intrinsics.checkNotNullParameter(pluginOptions, "pluginOptions");
            this.componentRegistrar = componentRegistrar;
            this.compilerPluginRegistrar = compilerPluginRegistrar;
            this.commandLineProcessor = commandLineProcessor;
            this.pluginOptions = pluginOptions;
        }

        public final ComponentRegistrar getComponentRegistrar() {
            return this.componentRegistrar;
        }

        public final CompilerPluginRegistrar getCompilerPluginRegistrar() {
            return this.compilerPluginRegistrar;
        }

        public final CommandLineProcessor getCommandLineProcessor() {
            return this.commandLineProcessor;
        }

        public final List<CliOptionValue> getPluginOptions() {
            return this.pluginOptions;
        }
    }

    private final List<RegisteredPluginInfo> loadRegisteredPluginsInfo(Iterable<String> rawPluginConfigurations) {
        List<PluginClasspathAndOptions> listExtractPluginClasspathAndOptions = PluginsOptionsParserKt.extractPluginClasspathAndOptions(rawPluginConfigurations);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listExtractPluginClasspathAndOptions, 10));
        for (PluginClasspathAndOptions pluginClasspathAndOptions : listExtractPluginClasspathAndOptions) {
            URLClassLoader uRLClassLoaderCreateClassLoader = INSTANCE.createClassLoader(pluginClasspathAndOptions.getClasspath());
            List listLoadImplementations = ServiceLoaderLite.INSTANCE.loadImplementations(ComponentRegistrar.class, uRLClassLoaderCreateClassLoader);
            List listLoadImplementations2 = ServiceLoaderLite.INSTANCE.loadImplementations(CompilerPluginRegistrar.class, uRLClassLoaderCreateClassLoader);
            int size = listLoadImplementations.size() + listLoadImplementations2.size();
            if (size == 0) {
                throw new PluginProcessingException("No plugins found in given classpath: " + CollectionsKt.joinToString$default(pluginClasspathAndOptions.getClasspath(), ",", null, null, 0, null, null, 62, null), null, 2, null);
            }
            if (size != 1) {
                throw new PluginProcessingException(loadRegisteredPluginsInfo$lambda$2$multiplePluginsErrorMessage(pluginClasspathAndOptions, CollectionsKt.plus((Collection) listLoadImplementations, (Iterable) listLoadImplementations2)), null, 2, null);
            }
            List listLoadImplementations3 = ServiceLoaderLite.INSTANCE.loadImplementations(CommandLineProcessor.class, uRLClassLoaderCreateClassLoader);
            if (listLoadImplementations3.size() > 1) {
                throw new PluginProcessingException(loadRegisteredPluginsInfo$lambda$2$multiplePluginsErrorMessage(pluginClasspathAndOptions, listLoadImplementations3), null, 2, null);
            }
            arrayList.add(new RegisteredPluginInfo((ComponentRegistrar) CollectionsKt.firstOrNull(listLoadImplementations), (CompilerPluginRegistrar) CollectionsKt.firstOrNull(listLoadImplementations2), (CommandLineProcessor) CollectionsKt.firstOrNull(listLoadImplementations3), pluginClasspathAndOptions.getOptions()));
        }
        return arrayList;
    }

    private static final String loadRegisteredPluginsInfo$lambda$2$multiplePluginsErrorMessage(PluginClasspathAndOptions pluginClasspathAndOptions, List<? extends Object> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("Multiple plugins found in given classpath: ");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            String qualifiedName = Reflection.getOrCreateKotlinClass(it.next().getClass()).getQualifiedName();
            if (qualifiedName != null) {
                arrayList.add(qualifiedName);
            }
        }
        StringBuilder sbAppend = sb.append(CollectionsKt.joinToString$default(arrayList, ", ", null, null, 0, null, null, 62, null));
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        Intrinsics.checkNotNullExpressionValue(sbAppend.append('\n'), "append(...)");
        sb.append("  Plugin configuration is: " + pluginClasspathAndOptions.getRawArgument());
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    private final void loadPluginsModernStyle(Iterable<String> rawPluginConfigurations, CompilerConfiguration configuration) {
        if (rawPluginConfigurations == null) {
            return;
        }
        for (RegisteredPluginInfo registeredPluginInfo : loadRegisteredPluginsInfo(rawPluginConfigurations)) {
            ComponentRegistrar componentRegistrar = registeredPluginInfo.getComponentRegistrar();
            if (componentRegistrar != null) {
                configuration.add(ComponentRegistrar.INSTANCE.getPLUGIN_COMPONENT_REGISTRARS(), componentRegistrar);
            }
            CompilerPluginRegistrar compilerPluginRegistrar = registeredPluginInfo.getCompilerPluginRegistrar();
            if (compilerPluginRegistrar != null) {
                configuration.add(CompilerPluginRegistrar.INSTANCE.getCOMPILER_PLUGIN_REGISTRARS(), compilerPluginRegistrar);
            }
            if (!registeredPluginInfo.getPluginOptions().isEmpty()) {
                CommandLineProcessor commandLineProcessor = registeredPluginInfo.getCommandLineProcessor();
                if (commandLineProcessor == null) {
                    throw new RuntimeException();
                }
                PluginsOptionsParserKt.processCompilerPluginOptions(commandLineProcessor, registeredPluginInfo.getPluginOptions(), configuration);
            }
        }
    }

    @JvmStatic
    private static final void loadPluginsLegacyStyle(Iterable<String> pluginClasspaths, Iterable<String> pluginOptions, CompilerConfiguration configuration) {
        PluginCliParser pluginCliParser = INSTANCE;
        if (pluginClasspaths == null) {
            pluginClasspaths = CollectionsKt.emptyList();
        }
        URLClassLoader uRLClassLoaderCreateClassLoader = pluginCliParser.createClassLoader(pluginClasspaths);
        configuration.addAll(ComponentRegistrar.INSTANCE.getPLUGIN_COMPONENT_REGISTRARS(), ServiceLoaderLite.INSTANCE.loadImplementations(ComponentRegistrar.class, uRLClassLoaderCreateClassLoader));
        configuration.addAll(CompilerPluginRegistrar.INSTANCE.getCOMPILER_PLUGIN_REGISTRARS(), ServiceLoaderLite.INSTANCE.loadImplementations(CompilerPluginRegistrar.class, uRLClassLoaderCreateClassLoader));
        pluginCliParser.processPluginOptions(pluginOptions, configuration, uRLClassLoaderCreateClassLoader);
    }

    private final void processPluginOptions(Iterable<String> pluginOptions, CompilerConfiguration configuration, URLClassLoader classLoader) {
        PluginsOptionsParserKt.processCompilerPluginsOptions(configuration, pluginOptions, ServiceLoaderLite.INSTANCE.loadImplementations(CommandLineProcessor.class, classLoader));
    }

    private final URLClassLoader createClassLoader(Iterable<String> classpath) {
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(classpath, 10));
        Iterator<String> it = classpath.iterator();
        while (it.hasNext()) {
            arrayList.add(new File(it.next()).toURI().toURL());
        }
        return new URLClassLoader((URL[]) arrayList.toArray(new URL[0]), getClass().getClassLoader());
    }
}
