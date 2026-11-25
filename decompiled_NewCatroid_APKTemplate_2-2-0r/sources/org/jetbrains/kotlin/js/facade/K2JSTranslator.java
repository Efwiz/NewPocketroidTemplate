package org.jetbrains.kotlin.js.facade;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtils;
import org.jetbrains.kotlin.incremental.js.IncrementalResultsConsumer;
import org.jetbrains.kotlin.js.analyze.TopDownAnalyzerFacadeForJS;
import org.jetbrains.kotlin.js.analyzer.JsAnalysisResult;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModule;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameBinding;
import org.jetbrains.kotlin.js.backend.ast.JsProgram;
import org.jetbrains.kotlin.js.backend.ast.JsProgramFragment;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.config.ErrorTolerancePolicy;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.config.JsConfig;
import org.jetbrains.kotlin.js.coroutine.CoroutineTransformerKt;
import org.jetbrains.kotlin.js.facade.exceptions.TranslationException;
import org.jetbrains.kotlin.js.inline.JsInliner;
import org.jetbrains.kotlin.js.inline.clean.LabeledBlockToDoWhileTransformationKt;
import org.jetbrains.kotlin.js.inline.clean.ResolveTemporaryNamesKt;
import org.jetbrains.kotlin.js.inline.util.CollectUtilsKt;
import org.jetbrains.kotlin.js.sourceMap.SourceFilePathResolver;
import org.jetbrains.kotlin.js.translate.general.AstGenerationResult;
import org.jetbrains.kotlin.js.translate.general.SourceFileTranslationResult;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.ExpandIsCallsKt;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.progress.ProgressIndicatorAndCompilationCanceledStatus;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.diagnostics.Diagnostics;
import org.jetbrains.kotlin.serialization.js.KotlinJavascriptSerializationUtil;
import org.jetbrains.kotlin.serialization.js.KotlinJavascriptSerializationUtilKt;
import org.jetbrains.kotlin.serialization.js.ast.JsAstProtoBuf;
import org.jetbrains.kotlin.serialization.js.ast.JsAstSerializer;
import org.jetbrains.kotlin.utils.JsMetadataVersion;

