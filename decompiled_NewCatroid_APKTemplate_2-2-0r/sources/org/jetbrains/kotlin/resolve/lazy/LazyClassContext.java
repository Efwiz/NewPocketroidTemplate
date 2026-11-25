package org.jetbrains.kotlin.resolve.lazy;

import kotlin.Metadata;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.SupertypeLoopChecker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.resolve.AdditionalClassPartsProvider;
import org.jetbrains.kotlin.resolve.AnnotationResolver;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorResolver;
import org.jetbrains.kotlin.resolve.FunctionDescriptorResolver;
import org.jetbrains.kotlin.resolve.SealedClassInheritorsProvider;
import org.jetbrains.kotlin.resolve.TypeResolver;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension;
import org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactory;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolver;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.WrappedTypeFactory;
import org.jetbrains.kotlin.types.checker.NewKotlinTypeChecker;

/* JADX INFO: compiled from: LazyClassContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000ª\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0012\u0010\u0012\u001a\u00020\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0012\u0010\u0016\u001a\u00020\u0017X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0012\u0010\u001a\u001a\u00020\u001bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u0004\u0018\u00010\u001fX¦\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u0012\u0010\"\u001a\u00020#X¦\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u0012\u0010&\u001a\u00020'X¦\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0012\u0010*\u001a\u00020+X¦\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-R\u0012\u0010.\u001a\u00020/X¦\u0004¢\u0006\u0006\u001a\u0004\b0\u00101R\u0012\u00102\u001a\u000203X¦\u0004¢\u0006\u0006\u001a\u0004\b4\u00105R\u0012\u00106\u001a\u000207X¦\u0004¢\u0006\u0006\u001a\u0004\b8\u00109R\u0012\u0010:\u001a\u00020;X¦\u0004¢\u0006\u0006\u001a\u0004\b<\u0010=R\u0012\u0010>\u001a\u00020?X¦\u0004¢\u0006\u0006\u001a\u0004\b@\u0010AR\u0012\u0010B\u001a\u00020CX¦\u0004¢\u0006\u0006\u001a\u0004\bD\u0010ER\u0012\u0010F\u001a\u00020GX¦\u0004¢\u0006\u0006\u001a\u0004\bH\u0010IR\u0012\u0010J\u001a\u00020KX¦\u0004¢\u0006\u0006\u001a\u0004\bL\u0010MR\u0012\u0010N\u001a\u00020OX¦\u0004¢\u0006\u0006\u001a\u0004\bP\u0010Qø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006RÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/LazyClassContext;", "", "additionalClassPartsProvider", "Lorg/jetbrains/kotlin/resolve/AdditionalClassPartsProvider;", "getAdditionalClassPartsProvider", "()Lorg/jetbrains/kotlin/resolve/AdditionalClassPartsProvider;", "annotationResolver", "Lorg/jetbrains/kotlin/resolve/AnnotationResolver;", "getAnnotationResolver", "()Lorg/jetbrains/kotlin/resolve/AnnotationResolver;", "declarationProviderFactory", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/DeclarationProviderFactory;", "getDeclarationProviderFactory", "()Lorg/jetbrains/kotlin/resolve/lazy/declarations/DeclarationProviderFactory;", "declarationScopeProvider", "Lorg/jetbrains/kotlin/resolve/lazy/DeclarationScopeProvider;", "getDeclarationScopeProvider", "()Lorg/jetbrains/kotlin/resolve/lazy/DeclarationScopeProvider;", "delegationFilter", "Lorg/jetbrains/kotlin/resolve/lazy/DelegationFilter;", "getDelegationFilter", "()Lorg/jetbrains/kotlin/resolve/lazy/DelegationFilter;", "descriptorResolver", "Lorg/jetbrains/kotlin/resolve/DescriptorResolver;", "getDescriptorResolver", "()Lorg/jetbrains/kotlin/resolve/DescriptorResolver;", "functionDescriptorResolver", "Lorg/jetbrains/kotlin/resolve/FunctionDescriptorResolver;", "getFunctionDescriptorResolver", "()Lorg/jetbrains/kotlin/resolve/FunctionDescriptorResolver;", "inferenceSession", "Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "getInferenceSession", "()Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "kotlinTypeCheckerOfOwnerModule", "Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;", "getKotlinTypeCheckerOfOwnerModule", "()Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "getLookupTracker", "()Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "samConversionResolver", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "getSamConversionResolver", "()Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "sealedClassInheritorsProvider", "Lorg/jetbrains/kotlin/resolve/SealedClassInheritorsProvider;", "getSealedClassInheritorsProvider", "()Lorg/jetbrains/kotlin/resolve/SealedClassInheritorsProvider;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "getStorageManager", "()Lorg/jetbrains/kotlin/storage/StorageManager;", "supertypeLoopChecker", "Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;", "getSupertypeLoopChecker", "()Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;", "syntheticResolveExtension", "Lorg/jetbrains/kotlin/resolve/extensions/SyntheticResolveExtension;", "getSyntheticResolveExtension", "()Lorg/jetbrains/kotlin/resolve/extensions/SyntheticResolveExtension;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "getTrace", "()Lorg/jetbrains/kotlin/resolve/BindingTrace;", "typeResolver", "Lorg/jetbrains/kotlin/resolve/TypeResolver;", "getTypeResolver", "()Lorg/jetbrains/kotlin/resolve/TypeResolver;", "wrappedTypeFactory", "Lorg/jetbrains/kotlin/types/WrappedTypeFactory;", "getWrappedTypeFactory", "()Lorg/jetbrains/kotlin/types/WrappedTypeFactory;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface LazyClassContext {
    AdditionalClassPartsProvider getAdditionalClassPartsProvider();

    AnnotationResolver getAnnotationResolver();

    DeclarationProviderFactory getDeclarationProviderFactory();

    DeclarationScopeProvider getDeclarationScopeProvider();

    DelegationFilter getDelegationFilter();

    DescriptorResolver getDescriptorResolver();

    FunctionDescriptorResolver getFunctionDescriptorResolver();

    InferenceSession getInferenceSession();

    NewKotlinTypeChecker getKotlinTypeCheckerOfOwnerModule();

    LanguageVersionSettings getLanguageVersionSettings();

    LookupTracker getLookupTracker();

    ModuleDescriptor getModuleDescriptor();

    SamConversionResolver getSamConversionResolver();

    SealedClassInheritorsProvider getSealedClassInheritorsProvider();

    StorageManager getStorageManager();

    SupertypeLoopChecker getSupertypeLoopChecker();

    SyntheticResolveExtension getSyntheticResolveExtension();

    BindingTrace getTrace();

    TypeResolver getTypeResolver();

    WrappedTypeFactory getWrappedTypeFactory();
}
