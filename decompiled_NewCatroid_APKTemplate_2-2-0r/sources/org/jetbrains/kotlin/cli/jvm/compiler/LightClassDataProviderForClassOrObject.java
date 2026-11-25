package org.jetbrains.kotlin.cli.jvm.compiler;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analyzer.KotlinModificationTrackerService;
import org.jetbrains.kotlin.asJava.LightClassGenerationSupport;
import org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclarationKt;
import org.jetbrains.kotlin.cli.jvm.compiler.builder.CodeGenerationResult;
import org.jetbrains.kotlin.cli.jvm.compiler.builder.LightClassBuilderKt;
import org.jetbrains.kotlin.codegen.MemberCodegen;
import org.jetbrains.kotlin.codegen.PackageCodegen;
import org.jetbrains.kotlin.codegen.context.PackageContext;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.diagnostics.Diagnostics;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: LightClassDataProvider.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u000e\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007H\u0016J\b\u0010\b\u001a\u00020\u0002H\u0002J\b\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/LightClassDataProviderForClassOrObject;", "Lorg/jetbrains/kotlin/com/intellij/psi/util/CachedValueProvider;", "Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "(Lorg/jetbrains/kotlin/psi/KtClassOrObject;)V", "compute", "Lorg/jetbrains/kotlin/com/intellij/psi/util/CachedValueProvider$Result;", "computeLightClassData", "toString", "", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LightClassDataProviderForClassOrObject implements CachedValueProvider<Diagnostics> {
    private final KtClassOrObject classOrObject;

    public LightClassDataProviderForClassOrObject(KtClassOrObject classOrObject) {
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        this.classOrObject = classOrObject;
    }

    private final Diagnostics computeLightClassData() {
        final KtFile containingKtFile = this.classOrObject.getContainingKtFile();
        Intrinsics.checkNotNullExpressionValue(containingKtFile, "getContainingKtFile(...)");
        final FqName packageFqName = containingKtFile.getPackageFqName();
        LightClassGenerationSupport.Companion companion = LightClassGenerationSupport.INSTANCE;
        Project project = this.classOrObject.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        LightClassGenerationSupport companion2 = companion.getInstance(project);
        Intrinsics.checkNotNull(companion2, "null cannot be cast to non-null type org.jetbrains.kotlin.cli.jvm.compiler.CliLightClassGenerationSupport");
        CliLightClassGenerationSupport cliLightClassGenerationSupport = (CliLightClassGenerationSupport) companion2;
        ClassDescriptor classDescriptor = (ClassDescriptor) cliLightClassGenerationSupport.getTraceHolder().getBindingContext().get(BindingContext.CLASS, this.classOrObject);
        if (classDescriptor != null) {
            classDescriptor.mo6346getCompanionObjectDescriptor();
        }
        CodeGenerationResult codeGenerationResultExtraJvmDiagnosticsFromBackend = LightClassBuilderKt.extraJvmDiagnosticsFromBackend(packageFqName, CollectionsKt.listOf(containingKtFile), new ClassFilterForClassOrObject(this.classOrObject), cliLightClassGenerationSupport.getContext(), new Function2<GenerationState, Collection<? extends KtFile>, Unit>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.LightClassDataProviderForClassOrObject.computeLightClassData.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                PackageCodegen packageCodegenForPackage = state.getFactory().forPackage(packageFqName, files);
                Intrinsics.checkNotNullExpressionValue(packageCodegenForPackage, "forPackage(...)");
                PackageContext packageContextIntoPackagePart = state.getRootContext().intoPackagePart(packageCodegenForPackage.getPackageFragment(), Type.getObjectType(JvmFileClassUtil.getFileClassInternalName(containingKtFile)), containingKtFile);
                Intrinsics.checkNotNullExpressionValue(packageContextIntoPackagePart, "intoPackagePart(...)");
                MemberCodegen.genClassOrObject(packageContextIntoPackagePart, KtLightClassForSourceDeclarationKt.getOutermostClassOrObject(this.classOrObject), state, null);
                state.getFactory().done();
            }
        });
        BindingContext bindingContext = codeGenerationResultExtraJvmDiagnosticsFromBackend.getBindingContext();
        Diagnostics diagnostics = codeGenerationResultExtraJvmDiagnosticsFromBackend.getDiagnostics();
        if (bindingContext.get(BindingContext.CLASS, this.classOrObject) == null) {
            diagnostics = null;
        }
        return diagnostics == null ? Diagnostics.INSTANCE.getEMPTY() : diagnostics;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
    public CachedValueProvider.Result<Diagnostics> compute() {
        KotlinModificationTrackerService.Companion companion = KotlinModificationTrackerService.INSTANCE;
        Project project = this.classOrObject.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        KotlinModificationTrackerService companion2 = companion.getInstance(project);
        Diagnostics diagnosticsComputeLightClassData = computeLightClassData();
        final KtClassOrObject ktClassOrObject = this.classOrObject;
        CachedValueProvider.Result<Diagnostics> resultCreate = CachedValueProvider.Result.create(diagnosticsComputeLightClassData, ((Boolean) ApplicationManager.getApplication().runReadAction(new Computable() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.LightClassDataProviderForClassOrObject$compute$$inlined$safeIsLocal$1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final T compute() {
                return (T) Boolean.valueOf(ktClassOrObject.isLocal());
            }
        })).booleanValue() ? companion2.getModificationTracker() : companion2.getOutOfBlockModificationTracker());
        Intrinsics.checkNotNullExpressionValue(resultCreate, "create(...)");
        return resultCreate;
    }

    public String toString() {
        return getClass().getName() + " for " + this.classOrObject.getName();
    }
}
