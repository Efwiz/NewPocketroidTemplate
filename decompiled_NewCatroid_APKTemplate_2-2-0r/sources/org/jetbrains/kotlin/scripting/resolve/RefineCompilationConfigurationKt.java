package org.jetbrains.kotlin.scripting.resolve;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KProperty;
import kotlin.script.experimental.api.ErrorHandlingKt;
import kotlin.script.experimental.api.ExternalSourceCode;
import kotlin.script.experimental.api.KotlinType;
import kotlin.script.experimental.api.RefineConfigurationOnAnnotationsData;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCollectedData;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationConfigurationKeys;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.ScriptDataKt;
import kotlin.script.experimental.api.ScriptDiagnostic;
import kotlin.script.experimental.api.ScriptSourceAnnotation;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.dependencies.AsyncDependenciesResolver;
import kotlin.script.experimental.dependencies.DependenciesResolver;
import kotlin.script.experimental.dependencies.ScriptDependencies;
import kotlin.script.experimental.host.FileBasedScriptSource;
import kotlin.script.experimental.host.FileScriptSource;
import kotlin.script.experimental.host.GetScriptingClass;
import kotlin.script.experimental.host.HostConfigurationKt;
import kotlin.script.experimental.host.ScriptHostUtilKt;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.impl.RunSuspendKt;
import kotlin.script.experimental.jvm.GetScriptingClassByClassLoader;
import kotlin.script.experimental.jvm.JvmScriptCompilationKt;
import kotlin.script.experimental.jvm.JvmScriptingHostConfigurationKt;
import kotlin.script.experimental.jvm.compat.DiagnosticsUtilKt;
import kotlin.script.experimental.jvm.impl.BridgeDependenciesResolverKt;
import kotlin.script.experimental.util.PropertiesCollection;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.LocalFileSystem;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.scripting.UtilsKt;
import org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinition;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: refineCompilationConfiguration.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000À\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\u001a\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00022\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001a(\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016\u001a*\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u001d\u001a\u0004\u0018\u00010\u0016H\u0000\u001a4\u0010\u001e\u001a\f\u0012\u0004\u0012\u00020 0\u001fj\u0002`!2\u0006\u0010\"\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00142\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\u0012\u001aL\u0010\u001e\u001a\f\u0012\u0004\u0012\u00020 0\u001fj\u0002`!2\u0006\u0010\"\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00142\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\u00122\u0016\b\u0002\u0010$\u001a\u0010\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020'\u0018\u00010%\u001a\u0012\u0010(\u001a\u00020\u0012*\u00020\u00122\u0006\u0010\u000b\u001a\u00020\f\u001a\u0012\u0010(\u001a\u00020)*\u00020)2\u0006\u0010\u000b\u001a\u00020\f\u001aL\u0010*\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020-0+0\u0002*\b\u0012\u0004\u0012\u00020-0.2\b\u0010\u001d\u001a\u0004\u0018\u00010\u00162\u0014\u0010/\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020,000\u00022\u0006\u0010\u0013\u001a\u00020\u0014H\u0002\u001aV\u0010*\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u0003010\u0002*\b\u0012\u0004\u0012\u00020-0.2\b\u0010\u001d\u001a\u0004\u0018\u00010\u00162\u0014\u0010/\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020,000\u00022\u0006\u0010\u0013\u001a\u00020\u00142\b\u00102\u001a\u0004\u0018\u0001032\u0006\u00104\u001a\u00020&H\u0002\u001a\u001a\u00105\u001a\b\u0012\u0004\u0012\u00020-0.*\u00020\u001a2\u0006\u0010\u0013\u001a\u00020\u0014H\u0000\u001a\u001a\u00106\u001a\u00020\u0010*\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0014\u001a\u0012\u00107\u001a\u00020\u001a*\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f\u001a:\u00108\u001a\b\u0012\u0004\u0012\u00020,0\u0002*\u00020\u001a2\u0014\u0010/\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020,000\u00022\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u001d\u001a\u0004\u0018\u00010\u0016H\u0000\u001a\u0014\u00109\u001a\u00020:*\u00020;2\u0006\u00102\u001a\u000203H\u0002\u001a\u0014\u0010<\u001a\u00020=*\u0002032\u0006\u0010>\u001a\u00020?H\u0002\u001a&\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00120\u001f*\u00020\u00122\u0014\u0010A\u001a\u0010\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020'\u0018\u00010%\u001a\u001a\u0010B\u001a\u00020C*\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0014\"+\u0010\u0000\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001*\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006*\u0016\u0010D\"\b\u0012\u0004\u0012\u00020 0\u001f2\b\u0012\u0004\u0012\u00020 0\u001f¨\u0006E²\u0006\n\u0010F\u001a\u00020GX\u008a\u0084\u0002"}, d2 = {"resolvedImportScripts", "Lkotlin/script/experimental/util/PropertiesCollection$Key;", "", "Lkotlin/script/experimental/api/SourceCode;", "Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;", "getResolvedImportScripts", "(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;", "resolvedImportScripts$delegate", "Lkotlin/script/experimental/util/PropertiesCollection$PropertyKeyDelegate;", "additionalClasspath", "Ljava/io/File;", "definition", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition;", "getScriptCollectedData", "Lkotlin/script/experimental/api/ScriptCollectedData;", "scriptFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "compilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "contextClassLoader", "Ljava/lang/ClassLoader;", "makeScriptContents", "Lorg/jetbrains/kotlin/scripting/resolve/ScriptContentLoader$BasicScriptContents;", "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "legacyDefinition", "Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;", "classLoader", "refineScriptCompilationConfiguration", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lorg/jetbrains/kotlin/scripting/resolve/ScriptCompilationConfigurationWrapper;", "Lorg/jetbrains/kotlin/scripting/resolve/ScriptCompilationConfigurationResult;", "script", "providedConfiguration", "knownVirtualFileSources", "", "", "Lorg/jetbrains/kotlin/scripting/resolve/VirtualFileScriptSource;", "adjustByDefinition", "Lkotlin/script/experimental/dependencies/ScriptDependencies;", "construct", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "", "acceptedAnnotations", "Lkotlin/reflect/KClass;", "Lkotlin/script/experimental/api/ScriptSourceAnnotation;", "document", "Lorg/jetbrains/kotlin/com/intellij/openapi/editor/Document;", "filePath", "getAnnotationEntries", "getKtFile", "getVirtualFile", "loadAnnotations", FirebaseAnalytics.Param.LOCATION, "Lkotlin/script/experimental/api/SourceCode$Location;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "offsetToPosition", "Lkotlin/script/experimental/api/SourceCode$Position;", TypedValues.CycleType.S_WAVE_OFFSET, "", "resolveImportsToVirtualFiles", "knownFileBasedSources", "toKtFileSource", "Lorg/jetbrains/kotlin/scripting/resolve/KtFileScriptSource;", "ScriptCompilationConfigurationResult", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME, "localFS", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileSystem;"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RefineCompilationConfigurationKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(RefineCompilationConfigurationKt.class, PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME), "resolvedImportScripts", "getResolvedImportScripts(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;"))};
    private static final PropertiesCollection.PropertyKeyDelegate resolvedImportScripts$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, true, 1, (Object) null);

    public static final List<Annotation> loadAnnotations(final VirtualFile virtualFile, final List<? extends KClass<? extends Annotation>> acceptedAnnotations, final Project project, final ClassLoader classLoader) {
        Intrinsics.checkNotNullParameter(virtualFile, "<this>");
        Intrinsics.checkNotNullParameter(acceptedAnnotations, "acceptedAnnotations");
        Intrinsics.checkNotNullParameter(project, "project");
        Object objRunReadAction = ApplicationManager.getApplication().runReadAction((Computable<Object>) new Computable() { // from class: org.jetbrains.kotlin.scripting.resolve.RefineCompilationConfigurationKt$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return RefineCompilationConfigurationKt.loadAnnotations$lambda$1(virtualFile, project, classLoader, acceptedAnnotations);
            }
        });
        Intrinsics.checkNotNullExpressionValue(objRunReadAction, "runReadAction(...)");
        return (List) objRunReadAction;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List loadAnnotations$lambda$1(VirtualFile this_loadAnnotations, Project project, ClassLoader classLoader, List acceptedAnnotations) throws ClassNotFoundException {
        Intrinsics.checkNotNullParameter(this_loadAnnotations, "$this_loadAnnotations");
        Intrinsics.checkNotNullParameter(project, "$project");
        Intrinsics.checkNotNullParameter(acceptedAnnotations, "$acceptedAnnotations");
        List<Pair<Annotation, KtAnnotationEntry>> listConstruct = construct(getAnnotationEntries(this_loadAnnotations, project), classLoader, acceptedAnnotations, project);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listConstruct, 10));
        Iterator<T> it = listConstruct.iterator();
        while (it.hasNext()) {
            arrayList.add((Annotation) ((Pair) it.next()).getFirst());
        }
        return arrayList;
    }

    public static final Iterable<KtAnnotationEntry> getAnnotationEntries(VirtualFile virtualFile, Project project) {
        List<KtAnnotationEntry> annotationEntries;
        Intrinsics.checkNotNullParameter(virtualFile, "<this>");
        Intrinsics.checkNotNullParameter(project, "project");
        PsiFile psiFileFindFile = PsiManager.getInstance(project).findFile(virtualFile);
        if (psiFileFindFile == null) {
            throw new IllegalArgumentException("Unable to load PSI from " + virtualFile.getCanonicalPath());
        }
        KtFile ktFile = psiFileFindFile instanceof KtFile ? (KtFile) psiFileFindFile : null;
        if (ktFile == null || (annotationEntries = ktFile.getAnnotationEntries()) == null) {
            throw new IllegalArgumentException("Unable to extract kotlin annotations from " + virtualFile.getName() + " (" + virtualFile.getFileType().getName() + ')');
        }
        return annotationEntries;
    }

    public static final PropertiesCollection.Key<List<SourceCode>> getResolvedImportScripts(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return resolvedImportScripts$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[0]);
    }

    public static /* synthetic */ ResultWithDiagnostics refineScriptCompilationConfiguration$default(SourceCode sourceCode, ScriptDefinition scriptDefinition, Project project, ScriptCompilationConfiguration scriptCompilationConfiguration, int i, Object obj) {
        if ((i & 8) != 0) {
            scriptCompilationConfiguration = null;
        }
        return refineScriptCompilationConfiguration(sourceCode, scriptDefinition, project, scriptCompilationConfiguration);
    }

    public static final ResultWithDiagnostics<ScriptCompilationConfigurationWrapper> refineScriptCompilationConfiguration(SourceCode script, ScriptDefinition definition, Project project, ScriptCompilationConfiguration scriptCompilationConfiguration) {
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(definition, "definition");
        Intrinsics.checkNotNullParameter(project, "project");
        return refineScriptCompilationConfiguration(script, definition, project, scriptCompilationConfiguration, null);
    }

    public static /* synthetic */ ResultWithDiagnostics refineScriptCompilationConfiguration$default(SourceCode sourceCode, ScriptDefinition scriptDefinition, Project project, ScriptCompilationConfiguration scriptCompilationConfiguration, Map map, int i, Object obj) {
        if ((i & 8) != 0) {
            scriptCompilationConfiguration = null;
        }
        if ((i & 16) != 0) {
            map = null;
        }
        return refineScriptCompilationConfiguration(sourceCode, scriptDefinition, project, scriptCompilationConfiguration, map);
    }

    /* JADX WARN: Code duplicated, block: B:6:0x0020  */
    public static final ResultWithDiagnostics<ScriptCompilationConfigurationWrapper> refineScriptCompilationConfiguration(SourceCode script, final ScriptDefinition definition, final Project project, ScriptCompilationConfiguration scriptCompilationConfiguration, Map<String, VirtualFileScriptSource> map) {
        KotlinScriptDefinition legacyDefinition;
        KotlinScriptDefinitionFromAnnotatedTemplate kotlinScriptDefinitionFromAnnotatedTemplate;
        DependenciesResolver.ResolveResult resolveResultResolve;
        KotlinScriptDefinitionFromAnnotatedTemplate kotlinScriptDefinitionFromAnnotatedTemplate2;
        ResultWithDiagnostics<ScriptCompilationConfiguration> resultWithDiagnosticsPlus;
        ResultWithDiagnostics resultWithDiagnosticsPlus2;
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(definition, "definition");
        Intrinsics.checkNotNullParameter(project, "project");
        final KtFileScriptSource ktFileSource = toKtFileSource(script, definition, project);
        if (definition instanceof ScriptDefinition.FromLegacy) {
            legacyDefinition = definition.getLegacyDefinition();
            if (!(legacyDefinition instanceof KotlinScriptDefinition)) {
                legacyDefinition = null;
            }
        } else {
            legacyDefinition = null;
        }
        if (legacyDefinition == null) {
            final ScriptCompilationConfiguration compilationConfiguration = scriptCompilationConfiguration == null ? definition.getCompilationConfiguration() : scriptCompilationConfiguration;
            ScriptCollectedData scriptCollectedData = (ScriptCollectedData) ApplicationManager.getApplication().runReadAction(new Computable() { // from class: org.jetbrains.kotlin.scripting.resolve.RefineCompilationConfigurationKt$refineScriptCompilationConfiguration$$inlined$runReadAction$1
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
                public final T compute() {
                    return (T) RefineCompilationConfigurationKt.getScriptCollectedData(ktFileSource.getKtFile(), compilationConfiguration, project, definition.getContextClassLoader());
                }
            });
            ResultWithDiagnostics<ScriptCompilationConfiguration> resultWithDiagnosticsRefineOnAnnotations = ScriptCompilationKt.refineOnAnnotations(compilationConfiguration, script, scriptCollectedData);
            if (resultWithDiagnosticsRefineOnAnnotations instanceof ResultWithDiagnostics.Success) {
                resultWithDiagnosticsPlus = ErrorHandlingKt.plus(resultWithDiagnosticsRefineOnAnnotations.getReports(), ScriptCompilationKt.refineBeforeCompiling((ScriptCompilationConfiguration) ((ResultWithDiagnostics.Success) resultWithDiagnosticsRefineOnAnnotations).getValue(), script, scriptCollectedData));
            } else if (!(resultWithDiagnosticsRefineOnAnnotations instanceof ResultWithDiagnostics.Failure)) {
                resultWithDiagnosticsPlus = resultWithDiagnosticsRefineOnAnnotations;
                throw new NoWhenBranchMatchedException();
            }
            resultWithDiagnosticsPlus = resultWithDiagnosticsRefineOnAnnotations;
            if (resultWithDiagnosticsPlus instanceof ResultWithDiagnostics.Success) {
                resultWithDiagnosticsPlus2 = ErrorHandlingKt.plus(resultWithDiagnosticsPlus.getReports(), resolveImportsToVirtualFiles((ScriptCompilationConfiguration) ((ResultWithDiagnostics.Success) resultWithDiagnosticsPlus).getValue(), map));
            } else if (!(resultWithDiagnosticsPlus instanceof ResultWithDiagnostics.Failure)) {
                resultWithDiagnosticsPlus2 = resultWithDiagnosticsPlus;
                throw new NoWhenBranchMatchedException();
            }
            resultWithDiagnosticsPlus2 = resultWithDiagnosticsPlus;
            if (resultWithDiagnosticsPlus2 instanceof ResultWithDiagnostics.Success) {
                return ErrorHandlingKt.plus(resultWithDiagnosticsPlus2.getReports(), ErrorHandlingKt.asSuccess$default(new ScriptCompilationConfigurationWrapper.FromCompilationConfiguration(ktFileSource, adjustByDefinition((ScriptCompilationConfiguration) ((ResultWithDiagnostics.Success) resultWithDiagnosticsPlus2).getValue(), definition)), null, 1, null));
            }
            if (resultWithDiagnosticsPlus2 instanceof ResultWithDiagnostics.Failure) {
                return resultWithDiagnosticsPlus2;
            }
            throw new NoWhenBranchMatchedException();
        }
        ScriptContentLoader.BasicScriptContents basicScriptContentsMakeScriptContents = makeScriptContents(getVirtualFile(script, definition), legacyDefinition, project, definition.getContextClassLoader());
        if (legacyDefinition instanceof KotlinScriptDefinitionFromAnnotatedTemplate) {
            kotlinScriptDefinitionFromAnnotatedTemplate2 = (KotlinScriptDefinitionFromAnnotatedTemplate) legacyDefinition;
        } else {
            kotlinScriptDefinitionFromAnnotatedTemplate = null;
        }
        Map<String, Object> environment = kotlinScriptDefinitionFromAnnotatedTemplate != null ? kotlinScriptDefinitionFromAnnotatedTemplate.getEnvironment() : null;
        if (environment == null) {
            kotlinScriptDefinitionFromAnnotatedTemplate = kotlinScriptDefinitionFromAnnotatedTemplate2;
            kotlinScriptDefinitionFromAnnotatedTemplate = kotlinScriptDefinitionFromAnnotatedTemplate2;
            environment = MapsKt.emptyMap();
        }
        try {
            kotlinScriptDefinitionFromAnnotatedTemplate = kotlinScriptDefinitionFromAnnotatedTemplate2;
            kotlinScriptDefinitionFromAnnotatedTemplate = kotlinScriptDefinitionFromAnnotatedTemplate2;
            DependenciesResolver dependencyResolver = legacyDefinition.getDependencyResolver();
            if (dependencyResolver instanceof AsyncDependenciesResolver) {
                resolveResultResolve = (DependenciesResolver.ResolveResult) RunSuspendKt.internalScriptingRunSuspend(new RefineCompilationConfigurationKt$refineScriptCompilationConfiguration$result$1(dependencyResolver, basicScriptContentsMakeScriptContents, environment, null));
            } else {
                resolveResultResolve = dependencyResolver.resolve(basicScriptContentsMakeScriptContents, environment);
            }
            if (resolveResultResolve instanceof DependenciesResolver.ResolveResult.Failure) {
                return ErrorHandlingKt.makeFailureResult(DiagnosticsUtilKt.mapToDiagnostics(resolveResultResolve.getReports()));
            }
            KtFileScriptSource ktFileScriptSource = ktFileSource;
            ScriptDependencies dependencies = resolveResultResolve.getDependencies();
            return ErrorHandlingKt.asSuccess(new ScriptCompilationConfigurationWrapper.FromLegacy(ktFileScriptSource, dependencies != null ? adjustByDefinition(dependencies, definition) : null, definition), DiagnosticsUtilKt.mapToDiagnostics(resolveResultResolve.getReports()));
        } catch (Throwable th) {
            return ErrorHandlingKt.makeFailureResult(ErrorHandlingKt.asDiagnostics$default(th, 0, null, null, null, ScriptDiagnostic.Severity.FATAL, 15, null));
        }
    }

    public static final ScriptDependencies adjustByDefinition(ScriptDependencies scriptDependencies, ScriptDefinition definition) {
        Intrinsics.checkNotNullParameter(scriptDependencies, "<this>");
        Intrinsics.checkNotNullParameter(definition, "definition");
        List<File> listAdditionalClasspath = additionalClasspath(definition);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listAdditionalClasspath) {
            if (!scriptDependencies.getClasspath().contains((File) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        return arrayList2.isEmpty() ? scriptDependencies : ScriptDependencies.copy$default(scriptDependencies, null, CollectionsKt.plus((Collection) scriptDependencies.getClasspath(), (Iterable) arrayList2), null, null, null, 29, null);
    }

    public static final ScriptCompilationConfiguration adjustByDefinition(ScriptCompilationConfiguration scriptCompilationConfiguration, ScriptDefinition definition) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(definition, "definition");
        return JvmScriptCompilationKt.withUpdatedClasspath(scriptCompilationConfiguration, additionalClasspath(definition));
    }

    public static final ResultWithDiagnostics<ScriptCompilationConfiguration> resolveImportsToVirtualFiles(ScriptCompilationConfiguration scriptCompilationConfiguration, Map<String, VirtualFileScriptSource> map) {
        final ArrayList arrayList;
        VirtualFileScriptSource virtualFileScriptSource;
        VirtualFileScriptSource virtualFileScriptSource2;
        Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "<this>");
        Lazy lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<VirtualFileSystem>() { // from class: org.jetbrains.kotlin.scripting.resolve.RefineCompilationConfigurationKt$resolveImportsToVirtualFiles$localFS$2
            @Override // kotlin.jvm.functions.Function0
            public final VirtualFileSystem invoke() {
                VirtualFileManager virtualFileManager = VirtualFileManager.getInstance();
                Intrinsics.checkNotNullExpressionValue(virtualFileManager, "getInstance(...)");
                return virtualFileManager.getFileSystem("file");
            }
        });
        List list = (List) scriptCompilationConfiguration.get(ScriptCompilationKt.getImportScripts(ScriptCompilationConfiguration.INSTANCE));
        if (list != null) {
            List<SourceCode> list2 = list;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            for (SourceCode sourceCode : list2) {
                if (sourceCode instanceof VirtualFileScriptSource) {
                    virtualFileScriptSource = (VirtualFileScriptSource) sourceCode;
                } else if (sourceCode instanceof FileBasedScriptSource) {
                    FileBasedScriptSource fileBasedScriptSource = (FileBasedScriptSource) sourceCode;
                    String absolutePath = FilesKt.normalize(fileBasedScriptSource.getFile()).getAbsolutePath();
                    if (map == null || (virtualFileScriptSource2 = map.get(absolutePath)) == null) {
                        VirtualFile virtualFileFindFileByPath = resolveImportsToVirtualFiles$lambda$7(lazy).findFileByPath(absolutePath);
                        if (virtualFileFindFileByPath == null) {
                            return ErrorHandlingKt.makeFailureResult(ErrorHandlingKt.asErrorDiagnostics$default("Imported source file not found: " + fileBasedScriptSource.getFile(), 0, null, null, 7, null));
                        }
                        Intrinsics.checkNotNull(virtualFileFindFileByPath);
                        virtualFileScriptSource = new VirtualFileScriptSource(virtualFileFindFileByPath, null, 2, null);
                        if (map != null) {
                            Intrinsics.checkNotNull(absolutePath);
                            map.put(absolutePath, virtualFileScriptSource);
                        }
                    } else {
                        virtualFileScriptSource = virtualFileScriptSource2;
                    }
                } else {
                    virtualFileScriptSource = new VirtualFileScriptSource(new ScriptLightVirtualFile(UtilsKt.scriptFileName(sourceCode, sourceCode, scriptCompilationConfiguration), sourceCode.getLocationId(), sourceCode.getText()), null, 2, null);
                }
                arrayList2.add(virtualFileScriptSource);
            }
            arrayList = arrayList2;
        } else {
            arrayList = null;
        }
        ArrayList arrayList3 = arrayList;
        if (arrayList3 != null && !arrayList3.isEmpty()) {
            scriptCompilationConfiguration = ScriptCompilationKt.with(scriptCompilationConfiguration, new Function1<ScriptCompilationConfiguration.Builder, Unit>() { // from class: org.jetbrains.kotlin.scripting.resolve.RefineCompilationConfigurationKt$resolveImportsToVirtualFiles$updatedConfiguration$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ScriptCompilationConfiguration.Builder builder) {
                    invoke2(builder);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ScriptCompilationConfiguration.Builder with) {
                    Intrinsics.checkNotNullParameter(with, "$this$with");
                    with.invoke((PropertiesCollection.Key<List<VirtualFileScriptSource>>) RefineCompilationConfigurationKt.getResolvedImportScripts(with), arrayList);
                }
            });
        }
        return ErrorHandlingKt.asSuccess$default(scriptCompilationConfiguration, null, 1, null);
    }

    private static final VirtualFileSystem resolveImportsToVirtualFiles$lambda$7(Lazy<? extends VirtualFileSystem> lazy) {
        VirtualFileSystem value = lazy.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return value;
    }

    public static final ScriptContentLoader.BasicScriptContents makeScriptContents(final VirtualFile file, final KotlinScriptDefinition legacyDefinition, final Project project, final ClassLoader classLoader) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(legacyDefinition, "legacyDefinition");
        Intrinsics.checkNotNullParameter(project, "project");
        return new ScriptContentLoader.BasicScriptContents(file, new Function0<Iterable<? extends Annotation>>() { // from class: org.jetbrains.kotlin.scripting.resolve.RefineCompilationConfigurationKt.makeScriptContents.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Iterable<? extends Annotation> invoke() {
                return RefineCompilationConfigurationKt.loadAnnotations(file, legacyDefinition.getAcceptedAnnotations(), project, classLoader);
            }
        });
    }

    public static final VirtualFile getVirtualFile(SourceCode sourceCode, ScriptDefinition definition) {
        String string;
        String mergedScriptText;
        VirtualFile virtualFileFindFileByIoFile;
        Intrinsics.checkNotNullParameter(sourceCode, "<this>");
        Intrinsics.checkNotNullParameter(definition, "definition");
        if (sourceCode instanceof VirtualFileScriptSource) {
            return ((VirtualFileScriptSource) sourceCode).getVirtualFile();
        }
        if (sourceCode instanceof KtFileScriptSource) {
            return ((KtFileScriptSource) sourceCode).getVirtualFile();
        }
        boolean z = sourceCode instanceof FileScriptSource;
        if (z && (virtualFileFindFileByIoFile = LocalFileSystem.getInstance().findFileByIoFile(((FileScriptSource) sourceCode).getFile())) != null) {
            return virtualFileFindFileByIoFile;
        }
        String name2 = sourceCode.getName();
        if (name2 == null) {
            name2 = definition.getDefaultClassName();
        }
        String strWithCorrectExtension = UtilsKt.withCorrectExtension(name2, definition.getFileExtension());
        KotlinScriptDefinition kotlinScriptDefinition = null;
        if (z) {
            string = ((FileScriptSource) sourceCode).getFile().getPath();
        } else {
            string = sourceCode instanceof ExternalSourceCode ? ((ExternalSourceCode) sourceCode).getExternalLocation().toString() : null;
        }
        if (definition instanceof ScriptDefinition.FromLegacy) {
            KotlinScriptDefinition legacyDefinition = definition.getLegacyDefinition();
            if (legacyDefinition instanceof KotlinScriptDefinition) {
                kotlinScriptDefinition = legacyDefinition;
            }
        }
        if (kotlinScriptDefinition == null || (mergedScriptText = sourceCode.getText()) == null) {
            mergedScriptText = ScriptHostUtilKt.getMergedScriptText(sourceCode, definition.getCompilationConfiguration());
        }
        return new ScriptLightVirtualFile(strWithCorrectExtension, string, mergedScriptText);
    }

    public static final KtFile getKtFile(SourceCode sourceCode, ScriptDefinition definition, final Project project) {
        Intrinsics.checkNotNullParameter(sourceCode, "<this>");
        Intrinsics.checkNotNullParameter(definition, "definition");
        Intrinsics.checkNotNullParameter(project, "project");
        if (sourceCode instanceof KtFileScriptSource) {
            return ((KtFileScriptSource) sourceCode).getKtFile();
        }
        final VirtualFile virtualFile = getVirtualFile(sourceCode, definition);
        Object objRunReadAction = ApplicationManager.getApplication().runReadAction((Computable<Object>) new Computable() { // from class: org.jetbrains.kotlin.scripting.resolve.RefineCompilationConfigurationKt$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return RefineCompilationConfigurationKt.getKtFile$lambda$12(project, virtualFile);
            }
        });
        Intrinsics.checkNotNull(objRunReadAction);
        return (KtFile) objRunReadAction;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final KtFile getKtFile$lambda$12(Project project, VirtualFile file) {
        Intrinsics.checkNotNullParameter(project, "$project");
        Intrinsics.checkNotNullParameter(file, "$file");
        PsiFile psiFileFindFile = PsiManager.getInstance(project).findFile(file);
        if (psiFileFindFile == null) {
            throw new IllegalArgumentException("Unable to load PSI from " + file.getPath());
        }
        KtFile ktFile = psiFileFindFile instanceof KtFile ? (KtFile) psiFileFindFile : null;
        if (ktFile != null) {
            return ktFile;
        }
        throw new IllegalArgumentException("Not a kotlin file " + file.getPath() + " (" + file.getFileType().getName() + ')');
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final KtFileScriptSource toKtFileSource(SourceCode sourceCode, ScriptDefinition definition, Project project) {
        Intrinsics.checkNotNullParameter(sourceCode, "<this>");
        Intrinsics.checkNotNullParameter(definition, "definition");
        Intrinsics.checkNotNullParameter(project, "project");
        if (sourceCode instanceof KtFileScriptSource) {
            return (KtFileScriptSource) sourceCode;
        }
        return new KtFileScriptSource(getKtFile(sourceCode, definition, project), null, 2, 0 == true ? 1 : 0);
    }

    public static final ScriptCollectedData getScriptCollectedData(KtFile scriptFile, ScriptCompilationConfiguration compilationConfiguration, Project project, ClassLoader classLoader) throws ClassNotFoundException {
        Intrinsics.checkNotNullParameter(scriptFile, "scriptFile");
        Intrinsics.checkNotNullParameter(compilationConfiguration, "compilationConfiguration");
        Intrinsics.checkNotNullParameter(project, "project");
        ScriptingHostConfiguration defaultJvmScriptingHostConfiguration = (ScriptingHostConfiguration) compilationConfiguration.get(ScriptCompilationKt.getHostConfiguration(ScriptCompilationConfiguration.INSTANCE));
        if (defaultJvmScriptingHostConfiguration == null) {
            defaultJvmScriptingHostConfiguration = JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration();
        }
        GetScriptingClass getScriptingClass = (GetScriptingClass) defaultJvmScriptingHostConfiguration.get(HostConfigurationKt.getGetScriptingClass(ScriptingHostConfiguration.INSTANCE));
        ArrayList arrayListEmptyList = null;
        GetScriptingClassByClassLoader getScriptingClassByClassLoader = getScriptingClass instanceof GetScriptingClassByClassLoader ? (GetScriptingClassByClassLoader) getScriptingClass : null;
        if (getScriptingClassByClassLoader == null) {
            throw new IllegalArgumentException("Expecting class implementing GetScriptingClassByClassLoader in the hostConfiguration[getScriptingClass], got " + getScriptingClass);
        }
        List list = (List) compilationConfiguration.get(ScriptCompilationKt.getRefineConfigurationOnAnnotations(ScriptCompilationConfiguration.INSTANCE));
        if (list != null) {
            ArrayList arrayList = new ArrayList();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                List<KotlinType> annotations = ((RefineConfigurationOnAnnotationsData) it.next()).getAnnotations();
                ArrayList arrayList2 = new ArrayList();
                Iterator<T> it2 = annotations.iterator();
                while (it2.hasNext()) {
                    KClass<?> kClassInvoke = getScriptingClassByClassLoader.invoke((KotlinType) it2.next(), classLoader, defaultJvmScriptingHostConfiguration);
                    if (!(kClassInvoke instanceof KClass)) {
                        kClassInvoke = null;
                    }
                    if (kClassInvoke != null) {
                        arrayList2.add(kClassInvoke);
                    }
                }
                CollectionsKt.addAll(arrayList, arrayList2);
            }
            arrayListEmptyList = arrayList;
        }
        if (arrayListEmptyList == null) {
            arrayListEmptyList = CollectionsKt.emptyList();
        }
        List<ScriptSourceAnnotation<?>> listConstruct = construct(scriptFile.getAnnotationEntries(), classLoader, arrayListEmptyList, project, scriptFile.getViewProvider().getDocument(), scriptFile.getVirtualFilePath());
        Pair[] pairArr = new Pair[2];
        pairArr[0] = TuplesKt.to(ScriptDataKt.getCollectedAnnotations(ScriptCollectedData.INSTANCE), listConstruct);
        PropertiesCollection.Key<List<Annotation>> foundAnnotations = ScriptDataKt.getFoundAnnotations(ScriptCollectedData.INSTANCE);
        List<ScriptSourceAnnotation<?>> list2 = listConstruct;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it3 = list2.iterator();
        while (it3.hasNext()) {
            arrayList3.add(((ScriptSourceAnnotation) it3.next()).getAnnotation());
        }
        pairArr[1] = TuplesKt.to(foundAnnotations, arrayList3);
        return new ScriptCollectedData(MapsKt.mapOf(pairArr));
    }

    private static final List<ScriptSourceAnnotation<?>> construct(Iterable<? extends KtAnnotationEntry> iterable, ClassLoader classLoader, List<? extends KClass<? extends Annotation>> list, Project project, Document document, String str) throws ClassNotFoundException {
        List<Pair<Annotation, KtAnnotationEntry>> listConstruct = construct(iterable, classLoader, list, project);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listConstruct, 10));
        Iterator<T> it = listConstruct.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            arrayList.add(new ScriptSourceAnnotation((Annotation) pair.component1(), document != null ? new SourceCode.LocationWithId(str, location((KtAnnotationEntry) pair.component2(), document)) : null));
        }
        return arrayList;
    }

    private static final SourceCode.Location location(PsiElement psiElement, Document document) {
        return new SourceCode.Location(offsetToPosition(document, PsiUtilsKt.getStartOffset(psiElement)), PsiUtilsKt.getEndOffset(psiElement) > PsiUtilsKt.getStartOffset(psiElement) ? offsetToPosition(document, PsiUtilsKt.getEndOffset(psiElement)) : null);
    }

    private static final SourceCode.Position offsetToPosition(Document document, int i) {
        int lineNumber = document.getLineNumber(i);
        return new SourceCode.Position(lineNumber + 1, (i - document.getLineStartOffset(lineNumber)) + 1, Integer.valueOf(i));
    }

    private static final List<File> additionalClasspath(ScriptDefinition scriptDefinition) {
        List<File> templateClasspath;
        KotlinScriptDefinitionFromAnnotatedTemplate kotlinScriptDefinitionFromAnnotatedTemplate = null;
        if (scriptDefinition instanceof ScriptDefinition.FromLegacy) {
            KotlinScriptDefinition legacyDefinition = scriptDefinition.getLegacyDefinition();
            kotlinScriptDefinitionFromAnnotatedTemplate = (KotlinScriptDefinitionFromAnnotatedTemplate) (legacyDefinition instanceof KotlinScriptDefinitionFromAnnotatedTemplate ? legacyDefinition : null);
        }
        KotlinScriptDefinitionFromAnnotatedTemplate kotlinScriptDefinitionFromAnnotatedTemplate2 = (KotlinScriptDefinitionFromAnnotatedTemplate) kotlinScriptDefinitionFromAnnotatedTemplate;
        return (kotlinScriptDefinitionFromAnnotatedTemplate2 == null || (templateClasspath = kotlinScriptDefinitionFromAnnotatedTemplate2.getTemplateClasspath()) == null) ? BridgeDependenciesResolverKt.toClassPathOrEmpty((List) scriptDefinition.getHostConfiguration().get(HostConfigurationKt.getConfigurationDependencies(ScriptingHostConfiguration.INSTANCE))) : templateClasspath;
    }

    private static final List<Pair<Annotation, KtAnnotationEntry>> construct(Iterable<? extends KtAnnotationEntry> iterable, ClassLoader classLoader, List<? extends KClass<? extends Annotation>> list, Project project) throws ClassNotFoundException {
        Pair pair;
        Object next;
        KClass kClass;
        String typeName;
        ArrayList arrayList = new ArrayList();
        for (KtAnnotationEntry ktAnnotationEntry : iterable) {
            Iterator<T> it = list.iterator();
            do {
                pair = null;
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                kClass = (KClass) next;
                typeName = ScriptAnnotationsPreprocessingKt.getTypeName(ktAnnotationEntry);
                if (Intrinsics.areEqual(typeName, kClass.getSimpleName())) {
                    break;
                }
            } while (!Intrinsics.areEqual(typeName, kClass.getQualifiedName()));
            KClass kClass2 = (KClass) next;
            if (kClass2 != null) {
                Class<?> clsLoadClass = (classLoader == null ? ClassLoader.getSystemClassLoader() : classLoader).loadClass(kClass2.getQualifiedName());
                Intrinsics.checkNotNullExpressionValue(clsLoadClass, "loadClass(...)");
                KClass kotlinClass = JvmClassMappingKt.getKotlinClass(clsLoadClass);
                Intrinsics.checkNotNull(kotlinClass, "null cannot be cast to non-null type kotlin.reflect.KClass<out kotlin.Annotation>");
                pair = TuplesKt.to(ScriptAnnotationsPreprocessingKt.constructAnnotation(ktAnnotationEntry, kotlinClass, project), ktAnnotationEntry);
            }
            if (pair != null) {
                arrayList.add(pair);
            }
        }
        return arrayList;
    }
}
