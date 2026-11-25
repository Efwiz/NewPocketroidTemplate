package org.jetbrains.kotlin.scripting.definitions;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ErrorHandlingKt;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.dependencies.ScriptDependencies;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptDependenciesProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u001e\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\b\u0018\u00010\fj\u0004\u0018\u0001`\r2\u0006\u0010\t\u001a\u00020\nH\u0016J(\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\b\u0018\u00010\fj\u0004\u0018\u0001`\r2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\u0012\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\t\u001a\u00020\u0012H\u0007J\u0012\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\t\u001a\u00020\u0013H\u0007R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/ScriptDependenciesProvider;", "", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lcom/intellij/openapi/project/Project;)V", "getProject", "()Lcom/intellij/openapi/project/Project;", "getScriptConfiguration", "Lorg/jetbrains/kotlin/scripting/resolve/ScriptCompilationConfigurationWrapper;", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "getScriptConfigurationResult", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lorg/jetbrains/kotlin/scripting/resolve/ScriptCompilationConfigurationResult;", "providedConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "getScriptDependencies", "Lkotlin/script/experimental/dependencies/ScriptDependencies;", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "Companion", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ScriptDependenciesProvider {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Project project;

    public ResultWithDiagnostics<ScriptCompilationConfigurationWrapper> getScriptConfigurationResult(KtFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return null;
    }

    public ScriptDependenciesProvider(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        this.project = project;
    }

    protected final Project getProject() {
        return this.project;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Migrating to configuration refinement")
    public final ScriptDependencies getScriptDependencies(VirtualFile file) {
        ScriptCompilationConfigurationWrapper scriptConfiguration;
        Intrinsics.checkNotNullParameter(file, "file");
        PsiFile psiFileFindFile = PsiManager.getInstance(this.project).findFile(file);
        KtFile ktFile = psiFileFindFile instanceof KtFile ? (KtFile) psiFileFindFile : null;
        if (ktFile == null || (scriptConfiguration = getScriptConfiguration(ktFile)) == null) {
            return null;
        }
        return scriptConfiguration.getLegacyDependencies();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Migrating to configuration refinement")
    public final ScriptDependencies getScriptDependencies(PsiFile file) {
        ScriptCompilationConfigurationWrapper scriptConfiguration;
        Intrinsics.checkNotNullParameter(file, "file");
        if ((file instanceof KtFile) && (scriptConfiguration = getScriptConfiguration((KtFile) file)) != null) {
            return scriptConfiguration.getLegacyDependencies();
        }
        return null;
    }

    public ResultWithDiagnostics<ScriptCompilationConfigurationWrapper> getScriptConfigurationResult(KtFile file, ScriptCompilationConfiguration providedConfiguration) {
        Intrinsics.checkNotNullParameter(file, "file");
        return getScriptConfigurationResult(file);
    }

    public ScriptCompilationConfigurationWrapper getScriptConfiguration(KtFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        ResultWithDiagnostics<ScriptCompilationConfigurationWrapper> scriptConfigurationResult = getScriptConfigurationResult(file);
        if (scriptConfigurationResult != null) {
            return (ScriptCompilationConfigurationWrapper) ErrorHandlingKt.valueOrNull(scriptConfigurationResult);
        }
        return null;
    }

    /* JADX INFO: compiled from: ScriptDependenciesProvider.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/ScriptDependenciesProvider$Companion;", "", "()V", "getInstance", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDependenciesProvider;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ScriptDependenciesProvider getInstance(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            return (ScriptDependenciesProvider) project.getService(ScriptDependenciesProvider.class);
        }
    }
}
