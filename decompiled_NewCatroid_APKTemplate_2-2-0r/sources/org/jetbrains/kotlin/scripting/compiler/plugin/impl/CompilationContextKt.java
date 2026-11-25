package org.jetbrains.kotlin.scripting.compiler.plugin.impl;

import io.netty.util.internal.StringUtil;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.KotlinType;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.ScriptDependency;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.jvm.JvmDependency;
import kotlin.script.experimental.jvm.JvmScriptCompilationKt;
import kotlin.script.experimental.jvm.JvmScriptingHostConfigurationKt;
import kotlin.script.experimental.jvm.util.KotlinJars;
import kotlin.text.StringsKt;
import org.apache.xerces.impl.Constants;
import org.jetbrains.kotlin.cli.common.ArgumentsKt;
import org.jetbrains.kotlin.cli.common.CLICompilerKt;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.ParseCommandLineArgumentsKt;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.jvm.JvmArgumentsKt;
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.cli.jvm.config.JvmClasspathRoot;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRootsKt;
import org.jetbrains.kotlin.cli.jvm.plugins.PluginCliParser;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.CompilerConfigurationKey;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.extensions.StorageComponentContainerContributor;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleKt;
import org.jetbrains.kotlin.scripting.compiler.plugin.ScriptingCompilerConfigurationComponentRegistrar;
import org.jetbrains.kotlin.scripting.compiler.plugin.dependencies.ScriptsCompilationDependencies;
import org.jetbrains.kotlin.scripting.compiler.plugin.dependencies.ScriptsCompilationDependenciesKt;
import org.jetbrains.kotlin.scripting.configuration.ScriptingConfigurationKeys;
import org.jetbrains.kotlin.scripting.definitions.ScriptCompilationConfigurationFromDefinitionKt;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinition;
import org.jetbrains.kotlin.scripting.definitions.ScriptDependenciesProvider;
import org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: compilationContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000p\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\u001a@\u0010\u0002\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00040\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0000\u001a \u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\rH\u0000\u001a(\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\f\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002\u001a4\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00130\u00032\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\u0019H\u0000\u001a&\u0010\u001c\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\u001e\u001a\u0016\u0010\u001f\u001a\u00020 2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004H\u0002\u001a\f\u0010\"\u001a\u00020\b*\u00020\bH\u0000\u001a0\u0010#\u001a\u00020$*\u00020\u00132\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00010\u00042\u0014\b\u0002\u0010%\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020'0&H\u0000\u001a2\u0010#\u001a\u00020$*\u00020\u00132\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00010\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\u00192\u0006\u0010(\u001a\u00020'H\u0002\u001a*\u0010)\u001a\u00020$*\u00020\u00132\u0006\u0010\u0007\u001a\u00020\b2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\f\u001a\u00020\rH\u0002\u001a\u0014\u0010+\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010,\u001a\u00020\u0013H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"SCRIPT_BASE_COMPILER_ARGUMENTS_PROPERTY", "", "collectRefinedSourcesAndUpdateEnvironment", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/dependencies/ScriptsCompilationDependencies$SourceDependencies;", "context", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/SharedScriptCompilationContext;", "mainKtFile", "initialConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "messageCollector", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/ScriptDiagnosticsMessageCollector;", "createCompilationContextFromEnvironment", "baseScriptCompilationConfiguration", "environment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "createInitialCompilerConfiguration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "scriptCompilationConfiguration", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "reportingState", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/IgnoredOptionsReportingState;", "createInitialConfigurations", "ignoredOptionsReportingState", "createIsolatedCompilationContext", "disposable", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", "makeScriptCompilerArguments", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments;", "compilerOptions", "applyConfigure", "updateWithCompilerOptions", "", Constants.DOM_VALIDATE, "Lkotlin/Function1;", "", "isRefinement", "updateWithRefinedConfigurations", "sourceFiles", "withUpdatesFromCompilerConfiguration", "kotlinCompilerConfiguration", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CompilationContextKt {
    public static final String SCRIPT_BASE_COMPILER_ARGUMENTS_PROPERTY = "kotlin.script.base.compiler.arguments";

    public static final SharedScriptCompilationContext createIsolatedCompilationContext(ScriptCompilationConfiguration baseScriptCompilationConfiguration, ScriptingHostConfiguration hostConfiguration, ScriptDiagnosticsMessageCollector messageCollector, Disposable disposable) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(baseScriptCompilationConfiguration, "baseScriptCompilationConfiguration");
        Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(disposable, "disposable");
        IgnoredOptionsReportingState ignoredOptionsReportingState = new IgnoredOptionsReportingState();
        Pair<ScriptCompilationConfiguration, CompilerConfiguration> pairCreateInitialConfigurations = createInitialConfigurations(baseScriptCompilationConfiguration, hostConfiguration, messageCollector, ignoredOptionsReportingState);
        return applyConfigure(new SharedScriptCompilationContext(disposable, pairCreateInitialConfigurations.component1(), KotlinCoreEnvironment.INSTANCE.createForProduction(disposable, pairCreateInitialConfigurations.component2(), EnvironmentConfigFiles.JVM_CONFIG_FILES), ignoredOptionsReportingState));
    }

    public static final SharedScriptCompilationContext createCompilationContextFromEnvironment(ScriptCompilationConfiguration baseScriptCompilationConfiguration, KotlinCoreEnvironment environment, ScriptDiagnosticsMessageCollector messageCollector) {
        Intrinsics.checkNotNullParameter(baseScriptCompilationConfiguration, "baseScriptCompilationConfiguration");
        Intrinsics.checkNotNullParameter(environment, "environment");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        IgnoredOptionsReportingState ignoredOptionsReportingState = new IgnoredOptionsReportingState();
        ScriptCompilationConfiguration scriptCompilationConfigurationWithUpdatesFromCompilerConfiguration = withUpdatesFromCompilerConfiguration(baseScriptCompilationConfiguration, environment.getConfiguration());
        List list = (List) scriptCompilationConfigurationWithUpdatesFromCompilerConfiguration.get(ScriptCompilationKt.getCompilerOptions(ScriptCompilationConfiguration.INSTANCE));
        if (list != null) {
            updateWithCompilerOptions(environment.getConfiguration(), list, messageCollector, ignoredOptionsReportingState, false);
        }
        return applyConfigure(new SharedScriptCompilationContext(null, scriptCompilationConfigurationWithUpdatesFromCompilerConfiguration, environment, ignoredOptionsReportingState));
    }

    public static final SharedScriptCompilationContext applyConfigure(SharedScriptCompilationContext sharedScriptCompilationContext) {
        Intrinsics.checkNotNullParameter(sharedScriptCompilationContext, "<this>");
        List list = (List) sharedScriptCompilationContext.getBaseScriptCompilationConfiguration().get(ScriptCompilationConfigurationFromDefinitionKt.getAnnotationsForSamWithReceivers(ScriptCompilationConfiguration.INSTANCE));
        if (list != null && !list.isEmpty()) {
            List list2 = list;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                arrayList.add(((KotlinType) it.next()).getTypeName());
            }
            ArrayList arrayList2 = arrayList;
            if (!sharedScriptCompilationContext.getEnvironment().getConfiguration().getBoolean(CommonConfigurationKeys.USE_FIR)) {
                StorageComponentContainerContributor.INSTANCE.registerExtension(sharedScriptCompilationContext.getEnvironment().getProject(), new ScriptingSamWithReceiverComponentContributor(arrayList2));
            }
        }
        return sharedScriptCompilationContext;
    }

    public static final Pair<ScriptCompilationConfiguration, CompilerConfiguration> createInitialConfigurations(ScriptCompilationConfiguration scriptCompilationConfiguration, ScriptingHostConfiguration hostConfiguration, ScriptDiagnosticsMessageCollector messageCollector, IgnoredOptionsReportingState ignoredOptionsReportingState) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        List listSplit$default;
        Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "scriptCompilationConfiguration");
        Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(ignoredOptionsReportingState, "ignoredOptionsReportingState");
        CompilerConfiguration compilerConfigurationCreateInitialCompilerConfiguration = createInitialCompilerConfiguration(scriptCompilationConfiguration, hostConfiguration, messageCollector, ignoredOptionsReportingState);
        String property = System.getProperty(SCRIPT_BASE_COMPILER_ARGUMENTS_PROPERTY);
        if (property != null) {
            if (StringsKt.isBlank(property)) {
                property = null;
            }
            if (property != null && (listSplit$default = StringsKt.split$default((CharSequence) property, new char[]{StringUtil.SPACE}, false, 0, 6, (Object) null)) != null) {
                updateWithCompilerOptions$default(compilerConfigurationCreateInitialCompilerConfiguration, listSplit$default, null, 2, null);
            }
        }
        ScriptCompilationConfiguration scriptCompilationConfigurationWithUpdatesFromCompilerConfiguration = withUpdatesFromCompilerConfiguration(scriptCompilationConfiguration, compilerConfigurationCreateInitialCompilerConfiguration);
        List list = (List) scriptCompilationConfigurationWithUpdatesFromCompilerConfiguration.get(ScriptCompilationKt.getCompilerOptions(ScriptCompilationConfiguration.INSTANCE));
        if (list != null) {
            updateWithCompilerOptions(compilerConfigurationCreateInitialCompilerConfiguration, list, messageCollector, ignoredOptionsReportingState, false);
        }
        return new Pair<>(scriptCompilationConfigurationWithUpdatesFromCompilerConfiguration, compilerConfigurationCreateInitialCompilerConfiguration);
    }

    private static final void updateWithCompilerOptions(final CompilerConfiguration compilerConfiguration, List<String> list, final ScriptDiagnosticsMessageCollector scriptDiagnosticsMessageCollector, final IgnoredOptionsReportingState ignoredOptionsReportingState, final boolean z) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        updateWithCompilerOptions(compilerConfiguration, list, new Function1<K2JVMCompilerArguments, Boolean>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.CompilationContextKt.updateWithCompilerOptions.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(K2JVMCompilerArguments it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String strValidateArguments = ParseCommandLineArgumentsKt.validateArguments(it.getErrors());
                boolean z2 = false;
                if (strValidateArguments != null) {
                    MessageCollector.report$default(scriptDiagnosticsMessageCollector, CompilerMessageSeverity.ERROR, strValidateArguments, null, 4, null);
                } else {
                    ScriptDiagnosticsMessageCollector scriptDiagnosticsMessageCollector2 = scriptDiagnosticsMessageCollector;
                    IgnoredOptionsReportingState ignoredOptionsReportingState2 = ignoredOptionsReportingState;
                    boolean z3 = z;
                    ScriptDiagnosticsMessageCollector scriptDiagnosticsMessageCollector3 = scriptDiagnosticsMessageCollector2;
                    ArgumentsKt.reportArgumentParseProblems(scriptDiagnosticsMessageCollector3, it);
                    boolean zReportArgumentsNotAllowed = ErrorReportingKt.reportArgumentsNotAllowed(it, scriptDiagnosticsMessageCollector3, ignoredOptionsReportingState2);
                    ErrorReportingKt.reportArgumentsIgnoredGenerally(it, scriptDiagnosticsMessageCollector3, ignoredOptionsReportingState2);
                    if (z3) {
                        ErrorReportingKt.reportArgumentsIgnoredFromRefinement(it, scriptDiagnosticsMessageCollector3, ignoredOptionsReportingState2);
                    }
                    if (!zReportArgumentsNotAllowed) {
                        z2 = true;
                    }
                }
                return Boolean.valueOf(z2);
            }
        });
    }

    public static /* synthetic */ void updateWithCompilerOptions$default(CompilerConfiguration compilerConfiguration, List list, Function1 function1, int i, Object obj) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        if ((i & 2) != 0) {
            function1 = new Function1<K2JVMCompilerArguments, Boolean>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.CompilationContextKt.updateWithCompilerOptions.2
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(K2JVMCompilerArguments it) throws Exception {
                    Intrinsics.checkNotNullParameter(it, "it");
                    String strValidateArguments = ParseCommandLineArgumentsKt.validateArguments(it.getErrors());
                    if (strValidateArguments == null) {
                        return true;
                    }
                    throw new Exception("Error parsing arguments: " + strValidateArguments);
                }
            };
        }
        updateWithCompilerOptions(compilerConfiguration, list, function1);
    }

    public static final void updateWithCompilerOptions(CompilerConfiguration compilerConfiguration, List<String> compilerOptions, Function1<? super K2JVMCompilerArguments, Boolean> validate) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(compilerOptions, "compilerOptions");
        Intrinsics.checkNotNullParameter(validate, "validate");
        K2JVMCompilerArguments k2JVMCompilerArgumentsMakeScriptCompilerArguments = makeScriptCompilerArguments(compilerOptions);
        if (validate.invoke(k2JVMCompilerArgumentsMakeScriptCompilerArguments).booleanValue()) {
            LoadCompilerPluginsKt.processPluginsCommandLine(compilerConfiguration, k2JVMCompilerArgumentsMakeScriptCompilerArguments);
            ArgumentsKt.setupCommonArguments$default(compilerConfiguration, k2JVMCompilerArgumentsMakeScriptCompilerArguments, null, 2, null);
            JvmArgumentsKt.setupJvmSpecificArguments(compilerConfiguration, k2JVMCompilerArgumentsMakeScriptCompilerArguments);
            JvmArgumentsKt.configureAdvancedJvmOptions(compilerConfiguration, k2JVMCompilerArgumentsMakeScriptCompilerArguments);
            JvmArgumentsKt.configureKlibPaths(compilerConfiguration, k2JVMCompilerArgumentsMakeScriptCompilerArguments);
        }
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0030  */
    private static final K2JVMCompilerArguments makeScriptCompilerArguments(List<String> list) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List listEmptyList;
        K2JVMCompilerArguments k2JVMCompilerArguments = new K2JVMCompilerArguments();
        String property = System.getProperty(SCRIPT_BASE_COMPILER_ARGUMENTS_PROPERTY);
        if (property == null) {
            listEmptyList = CollectionsKt.emptyList();
        } else {
            if (StringsKt.isBlank(property)) {
                property = null;
            }
            if (property == null || (listEmptyList = StringsKt.split$default((CharSequence) property, new char[]{StringUtil.SPACE}, false, 0, 6, (Object) null)) == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
        }
        ParseCommandLineArgumentsKt.parseCommandLineArguments$default(CollectionsKt.plus((Collection) listEmptyList, (Iterable) list), k2JVMCompilerArguments, false, 4, null);
        return k2JVMCompilerArguments;
    }

    private static final ScriptCompilationConfiguration withUpdatesFromCompilerConfiguration(ScriptCompilationConfiguration scriptCompilationConfiguration, CompilerConfiguration compilerConfiguration) {
        return JvmScriptCompilationKt.withUpdatedClasspath(scriptCompilationConfiguration, JvmContentRootsKt.getJvmClasspathRoots(compilerConfiguration));
    }

    private static final CompilerConfiguration createInitialCompilerConfiguration(ScriptCompilationConfiguration scriptCompilationConfiguration, ScriptingHostConfiguration scriptingHostConfiguration, MessageCollector messageCollector, IgnoredOptionsReportingState ignoredOptionsReportingState) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        List<File> listEmptyList;
        JvmTarget jvmTarget;
        List listEmptyList2 = (List) scriptCompilationConfiguration.get(ScriptCompilationKt.getCompilerOptions(ScriptCompilationConfiguration.INSTANCE));
        if (listEmptyList2 == null) {
            listEmptyList2 = CollectionsKt.emptyList();
        }
        K2JVMCompilerArguments k2JVMCompilerArgumentsMakeScriptCompilerArguments = makeScriptCompilerArguments(listEmptyList2);
        ErrorReportingKt.reportArgumentsIgnoredGenerally(k2JVMCompilerArgumentsMakeScriptCompilerArguments, messageCollector, ignoredOptionsReportingState);
        ignoredOptionsReportingState.setCurrentArguments(k2JVMCompilerArgumentsMakeScriptCompilerArguments);
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, messageCollector);
        ArgumentsKt.setupCommonArguments$default(compilerConfiguration, k2JVMCompilerArgumentsMakeScriptCompilerArguments, null, 2, null);
        JvmArgumentsKt.setupJvmSpecificArguments(compilerConfiguration, k2JVMCompilerArgumentsMakeScriptCompilerArguments);
        String str = (String) scriptCompilationConfiguration.get(JvmScriptCompilationKt.getJvmTarget(JvmScriptCompilationKt.getJvm(ScriptCompilationConfiguration.INSTANCE)));
        if (str != null) {
            JvmTarget[] jvmTargetArrValues = JvmTarget.values();
            int length = jvmTargetArrValues.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    jvmTarget = null;
                    break;
                }
                jvmTarget = jvmTargetArrValues[i];
                if (Intrinsics.areEqual(jvmTarget.getDescription(), str)) {
                    break;
                }
                i++;
            }
            if (jvmTarget == null) {
                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.STRONG_WARNING, "Unknown JVM target \"" + str + "\", using default", null, 4, null);
            } else {
                compilerConfiguration.put(JVMConfigurationKeys.JVM_TARGET, jvmTarget);
            }
        }
        File file = (File) scriptCompilationConfiguration.get(JvmScriptCompilationKt.getJdkHome(JvmScriptCompilationKt.getJvm(ScriptCompilationConfiguration.INSTANCE)));
        if (file == null) {
            file = (File) scriptingHostConfiguration.get(JvmScriptingHostConfigurationKt.getJdkHome(JvmScriptingHostConfigurationKt.getJvm(ScriptingHostConfiguration.INSTANCE)));
        }
        if (file != null) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.LOGGING, "Using JDK home directory " + file, null, 4, null);
            compilerConfiguration.put(JVMConfigurationKeys.JDK_HOME, file);
        } else {
            JvmArgumentsKt.configureJdkHome(compilerConfiguration, k2JVMCompilerArgumentsMakeScriptCompilerArguments);
        }
        compilerConfiguration.put(JVMConfigurationKeys.RETAIN_OUTPUT_IN_MEMORY, true);
        boolean zIsModularJava = JvmArgumentsKt.isModularJava(compilerConfiguration);
        List<ScriptDependency> list = (List) scriptCompilationConfiguration.get(ScriptCompilationKt.getDependencies(ScriptCompilationConfiguration.INSTANCE));
        if (list != null) {
            ArrayList arrayList = new ArrayList();
            for (ScriptDependency scriptDependency : list) {
                JvmDependency jvmDependency = scriptDependency instanceof JvmDependency ? (JvmDependency) scriptDependency : null;
                if (jvmDependency == null || (listEmptyList = jvmDependency.getClasspath()) == null) {
                    listEmptyList = CollectionsKt.emptyList();
                }
                CollectionsKt.addAll(arrayList, listEmptyList);
            }
            JvmContentRootsKt.addJvmClasspathRoots(compilerConfiguration, arrayList);
        }
        compilerConfiguration.add(ComponentRegistrar.INSTANCE.getPLUGIN_COMPONENT_REGISTRARS(), new ScriptingCompilerConfigurationComponentRegistrar());
        JvmArgumentsKt.configureJavaModulesContentRoots(compilerConfiguration, k2JVMCompilerArgumentsMakeScriptCompilerArguments);
        JvmArgumentsKt.configureContentRootsFromClassPath(compilerConfiguration, k2JVMCompilerArgumentsMakeScriptCompilerArguments);
        if (!k2JVMCompilerArgumentsMakeScriptCompilerArguments.getNoStdlib()) {
            JvmArgumentsKt.addModularRootIfNotNull(compilerConfiguration, zIsModularJava, JavaModuleKt.KOTLIN_STDLIB_MODULE_NAME, KotlinJars.INSTANCE.getStdlib());
            JvmArgumentsKt.addModularRootIfNotNull(compilerConfiguration, zIsModularJava, "kotlin.script.runtime", KotlinJars.INSTANCE.getScriptRuntimeOrNull());
        }
        if (!k2JVMCompilerArgumentsMakeScriptCompilerArguments.getNoReflect() && !k2JVMCompilerArgumentsMakeScriptCompilerArguments.getNoStdlib()) {
            JvmArgumentsKt.addModularRootIfNotNull(compilerConfiguration, zIsModularJava, "kotlin.reflect", KotlinJars.INSTANCE.getReflectOrNull());
        }
        CompilerConfigurationKey<String> compilerConfigurationKey = CommonConfigurationKeys.MODULE_NAME;
        String moduleName = k2JVMCompilerArgumentsMakeScriptCompilerArguments.getModuleName();
        if (moduleName == null) {
            moduleName = "kotlin-script";
        }
        compilerConfiguration.put(compilerConfigurationKey, moduleName);
        JvmArgumentsKt.configureAdvancedJvmOptions(compilerConfiguration, k2JVMCompilerArgumentsMakeScriptCompilerArguments);
        JvmContentRootsKt.configureJdkClasspathRoots(compilerConfiguration);
        compilerConfiguration.put(JVMConfigurationKeys.USE_FAST_JAR_FILE_SYSTEM, true);
        compilerConfiguration.add(ScriptingConfigurationKeys.INSTANCE.getSCRIPT_DEFINITIONS(), new ScriptDefinition.FromConfigurations(scriptingHostConfiguration, scriptCompilationConfiguration, null, null, 8, null));
        String[] pluginClasspaths = k2JVMCompilerArgumentsMakeScriptCompilerArguments.getPluginClasspaths();
        List listAsList = pluginClasspaths != null ? ArraysKt.asList(pluginClasspaths) : null;
        if (listAsList == null) {
            listAsList = CollectionsKt.emptyList();
        }
        String[] pluginOptions = k2JVMCompilerArgumentsMakeScriptCompilerArguments.getPluginOptions();
        List listAsList2 = pluginOptions != null ? ArraysKt.asList(pluginOptions) : null;
        if (listAsList2 == null) {
            listAsList2 = CollectionsKt.emptyList();
        }
        String[] pluginConfigurations = k2JVMCompilerArgumentsMakeScriptCompilerArguments.getPluginConfigurations();
        if (pluginConfigurations == null) {
            pluginConfigurations = new String[0];
        }
        List mutableList = ArraysKt.toMutableList(pluginConfigurations);
        CLICompilerKt.checkPluginsArguments(messageCollector, false, listAsList, listAsList2, mutableList);
        List list2 = listAsList;
        if (!list2.isEmpty() || !mutableList.isEmpty()) {
            PluginCliParser.loadPluginsSafe(list2, listAsList2, mutableList, compilerConfiguration);
        } else {
            LoadCompilerPluginsKt.loadPlugins$default(compilerConfiguration, null, 1, null);
        }
        return compilerConfiguration;
    }

    public static final Pair<List<KtFile>, List<ScriptsCompilationDependencies.SourceDependencies>> collectRefinedSourcesAndUpdateEnvironment(SharedScriptCompilationContext context, KtFile mainKtFile, ScriptCompilationConfiguration initialConfiguration, ScriptDiagnosticsMessageCollector messageCollector) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(mainKtFile, "mainKtFile");
        Intrinsics.checkNotNullParameter(initialConfiguration, "initialConfiguration");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        ArrayList arrayListArrayListOf = CollectionsKt.arrayListOf(mainKtFile);
        ScriptsCompilationDependencies scriptsCompilationDependenciesCollectScriptsCompilationDependencies = ScriptsCompilationDependenciesKt.collectScriptsCompilationDependencies(context.getEnvironment().getConfiguration(), context.getEnvironment().getProject(), arrayListArrayListOf, initialConfiguration);
        List<File> listComponent1 = scriptsCompilationDependenciesCollectScriptsCompilationDependencies.component1();
        List<KtFile> listComponent2 = scriptsCompilationDependenciesCollectScriptsCompilationDependencies.component2();
        List<ScriptsCompilationDependencies.SourceDependencies> listComponent3 = scriptsCompilationDependenciesCollectScriptsCompilationDependencies.component3();
        KotlinCoreEnvironment environment = context.getEnvironment();
        List<File> list = listComponent1;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new JvmClasspathRoot((File) it.next()));
        }
        environment.updateClasspath(arrayList);
        arrayListArrayListOf.addAll(listComponent2);
        updateWithRefinedConfigurations(context.getEnvironment().getConfiguration(), context, arrayListArrayListOf, messageCollector);
        return TuplesKt.to(arrayListArrayListOf, listComponent3);
    }

    private static final void updateWithRefinedConfigurations(CompilerConfiguration compilerConfiguration, SharedScriptCompilationContext sharedScriptCompilationContext, List<? extends KtFile> list, ScriptDiagnosticsMessageCollector scriptDiagnosticsMessageCollector) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        List listEmptyList;
        ScriptCompilationConfigurationWrapper scriptConfiguration;
        ScriptCompilationConfiguration configuration;
        ScriptDependenciesProvider companion = ScriptDependenciesProvider.INSTANCE.getInstance(sharedScriptCompilationContext.getEnvironment().getProject());
        ArrayList arrayList = new ArrayList();
        for (KtFile ktFile : list) {
            if (companion == null || (scriptConfiguration = companion.getScriptConfiguration(ktFile)) == null || (configuration = scriptConfiguration.getConfiguration()) == null || (listEmptyList = (List) configuration.get(ScriptCompilationKt.getCompilerOptions(ScriptCompilationConfiguration.INSTANCE))) == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            CollectionsKt.addAll(arrayList, listEmptyList);
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty() || Intrinsics.areEqual(arrayList2, sharedScriptCompilationContext.getBaseScriptCompilationConfiguration().get(ScriptCompilationKt.getCompilerOptions(ScriptCompilationConfiguration.INSTANCE)))) {
            return;
        }
        updateWithCompilerOptions(compilerConfiguration, arrayList2, scriptDiagnosticsMessageCollector, sharedScriptCompilationContext.getIgnoredOptionsReportingState(), true);
    }
}
