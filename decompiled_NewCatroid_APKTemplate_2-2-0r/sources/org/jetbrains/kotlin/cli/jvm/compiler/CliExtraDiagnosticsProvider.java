package org.jetbrains.kotlin.cli.jvm.compiler;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analyzer.KotlinModificationTrackerService;
import org.jetbrains.kotlin.asJava.KotlinAsJavaSupport;
import org.jetbrains.kotlin.asJava.LightClassGenerationSupport;
import org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclarationKt;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.cli.jvm.compiler.builder.LightClassBuilderKt;
import org.jetbrains.kotlin.cli.jvm.compiler.builder.LightClassConstructionContext;
import org.jetbrains.kotlin.codegen.MultifileClassCodegen;
import org.jetbrains.kotlin.codegen.PackageCodegen;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtil;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtilKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.diagnostics.Diagnostics;

/* JADX INFO: compiled from: CliExtraDiagnosticsProvider.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/CliExtraDiagnosticsProvider;", "", "()V", "calculateForFacade", "Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "forClassOrObject", MangleConstant.CLASS_PREFIX, "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "forFacade", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CliExtraDiagnosticsProvider {
    public static final CliExtraDiagnosticsProvider INSTANCE = new CliExtraDiagnosticsProvider();

    private CliExtraDiagnosticsProvider() {
    }

    public final Diagnostics forClassOrObject(KtClassOrObject kclass) {
        Intrinsics.checkNotNullParameter(kclass, "kclass");
        if (!KtLightClassForSourceDeclarationKt.shouldNotBeVisibleAsLightClass(kclass)) {
            Object value = CliExtraDiagnosticsProviderKt.getLightClassCachedValue(kclass).getValue();
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            return (Diagnostics) value;
        }
        return Diagnostics.INSTANCE.getEMPTY();
    }

    public final Diagnostics forFacade(final KtFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        Object cachedValue = CachedValuesManager.getCachedValue((PsiElement) file, (CachedValueProvider<Object>) new CachedValueProvider() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.CliExtraDiagnosticsProvider$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return CliExtraDiagnosticsProvider.forFacade$lambda$0(file);
            }
        });
        Intrinsics.checkNotNullExpressionValue(cachedValue, "getCachedValue(...)");
        return (Diagnostics) cachedValue;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CachedValueProvider.Result forFacade$lambda$0(KtFile file) {
        Intrinsics.checkNotNullParameter(file, "$file");
        Diagnostics diagnosticsCalculateForFacade = INSTANCE.calculateForFacade(file);
        KotlinModificationTrackerService.Companion companion = KotlinModificationTrackerService.INSTANCE;
        Project project = file.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return CachedValueProvider.Result.create(diagnosticsCalculateForFacade, companion.getInstance(project).getOutOfBlockModificationTracker());
    }

    private final Diagnostics calculateForFacade(KtFile file) {
        Project project = file.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        final FqName javaFileFacadeFqName = JvmFileClassUtilKt.getJavaFileFacadeFqName(file);
        KotlinAsJavaSupport companion = KotlinAsJavaSupport.INSTANCE.getInstance(project);
        GlobalSearchScope globalSearchScopeAllScope = GlobalSearchScope.allScope(project);
        Intrinsics.checkNotNullExpressionValue(globalSearchScopeAllScope, "allScope(...)");
        Collection<KtFile> collectionFindFilesForFacade = companion.findFilesForFacade(javaFileFacadeFqName, globalSearchScopeAllScope);
        if (collectionFindFilesForFacade.isEmpty()) {
            return Diagnostics.INSTANCE.getEMPTY();
        }
        LightClassGenerationSupport companion2 = LightClassGenerationSupport.INSTANCE.getInstance(project);
        Intrinsics.checkNotNull(companion2, "null cannot be cast to non-null type org.jetbrains.kotlin.cli.jvm.compiler.CliLightClassGenerationSupport");
        LightClassConstructionContext context = ((CliLightClassGenerationSupport) companion2).getContext();
        FqName fqNameParent = javaFileFacadeFqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        return LightClassBuilderKt.extraJvmDiagnosticsFromBackend(fqNameParent, collectionFindFilesForFacade, ClassFilterForFacade.INSTANCE, context, new Function2<GenerationState, Collection<? extends KtFile>, Unit>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.CliExtraDiagnosticsProvider.calculateForFacade.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(GenerationState generationState, Collection<? extends KtFile> collection) {
                invoke2(generationState, collection);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(GenerationState state, Collection<? extends KtFile> files) {
                Intrinsics.checkNotNullParameter(state, "state");
                Intrinsics.checkNotNullParameter(files, "files");
                KtFile ktFile = (KtFile) CollectionsKt.first(files);
                if (!JvmFileClassUtil.getFileClassInfoNoResolve(ktFile).getWithJvmMultifileClass()) {
                    PackageCodegen packageCodegenForPackage = state.getFactory().forPackage(ktFile.getPackageFqName(), files);
                    Intrinsics.checkNotNullExpressionValue(packageCodegenForPackage, "forPackage(...)");
                    packageCodegenForPackage.generate();
                    state.getFactory().done();
                    return;
                }
                MultifileClassCodegen multifileClassCodegenForMultifileClass = state.getFactory().forMultifileClass(javaFileFacadeFqName, files);
                Intrinsics.checkNotNullExpressionValue(multifileClassCodegenForMultifileClass, "forMultifileClass(...)");
                multifileClassCodegenForMultifileClass.generate();
                state.getFactory().done();
            }
        }).getDiagnostics();
    }
}
