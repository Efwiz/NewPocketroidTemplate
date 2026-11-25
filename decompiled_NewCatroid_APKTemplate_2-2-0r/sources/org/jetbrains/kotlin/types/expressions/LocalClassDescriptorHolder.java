package org.jetbrains.kotlin.types.expressions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.SupertypeLoopChecker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.AdditionalClassPartsProvider;
import org.jetbrains.kotlin.resolve.AnnotationResolver;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorResolver;
import org.jetbrains.kotlin.resolve.FunctionDescriptorResolver;
import org.jetbrains.kotlin.resolve.SealedClassInheritorsProvider;
import org.jetbrains.kotlin.resolve.TypeResolver;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension;
import org.jetbrains.kotlin.resolve.lazy.DeclarationScopeProvider;
import org.jetbrains.kotlin.resolve.lazy.DelegationFilter;
import org.jetbrains.kotlin.resolve.lazy.LazyClassContext;
import org.jetbrains.kotlin.resolve.lazy.data.KtClassInfoUtil;
import org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo;
import org.jetbrains.kotlin.resolve.lazy.declarations.ClassMemberDeclarationProvider;
import org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactory;
import org.jetbrains.kotlin.resolve.lazy.declarations.PackageMemberDeclarationProvider;
import org.jetbrains.kotlin.resolve.lazy.declarations.PsiBasedClassMemberDeclarationProvider;
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolver;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalWritableScope;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.WrappedTypeFactory;
import org.jetbrains.kotlin.types.checker.NewKotlinTypeChecker;

