package org.jetbrains.kotlin.types.expressions;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.ControlFlowInformationProvider;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.container.DslKt;
import org.jetbrains.kotlin.context.ContextKt;
import org.jetbrains.kotlin.context.GlobalContext;
import org.jetbrains.kotlin.context.ModuleContext;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.SupertypeLoopChecker;
import org.jetbrains.kotlin.frontend.di.InjectionKt;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.resolve.AdditionalClassPartsProvider;
import org.jetbrains.kotlin.resolve.AnnotationResolver;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorResolver;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.FunctionDescriptorResolver;
import org.jetbrains.kotlin.resolve.LazyTopDownAnalyzer;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;
import org.jetbrains.kotlin.resolve.SealedClassInheritorsProvider;
import org.jetbrains.kotlin.resolve.StatementFilter;
import org.jetbrains.kotlin.resolve.TopDownAnalysisMode;
import org.jetbrains.kotlin.resolve.TypeResolver;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension;
import org.jetbrains.kotlin.resolve.lazy.AbsentDescriptorHandler;
import org.jetbrains.kotlin.resolve.lazy.DelegationFilter;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolver;
import org.jetbrains.kotlin.resolve.scopes.LexicalWritableScope;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.WrappedTypeFactory;
import org.jetbrains.kotlin.types.checker.NewKotlinTypeChecker;

