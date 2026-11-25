package org.jetbrains.kotlin.scripting.compiler.plugin.services;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ErrorHandlingKt;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.host.FileScriptSource;
import kotlin.script.experimental.host.StringScriptSource;
import org.jetbrains.kotlin.KtInMemoryTextSourceFile;
import org.jetbrains.kotlin.KtIoFileSourceFile;
import org.jetbrains.kotlin.KtPsiSourceFile;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.KtVirtualFileSourceFile;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinition;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinitionProvider;
import org.jetbrains.kotlin.scripting.definitions.ScriptDependenciesProvider;
import org.jetbrains.kotlin.scripting.resolve.KtFileScriptSource;
import org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper;
import org.jetbrains.kotlin.scripting.resolve.VirtualFileScriptSource;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: FirScriptConfigurationExtensionImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0016\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u001a\u0016\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u000e\u0010\u0007\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u0002\u001a\f\u0010\b\u001a\u00020\u0006*\u00020\u0004H\u0002\u001a\u000e\u0010\t\u001a\u0004\u0018\u00010\n*\u00020\u000bH\u0002\u001a\f\u0010\f\u001a\u0004\u0018\u00010\u0004*\u00020\u000b¨\u0006\r"}, d2 = {"configurationFor", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/services/FirScriptDefinitionProviderService;", "sourceCode", "Lkotlin/script/experimental/api/SourceCode;", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "defaultConfiguration", "originalKtFile", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "Lorg/jetbrains/kotlin/KtSourceFile;", "toSourceCode", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirScriptConfigurationExtensionImplKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final Project project(KtSourceFile ktSourceFile) {
        KtFile ktFile;
        SourceCode sourceCode = toSourceCode(ktSourceFile);
        KtFileScriptSource ktFileScriptSource = sourceCode instanceof KtFileScriptSource ? (KtFileScriptSource) sourceCode : null;
        if (ktFileScriptSource == null || (ktFile = ktFileScriptSource.getKtFile()) == null) {
            return null;
        }
        return ktFile.getProject();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final KtFile originalKtFile(SourceCode sourceCode) {
        KtFile ktFile;
        KtFileScriptSource ktFileScriptSource = sourceCode instanceof KtFileScriptSource ? (KtFileScriptSource) sourceCode : null;
        PsiFile originalFile = (ktFileScriptSource == null || (ktFile = ktFileScriptSource.getKtFile()) == null) ? null : ktFile.getOriginalFile();
        KtFile ktFile2 = originalFile instanceof KtFile ? (KtFile) originalFile : null;
        if (ktFile2 != null) {
            return ktFile2;
        }
        throw new IllegalStateException("only PSI scripts are supported at the moment".toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ScriptCompilationConfiguration configurationFor(FirScriptDefinitionProviderService firScriptDefinitionProviderService, KtFile ktFile) {
        ResultWithDiagnostics<ScriptCompilationConfigurationWrapper> scriptConfigurationResult;
        ScriptCompilationConfigurationWrapper scriptCompilationConfigurationWrapper;
        ScriptDependenciesProvider configurationProvider = firScriptDefinitionProviderService.getConfigurationProvider();
        if (configurationProvider == null || (scriptConfigurationResult = configurationProvider.getScriptConfigurationResult(ktFile)) == null || (scriptCompilationConfigurationWrapper = (ScriptCompilationConfigurationWrapper) ErrorHandlingKt.valueOrNull(scriptConfigurationResult)) == null) {
            return null;
        }
        return scriptCompilationConfigurationWrapper.getConfiguration();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ScriptCompilationConfiguration configurationFor(FirScriptDefinitionProviderService firScriptDefinitionProviderService, SourceCode sourceCode) {
        ScriptDefinition scriptDefinitionFindDefinition;
        ScriptDefinitionProvider definitionProvider = firScriptDefinitionProviderService.getDefinitionProvider();
        if (definitionProvider == null || (scriptDefinitionFindDefinition = definitionProvider.findDefinition(sourceCode)) == null) {
            return null;
        }
        return scriptDefinitionFindDefinition.getCompilationConfiguration();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ScriptCompilationConfiguration defaultConfiguration(FirScriptDefinitionProviderService firScriptDefinitionProviderService) {
        ScriptDefinition defaultDefinition;
        ScriptDefinitionProvider definitionProvider = firScriptDefinitionProviderService.getDefinitionProvider();
        if (definitionProvider == null || (defaultDefinition = definitionProvider.getDefaultDefinition()) == null) {
            return null;
        }
        return defaultDefinition.getCompilationConfiguration();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final SourceCode toSourceCode(KtSourceFile ktSourceFile) {
        KtFileScriptSource virtualFileScriptSource;
        Intrinsics.checkNotNullParameter(ktSourceFile, "<this>");
        int i = 2;
        String str = null;
        Object[] objArr = 0;
        if (!(ktSourceFile instanceof KtPsiSourceFile)) {
            if (ktSourceFile instanceof KtVirtualFileSourceFile) {
                return new VirtualFileScriptSource(((KtVirtualFileSourceFile) ktSourceFile).getVirtualFile(), null, 2, null);
            }
            if (ktSourceFile instanceof KtIoFileSourceFile) {
                return new FileScriptSource(((KtIoFileSourceFile) ktSourceFile).getFile(), null, 2, null);
            }
            if (ktSourceFile instanceof KtInMemoryTextSourceFile) {
                return new StringScriptSource(((KtInMemoryTextSourceFile) ktSourceFile).getText().toString(), ktSourceFile.getName());
            }
            return null;
        }
        KtPsiSourceFile ktPsiSourceFile = (KtPsiSourceFile) ktSourceFile;
        PsiFile psiFile = ktPsiSourceFile.getPsiFile();
        KtFile ktFile = psiFile instanceof KtFile ? (KtFile) psiFile : null;
        if (ktFile != null) {
            virtualFileScriptSource = new KtFileScriptSource(ktFile, str, i, objArr == true ? 1 : 0);
        } else {
            VirtualFile virtualFile = ktPsiSourceFile.getPsiFile().getVirtualFile();
            Intrinsics.checkNotNullExpressionValue(virtualFile, "getVirtualFile(...)");
            virtualFileScriptSource = new VirtualFileScriptSource(virtualFile, null, 2, null);
        }
        return virtualFileScriptSource;
    }
}