/* JADX INFO: compiled from: LocalClassifierAnalyzer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u009f\u0001\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\u0006\u0010\u0016\u001a\u00020\u0017\u0012\u0006\u0010\u0018\u001a\u00020\u0019\u0012\u0006\u0010\u001a\u001a\u00020\u001b\u0012\u0006\u0010\u001c\u001a\u00020\u001d\u0012\u0006\u0010\u001e\u001a\u00020\u001f\u0012\u0006\u0010 \u001a\u00020!\u0012\u0006\u0010\"\u001a\u00020#\u0012\u0006\u0010$\u001a\u00020%\u0012\u0006\u0010&\u001a\u00020'¢\u0006\u0002\u0010(J\u0016\u0010Q\u001a\u00020.2\u0006\u0010R\u001a\u00020\u00052\u0006\u0010S\u001a\u00020TJ\u000e\u0010U\u001a\u00020V2\u0006\u0010R\u001a\u00020\u0005J\u000e\u0010W\u001a\u00020X2\u0006\u0010Y\u001a\u00020ZJ\u000e\u0010[\u001a\u00020X2\u0006\u0010Y\u001a\u00020ZR\u0011\u0010$\u001a\u00020%¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0011\u0010\u0014\u001a\u00020\u0015¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0010\u0010-\u001a\u0004\u0018\u00010.X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b/\u00100R\u0011\u0010\u001c\u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\b1\u00102R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b3\u00104R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b5\u00106R\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b7\u00108R\u0011\u0010 \u001a\u00020!¢\u0006\b\n\u0000\u001a\u0004\b9\u0010:R\u0011\u0010\u0018\u001a\u00020\u0019¢\u0006\b\n\u0000\u001a\u0004\b;\u0010<R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b=\u0010>R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b?\u0010@R\u0011\u0010\"\u001a\u00020#¢\u0006\b\n\u0000\u001a\u0004\bA\u0010BR\u0011\u0010&\u001a\u00020'¢\u0006\b\n\u0000\u001a\u0004\bC\u0010DR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bE\u0010FR\u0011\u0010\u0016\u001a\u00020\u0017¢\u0006\b\n\u0000\u001a\u0004\bG\u0010HR\u0011\u0010\u001a\u001a\u00020\u001b¢\u0006\b\n\u0000\u001a\u0004\bI\u0010JR\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\bK\u0010LR\u0011\u0010\u001e\u001a\u00020\u001f¢\u0006\b\n\u0000\u001a\u0004\bM\u0010NR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\bO\u0010P¨\u0006\\"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/LocalClassDescriptorHolder;", "", "writableScope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalWritableScope;", "myClass", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "expressionTypingContext", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "descriptorResolver", "Lorg/jetbrains/kotlin/resolve/DescriptorResolver;", "functionDescriptorResolver", "Lorg/jetbrains/kotlin/resolve/FunctionDescriptorResolver;", "typeResolver", "Lorg/jetbrains/kotlin/resolve/TypeResolver;", "annotationResolver", "Lorg/jetbrains/kotlin/resolve/AnnotationResolver;", "supertypeLoopChecker", "Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "syntheticResolveExtension", "Lorg/jetbrains/kotlin/resolve/extensions/SyntheticResolveExtension;", "delegationFilter", "Lorg/jetbrains/kotlin/resolve/lazy/DelegationFilter;", "wrappedTypeFactory", "Lorg/jetbrains/kotlin/types/WrappedTypeFactory;", "kotlinTypeChecker", "Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;", "samConversionResolver", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "additionalClassPartsProvider", "Lorg/jetbrains/kotlin/resolve/AdditionalClassPartsProvider;", "sealedClassInheritorsProvider", "Lorg/jetbrains/kotlin/resolve/SealedClassInheritorsProvider;", "(Lorg/jetbrains/kotlin/resolve/scopes/LexicalWritableScope;Lorg/jetbrains/kotlin/psi/KtClassOrObject;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/DescriptorResolver;Lorg/jetbrains/kotlin/resolve/FunctionDescriptorResolver;Lorg/jetbrains/kotlin/resolve/TypeResolver;Lorg/jetbrains/kotlin/resolve/AnnotationResolver;Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/extensions/SyntheticResolveExtension;Lorg/jetbrains/kotlin/resolve/lazy/DelegationFilter;Lorg/jetbrains/kotlin/types/WrappedTypeFactory;Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;Lorg/jetbrains/kotlin/resolve/AdditionalClassPartsProvider;Lorg/jetbrains/kotlin/resolve/SealedClassInheritorsProvider;)V", "getAdditionalClassPartsProvider", "()Lorg/jetbrains/kotlin/resolve/AdditionalClassPartsProvider;", "getAnnotationResolver", "()Lorg/jetbrains/kotlin/resolve/AnnotationResolver;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getContainingDeclaration", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getDelegationFilter", "()Lorg/jetbrains/kotlin/resolve/lazy/DelegationFilter;", "getDescriptorResolver", "()Lorg/jetbrains/kotlin/resolve/DescriptorResolver;", "getExpressionTypingContext", "()Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "getFunctionDescriptorResolver", "()Lorg/jetbrains/kotlin/resolve/FunctionDescriptorResolver;", "getKotlinTypeChecker", "()Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getMyClass", "()Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "getSamConversionResolver", "()Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "getSealedClassInheritorsProvider", "()Lorg/jetbrains/kotlin/resolve/SealedClassInheritorsProvider;", "getStorageManager", "()Lorg/jetbrains/kotlin/storage/StorageManager;", "getSupertypeLoopChecker", "()Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;", "getSyntheticResolveExtension", "()Lorg/jetbrains/kotlin/resolve/extensions/SyntheticResolveExtension;", "getTypeResolver", "()Lorg/jetbrains/kotlin/resolve/TypeResolver;", "getWrappedTypeFactory", "()Lorg/jetbrains/kotlin/types/WrappedTypeFactory;", "getWritableScope", "()Lorg/jetbrains/kotlin/resolve/scopes/LexicalWritableScope;", "getClassDescriptor", "classOrObject", "declarationScopeProvider", "Lorg/jetbrains/kotlin/resolve/lazy/DeclarationScopeProvider;", "getResolutionScopeForClass", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "insideMyClass", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "isMyClass", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LocalClassDescriptorHolder {
    private final AdditionalClassPartsProvider additionalClassPartsProvider;
    private final AnnotationResolver annotationResolver;
    private ClassDescriptor classDescriptor;
    private final DeclarationDescriptor containingDeclaration;
    private final DelegationFilter delegationFilter;
    private final DescriptorResolver descriptorResolver;
    private final ExpressionTypingContext expressionTypingContext;
    private final FunctionDescriptorResolver functionDescriptorResolver;
    private final NewKotlinTypeChecker kotlinTypeChecker;
    private final LanguageVersionSettings languageVersionSettings;
    private final ModuleDescriptor moduleDescriptor;
    private final KtClassOrObject myClass;
    private final SamConversionResolver samConversionResolver;
    private final SealedClassInheritorsProvider sealedClassInheritorsProvider;
    private final StorageManager storageManager;
    private final SupertypeLoopChecker supertypeLoopChecker;
    private final SyntheticResolveExtension syntheticResolveExtension;
    private final TypeResolver typeResolver;
    private final WrappedTypeFactory wrappedTypeFactory;
    private final LexicalWritableScope writableScope;

    public LocalClassDescriptorHolder(LexicalWritableScope lexicalWritableScope, KtClassOrObject myClass, DeclarationDescriptor containingDeclaration, StorageManager storageManager, ExpressionTypingContext expressionTypingContext, ModuleDescriptor moduleDescriptor, DescriptorResolver descriptorResolver, FunctionDescriptorResolver functionDescriptorResolver, TypeResolver typeResolver, AnnotationResolver annotationResolver, SupertypeLoopChecker supertypeLoopChecker, LanguageVersionSettings languageVersionSettings, SyntheticResolveExtension syntheticResolveExtension, DelegationFilter delegationFilter, WrappedTypeFactory wrappedTypeFactory, NewKotlinTypeChecker kotlinTypeChecker, SamConversionResolver samConversionResolver, AdditionalClassPartsProvider additionalClassPartsProvider, SealedClassInheritorsProvider sealedClassInheritorsProvider) {
        Intrinsics.checkNotNullParameter(myClass, "myClass");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(expressionTypingContext, "expressionTypingContext");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(descriptorResolver, "descriptorResolver");
        Intrinsics.checkNotNullParameter(functionDescriptorResolver, "functionDescriptorResolver");
        Intrinsics.checkNotNullParameter(typeResolver, "typeResolver");
        Intrinsics.checkNotNullParameter(annotationResolver, "annotationResolver");
        Intrinsics.checkNotNullParameter(supertypeLoopChecker, "supertypeLoopChecker");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(syntheticResolveExtension, "syntheticResolveExtension");
        Intrinsics.checkNotNullParameter(delegationFilter, "delegationFilter");
        Intrinsics.checkNotNullParameter(wrappedTypeFactory, "wrappedTypeFactory");
        Intrinsics.checkNotNullParameter(kotlinTypeChecker, "kotlinTypeChecker");
        Intrinsics.checkNotNullParameter(samConversionResolver, "samConversionResolver");
        Intrinsics.checkNotNullParameter(additionalClassPartsProvider, "additionalClassPartsProvider");
        Intrinsics.checkNotNullParameter(sealedClassInheritorsProvider, "sealedClassInheritorsProvider");
        this.writableScope = lexicalWritableScope;
        this.myClass = myClass;
        this.containingDeclaration = containingDeclaration;
        this.storageManager = storageManager;
        this.expressionTypingContext = expressionTypingContext;
        this.moduleDescriptor = moduleDescriptor;
        this.descriptorResolver = descriptorResolver;
        this.functionDescriptorResolver = functionDescriptorResolver;
        this.typeResolver = typeResolver;
        this.annotationResolver = annotationResolver;
        this.supertypeLoopChecker = supertypeLoopChecker;
        this.languageVersionSettings = languageVersionSettings;
        this.syntheticResolveExtension = syntheticResolveExtension;
        this.delegationFilter = delegationFilter;
        this.wrappedTypeFactory = wrappedTypeFactory;
        this.kotlinTypeChecker = kotlinTypeChecker;
        this.samConversionResolver = samConversionResolver;
        this.additionalClassPartsProvider = additionalClassPartsProvider;
        this.sealedClassInheritorsProvider = sealedClassInheritorsProvider;
    }

    public final LexicalWritableScope getWritableScope() {
        return this.writableScope;
    }

    public final KtClassOrObject getMyClass() {
        return this.myClass;
    }

    public final DeclarationDescriptor getContainingDeclaration() {
        return this.containingDeclaration;
    }

    public final StorageManager getStorageManager() {
        return this.storageManager;
    }

    public final ExpressionTypingContext getExpressionTypingContext() {
        return this.expressionTypingContext;
    }

    public final ModuleDescriptor getModuleDescriptor() {
        return this.moduleDescriptor;
    }

    public final DescriptorResolver getDescriptorResolver() {
        return this.descriptorResolver;
    }

    public final FunctionDescriptorResolver getFunctionDescriptorResolver() {
        return this.functionDescriptorResolver;
    }

    public final TypeResolver getTypeResolver() {
        return this.typeResolver;
    }

    public final AnnotationResolver getAnnotationResolver() {
        return this.annotationResolver;
    }

    public final SupertypeLoopChecker getSupertypeLoopChecker() {
        return this.supertypeLoopChecker;
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public final SyntheticResolveExtension getSyntheticResolveExtension() {
        return this.syntheticResolveExtension;
    }

    public final DelegationFilter getDelegationFilter() {
        return this.delegationFilter;
    }

    public final WrappedTypeFactory getWrappedTypeFactory() {
        return this.wrappedTypeFactory;
    }

    public final NewKotlinTypeChecker getKotlinTypeChecker() {
        return this.kotlinTypeChecker;
    }

    public final SamConversionResolver getSamConversionResolver() {
        return this.samConversionResolver;
    }

    public final AdditionalClassPartsProvider getAdditionalClassPartsProvider() {
        return this.additionalClassPartsProvider;
    }

    public final SealedClassInheritorsProvider getSealedClassInheritorsProvider() {
        return this.sealedClassInheritorsProvider;
    }

    public final boolean isMyClass(PsiElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        return Intrinsics.areEqual(element, this.myClass);
    }

    public final boolean insideMyClass(PsiElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        return PsiTreeUtil.isAncestor(this.myClass, element, false);
    }

    public final ClassDescriptor getClassDescriptor(KtClassOrObject classOrObject, DeclarationScopeProvider declarationScopeProvider) {
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        Intrinsics.checkNotNullParameter(declarationScopeProvider, "declarationScopeProvider");
        isMyClass(classOrObject);
        if (this.classDescriptor == null) {
            LazyClassDescriptor lazyClassDescriptor = new LazyClassDescriptor(new AnonymousClass2(declarationScopeProvider, this), this.containingDeclaration, classOrObject.getNameAsSafeName(), KtClassInfoUtil.createClassOrObjectInfo(classOrObject), classOrObject.hasModifier(KtTokens.EXTERNAL_KEYWORD));
            this.classDescriptor = lazyClassDescriptor;
            LexicalWritableScope lexicalWritableScope = this.writableScope;
            if (lexicalWritableScope != null) {
                Intrinsics.checkNotNull(lazyClassDescriptor);
                lexicalWritableScope.addClassifierDescriptor(lazyClassDescriptor);
            }
        }
        ClassDescriptor classDescriptor = this.classDescriptor;
        Intrinsics.checkNotNull(classDescriptor);
        return classDescriptor;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.types.expressions.LocalClassDescriptorHolder$getClassDescriptor$2, reason: invalid class name */
    /* JADX INFO: compiled from: LocalClassifierAnalyzer.kt */
    @Metadata(d1 = {"\u0000¨\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0002\u0000\u000b\b\n\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0016\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000f\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u0018X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001b\u001a\u00020\u001cX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010\u001f\u001a\u00020 X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0014\u0010#\u001a\u00020$X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0014\u0010'\u001a\u00020(X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0014\u0010+\u001a\u00020,X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u0014\u0010/\u001a\u000200X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b1\u00102R\u0014\u00103\u001a\u000204X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b5\u00106R\u0014\u00107\u001a\u000208X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b9\u0010:R\u0014\u0010;\u001a\u00020<X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b=\u0010>R\u0014\u0010?\u001a\u00020@X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bA\u0010BR\u0014\u0010C\u001a\u00020DX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bE\u0010FR\u0014\u0010G\u001a\u00020HX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bI\u0010JR\u0014\u0010K\u001a\u00020LX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bM\u0010NR\u0014\u0010O\u001a\u00020PX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bQ\u0010R¨\u0006S"}, d2 = {"org/jetbrains/kotlin/types/expressions/LocalClassDescriptorHolder$getClassDescriptor$2", "Lorg/jetbrains/kotlin/resolve/lazy/LazyClassContext;", "additionalClassPartsProvider", "Lorg/jetbrains/kotlin/resolve/AdditionalClassPartsProvider;", "getAdditionalClassPartsProvider", "()Lorg/jetbrains/kotlin/resolve/AdditionalClassPartsProvider;", "annotationResolver", "Lorg/jetbrains/kotlin/resolve/AnnotationResolver;", "getAnnotationResolver", "()Lorg/jetbrains/kotlin/resolve/AnnotationResolver;", "declarationProviderFactory", "org/jetbrains/kotlin/types/expressions/LocalClassDescriptorHolder$getClassDescriptor$2$declarationProviderFactory$1", "getDeclarationProviderFactory", "()Lorg/jetbrains/kotlin/types/expressions/LocalClassDescriptorHolder$getClassDescriptor$2$declarationProviderFactory$1;", "Lorg/jetbrains/kotlin/types/expressions/LocalClassDescriptorHolder$getClassDescriptor$2$declarationProviderFactory$1;", "declarationScopeProvider", "Lorg/jetbrains/kotlin/resolve/lazy/DeclarationScopeProvider;", "getDeclarationScopeProvider", "()Lorg/jetbrains/kotlin/resolve/lazy/DeclarationScopeProvider;", "delegationFilter", "Lorg/jetbrains/kotlin/resolve/lazy/DelegationFilter;", "getDelegationFilter", "()Lorg/jetbrains/kotlin/resolve/lazy/DelegationFilter;", "descriptorResolver", "Lorg/jetbrains/kotlin/resolve/DescriptorResolver;", "getDescriptorResolver", "()Lorg/jetbrains/kotlin/resolve/DescriptorResolver;", "functionDescriptorResolver", "Lorg/jetbrains/kotlin/resolve/FunctionDescriptorResolver;", "getFunctionDescriptorResolver", "()Lorg/jetbrains/kotlin/resolve/FunctionDescriptorResolver;", "inferenceSession", "Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "getInferenceSession", "()Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "kotlinTypeCheckerOfOwnerModule", "Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;", "getKotlinTypeCheckerOfOwnerModule", "()Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "getLookupTracker", "()Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "samConversionResolver", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "getSamConversionResolver", "()Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "sealedClassInheritorsProvider", "Lorg/jetbrains/kotlin/resolve/SealedClassInheritorsProvider;", "getSealedClassInheritorsProvider", "()Lorg/jetbrains/kotlin/resolve/SealedClassInheritorsProvider;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "getStorageManager", "()Lorg/jetbrains/kotlin/storage/StorageManager;", "supertypeLoopChecker", "Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;", "getSupertypeLoopChecker", "()Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;", "syntheticResolveExtension", "Lorg/jetbrains/kotlin/resolve/extensions/SyntheticResolveExtension;", "getSyntheticResolveExtension", "()Lorg/jetbrains/kotlin/resolve/extensions/SyntheticResolveExtension;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "getTrace", "()Lorg/jetbrains/kotlin/resolve/BindingTrace;", "typeResolver", "Lorg/jetbrains/kotlin/resolve/TypeResolver;", "getTypeResolver", "()Lorg/jetbrains/kotlin/resolve/TypeResolver;", "wrappedTypeFactory", "Lorg/jetbrains/kotlin/types/WrappedTypeFactory;", "getWrappedTypeFactory", "()Lorg/jetbrains/kotlin/types/WrappedTypeFactory;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AnonymousClass2 implements LazyClassContext {
        private final AdditionalClassPartsProvider additionalClassPartsProvider;
        private final AnnotationResolver annotationResolver;
        private final LocalClassDescriptorHolder$getClassDescriptor$2$declarationProviderFactory$1 declarationProviderFactory;
        private final DeclarationScopeProvider declarationScopeProvider;
        private final DelegationFilter delegationFilter;
        private final DescriptorResolver descriptorResolver;
        private final FunctionDescriptorResolver functionDescriptorResolver;
        private final InferenceSession inferenceSession;
        private final NewKotlinTypeChecker kotlinTypeCheckerOfOwnerModule;
        private final LanguageVersionSettings languageVersionSettings;
        private final LookupTracker lookupTracker;
        private final ModuleDescriptor moduleDescriptor;
        private final SamConversionResolver samConversionResolver;
        private final SealedClassInheritorsProvider sealedClassInheritorsProvider;
        private final StorageManager storageManager;
        private final SupertypeLoopChecker supertypeLoopChecker;
        private final SyntheticResolveExtension syntheticResolveExtension;
        private final BindingTrace trace;
        private final TypeResolver typeResolver;
        private final WrappedTypeFactory wrappedTypeFactory;

        /* JADX WARN: Type inference failed for: r2v10, types: [org.jetbrains.kotlin.types.expressions.LocalClassDescriptorHolder$getClassDescriptor$2$declarationProviderFactory$1] */
        AnonymousClass2(DeclarationScopeProvider declarationScopeProvider, LocalClassDescriptorHolder localClassDescriptorHolder) {
            this.declarationScopeProvider = declarationScopeProvider;
            InferenceSession inferenceSession = localClassDescriptorHolder.getExpressionTypingContext().inferenceSession;
            Intrinsics.checkNotNullExpressionValue(inferenceSession, "inferenceSession");
            this.inferenceSession = inferenceSession;
            this.storageManager = localClassDescriptorHolder.getStorageManager();
            BindingTrace trace = localClassDescriptorHolder.getExpressionTypingContext().trace;
            Intrinsics.checkNotNullExpressionValue(trace, "trace");
            this.trace = trace;
            this.moduleDescriptor = localClassDescriptorHolder.getModuleDescriptor();
            this.descriptorResolver = localClassDescriptorHolder.getDescriptorResolver();
            this.functionDescriptorResolver = localClassDescriptorHolder.getFunctionDescriptorResolver();
            this.typeResolver = localClassDescriptorHolder.getTypeResolver();
            this.declarationProviderFactory = new DeclarationProviderFactory() { // from class: org.jetbrains.kotlin.types.expressions.LocalClassDescriptorHolder$getClassDescriptor$2$declarationProviderFactory$1
                @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactory
                public ClassMemberDeclarationProvider getClassMemberDeclarationProvider(KtClassLikeInfo classLikeInfo) {
                    Intrinsics.checkNotNullParameter(classLikeInfo, "classLikeInfo");
                    return new PsiBasedClassMemberDeclarationProvider(this.this$0.getStorageManager(), classLikeInfo);
                }

                @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactory
                public PackageMemberDeclarationProvider getPackageMemberDeclarationProvider(FqName packageFqName) {
                    Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
                    throw new UnsupportedOperationException("Should not be called for top-level declarations");
                }

                @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactory
                public void diagnoseMissingPackageFragment(FqName fqName, KtFile file) {
                    Intrinsics.checkNotNullParameter(fqName, "fqName");
                    throw new UnsupportedOperationException();
                }
            };
            this.annotationResolver = localClassDescriptorHolder.getAnnotationResolver();
            this.lookupTracker = LookupTracker.DO_NOTHING.INSTANCE;
            this.supertypeLoopChecker = localClassDescriptorHolder.getSupertypeLoopChecker();
            this.languageVersionSettings = localClassDescriptorHolder.getLanguageVersionSettings();
            this.syntheticResolveExtension = localClassDescriptorHolder.getSyntheticResolveExtension();
            this.delegationFilter = localClassDescriptorHolder.getDelegationFilter();
            this.wrappedTypeFactory = localClassDescriptorHolder.getWrappedTypeFactory();
            this.kotlinTypeCheckerOfOwnerModule = localClassDescriptorHolder.getKotlinTypeChecker();
            this.samConversionResolver = localClassDescriptorHolder.getSamConversionResolver();
            this.additionalClassPartsProvider = localClassDescriptorHolder.getAdditionalClassPartsProvider();
            this.sealedClassInheritorsProvider = localClassDescriptorHolder.getSealedClassInheritorsProvider();
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
        public DeclarationScopeProvider getDeclarationScopeProvider() {
            return this.declarationScopeProvider;
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
        public InferenceSession getInferenceSession() {
            return this.inferenceSession;
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
        public StorageManager getStorageManager() {
            return this.storageManager;
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
        public BindingTrace getTrace() {
            return this.trace;
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
        public ModuleDescriptor getModuleDescriptor() {
            return this.moduleDescriptor;
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
        public DescriptorResolver getDescriptorResolver() {
            return this.descriptorResolver;
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
        public FunctionDescriptorResolver getFunctionDescriptorResolver() {
            return this.functionDescriptorResolver;
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
        public TypeResolver getTypeResolver() {
            return this.typeResolver;
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
        public LocalClassDescriptorHolder$getClassDescriptor$2$declarationProviderFactory$1 getDeclarationProviderFactory() {
            return this.declarationProviderFactory;
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
        public AnnotationResolver getAnnotationResolver() {
            return this.annotationResolver;
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
        public LookupTracker getLookupTracker() {
            return this.lookupTracker;
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
        public SupertypeLoopChecker getSupertypeLoopChecker() {
            return this.supertypeLoopChecker;
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
        public LanguageVersionSettings getLanguageVersionSettings() {
            return this.languageVersionSettings;
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
        public SyntheticResolveExtension getSyntheticResolveExtension() {
            return this.syntheticResolveExtension;
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
        public DelegationFilter getDelegationFilter() {
            return this.delegationFilter;
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
        public WrappedTypeFactory getWrappedTypeFactory() {
            return this.wrappedTypeFactory;
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
        public NewKotlinTypeChecker getKotlinTypeCheckerOfOwnerModule() {
            return this.kotlinTypeCheckerOfOwnerModule;
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
        public SamConversionResolver getSamConversionResolver() {
            return this.samConversionResolver;
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
        public AdditionalClassPartsProvider getAdditionalClassPartsProvider() {
            return this.additionalClassPartsProvider;
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
        public SealedClassInheritorsProvider getSealedClassInheritorsProvider() {
            return this.sealedClassInheritorsProvider;
        }
    }

    public final LexicalScope getResolutionScopeForClass(KtClassOrObject classOrObject) {
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        isMyClass(classOrObject);
        LexicalScope scope = this.expressionTypingContext.scope;
        Intrinsics.checkNotNullExpressionValue(scope, "scope");
        return scope;
    }
}
