package org.jetbrains.kotlin.scripting.compiler.plugin.dependencies;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ErrorHandlingKt;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.SourceCode;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocation;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.jvm.compiler.CoreEnvironmentUtilsKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.scripting.definitions.ScriptDependenciesProvider;
import org.jetbrains.kotlin.scripting.resolve.KtFileScriptSource;
import org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper;
import org.jetbrains.kotlin.scripting.resolve.VirtualFileScriptSource;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptsCompilationDependencies.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u001a0\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n¨\u0006\u000b²\u0006\u000f\u0010\f\u001a\u00070\r¢\u0006\u0002\b\u000eX\u008a\u0084\u0002"}, d2 = {"collectScriptsCompilationDependencies", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/dependencies/ScriptsCompilationDependencies;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "initialSources", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "providedConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME, "psiManager", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiManager;", "Lorg/jetbrains/annotations/NotNull;"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ScriptsCompilationDependenciesKt {
    public static /* synthetic */ ScriptsCompilationDependencies collectScriptsCompilationDependencies$default(CompilerConfiguration compilerConfiguration, Project project, Iterable iterable, ScriptCompilationConfiguration scriptCompilationConfiguration, int i, Object obj) {
        if ((i & 8) != 0) {
            scriptCompilationConfiguration = null;
        }
        return collectScriptsCompilationDependencies(compilerConfiguration, project, iterable, scriptCompilationConfiguration);
    }

    /* JADX WARN: Code duplicated, block: B:42:0x012a  */
    /* JADX WARN: Code duplicated, block: B:89:0x012d A[SYNTHETIC] */
    public static final ScriptsCompilationDependencies collectScriptsCompilationDependencies(CompilerConfiguration configuration, final Project project, Iterable<? extends KtFile> iterable, ScriptCompilationConfiguration scriptCompilationConfiguration) {
        Lazy lazy;
        KtFile ktFile;
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(project, "project");
        ArrayList initialSources = iterable;
        Intrinsics.checkNotNullParameter(initialSources, "initialSources");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        HashSet hashSet = new HashSet();
        Iterator<? extends KtFile> it = iterable.iterator();
        while (it.hasNext()) {
            VirtualFile virtualFile = it.next().getVirtualFile();
            String path = virtualFile != null ? virtualFile.getPath() : null;
            if (path != null) {
                hashSet.add(path);
            }
        }
        HashSet hashSet2 = hashSet;
        ScriptDependenciesProvider companion = ScriptDependenciesProvider.INSTANCE.getInstance(project);
        Lazy lazy2 = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<PsiManager>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.dependencies.ScriptsCompilationDependenciesKt$collectScriptsCompilationDependencies$psiManager$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiManager invoke() {
                return PsiManager.getInstance(project);
            }
        });
        if (companion != null) {
            while (true) {
                ArrayList arrayList4 = new ArrayList();
                Iterator<? extends KtFile> it2 = initialSources.iterator();
                while (it2.hasNext()) {
                    KtFile next = it2.next();
                    ResultWithDiagnostics<ScriptCompilationConfigurationWrapper> scriptConfigurationResult = companion.getScriptConfigurationResult(next, scriptCompilationConfiguration);
                    if (scriptConfigurationResult != null) {
                        if (scriptConfigurationResult instanceof ResultWithDiagnostics.Failure) {
                            arrayList3.add(new ScriptsCompilationDependencies.SourceDependencies(next, scriptConfigurationResult));
                        } else if (scriptConfigurationResult instanceof ResultWithDiagnostics.Success) {
                            ResultWithDiagnostics.Success success = (ResultWithDiagnostics.Success) scriptConfigurationResult;
                            arrayList.addAll(((ScriptCompilationConfigurationWrapper) success.getValue()).getDependenciesClassPath());
                            List<SourceCode> importedScripts = ((ScriptCompilationConfigurationWrapper) success.getValue()).getImportedScripts();
                            ArrayList arrayList5 = new ArrayList();
                            for (SourceCode sourceCode : importedScripts) {
                                if (sourceCode instanceof KtFileScriptSource) {
                                    ktFile = ((KtFileScriptSource) sourceCode).getKtFile();
                                    lazy = lazy2;
                                } else {
                                    VirtualFileScriptSource virtualFileScriptSource = sourceCode instanceof VirtualFileScriptSource ? (VirtualFileScriptSource) sourceCode : null;
                                    if (virtualFileScriptSource == null) {
                                        throw new IllegalStateException("expecting script sources resolved to virtual files here".toString());
                                    }
                                    lazy = lazy2;
                                    PsiFile psiFileFindFile = collectScriptsCompilationDependencies$lambda$1(lazy2).findFile(virtualFileScriptSource.getVirtualFile());
                                    ktFile = psiFileFindFile instanceof KtFile ? (KtFile) psiFileFindFile : null;
                                    if (ktFile == null) {
                                        CoreEnvironmentUtilsKt.report(configuration, CompilerMessageSeverity.ERROR, "imported file is not kotlin source: " + virtualFileScriptSource.getVirtualFile().getPath(), CompilerMessageLocation.INSTANCE.create(next.getVirtualFile().getPath()));
                                    }
                                    if (ktFile != null) {
                                        arrayList5.add(ktFile);
                                    }
                                    lazy2 = lazy;
                                    it2 = it2;
                                    companion = companion;
                                }
                                if (ktFile != null) {
                                    arrayList5.add(ktFile);
                                }
                                lazy2 = lazy;
                                it2 = it2;
                                companion = companion;
                            }
                            Lazy lazy3 = lazy2;
                            Iterator<? extends KtFile> it3 = it2;
                            ScriptDependenciesProvider scriptDependenciesProvider = companion;
                            ArrayList arrayList6 = arrayList5;
                            if (!arrayList6.isEmpty()) {
                                arrayList3.add(new ScriptsCompilationDependencies.SourceDependencies(next, ErrorHandlingKt.asSuccess(arrayList6, success.getReports())));
                                ArrayList<KtFile> arrayList7 = new ArrayList();
                                for (Object obj : arrayList6) {
                                    if (!hashSet2.contains(((KtFile) obj).getVirtualFile().getPath())) {
                                        arrayList7.add(obj);
                                    }
                                }
                                for (KtFile ktFile2 : arrayList7) {
                                    arrayList2.add(ktFile2);
                                    arrayList4.add(ktFile2);
                                    hashSet2.add(ktFile2.getVirtualFile().getPath());
                                }
                            }
                            lazy2 = lazy3;
                            it2 = it3;
                            companion = scriptDependenciesProvider;
                        } else {
                            continue;
                        }
                    }
                }
                Lazy lazy4 = lazy2;
                ScriptDependenciesProvider scriptDependenciesProvider2 = companion;
                if (arrayList4.isEmpty()) {
                    break;
                }
                initialSources = arrayList4;
                lazy2 = lazy4;
                companion = scriptDependenciesProvider2;
            }
        }
        HashSet hashSet3 = new HashSet();
        ArrayList arrayList8 = new ArrayList();
        for (Object obj2 : arrayList) {
            if (hashSet3.add(((File) obj2).getAbsolutePath())) {
                arrayList8.add(obj2);
            }
        }
        return new ScriptsCompilationDependencies(arrayList8, arrayList2, arrayList3);
    }

    private static final PsiManager collectScriptsCompilationDependencies$lambda$1(Lazy<? extends PsiManager> lazy) {
        return lazy.getValue();
    }
}