/* JADX INFO: compiled from: LocalClassifierAnalyzer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u009c\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u009d\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\u0006\u0010\u0016\u001a\u00020\u0017\u0012\u0006\u0010\u0018\u001a\u00020\u0019\u0012\u0006\u0010\u001a\u001a\u00020\u001b\u0012\u0006\u0010\u001c\u001a\u00020\u001d\u0012\u0006\u0010\u001e\u001a\u00020\u001f\u0012\u0006\u0010 \u001a\u00020!\u0012\u0006\u0010\"\u001a\u00020#\u0012\u0006\u0010$\u001a\u00020%\u0012\u0006\u0010&\u001a\u00020'¢\u0006\u0002\u0010(J(\u0010)\u001a\u00020*2\b\u0010+\u001a\u0004\u0018\u00010,2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u000202R\u000e\u0010&\u001a\u00020'X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/LocalClassifierAnalyzer;", "", "globalContext", "Lorg/jetbrains/kotlin/context/GlobalContext;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "descriptorResolver", "Lorg/jetbrains/kotlin/resolve/DescriptorResolver;", "functionDescriptorResolver", "Lorg/jetbrains/kotlin/resolve/FunctionDescriptorResolver;", "typeResolver", "Lorg/jetbrains/kotlin/resolve/TypeResolver;", "annotationResolver", "Lorg/jetbrains/kotlin/resolve/AnnotationResolver;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "analyzerServices", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "supertypeLoopChecker", "Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "delegationFilter", "Lorg/jetbrains/kotlin/resolve/lazy/DelegationFilter;", "wrappedTypeFactory", "Lorg/jetbrains/kotlin/types/WrappedTypeFactory;", "kotlinTypeChecker", "Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;", "samConversionResolver", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "additionalClassPartsProvider", "Lorg/jetbrains/kotlin/resolve/AdditionalClassPartsProvider;", "sealedClassInheritorsProvider", "Lorg/jetbrains/kotlin/resolve/SealedClassInheritorsProvider;", "controlFlowInformationProviderFactory", "Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProvider$Factory;", "absentDescriptorHandler", "Lorg/jetbrains/kotlin/resolve/lazy/AbsentDescriptorHandler;", "(Lorg/jetbrains/kotlin/context/GlobalContext;Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/resolve/DescriptorResolver;Lorg/jetbrains/kotlin/resolve/FunctionDescriptorResolver;Lorg/jetbrains/kotlin/resolve/TypeResolver;Lorg/jetbrains/kotlin/resolve/AnnotationResolver;Lorg/jetbrains/kotlin/platform/TargetPlatform;Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;Lorg/jetbrains/kotlin/incremental/components/LookupTracker;Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/lazy/DelegationFilter;Lorg/jetbrains/kotlin/types/WrappedTypeFactory;Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;Lorg/jetbrains/kotlin/resolve/AdditionalClassPartsProvider;Lorg/jetbrains/kotlin/resolve/SealedClassInheritorsProvider;Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProvider$Factory;Lorg/jetbrains/kotlin/resolve/lazy/AbsentDescriptorHandler;)V", "processClassOrObject", "", "scope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalWritableScope;", "context", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LocalClassifierAnalyzer {
    private final AbsentDescriptorHandler absentDescriptorHandler;
    private final AdditionalClassPartsProvider additionalClassPartsProvider;
    private final PlatformDependentAnalyzerServices analyzerServices;
    private final AnnotationResolver annotationResolver;
    private final ControlFlowInformationProvider.Factory controlFlowInformationProviderFactory;
    private final DelegationFilter delegationFilter;
    private final DescriptorResolver descriptorResolver;
    private final FunctionDescriptorResolver functionDescriptorResolver;
    private final GlobalContext globalContext;
    private final NewKotlinTypeChecker kotlinTypeChecker;
    private final LanguageVersionSettings languageVersionSettings;
    private final LookupTracker lookupTracker;
    private final TargetPlatform platform;
    private final SamConversionResolver samConversionResolver;
    private final SealedClassInheritorsProvider sealedClassInheritorsProvider;
    private final StorageManager storageManager;
    private final SupertypeLoopChecker supertypeLoopChecker;
    private final TypeResolver typeResolver;
    private final WrappedTypeFactory wrappedTypeFactory;

    public LocalClassifierAnalyzer(GlobalContext globalContext, StorageManager storageManager, DescriptorResolver descriptorResolver, FunctionDescriptorResolver functionDescriptorResolver, TypeResolver typeResolver, AnnotationResolver annotationResolver, TargetPlatform platform, PlatformDependentAnalyzerServices analyzerServices, LookupTracker lookupTracker, SupertypeLoopChecker supertypeLoopChecker, LanguageVersionSettings languageVersionSettings, DelegationFilter delegationFilter, WrappedTypeFactory wrappedTypeFactory, NewKotlinTypeChecker kotlinTypeChecker, SamConversionResolver samConversionResolver, AdditionalClassPartsProvider additionalClassPartsProvider, SealedClassInheritorsProvider sealedClassInheritorsProvider, ControlFlowInformationProvider.Factory controlFlowInformationProviderFactory, AbsentDescriptorHandler absentDescriptorHandler) {
        Intrinsics.checkNotNullParameter(globalContext, "globalContext");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(descriptorResolver, "descriptorResolver");
        Intrinsics.checkNotNullParameter(functionDescriptorResolver, "functionDescriptorResolver");
        Intrinsics.checkNotNullParameter(typeResolver, "typeResolver");
        Intrinsics.checkNotNullParameter(annotationResolver, "annotationResolver");
        Intrinsics.checkNotNullParameter(platform, "platform");
        Intrinsics.checkNotNullParameter(analyzerServices, "analyzerServices");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        Intrinsics.checkNotNullParameter(supertypeLoopChecker, "supertypeLoopChecker");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(delegationFilter, "delegationFilter");
        Intrinsics.checkNotNullParameter(wrappedTypeFactory, "wrappedTypeFactory");
        Intrinsics.checkNotNullParameter(kotlinTypeChecker, "kotlinTypeChecker");
        Intrinsics.checkNotNullParameter(samConversionResolver, "samConversionResolver");
        Intrinsics.checkNotNullParameter(additionalClassPartsProvider, "additionalClassPartsProvider");
        Intrinsics.checkNotNullParameter(sealedClassInheritorsProvider, "sealedClassInheritorsProvider");
        Intrinsics.checkNotNullParameter(controlFlowInformationProviderFactory, "controlFlowInformationProviderFactory");
        Intrinsics.checkNotNullParameter(absentDescriptorHandler, "absentDescriptorHandler");
        this.globalContext = globalContext;
        this.storageManager = storageManager;
        this.descriptorResolver = descriptorResolver;
        this.functionDescriptorResolver = functionDescriptorResolver;
        this.typeResolver = typeResolver;
        this.annotationResolver = annotationResolver;
        this.platform = platform;
        this.analyzerServices = analyzerServices;
        this.lookupTracker = lookupTracker;
        this.supertypeLoopChecker = supertypeLoopChecker;
        this.languageVersionSettings = languageVersionSettings;
        this.delegationFilter = delegationFilter;
        this.wrappedTypeFactory = wrappedTypeFactory;
        this.kotlinTypeChecker = kotlinTypeChecker;
        this.samConversionResolver = samConversionResolver;
        this.additionalClassPartsProvider = additionalClassPartsProvider;
        this.sealedClassInheritorsProvider = sealedClassInheritorsProvider;
        this.controlFlowInformationProviderFactory = controlFlowInformationProviderFactory;
        this.absentDescriptorHandler = absentDescriptorHandler;
    }

    public final void processClassOrObject(LexicalWritableScope scope, ExpressionTypingContext context, DeclarationDescriptor containingDeclaration, KtClassOrObject classOrObject) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        ModuleDescriptor containingModule = DescriptorUtils.getContainingModule(containingDeclaration);
        Intrinsics.checkNotNullExpressionValue(containingModule, "getContainingModule(...)");
        Project project = classOrObject.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        ModuleContext moduleContextWithModule = ContextKt.withModule(ContextKt.withProject(this.globalContext, project), containingModule);
        BindingTrace trace = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        TargetPlatform targetPlatform = this.platform;
        LookupTracker lookupTracker = this.lookupTracker;
        LanguageVersionSettings languageVersionSettings = this.languageVersionSettings;
        StatementFilter statementFilter = context.statementFilter;
        Intrinsics.checkNotNullExpressionValue(statementFilter, "statementFilter");
        LazyTopDownAnalyzer lazyTopDownAnalyzer = (LazyTopDownAnalyzer) DslKt.getService(InjectionKt.createContainerForLazyLocalClassifierAnalyzer(moduleContextWithModule, trace, targetPlatform, lookupTracker, languageVersionSettings, statementFilter, new LocalClassDescriptorHolder(scope, classOrObject, containingDeclaration, this.storageManager, context, containingModule, this.descriptorResolver, this.functionDescriptorResolver, this.typeResolver, this.annotationResolver, this.supertypeLoopChecker, this.languageVersionSettings, SyntheticResolveExtension.INSTANCE.getInstance(project), this.delegationFilter, this.wrappedTypeFactory, this.kotlinTypeChecker, this.samConversionResolver, this.additionalClassPartsProvider, this.sealedClassInheritorsProvider), this.analyzerServices, this.controlFlowInformationProviderFactory, this.absentDescriptorHandler), LazyTopDownAnalyzer.class);
        TopDownAnalysisMode topDownAnalysisMode = TopDownAnalysisMode.LocalDeclarations;
        List listListOf = CollectionsKt.listOf(classOrObject);
        DataFlowInfo dataFlowInfo = context.dataFlowInfo;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
        lazyTopDownAnalyzer.analyzeDeclarations(topDownAnalysisMode, listListOf, dataFlowInfo, context);
    }
}
