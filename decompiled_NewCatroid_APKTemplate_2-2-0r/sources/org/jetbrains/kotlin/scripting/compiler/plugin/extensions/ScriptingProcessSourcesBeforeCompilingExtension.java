package org.jetbrains.kotlin.scripting.compiler.plugin.extensions;

import java.util.ArrayList;
import java.util.Collection;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.extensions.ProcessSourcesBeforeCompilingExtension;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinition;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinitionProvider;
import org.jetbrains.kotlin.scripting.resolve.KtFileScriptSource;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptingProcessSourcesBeforeCompilingExtension.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J$\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r²\u0006\f\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/extensions/ScriptingProcessSourcesBeforeCompilingExtension;", "Lorg/jetbrains/kotlin/extensions/ProcessSourcesBeforeCompilingExtension;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lcom/intellij/openapi/project/Project;)V", "getProject", "()Lcom/intellij/openapi/project/Project;", "processSources", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "sources", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME, "definitionProvider", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinitionProvider;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptingProcessSourcesBeforeCompilingExtension implements ProcessSourcesBeforeCompilingExtension {
    private final Project project;

    public ScriptingProcessSourcesBeforeCompilingExtension(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        this.project = project;
    }

    public final Project getProject() {
        return this.project;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.extensions.ProcessSourcesBeforeCompilingExtension
    public Collection<KtFile> processSources(Collection<? extends KtFile> sources, CompilerConfiguration configuration) {
        Intrinsics.checkNotNullParameter(sources, "sources");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        boolean zSupportsFeature = CommonConfigurationKeysKt.getLanguageVersionSettings(configuration).supportsFeature(LanguageFeature.SkipStandaloneScriptsInSourceRoots);
        Lazy lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<ScriptDefinitionProvider>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.extensions.ScriptingProcessSourcesBeforeCompilingExtension$processSources$definitionProvider$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ScriptDefinitionProvider invoke() {
                return ScriptDefinitionProvider.INSTANCE.getInstance(this.this$0.getProject());
            }
        });
        Object notNull = configuration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        MessageCollector messageCollector = (MessageCollector) notNull;
        if (configuration.getBoolean(CommonConfigurationKeys.ALLOW_ANY_SCRIPTS_IN_SOURCE_ROOTS)) {
            return sources;
        }
        String[] strArr = {KotlinFileType.DOT_DEFAULT_EXTENSION, SuffixConstants.SUFFIX_STRING_java};
        ArrayList arrayList = new ArrayList();
        for (Object obj : sources) {
            KtFile ktFile = (KtFile) obj;
            int i = 0;
            while (true) {
                if (i < 2) {
                    if (!StringsKt.endsWith$default(ktFile.getVirtualFilePath(), strArr[i], false, 2, (Object) null)) {
                        i++;
                    }
                } else if (processSources$isStandaloneScript(ktFile, lazy)) {
                    if (!zSupportsFeature) {
                        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.WARNING, "Script '" + ktFile.getMyName() + "' is not supposed to be used along with regular Kotlin sources, and will be ignored in the future versions by default. (Use -Xallow-any-scripts-in-source-roots command line option to opt-in for the old behavior.)", null, 4, null);
                    }
                    if (zSupportsFeature) {
                        break;
                    }
                }
                arrayList.add(obj);
                break;
            }
        }
        return arrayList;
    }

    private static final ScriptDefinitionProvider processSources$lambda$0(Lazy<? extends ScriptDefinitionProvider> lazy) {
        return lazy.getValue();
    }

    private static final boolean processSources$isStandaloneScript(KtFile ktFile, Lazy<? extends ScriptDefinitionProvider> lazy) {
        ScriptCompilationConfiguration compilationConfiguration;
        Boolean bool;
        ScriptDefinitionProvider scriptDefinitionProviderProcessSources$lambda$0 = processSources$lambda$0(lazy);
        ScriptDefinition scriptDefinitionFindDefinition = scriptDefinitionProviderProcessSources$lambda$0 != null ? scriptDefinitionProviderProcessSources$lambda$0.findDefinition(new KtFileScriptSource(ktFile, null, 2, null)) : null;
        if (scriptDefinitionFindDefinition == null || (compilationConfiguration = scriptDefinitionFindDefinition.getCompilationConfiguration()) == null || (bool = (Boolean) compilationConfiguration.get(ScriptCompilationKt.isStandalone(ScriptCompilationConfiguration.INSTANCE))) == null) {
            return true;
        }
        return bool.booleanValue();
    }
}