/* JADX INFO: compiled from: K2JSTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Â\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0005\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001BB\u0019\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\t\u001a\u00020\nH\u0002J\u0016\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0002J.\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019H\u0002JP\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0 2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'2\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\f0)H\u0002J2\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 2\u0006\u0010$\u001a\u00020%2\n\b\u0002\u0010&\u001a\u0004\u0018\u00010'H\u0007JH\u0010*\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020#0 2\u0006\u0010$\u001a\u00020%2\n\b\u0002\u0010&\u001a\u0004\u0018\u00010'2\u0014\b\u0002\u0010(\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\f0)H\u0007J2\u0010,\u001a\u00020\u001c2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 2\u0006\u0010&\u001a\u00020'2\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\f0)H\u0002JM\u0010-\u001a\u00020\n2\f\u0010.\u001a\b\u0012\u0004\u0012\u00020!0/2\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u0002032\u0006\u0010\u0014\u001a\u00020\u00152\u001d\u00104\u001a\u0019\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020705¢\u0006\u0002\b8H\u0002J,\u00109\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\f0)H\u0002J\u001e\u0010:\u001a\u00020\n2\u0006\u0010;\u001a\u00020<2\f\u0010=\u001a\b\u0012\u0004\u0012\u00020>0\u000eH\u0002J\u0014\u0010?\u001a\u000207*\u0002062\u0006\u0010@\u001a\u00020AH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006C"}, d2 = {"Lorg/jetbrains/kotlin/js/facade/K2JSTranslator;", "", "config", "Lorg/jetbrains/kotlin/js/config/JsConfig;", "shouldValidateJsAst", "", "(Lorg/jetbrains/kotlin/js/config/JsConfig;Z)V", "incrementalResults", "Lorg/jetbrains/kotlin/incremental/js/IncrementalResultsConsumer;", "checkCanceled", "", "serializeInlineData", "", "importedTags", "", "", "serializeScope", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "packageName", "Lorg/jetbrains/kotlin/name/FqName;", "scope", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "translate", "Lorg/jetbrains/kotlin/js/facade/TranslationResult;", "reporter", "Lorg/jetbrains/kotlin/js/config/JsConfig$Reporter;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "allUnits", "Lorg/jetbrains/kotlin/js/facade/TranslationUnit;", "mainCallParameters", "Lorg/jetbrains/kotlin/js/facade/MainCallParameters;", "analysisResult", "Lorg/jetbrains/kotlin/js/analyzer/JsAnalysisResult;", "packageMetadata", "", "translateUnits", "units", "translateWithoutCode", "trySaveIncrementalData", "sourceFiles", "", "pathResolver", "Lorg/jetbrains/kotlin/js/sourceMap/SourceFilePathResolver;", "bindingTrace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "translationData", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstSerializer;", "Lorg/jetbrains/kotlin/js/facade/K2JSTranslator$TranslationData;", "Lkotlin/ExtensionFunctionType;", "updateMetadataMap", "validateJsAst", "fragment", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "serializedNames", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "toTranslationData", "fileTranslationResult", "Lorg/jetbrains/kotlin/js/translate/general/SourceFileTranslationResult;", "TranslationData", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class K2JSTranslator {
    private final JsConfig config;
    private final IncrementalResultsConsumer incrementalResults;
    private final boolean shouldValidateJsAst;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public K2JSTranslator(JsConfig config) {
        this(config, false, 2, null);
        Intrinsics.checkNotNullParameter(config, "config");
    }

    public final TranslationResult translate(JsConfig.Reporter reporter, List<? extends KtFile> files, MainCallParameters mainCallParameters) throws TranslationException {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(mainCallParameters, "mainCallParameters");
        return translate$default(this, reporter, files, mainCallParameters, null, 8, null);
    }

    public final TranslationResult translateUnits(JsConfig.Reporter reporter, List<? extends TranslationUnit> units, MainCallParameters mainCallParameters) throws TranslationException {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(units, "units");
        Intrinsics.checkNotNullParameter(mainCallParameters, "mainCallParameters");
        return translateUnits$default(this, reporter, units, mainCallParameters, null, null, 24, null);
    }

    public final TranslationResult translateUnits(JsConfig.Reporter reporter, List<? extends TranslationUnit> units, MainCallParameters mainCallParameters, JsAnalysisResult jsAnalysisResult) throws TranslationException {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(units, "units");
        Intrinsics.checkNotNullParameter(mainCallParameters, "mainCallParameters");
        return translateUnits$default(this, reporter, units, mainCallParameters, jsAnalysisResult, null, 16, null);
    }

    public K2JSTranslator(JsConfig config, boolean z) {
        Intrinsics.checkNotNullParameter(config, "config");
        this.config = config;
        this.shouldValidateJsAst = z;
        this.incrementalResults = (IncrementalResultsConsumer) config.getConfiguration().get(JSConfigurationKeys.INCREMENTAL_RESULTS_CONSUMER);
    }

    public /* synthetic */ K2JSTranslator(JsConfig jsConfig, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(jsConfig, (i & 2) != 0 ? false : z);
    }

    public static /* synthetic */ TranslationResult translate$default(K2JSTranslator k2JSTranslator, JsConfig.Reporter reporter, List list, MainCallParameters mainCallParameters, JsAnalysisResult jsAnalysisResult, int i, Object obj) throws TranslationException {
        if ((i & 8) != 0) {
            jsAnalysisResult = null;
        }
        return k2JSTranslator.translate(reporter, list, mainCallParameters, jsAnalysisResult);
    }

    public final TranslationResult translate(JsConfig.Reporter reporter, List<? extends KtFile> files, MainCallParameters mainCallParameters, JsAnalysisResult analysisResult) throws TranslationException {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(mainCallParameters, "mainCallParameters");
        ArrayList arrayList = new ArrayList();
        Iterator<? extends KtFile> it = files.iterator();
        while (it.hasNext()) {
            arrayList.add(new TranslationUnit.SourceFile(it.next()));
        }
        return translateUnits$default(this, reporter, arrayList, mainCallParameters, analysisResult, null, 16, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ TranslationResult translateUnits$default(K2JSTranslator k2JSTranslator, JsConfig.Reporter reporter, List list, MainCallParameters mainCallParameters, JsAnalysisResult jsAnalysisResult, Map map, int i, Object obj) throws TranslationException {
        if ((i & 8) != 0) {
            jsAnalysisResult = null;
        }
        JsAnalysisResult jsAnalysisResult2 = jsAnalysisResult;
        if ((i & 16) != 0) {
            map = new LinkedHashMap();
        }
        return k2JSTranslator.translateUnits(reporter, list, mainCallParameters, jsAnalysisResult2, map);
    }

    public final TranslationResult translateUnits(JsConfig.Reporter reporter, List<? extends TranslationUnit> units, MainCallParameters mainCallParameters, JsAnalysisResult analysisResult, Map<FqName, byte[]> packageMetadata) throws TranslationException {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(units, "units");
        Intrinsics.checkNotNullParameter(mainCallParameters, "mainCallParameters");
        Intrinsics.checkNotNullParameter(packageMetadata, "packageMetadata");
        ArrayList arrayList = new ArrayList();
        for (TranslationUnit translationUnit : units) {
            if (translationUnit instanceof TranslationUnit.SourceFile) {
                arrayList.add(((TranslationUnit.SourceFile) translationUnit).getFile());
            }
        }
        if (analysisResult == null) {
            analysisResult = TopDownAnalyzerFacadeForJS.analyzeFiles(arrayList, this.config);
        }
        JsAnalysisResult jsAnalysisResult = analysisResult;
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        if (this.config.getConfiguration().getBoolean(JSConfigurationKeys.METADATA_ONLY)) {
            return translateWithoutCode(arrayList, jsAnalysisResult, packageMetadata);
        }
        return translate(reporter, arrayList, units, mainCallParameters, jsAnalysisResult, packageMetadata);
    }

    private final TranslationResult translateWithoutCode(List<? extends KtFile> files, JsAnalysisResult analysisResult, Map<FqName, byte[]> packageMetadata) {
        BindingTrace bindingTrace = analysisResult.getBindingTrace();
        BindingContext context = bindingTrace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        TopDownAnalyzerFacadeForJS.INSTANCE.checkForErrors(files, context, ErrorTolerancePolicy.NONE);
        ModuleDescriptor moduleDescriptor = analysisResult.getModuleDescriptor();
        Diagnostics diagnostics = bindingTrace.getContext().getDiagnostics();
        Intrinsics.checkNotNullExpressionValue(diagnostics, "getDiagnostics(...)");
        SourceFilePathResolver sourceFilePathResolverCreate = SourceFilePathResolver.INSTANCE.create(this.config);
        final BindingContext context2 = bindingTrace.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
        checkCanceled();
        BindingContext context3 = bindingTrace.getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "getBindingContext(...)");
        updateMetadataMap(context3, moduleDescriptor, packageMetadata);
        trySaveIncrementalData(files, sourceFilePathResolverCreate, bindingTrace, moduleDescriptor, new Function2<JsAstSerializer, KtFile, TranslationData>() { // from class: org.jetbrains.kotlin.js.facade.K2JSTranslator.translateWithoutCode.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final TranslationData invoke(JsAstSerializer trySaveIncrementalData, KtFile sourceFile) {
                Intrinsics.checkNotNullParameter(trySaveIncrementalData, "$this$trySaveIncrementalData");
                Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
                List<KtDeclaration> declarations = sourceFile.getDeclarations();
                BindingContext bindingContext = context2;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(declarations, 10));
                Iterator<T> it = declarations.iterator();
                while (it.hasNext()) {
                    DeclarationDescriptor descriptorForElement = BindingUtils.getDescriptorForElement(bindingContext, (KtDeclaration) it.next());
                    Intrinsics.checkNotNullExpressionValue(descriptorForElement, "getDescriptorForElement(...)");
                    arrayList.add(descriptorForElement);
                }
                return new TranslationData(arrayList, null, null, 6, null);
            }
        });
        if (DiagnosticUtils.hasError(diagnostics)) {
            return new TranslationResult.Fail(diagnostics);
        }
        JsConfig jsConfig = this.config;
        List listEmptyList = CollectionsKt.emptyList();
        BindingContext context4 = bindingTrace.getContext();
        Intrinsics.checkNotNullExpressionValue(context4, "getBindingContext(...)");
        return new TranslationResult.SuccessNoCode(jsConfig, files, diagnostics, listEmptyList, moduleDescriptor, context4, packageMetadata);
    }

    private final TranslationResult translate(JsConfig.Reporter reporter, List<? extends KtFile> files, List<? extends TranslationUnit> allUnits, MainCallParameters mainCallParameters, JsAnalysisResult analysisResult, Map<FqName, byte[]> packageMetadata) throws TranslationException {
        BindingTrace bindingTrace = analysisResult.getBindingTrace();
        BindingContext context = bindingTrace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        TopDownAnalyzerFacadeForJS.INSTANCE.checkForErrors(files, context, ErrorTolerancePolicy.NONE);
        ModuleDescriptor moduleDescriptor = analysisResult.getModuleDescriptor();
        Diagnostics diagnostics = bindingTrace.getContext().getDiagnostics();
        Intrinsics.checkNotNullExpressionValue(diagnostics, "getDiagnostics(...)");
        SourceFilePathResolver sourceFilePathResolverCreate = SourceFilePathResolver.INSTANCE.create(this.config);
        final AstGenerationResult astGenerationResultGenerateAst = Translation.generateAst(bindingTrace, allUnits, mainCallParameters, moduleDescriptor, this.config, sourceFilePathResolverCreate);
        Intrinsics.checkNotNullExpressionValue(astGenerationResultGenerateAst, "generateAst(...)");
        if (DiagnosticUtils.hasError(diagnostics)) {
            return new TranslationResult.Fail(diagnostics);
        }
        checkCanceled();
        JsConfig jsConfig = this.config;
        BindingTrace bindingTrace2 = analysisResult.getBindingTrace();
        BindingContext context2 = bindingTrace.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
        new JsInliner(reporter, jsConfig, bindingTrace2, context2, astGenerationResultGenerateAst).process();
        if (DiagnosticUtils.hasError(diagnostics)) {
            return new TranslationResult.Fail(diagnostics);
        }
        checkCanceled();
        LabeledBlockToDoWhileTransformationKt.transformLabeledBlockToDoWhile(astGenerationResultGenerateAst.getNewFragments());
        checkCanceled();
        CoroutineTransformerKt.transformCoroutines(astGenerationResultGenerateAst.getNewFragments());
        checkCanceled();
        ExpandIsCallsKt.expandIsCalls(astGenerationResultGenerateAst.getNewFragments());
        checkCanceled();
        BindingContext context3 = bindingTrace.getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "getBindingContext(...)");
        updateMetadataMap(context3, moduleDescriptor, packageMetadata);
        trySaveIncrementalData(astGenerationResultGenerateAst.getTranslatedSourceFiles().keySet(), sourceFilePathResolverCreate, bindingTrace, moduleDescriptor, new Function2<JsAstSerializer, KtFile, TranslationData>() { // from class: org.jetbrains.kotlin.js.facade.K2JSTranslator.translate.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final TranslationData invoke(JsAstSerializer trySaveIncrementalData, KtFile sourceFile) {
                Intrinsics.checkNotNullParameter(trySaveIncrementalData, "$this$trySaveIncrementalData");
                Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
                K2JSTranslator k2JSTranslator = K2JSTranslator.this;
                SourceFileTranslationResult sourceFileTranslationResult = astGenerationResultGenerateAst.getTranslatedSourceFiles().get(sourceFile);
                Intrinsics.checkNotNull(sourceFileTranslationResult);
                return k2JSTranslator.toTranslationData(trySaveIncrementalData, sourceFileTranslationResult);
            }
        });
        checkCanceled();
        Pair<JsProgram, List<String>> pairBuildProgram = astGenerationResultGenerateAst.buildProgram();
        JsProgram jsProgramComponent1 = pairBuildProgram.component1();
        List<String> listComponent2 = pairBuildProgram.component2();
        ResolveTemporaryNamesKt.resolveTemporaryNames(jsProgramComponent1);
        checkCanceled();
        if (DiagnosticUtils.hasError(diagnostics)) {
            return new TranslationResult.Fail(diagnostics);
        }
        JsConfig jsConfig2 = this.config;
        BindingContext context4 = bindingTrace.getContext();
        Intrinsics.checkNotNullExpressionValue(context4, "getBindingContext(...)");
        return new TranslationResult.Success(jsConfig2, files, jsProgramComponent1, diagnostics, listComponent2, moduleDescriptor, context4, packageMetadata);
    }

    private final void updateMetadataMap(BindingContext bindingContext, ModuleDescriptor moduleDescriptor, Map<FqName, byte[]> packageMetadata) {
        CompilerConfiguration configuration = this.config.getConfiguration();
        Intrinsics.checkNotNullExpressionValue(configuration, "getConfiguration(...)");
        LanguageVersionSettings languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(configuration);
        Object obj = this.config.getConfiguration().get(CommonConfigurationKeys.METADATA_VERSION);
        JsMetadataVersion jsMetadataVersion = obj instanceof JsMetadataVersion ? (JsMetadataVersion) obj : null;
        if (jsMetadataVersion == null) {
            jsMetadataVersion = JsMetadataVersion.INSTANCE;
        }
        Project project = this.config.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        Map<FqName, byte[]> mapMissingMetadata = KotlinJavascriptSerializationUtilKt.missingMetadata(packageMetadata, bindingContext, moduleDescriptor, languageVersionSettings, jsMetadataVersion, project);
        for (Map.Entry<FqName, byte[]> entry : mapMissingMetadata.entrySet()) {
            FqName key = entry.getKey();
            byte[] value = entry.getValue();
            IncrementalResultsConsumer incrementalResultsConsumer = this.incrementalResults;
            if (incrementalResultsConsumer != null) {
                String strAsString = key.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                incrementalResultsConsumer.processPackageMetadata(strAsString, value);
            }
        }
        packageMetadata.putAll(mapMissingMetadata);
    }

    private final void checkCanceled() {
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
    }

    private final ProtoBuf.PackageFragment serializeScope(BindingContext bindingContext, ModuleDescriptor moduleDescriptor, FqName packageName, Collection<? extends DeclarationDescriptor> scope) {
        BinaryVersion binaryVersion = (BinaryVersion) this.config.getConfiguration().get(CommonConfigurationKeys.METADATA_VERSION);
        if (!(binaryVersion == null ? true : binaryVersion instanceof JsMetadataVersion)) {
            throw new IllegalStateException(((binaryVersion != null ? Reflection.getOrCreateKotlinClass(binaryVersion.getClass()) : null) + " must be either null or " + Reflection.getOrCreateKotlinClass(JsMetadataVersion.class).getSimpleName()).toString());
        }
        KotlinJavascriptSerializationUtil kotlinJavascriptSerializationUtil = KotlinJavascriptSerializationUtil.INSTANCE;
        CompilerConfiguration configuration = this.config.getConfiguration();
        Intrinsics.checkNotNullExpressionValue(configuration, "getConfiguration(...)");
        LanguageVersionSettings languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(configuration);
        Project project = this.config.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        JsMetadataVersion jsMetadataVersion = (JsMetadataVersion) binaryVersion;
        if (jsMetadataVersion == null) {
            jsMetadataVersion = JsMetadataVersion.INSTANCE;
        }
        return kotlinJavascriptSerializationUtil.serializeDescriptors(bindingContext, moduleDescriptor, scope, packageName, languageVersionSettings, project, jsMetadataVersion);
    }

    /* JADX INFO: compiled from: K2JSTranslator.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B+\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\bR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/js/facade/K2JSTranslator$TranslationData;", "", "memberScope", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "binaryAst", "", "inlineData", "(Ljava/util/Collection;[B[B)V", "getBinaryAst", "()[B", "getInlineData", "getMemberScope", "()Ljava/util/Collection;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class TranslationData {
        private final byte[] binaryAst;
        private final byte[] inlineData;
        private final Collection<DeclarationDescriptor> memberScope;

        /* JADX WARN: Multi-variable type inference failed */
        public TranslationData(Collection<? extends DeclarationDescriptor> memberScope, byte[] bArr, byte[] bArr2) {
            Intrinsics.checkNotNullParameter(memberScope, "memberScope");
            this.memberScope = memberScope;
            this.binaryAst = bArr;
            this.inlineData = bArr2;
        }

        public /* synthetic */ TranslationData(Collection collection, byte[] bArr, byte[] bArr2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(collection, (i & 2) != 0 ? null : bArr, (i & 4) != 0 ? null : bArr2);
        }

        public final Collection<DeclarationDescriptor> getMemberScope() {
            return this.memberScope;
        }

        public final byte[] getBinaryAst() {
            return this.binaryAst;
        }

        public final byte[] getInlineData() {
            return this.inlineData;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final TranslationData toTranslationData(JsAstSerializer jsAstSerializer, SourceFileTranslationResult sourceFileTranslationResult) {
        JsProgramFragment fragment = sourceFileTranslationResult.getFragment();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        jsAstSerializer.serialize(fragment, byteArrayOutputStream);
        return new TranslationData(sourceFileTranslationResult.getMemberScope(), byteArrayOutputStream.toByteArray(), serializeInlineData(sourceFileTranslationResult.getInlineFunctionTags()));
    }

    private final void trySaveIncrementalData(Iterable<? extends KtFile> sourceFiles, final SourceFilePathResolver pathResolver, BindingTrace bindingTrace, ModuleDescriptor moduleDescriptor, Function2<? super JsAstSerializer, ? super KtFile, TranslationData> translationData) {
        if (this.incrementalResults != null || this.shouldValidateJsAst) {
            JsAstSerializer jsAstSerializer = new JsAstSerializer(this.shouldValidateJsAst ? new K2JSTranslator$trySaveIncrementalData$serializer$1(this) : null, new Function1<File, String>() { // from class: org.jetbrains.kotlin.js.facade.K2JSTranslator$trySaveIncrementalData$serializer$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final String invoke(File file) {
                    Intrinsics.checkNotNullParameter(file, "file");
                    try {
                        return pathResolver.getPathRelativeToSourceRoots(file);
                    } catch (IOException e) {
                        throw new RuntimeException("IO error occurred resolving path to source file", e);
                    }
                }
            });
            for (KtFile ktFile : sourceFiles) {
                TranslationData translationDataInvoke = translationData.invoke(jsAstSerializer, ktFile);
                BindingContext context = bindingTrace.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
                ProtoBuf.PackageFragment packageFragmentSerializeScope = serializeScope(context, moduleDescriptor, ktFile.getPackageFqName(), translationDataInvoke.getMemberScope());
                File fileVirtualToIoFile = VfsUtilCore.virtualToIoFile(ktFile.getVirtualFile());
                Intrinsics.checkNotNullExpressionValue(fileVirtualToIoFile, "virtualToIoFile(...)");
                IncrementalResultsConsumer incrementalResultsConsumer = this.incrementalResults;
                if (incrementalResultsConsumer != null) {
                    byte[] byteArray = packageFragmentSerializeScope.toByteArray();
                    Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
                    byte[] binaryAst = translationDataInvoke.getBinaryAst();
                    if (binaryAst == null) {
                        binaryAst = new byte[0];
                    }
                    byte[] inlineData = translationDataInvoke.getInlineData();
                    if (inlineData == null) {
                        inlineData = new byte[0];
                    }
                    incrementalResultsConsumer.processPackagePart(fileVirtualToIoFile, byteArray, binaryAst, inlineData);
                }
            }
            CompilerConfiguration configuration = this.config.getConfiguration();
            Intrinsics.checkNotNullExpressionValue(configuration, "getConfiguration(...)");
            LanguageVersionSettings languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(configuration);
            IncrementalResultsConsumer incrementalResultsConsumer2 = this.incrementalResults;
            if (incrementalResultsConsumer2 != null) {
                byte[] byteArray2 = KotlinJavascriptSerializationUtil.INSTANCE.serializeHeader(moduleDescriptor, null, languageVersionSettings).toByteArray();
                Intrinsics.checkNotNullExpressionValue(byteArray2, "toByteArray(...)");
                incrementalResultsConsumer2.processHeader(byteArray2);
            }
        }
    }

    private final byte[] serializeInlineData(Set<String> importedTags) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JsAstProtoBuf.InlineData.Builder builderNewBuilder = JsAstProtoBuf.InlineData.newBuilder();
        builderNewBuilder.addAllInlineFunctionTags(importedTags);
        builderNewBuilder.build().writeTo(byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
        return byteArray;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void validateJsAst(JsProgramFragment fragment, Set<? extends JsName> serializedNames) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = fragment.getNameBindings().iterator();
        while (it.hasNext()) {
            linkedHashSet.add(((JsNameBinding) it.next()).getName());
        }
        LinkedHashSet linkedHashSet2 = linkedHashSet;
        Iterator<T> it2 = fragment.getImportedModules().iterator();
        while (it2.hasNext()) {
            linkedHashSet2.add(((JsImportedModule) it2.next()).getInternalName());
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(fragment.getDeclarationBlock());
        arrayList.add(fragment.getExportBlock());
        arrayList.add(fragment.getInitializerBlock());
        JsStatement tests = fragment.getTests();
        if (tests != null) {
            arrayList.add(tests);
        }
        JsStatement mainFunction = fragment.getMainFunction();
        if (mainFunction != null) {
            arrayList.add(mainFunction);
        }
        arrayList.addAll(fragment.getInlinedLocalDeclarations().values());
        Set<JsName> setCollectDefinedNamesInAllScopes = CollectUtilsKt.collectDefinedNamesInAllScopes(new JsBlock(arrayList));
        for (JsName jsName : serializedNames) {
            if (jsName.isTemporary() && !setCollectDefinedNamesInAllScopes.contains(jsName)) {
                linkedHashSet.contains(jsName);
            }
        }
    }
}
