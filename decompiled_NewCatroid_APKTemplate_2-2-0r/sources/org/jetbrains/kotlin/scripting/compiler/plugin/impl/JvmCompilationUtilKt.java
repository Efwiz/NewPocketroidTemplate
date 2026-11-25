package org.jetbrains.kotlin.scripting.compiler.plugin.impl;

import androidx.exifinterface.media.ExifInterface;
import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ErrorHandlingKt;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptDiagnostic;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.host.FileBasedScriptSource;
import kotlin.script.experimental.host.ScriptHostUtilKt;
import kotlin.script.experimental.jvm.impl.KJvmCompiledScript;
import org.jetbrains.kotlin.backend.common.output.OutputFile;
import org.jetbrains.kotlin.cli.common.environment.UtilKt;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiFileFactoryImpl;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.scripting.UtilsKt;
import org.jetbrains.kotlin.scripting.compiler.plugin.dependencies.ScriptsCompilationDependencies;
import org.jetbrains.kotlin.scripting.resolve.ScriptLightVirtualFile;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: jvmCompilationUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a.\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0000\u001a\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0000\u001aH\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u00012\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00022\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0016H\u0000\u001aJ\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00180\u0001\"\u0004\b\u0000\u0010\u00182\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0018\u0010\u001b\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\u00010\u0016H\u0086\bø\u0001\u0000\u001ad\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00180\u0001\"\u0004\b\u0000\u0010\u00182\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\b\b\u0002\u0010\u001d\u001a\u00020\u001e2\b\b\u0002\u0010\u001f\u001a\u00020 2\u001e\u0010\u001b\u001a\u001a\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\u00010!H\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\""}, d2 = {"getScriptKtFile", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lorg/jetbrains/kotlin/psi/KtFile;", "script", "Lkotlin/script/experimental/api/SourceCode;", "scriptCompilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "messageCollector", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/ScriptDiagnosticsMessageCollector;", "makeCompiledModule", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/KJvmCompiledModuleInMemoryImpl;", "generationState", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "makeCompiledScript", "Lkotlin/script/experimental/jvm/impl/KJvmCompiledScript;", "ktFile", "sourceDependencies", "", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/dependencies/ScriptsCompilationDependencies$SourceDependencies;", "getScriptConfiguration", "Lkotlin/Function1;", "withMessageCollector", ExifInterface.GPS_DIRECTION_TRUE, "parentMessageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "body", "withMessageCollectorAndDisposable", "disposable", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", "disposeOnSuccess", "", "Lkotlin/Function2;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmCompilationUtilKt {
    public static final KJvmCompiledModuleInMemoryImpl makeCompiledModule(GenerationState generationState) {
        Intrinsics.checkNotNullParameter(generationState, "generationState");
        List<OutputFile> listAsList = generationState.getFactory().asList();
        Intrinsics.checkNotNullExpressionValue(listAsList, "asList(...)");
        SortedMap sortedMapSortedMapOf = MapsKt.sortedMapOf(new Pair[0]);
        for (OutputFile outputFile : listAsList) {
            Pair pair = TuplesKt.to(outputFile.getRelativePath(), outputFile.getContent());
            sortedMapSortedMapOf.put(pair.getFirst(), pair.getSecond());
        }
        return new KJvmCompiledModuleInMemoryImpl(sortedMapSortedMapOf);
    }

    public static /* synthetic */ ResultWithDiagnostics withMessageCollectorAndDisposable$default(SourceCode sourceCode, MessageCollector messageCollector, Disposable disposable, boolean z, Function2 body, int i, Object obj) {
        ResultWithDiagnostics.Failure failure;
        if ((i & 1) != 0) {
            sourceCode = null;
        }
        if ((i & 2) != 0) {
            messageCollector = null;
        }
        if ((i & 4) != 0) {
            disposable = Disposer.newDisposable();
            Intrinsics.checkNotNullExpressionValue(disposable, "newDisposable(...)");
        }
        if ((i & 8) != 0) {
            z = true;
        }
        Intrinsics.checkNotNullParameter(disposable, "disposable");
        Intrinsics.checkNotNullParameter(body, "body");
        ScriptDiagnosticsMessageCollector scriptDiagnosticsMessageCollector = new ScriptDiagnosticsMessageCollector(messageCollector);
        try {
            UtilKt.setIdeaIoUseFallback();
            Object objInvoke = body.invoke(scriptDiagnosticsMessageCollector, disposable);
            boolean z2 = ((ResultWithDiagnostics) objInvoke) instanceof ResultWithDiagnostics.Failure;
            failure = (ResultWithDiagnostics) objInvoke;
            InlineMarker.finallyStart(1);
            if (z || z2) {
                Disposer.dispose(disposable);
            }
        } catch (Throwable th) {
            try {
                ScriptDiagnostic[] scriptDiagnosticArr = new ScriptDiagnostic[1];
                scriptDiagnosticArr[0] = ErrorHandlingKt.asDiagnostics$default(th, 0, null, sourceCode != null ? sourceCode.getLocationId() : null, null, null, 27, null);
                failure = ErrorReportingKt.failure(scriptDiagnosticsMessageCollector, scriptDiagnosticArr);
                InlineMarker.finallyStart(1);
            } finally {
                InlineMarker.finallyStart(1);
                Disposer.dispose(disposable);
                InlineMarker.finallyEnd(1);
            }
        }
        return failure;
    }

    public static final <T> ResultWithDiagnostics<T> withMessageCollectorAndDisposable(SourceCode sourceCode, MessageCollector messageCollector, Disposable disposable, boolean z, Function2<? super ScriptDiagnosticsMessageCollector, ? super Disposable, ? extends ResultWithDiagnostics<? extends T>> body) {
        ResultWithDiagnostics.Failure failure;
        Intrinsics.checkNotNullParameter(disposable, "disposable");
        Intrinsics.checkNotNullParameter(body, "body");
        ScriptDiagnosticsMessageCollector scriptDiagnosticsMessageCollector = new ScriptDiagnosticsMessageCollector(messageCollector);
        try {
            UtilKt.setIdeaIoUseFallback();
            ResultWithDiagnostics resultWithDiagnosticsInvoke = body.invoke(scriptDiagnosticsMessageCollector, disposable);
            boolean z2 = resultWithDiagnosticsInvoke instanceof ResultWithDiagnostics.Failure;
            failure = resultWithDiagnosticsInvoke;
            InlineMarker.finallyStart(1);
            if (z || z2) {
                Disposer.dispose(disposable);
            }
        } catch (Throwable th) {
            try {
                ScriptDiagnostic[] scriptDiagnosticArr = new ScriptDiagnostic[1];
                scriptDiagnosticArr[0] = ErrorHandlingKt.asDiagnostics$default(th, 0, null, sourceCode != null ? sourceCode.getLocationId() : null, null, null, 27, null);
                failure = ErrorReportingKt.failure(scriptDiagnosticsMessageCollector, scriptDiagnosticArr);
                InlineMarker.finallyStart(1);
            } finally {
                InlineMarker.finallyStart(1);
                Disposer.dispose(disposable);
                InlineMarker.finallyEnd(1);
            }
        }
        return failure;
    }

    public static /* synthetic */ ResultWithDiagnostics withMessageCollector$default(SourceCode sourceCode, MessageCollector messageCollector, Function1 body, int i, Object obj) {
        if ((i & 1) != 0) {
            sourceCode = null;
        }
        if ((i & 2) != 0) {
            messageCollector = null;
        }
        Intrinsics.checkNotNullParameter(body, "body");
        ScriptDiagnosticsMessageCollector scriptDiagnosticsMessageCollector = new ScriptDiagnosticsMessageCollector(messageCollector);
        try {
            return (ResultWithDiagnostics) body.invoke(scriptDiagnosticsMessageCollector);
        } catch (Throwable th) {
            ScriptDiagnostic[] scriptDiagnosticArr = new ScriptDiagnostic[1];
            scriptDiagnosticArr[0] = ErrorHandlingKt.asDiagnostics$default(th, 0, null, sourceCode != null ? sourceCode.getLocationId() : null, null, null, 27, null);
            return ErrorReportingKt.failure(scriptDiagnosticsMessageCollector, scriptDiagnosticArr);
        }
    }

    public static final <T> ResultWithDiagnostics<T> withMessageCollector(SourceCode sourceCode, MessageCollector messageCollector, Function1<? super ScriptDiagnosticsMessageCollector, ? extends ResultWithDiagnostics<? extends T>> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        ScriptDiagnosticsMessageCollector scriptDiagnosticsMessageCollector = new ScriptDiagnosticsMessageCollector(messageCollector);
        try {
            return body.invoke(scriptDiagnosticsMessageCollector);
        } catch (Throwable th) {
            ScriptDiagnostic[] scriptDiagnosticArr = new ScriptDiagnostic[1];
            scriptDiagnosticArr[0] = ErrorHandlingKt.asDiagnostics$default(th, 0, null, sourceCode != null ? sourceCode.getLocationId() : null, null, null, 27, null);
            return ErrorReportingKt.failure(scriptDiagnosticsMessageCollector, scriptDiagnosticArr);
        }
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0079  */
    /* JADX WARN: Code duplicated, block: B:23:0x0082  */
    public static final ResultWithDiagnostics<KtFile> getScriptKtFile(SourceCode script, ScriptCompilationConfiguration scriptCompilationConfiguration, Project project, ScriptDiagnosticsMessageCollector messageCollector) {
        File file;
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "scriptCompilationConfiguration");
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        PsiFileFactory psiFileFactory = PsiFileFactory.getInstance(project);
        Intrinsics.checkNotNull(psiFileFactory, "null cannot be cast to non-null type com.intellij.psi.impl.PsiFileFactoryImpl");
        PsiFileFactoryImpl psiFileFactoryImpl = (PsiFileFactoryImpl) psiFileFactory;
        String mergedScriptText = ScriptHostUtilKt.getMergedScriptText(script, scriptCompilationConfiguration);
        String strScriptFileName = UtilsKt.scriptFileName(script, script, scriptCompilationConfiguration);
        FileBasedScriptSource fileBasedScriptSource = script instanceof FileBasedScriptSource ? (FileBasedScriptSource) script : null;
        KtFile ktFile = (KtFile) psiFileFactoryImpl.trySetupPsiForFile(new ScriptLightVirtualFile(strScriptFileName, (fileBasedScriptSource == null || (file = fileBasedScriptSource.getFile()) == null) ? null : file.getPath(), mergedScriptText), KotlinLanguage.INSTANCE, true, false);
        if (ktFile == null) {
            return ErrorReportingKt.failure(script, messageCollector, "Unable to make PSI file from script");
        }
        for (Object obj : ktFile.getDeclarations()) {
            if (obj instanceof KtScript) {
                if (obj == null) {
                    return ErrorReportingKt.failure(script, messageCollector, "Not a script file");
                }
                return ErrorHandlingKt.asSuccess$default(ktFile, null, 1, null);
            }
        }
        obj = null;
        if (obj == null) {
            return ErrorReportingKt.failure(script, messageCollector, "Not a script file");
        }
        return ErrorHandlingKt.asSuccess$default(ktFile, null, 1, null);
    }

    public static final ResultWithDiagnostics<KJvmCompiledScript> makeCompiledScript(GenerationState generationState, SourceCode script, KtFile ktFile, List<ScriptsCompilationDependencies.SourceDependencies> sourceDependencies, Function1<? super KtFile, ? extends ScriptCompilationConfiguration> getScriptConfiguration) {
        Object next;
        Pair pair;
        Intrinsics.checkNotNullParameter(generationState, "generationState");
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(ktFile, "ktFile");
        Intrinsics.checkNotNullParameter(sourceDependencies, "sourceDependencies");
        Intrinsics.checkNotNullParameter(getScriptConfiguration, "getScriptConfiguration");
        ArrayDeque arrayDeque = new ArrayDeque();
        Iterator<T> it = ktFile.getDeclarations().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!(next instanceof KtScript));
        KtScript ktScript = (KtScript) next;
        if (ktScript == null) {
            throw new IllegalStateException("Expecting script file: KtScript is not found in " + ktFile.get$name());
        }
        KJvmCompiledModuleInMemoryImpl kJvmCompiledModuleInMemoryImplMakeCompiledModule = makeCompiledModule(generationState);
        GenerationState.ForScript scriptSpecific = generationState.getScriptSpecific();
        if (scriptSpecific.getResultFieldName() == null) {
            pair = null;
        } else {
            String resultFieldName = scriptSpecific.getResultFieldName();
            Intrinsics.checkNotNull(resultFieldName);
            DescriptorRenderer descriptorRenderer = DescriptorRenderer.FQ_NAMES_IN_TYPES;
            KotlinType resultType = scriptSpecific.getResultType();
            Intrinsics.checkNotNull(resultType);
            pair = TuplesKt.to(resultFieldName, new kotlin.script.experimental.api.KotlinType(descriptorRenderer.renderType(resultType), false, 2, (DefaultConstructorMarker) null));
        }
        ResultWithDiagnostics resultWithDiagnosticsMakeCompiledScript$makeOtherScripts = makeCompiledScript$makeOtherScripts(arrayDeque, sourceDependencies, getScriptConfiguration, ktScript);
        if (resultWithDiagnosticsMakeCompiledScript$makeOtherScripts instanceof ResultWithDiagnostics.Success) {
            List<ScriptDiagnostic> reports = resultWithDiagnosticsMakeCompiledScript$makeOtherScripts.getReports();
            List list = (List) ((ResultWithDiagnostics.Success) resultWithDiagnosticsMakeCompiledScript$makeOtherScripts).getValue();
            String locationId = script.getLocationId();
            KtFile containingKtFile = ktScript.getContainingKtFile();
            Intrinsics.checkNotNullExpressionValue(containingKtFile, "getContainingKtFile(...)");
            ScriptCompilationConfiguration scriptCompilationConfigurationInvoke = getScriptConfiguration.invoke(containingKtFile);
            String strAsString = ktScript.mo7391getFqName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return ErrorHandlingKt.plus(reports, ErrorHandlingKt.asSuccess$default(new KJvmCompiledScript(locationId, scriptCompilationConfigurationInvoke, strAsString, pair, list, kJvmCompiledModuleInMemoryImplMakeCompiledModule), null, 1, null));
        }
        if (resultWithDiagnosticsMakeCompiledScript$makeOtherScripts instanceof ResultWithDiagnostics.Failure) {
            return resultWithDiagnosticsMakeCompiledScript$makeOtherScripts;
        }
        throw new NoWhenBranchMatchedException();
    }

    /* JADX WARN: Code duplicated, block: B:43:0x0103  */
    /* JADX WARN: Code duplicated, block: B:53:0x013c  */
    private static final ResultWithDiagnostics<List<KJvmCompiledScript>> makeCompiledScript$makeOtherScripts(ArrayDeque<KtScript> arrayDeque, List<ScriptsCompilationDependencies.SourceDependencies> list, Function1<? super KtFile, ? extends ScriptCompilationConfiguration> function1, KtScript ktScript) {
        Object next;
        ResultWithDiagnostics.Success successAsSuccess$default;
        ResultWithDiagnostics<List<KtFile>> sourceDependencies;
        List list2;
        Object next2;
        ResultWithDiagnostics.Success successAsSuccess$default2;
        if (arrayDeque.contains(ktScript)) {
            ScriptDiagnostic[] scriptDiagnosticArr = new ScriptDiagnostic[1];
            VirtualFile virtualFile = ktScript.getContainingFile().getVirtualFile();
            scriptDiagnosticArr[0] = new ScriptDiagnostic(-1, "Unable to handle recursive script dependencies", null, virtualFile != null ? virtualFile.getPath() : null, null, null, 52, null);
            return new ResultWithDiagnostics.Failure(scriptDiagnosticArr);
        }
        arrayDeque.push(ktScript);
        KtFile containingKtFile = ktScript.getContainingKtFile();
        Intrinsics.checkNotNullExpressionValue(containingKtFile, "getContainingKtFile(...)");
        Iterator<T> it = list.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(((ScriptsCompilationDependencies.SourceDependencies) next).getScriptFile(), containingKtFile));
        ScriptsCompilationDependencies.SourceDependencies sourceDependencies2 = (ScriptsCompilationDependencies.SourceDependencies) next;
        if (sourceDependencies2 == null || (sourceDependencies = sourceDependencies2.getSourceDependencies()) == null || (list2 = (List) ErrorHandlingKt.valueOrThrow(sourceDependencies)) == null) {
            successAsSuccess$default = ErrorHandlingKt.asSuccess$default(CollectionsKt.emptyList(), null, 1, null);
        } else {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            Iterator it2 = list2.iterator();
            while (true) {
                if (it2.hasNext()) {
                    KtFile ktFile = (KtFile) it2.next();
                    Iterator<T> it3 = ktFile.getDeclarations().iterator();
                    do {
                        if (!it3.hasNext()) {
                            next2 = null;
                            break;
                        }
                        next2 = it3.next();
                    } while (!(next2 instanceof KtScript));
                    KtScript ktScript2 = (KtScript) next2;
                    if (ktScript2 != null) {
                        successAsSuccess$default2 = makeCompiledScript$makeOtherScripts(arrayDeque, list, function1, ktScript2);
                        if (successAsSuccess$default2 instanceof ResultWithDiagnostics.Success) {
                            List<ScriptDiagnostic> reports = successAsSuccess$default2.getReports();
                            List list3 = (List) ((ResultWithDiagnostics.Success) successAsSuccess$default2).getValue();
                            String virtualFilePath = ktFile.getVirtualFilePath();
                            ScriptCompilationConfiguration scriptCompilationConfigurationInvoke = function1.invoke(ktFile);
                            String strAsString = ktScript2.mo7391getFqName().asString();
                            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                            successAsSuccess$default2 = ErrorHandlingKt.plus(reports, ErrorHandlingKt.asSuccess$default(new KJvmCompiledScript(virtualFilePath, scriptCompilationConfigurationInvoke, strAsString, null, list3, null), null, 1, null));
                        } else if (!(successAsSuccess$default2 instanceof ResultWithDiagnostics.Failure)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        if (successAsSuccess$default2 == null) {
                            successAsSuccess$default2 = ErrorHandlingKt.asSuccess$default(null, null, 1, null);
                        }
                    } else {
                        successAsSuccess$default2 = ErrorHandlingKt.asSuccess$default(null, null, 1, null);
                    }
                    arrayList.addAll(successAsSuccess$default2.getReports());
                    if (successAsSuccess$default2 instanceof ResultWithDiagnostics.Success) {
                        Object value = ((ResultWithDiagnostics.Success) successAsSuccess$default2).getValue();
                        ArrayList arrayList3 = arrayList2;
                        if (value != null) {
                            arrayList3.add(value);
                        }
                    } else {
                        successAsSuccess$default = new ResultWithDiagnostics.Failure(arrayList);
                        break;
                    }
                } else {
                    successAsSuccess$default = ErrorHandlingKt.asSuccess(arrayList2, arrayList);
                    break;
                }
            }
            if (successAsSuccess$default == null) {
                successAsSuccess$default = ErrorHandlingKt.asSuccess$default(CollectionsKt.emptyList(), null, 1, null);
            }
        }
        arrayDeque.pop();
        return successAsSuccess$default;
    }
}
