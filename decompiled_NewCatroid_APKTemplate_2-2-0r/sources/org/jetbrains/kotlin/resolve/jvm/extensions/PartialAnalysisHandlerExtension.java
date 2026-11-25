package org.jetbrains.kotlin.resolve.jvm.extensions;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analyzer.AnalysisResult;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.container.ComponentProvider;
import org.jetbrains.kotlin.container.DslKt;
import org.jetbrains.kotlin.context.ProjectContext;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.BodyResolver;
import org.jetbrains.kotlin.resolve.LazyTopDownAnalyzer;
import org.jetbrains.kotlin.resolve.TopDownAnalysisContext;
import org.jetbrains.kotlin.resolve.TopDownAnalysisMode;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.lazy.DeclarationScopeProvider;
import org.jetbrains.kotlin.resolve.lazy.ForceResolveUtil;
import org.jetbrains.kotlin.resolve.lazy.ResolveSession;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt;

/* JADX INFO: compiled from: PartialAnalysisHandlerExtension.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J@\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J*\u0010\u001d\u001a\u00020\u001e2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u001e0 H\u0002J$\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\"\u001a\u00020!2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u001e0 H\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u001a\u0010\t\u001a\u0004\u0018\u00010\n*\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/extensions/PartialAnalysisHandlerExtension;", "Lorg/jetbrains/kotlin/resolve/jvm/extensions/AnalysisHandlerExtension;", "()V", "analyzeDefaultParameterValues", "", "getAnalyzeDefaultParameterValues", "()Z", "analyzePartially", "getAnalyzePartially", "containingScope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getContainingScope", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "doAnalysis", "Lorg/jetbrains/kotlin/analyzer/AnalysisResult;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "projectContext", "Lorg/jetbrains/kotlin/context/ProjectContext;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "bindingTrace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "componentProvider", "Lorg/jetbrains/kotlin/container/ComponentProvider;", "doForEachDeclaration", "", "f", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "declaration", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class PartialAnalysisHandlerExtension implements AnalysisHandlerExtension {
    public boolean getAnalyzeDefaultParameterValues() {
        return false;
    }

    public boolean getAnalyzePartially() {
        return true;
    }

    @Override // org.jetbrains.kotlin.resolve.extensions.AnalysisHandlerExtension
    public AnalysisResult doAnalysis(Project project, ModuleDescriptor module, ProjectContext projectContext, Collection<? extends KtFile> files, final BindingTrace bindingTrace, ComponentProvider componentProvider) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(projectContext, "projectContext");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        Intrinsics.checkNotNullParameter(componentProvider, "componentProvider");
        if (!getAnalyzePartially()) {
            return null;
        }
        final ResolveSession resolveSession = (ResolveSession) DslKt.getService(componentProvider, ResolveSession.class);
        final BodyResolver bodyResolver = (BodyResolver) DslKt.getService(componentProvider, BodyResolver.class);
        DeclarationScopeProvider declarationScopeProvider = (DeclarationScopeProvider) DslKt.getService(componentProvider, DeclarationScopeProvider.class);
        LazyTopDownAnalyzer lazyTopDownAnalyzer = (LazyTopDownAnalyzer) DslKt.getService(componentProvider, LazyTopDownAnalyzer.class);
        final TopDownAnalysisContext topDownAnalysisContext = new TopDownAnalysisContext(TopDownAnalysisMode.TopLevelDeclarations, DataFlowInfo.INSTANCE.getEMPTY(), declarationScopeProvider);
        for (KtFile ktFile : files) {
            ForceResolveUtil.forceResolveAllContents(resolveSession.getFileAnnotations(ktFile));
            lazyTopDownAnalyzer.resolveImportsInFile(ktFile);
        }
        doForEachDeclaration(files, new Function1<KtDeclaration, Unit>() { // from class: org.jetbrains.kotlin.resolve.jvm.extensions.PartialAnalysisHandlerExtension.doAnalysis.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(KtDeclaration ktDeclaration) {
                invoke2(ktDeclaration);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(KtDeclaration declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                DeclarationDescriptor declarationDescriptorResolveToDescriptor = resolveSession.resolveToDescriptor(declaration);
                Intrinsics.checkNotNullExpressionValue(declarationDescriptorResolveToDescriptor, "resolveToDescriptor(...)");
                if (declarationDescriptorResolveToDescriptor instanceof ClassDescriptor) {
                    ForceResolveUtil.forceResolveAllContents(declarationDescriptorResolveToDescriptor);
                    ClassDescriptor classDescriptor = (ClassDescriptor) declarationDescriptorResolveToDescriptor;
                    ForceResolveUtil.forceResolveAllContents(classDescriptor.getTypeConstructor().mo7438getSupertypes());
                    if ((declaration instanceof KtClassOrObject) && (declarationDescriptorResolveToDescriptor instanceof ClassDescriptorWithResolutionScopes)) {
                        ClassDescriptorWithResolutionScopes classDescriptorWithResolutionScopes = (ClassDescriptorWithResolutionScopes) declarationDescriptorResolveToDescriptor;
                        bodyResolver.resolveSuperTypeEntryList(DataFlowInfo.INSTANCE.getEMPTY(), (KtClassOrObject) declaration, classDescriptor, classDescriptorWithResolutionScopes.mo6347getUnsubstitutedPrimaryConstructor(), classDescriptorWithResolutionScopes.getScopeForConstructorHeaderResolution(), classDescriptorWithResolutionScopes.getScopeForMemberDeclarationResolution(), resolveSession.getInferenceSession());
                        return;
                    }
                    return;
                }
                if (declarationDescriptorResolveToDescriptor instanceof PropertyDescriptor) {
                    if (declaration instanceof KtProperty) {
                        bodyResolver.resolveProperty(topDownAnalysisContext, (KtProperty) declaration, (PropertyDescriptor) declarationDescriptorResolveToDescriptor);
                        return;
                    }
                    return;
                }
                if (declarationDescriptorResolveToDescriptor instanceof FunctionDescriptor) {
                    if ((declaration instanceof KtPrimaryConstructor) && (this.getAnalyzeDefaultParameterValues() || DescriptorUtilsKt.isAnnotationConstructor(declarationDescriptorResolveToDescriptor))) {
                        LexicalScope containingScope = this.getContainingScope(declarationDescriptorResolveToDescriptor);
                        if (containingScope != null) {
                            bodyResolver.resolveConstructorParameterDefaultValues(topDownAnalysisContext.getOuterDataFlowInfo(), bindingTrace, (KtPrimaryConstructor) declaration, (ConstructorDescriptor) declarationDescriptorResolveToDescriptor, containingScope, resolveSession.getInferenceSession());
                            return;
                        }
                        return;
                    }
                    if (declaration instanceof KtFunction) {
                        KtFunction ktFunction = (KtFunction) declaration;
                        if (ktFunction.hasDeclaredReturnType() || ktFunction.hasBlockBody()) {
                            return;
                        }
                        ForceResolveUtil.forceResolveAllContents(declarationDescriptorResolveToDescriptor);
                    }
                }
            }
        });
        AnalysisResult.Companion companion = AnalysisResult.INSTANCE;
        BindingContext context = bindingTrace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        return companion.success(context, module, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final LexicalScope getContainingScope(DeclarationDescriptor declarationDescriptor) {
        DeclarationDescriptor containingModule = declarationDescriptor.getContainingModule();
        if (containingModule == null) {
            return null;
        }
        if (containingModule instanceof ClassDescriptorWithResolutionScopes) {
            return ((ClassDescriptorWithResolutionScopes) containingModule).getScopeForInitializerResolution();
        }
        if (containingModule instanceof PackageFragmentDescriptor) {
            return new LexicalScope.Base(ScopeUtilsKt.memberScopeAsImportingScope$default(((PackageFragmentDescriptor) containingModule).getScope(), null, 1, null), declarationDescriptor);
        }
        return null;
    }

    private final void doForEachDeclaration(KtDeclaration declaration, Function1<? super KtDeclaration, Unit> f) {
        if (!(declaration instanceof KtAnonymousInitializer)) {
            f.invoke(declaration);
        }
        if (declaration instanceof KtClassOrObject) {
            KtClassOrObject ktClassOrObject = (KtClassOrObject) declaration;
            KtPrimaryConstructor primaryConstructor = ktClassOrObject.getPrimaryConstructor();
            if (primaryConstructor != null) {
                f.invoke(primaryConstructor);
            }
            Iterator<T> it = ktClassOrObject.getDeclarations().iterator();
            while (it.hasNext()) {
                doForEachDeclaration((KtDeclaration) it.next(), f);
            }
        }
    }

    private final void doForEachDeclaration(Collection<? extends KtFile> files, Function1<? super KtDeclaration, Unit> f) {
        Iterator<? extends KtFile> it = files.iterator();
        while (it.hasNext()) {
            Iterator<T> it2 = it.next().getDeclarations().iterator();
            while (it2.hasNext()) {
                doForEachDeclaration((KtDeclaration) it2.next(), f);
            }
        }
    }
}
