package org.jetbrains.kotlin.scripting.compiler.plugin;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.util.PropertiesCollection;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.com.intellij.core.CoreFileTypeRegistry;
import org.jetbrains.kotlin.com.intellij.mock.MockProject;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeRegistry;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.extensions.CompilerConfigurationExtension;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.scripting.compiler.plugin.definitions.CliScriptDefinitionProvider;
import org.jetbrains.kotlin.scripting.configuration.ScriptingConfigurationKeys;
import org.jetbrains.kotlin.scripting.definitions.ScriptCompilationConfigurationFromDefinitionKt;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinition;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinitionProvider;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinitionsSource;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptingCompilerConfigurationExtension.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\fH\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/ScriptingCompilerConfigurationExtension;", "Lorg/jetbrains/kotlin/extensions/CompilerConfigurationExtension;", "project", "Lorg/jetbrains/kotlin/com/intellij/mock/MockProject;", "baseHostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "(Lcom/intellij/mock/MockProject;Lkotlin/script/experimental/host/ScriptingHostConfiguration;)V", "getBaseHostConfiguration", "()Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "getProject", "()Lcom/intellij/mock/MockProject;", "updateConfiguration", "", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "updateFileRegistry", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptingCompilerConfigurationExtension implements CompilerConfigurationExtension {
    private final ScriptingHostConfiguration baseHostConfiguration;
    private final MockProject project;

    public ScriptingCompilerConfigurationExtension(MockProject project, ScriptingHostConfiguration baseHostConfiguration) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(baseHostConfiguration, "baseHostConfiguration");
        this.project = project;
        this.baseHostConfiguration = baseHostConfiguration;
    }

    public final MockProject getProject() {
        return this.project;
    }

    public final ScriptingHostConfiguration getBaseHostConfiguration() {
        return this.baseHostConfiguration;
    }

    @Override // org.jetbrains.kotlin.extensions.CompilerConfigurationExtension
    public void updateConfiguration(final CompilerConfiguration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        if (configuration.getBoolean(ScriptingConfigurationKeys.INSTANCE.getDISABLE_SCRIPTING_PLUGIN_OPTION())) {
            return;
        }
        MockProject mockProject = this.project;
        String basePath = mockProject.getBasePath();
        if (basePath == null) {
            VirtualFile baseDir = mockProject.getBaseDir();
            basePath = baseDir != null ? baseDir.getCanonicalPath() : null;
        }
        File file = basePath != null ? new File(basePath) : null;
        if (file != null) {
            configuration.put(ScriptingConfigurationKeys.INSTANCE.getLEGACY_SCRIPT_RESOLVER_ENVIRONMENT_OPTION(), "projectRoot", file);
        }
        ScriptingHostConfiguration scriptingHostConfiguration = new ScriptingHostConfiguration(new ScriptingHostConfiguration[]{this.baseHostConfiguration}, new Function1<ScriptingHostConfiguration.Builder, Unit>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.ScriptingCompilerConfigurationExtension$updateConfiguration$hostConfiguration$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ScriptingHostConfiguration.Builder builder) {
                invoke2(builder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ScriptingHostConfiguration.Builder $receiver) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                PropertiesCollection.Key<Function0<Map<String, Object>>> getEnvironment = ScriptCompilationConfigurationFromDefinitionKt.getGetEnvironment($receiver);
                final CompilerConfiguration compilerConfiguration = configuration;
                $receiver.invoke(getEnvironment, new Function0<Map<String, Object>>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.ScriptingCompilerConfigurationExtension$updateConfiguration$hostConfiguration$1.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Map<String, Object> invoke() {
                        return compilerConfiguration.getMap(ScriptingConfigurationKeys.INSTANCE.getLEGACY_SCRIPT_RESOLVER_ENVIRONMENT_OPTION());
                    }
                });
            }
        });
        ClassLoader classLoader = getClass().getClassLoader();
        Intrinsics.checkNotNullExpressionValue(classLoader, "getClassLoader(...)");
        ScriptingCompilerConfigurationExtensionKt.configureScriptDefinitions(configuration, scriptingHostConfiguration, classLoader);
        ScriptDefinitionProvider companion = ScriptDefinitionProvider.INSTANCE.getInstance(this.project);
        CliScriptDefinitionProvider cliScriptDefinitionProvider = companion instanceof CliScriptDefinitionProvider ? (CliScriptDefinitionProvider) companion : null;
        if (cliScriptDefinitionProvider != null) {
            List<? extends ScriptDefinitionsSource> list = configuration.getList(ScriptingConfigurationKeys.INSTANCE.getSCRIPT_DEFINITIONS_SOURCES());
            Intrinsics.checkNotNullExpressionValue(list, "getList(...)");
            cliScriptDefinitionProvider.setScriptDefinitionsSources(list);
            List<? extends ScriptDefinition> list2 = configuration.getList(ScriptingConfigurationKeys.INSTANCE.getSCRIPT_DEFINITIONS());
            Intrinsics.checkNotNullExpressionValue(list2, "getList(...)");
            cliScriptDefinitionProvider.setScriptDefinitions(list2);
        }
    }

    @Override // org.jetbrains.kotlin.extensions.CompilerConfigurationExtension
    public void updateFileRegistry() {
        ScriptDefinitionProvider companion = ScriptDefinitionProvider.INSTANCE.getInstance(this.project);
        CliScriptDefinitionProvider cliScriptDefinitionProvider = companion instanceof CliScriptDefinitionProvider ? (CliScriptDefinitionProvider) companion : null;
        if (cliScriptDefinitionProvider != null) {
            FileTypeRegistry fileTypeRegistry = FileTypeRegistry.getInstance();
            Intrinsics.checkNotNull(fileTypeRegistry, "null cannot be cast to non-null type com.intellij.core.CoreFileTypeRegistry");
            final CoreFileTypeRegistry coreFileTypeRegistry = (CoreFileTypeRegistry) fileTypeRegistry;
            synchronized (KotlinCoreEnvironment.INSTANCE.getAPPLICATION_LOCK()) {
                Iterator it = SequencesKt.filter(cliScriptDefinitionProvider.getKnownFilenameExtensions(), new Function1<String, Boolean>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.ScriptingCompilerConfigurationExtension$updateFileRegistry$1$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(String it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        return Boolean.valueOf(!Intrinsics.areEqual(coreFileTypeRegistry.getFileTypeByExtension(it2), KotlinFileType.INSTANCE));
                    }
                }).iterator();
                while (it.hasNext()) {
                    coreFileTypeRegistry.registerFileType(KotlinFileType.INSTANCE, (String) it.next());
                }
                Unit unit = Unit.INSTANCE;
            }
        }
    }
}
