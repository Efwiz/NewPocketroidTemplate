package org.jetbrains.kotlin.resolve.lazy.descriptors;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.google.common.collect.HashMultimap;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ScopesHolderForClass;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.SupertypeLoopChecker;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterUtilsKt;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentationKt;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.ClassDescriptorBase;
import org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.NameUtils;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtContextReceiver;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtObjectLiteralExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.KtTypeParameterList;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi.synthetics.SyntheticClassOrObjectDescriptor;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorFactory;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.ModifiersChecker;
import org.jetbrains.kotlin.resolve.lazy.ForceResolveUtil;
import org.jetbrains.kotlin.resolve.lazy.LazyClassContext;
import org.jetbrains.kotlin.resolve.lazy.LazyEntity;
import org.jetbrains.kotlin.resolve.lazy.data.KtClassInfoUtil;
import org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo;
import org.jetbrains.kotlin.resolve.lazy.data.KtClassOrObjectInfo;
import org.jetbrains.kotlin.resolve.lazy.data.KtObjectInfo;
import org.jetbrains.kotlin.resolve.lazy.declarations.ClassMemberDeclarationProvider;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.StaticScopeForKotlinEnum;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElementKt;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.NullableLazyValue;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.AbstractClassTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.checker.SimpleClassicTypeSystemContext;

/* JADX INFO: loaded from: classes6.dex */
public class LazyClassDescriptor extends ClassDescriptorBase implements ClassDescriptorWithResolutionScopes, LazyEntity {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Function1<KotlinType, Boolean> VALID_SUPERTYPE = new Function1() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return Boolean.valueOf(TypeUtils.getClassDescriptor((KotlinType) obj) != null);
        }
    };
    private final Annotations annotations;
    private final LazyClassContext c;
    private final KtClassOrObject classOrObject;
    private final NullableLazyValue<ClassDescriptorWithResolutionScopes> companionObjectDescriptor;
    private final NotNullLazyValue<List<ReceiverParameterDescriptor>> contextReceivers;
    private final Annotations danglingAnnotations;
    private final ClassMemberDeclarationProvider declarationProvider;
    private final MemoizedFunctionToNotNull<KtObjectDeclaration, ClassDescriptor> extraCompanionObjectDescriptors;
    private final NullableLazyValue<Void> forceResolveAllContents;
    private final boolean isActual;
    private final boolean isCompanionObject;
    private final boolean isData;
    private final boolean isExpect;
    private final boolean isFun;
    private final boolean isInline;
    private final boolean isInner;
    private final boolean isValue;
    private final ClassKind kind;
    private final NotNullLazyValue<Modality> modality;
    private final NotNullLazyValue<List<TypeParameterDescriptor>> parameters;
    private final ClassResolutionScopesSupport resolutionScopesSupport;
    private final NotNullLazyValue<LexicalScope> scopeForInitializerResolution;
    private final ScopesHolderForClass<LazyClassMemberScope> scopesHolderForClass;
    private final NotNullLazyValue<Collection<ClassDescriptor>> sealedSubclasses;
    private final MemberScope staticScope;
    private final LazyClassTypeConstructor typeConstructor;
    private final DescriptorVisibility visibility;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 5:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 31:
            case 32:
            case 33:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 6:
            case 7:
            case 9:
            case 30:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 5:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 31:
            case 32:
            case 33:
                i2 = 2;
                break;
            case 6:
            case 7:
            case 9:
            case 30:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "containingDeclaration";
                break;
            case 2:
                objArr[0] = "name";
                break;
            case 3:
                objArr[0] = "classLikeInfo";
                break;
            case 4:
                objArr[0] = "descriptor";
                break;
            case 5:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 31:
            case 32:
            case 33:
                objArr[0] = "org/jetbrains/kotlin/resolve/lazy/descriptors/LazyClassDescriptor";
                break;
            case 6:
            default:
                objArr[0] = "c";
                break;
            case 7:
                objArr[0] = "declarationProvider";
                break;
            case 9:
                objArr[0] = "kotlinTypeRefiner";
                break;
            case 30:
                objArr[0] = "visited";
                break;
        }
        switch (i) {
            case 5:
                objArr[1] = "createInitializerScopeParent";
                break;
            case 6:
            case 7:
            case 9:
            case 30:
            default:
                objArr[1] = "org/jetbrains/kotlin/resolve/lazy/descriptors/LazyClassDescriptor";
                break;
            case 8:
                objArr[1] = "createScopesHolderForClass";
                break;
            case 10:
                objArr[1] = "getUnsubstitutedMemberScope";
                break;
            case 11:
                objArr[1] = "getOuterScope";
                break;
            case 12:
                objArr[1] = "getScopeForClassHeaderResolution";
                break;
            case 13:
                objArr[1] = "getScopeForConstructorHeaderResolution";
                break;
            case 14:
                objArr[1] = "getScopeForCompanionObjectHeaderResolution";
                break;
            case 15:
                objArr[1] = "getScopeForMemberDeclarationResolution";
                break;
            case 16:
                objArr[1] = "getScopeForStaticMemberDeclarationResolution";
                break;
            case 17:
                objArr[1] = "getScopeForInitializerResolution";
                break;
            case 18:
                objArr[1] = "getDeclaredCallableMembers";
                break;
            case 19:
                objArr[1] = "getStaticScope";
                break;
            case 20:
                objArr[1] = "getConstructors";
                break;
            case 21:
                objArr[1] = "getContextReceivers";
                break;
            case 22:
                objArr[1] = "getTypeConstructor";
                break;
            case 23:
                objArr[1] = "getDescriptorsForExtraCompanionObjects";
                break;
            case 24:
                objArr[1] = "getKind";
                break;
            case 25:
                objArr[1] = "getModality";
                break;
            case 26:
                objArr[1] = "getVisibility";
                break;
            case 27:
                objArr[1] = "getAnnotations";
                break;
            case 28:
                objArr[1] = "getDanglingAnnotations";
                break;
            case 29:
                objArr[1] = "getSealedSubclasses";
                break;
            case 31:
                objArr[1] = "getDeclaredTypeParameters";
                break;
            case 32:
            case 33:
                objArr[1] = "computeSupertypes";
                break;
        }
        switch (i) {
            case 4:
                objArr[2] = "isIllegalInner";
                break;
            case 5:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 31:
            case 32:
            case 33:
                break;
            case 6:
            case 7:
                objArr[2] = "createScopesHolderForClass";
                break;
            case 9:
                objArr[2] = "getUnsubstitutedMemberScope";
                break;
            case 30:
                objArr[2] = "isRecursiveInlineClass";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 5:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 31:
            case 32:
            case 33:
                throw new IllegalStateException(str2);
            case 6:
            case 7:
            case 9:
            case 30:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyClassDescriptor(final LazyClassContext lazyClassContext, DeclarationDescriptor declarationDescriptor, Name name2, final KtClassLikeInfo ktClassLikeInfo, boolean z) {
        super(lazyClassContext.getStorageManager(), declarationDescriptor, name2, KotlinSourceElementKt.toSourceElement(ktClassLikeInfo.mo7451getCorrespondingClassOrObject()), z);
        boolean z2 = false;
        if (lazyClassContext == null) {
            $$$reportNull$$$0(0);
        }
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        if (name2 == null) {
            $$$reportNull$$$0(2);
        }
        if (ktClassLikeInfo == null) {
            $$$reportNull$$$0(3);
        }
        this.c = lazyClassContext;
        KtClassOrObject ktClassOrObjectMo7451getCorrespondingClassOrObject = ktClassLikeInfo.mo7451getCorrespondingClassOrObject();
        this.classOrObject = ktClassOrObjectMo7451getCorrespondingClassOrObject;
        ClassMemberDeclarationProvider classMemberDeclarationProvider = lazyClassContext.getDeclarationProviderFactory().getClassMemberDeclarationProvider(ktClassLikeInfo);
        this.declarationProvider = classMemberDeclarationProvider;
        final StorageManager storageManager = lazyClassContext.getStorageManager();
        this.scopesHolderForClass = createScopesHolderForClass(lazyClassContext, classMemberDeclarationProvider);
        ClassKind classKind = ktClassLikeInfo.getClassKind();
        this.kind = classKind;
        this.staticScope = classKind == ClassKind.ENUM_CLASS ? new StaticScopeForKotlinEnum(storageManager, this, true) : MemberScope.Empty.INSTANCE;
        this.typeConstructor = new LazyClassTypeConstructor();
        this.isCompanionObject = (ktClassLikeInfo instanceof KtObjectInfo) && ((KtObjectInfo) ktClassLikeInfo).isCompanionObject();
        KtModifierList ktModifierListMo7452getModifierList = ktClassLikeInfo.mo7452getModifierList();
        if (classKind.isSingleton()) {
            this.modality = storageManager.createLazyValue(new Function0() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor$$ExternalSyntheticLambda10
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return Modality.FINAL;
                }
            });
        } else {
            final Modality modality = classKind == ClassKind.INTERFACE ? Modality.ABSTRACT : Modality.FINAL;
            this.modality = storageManager.createLazyValue(new Function0() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor$$ExternalSyntheticLambda11
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return this.f$0.m7456xd251686b(modality, lazyClassContext);
                }
            });
        }
        this.visibility = (ktClassOrObjectMo7451getCorrespondingClassOrObject == null || !KtPsiUtil.isLocal(ktClassOrObjectMo7451getCorrespondingClassOrObject)) ? ModifiersChecker.resolveVisibilityFromModifiers(ktModifierListMo7452getModifierList, DescriptorVisibilities.DEFAULT_VISIBILITY) : DescriptorVisibilities.LOCAL;
        this.isInner = (ktModifierListMo7452getModifierList == null || !ktModifierListMo7452getModifierList.hasModifier(KtTokens.INNER_KEYWORD) || isIllegalInner(this)) ? false : true;
        this.isData = ktModifierListMo7452getModifierList != null && ktModifierListMo7452getModifierList.hasModifier(KtTokens.DATA_KEYWORD);
        this.isInline = ktModifierListMo7452getModifierList != null && ktModifierListMo7452getModifierList.hasModifier(KtTokens.INLINE_KEYWORD);
        this.isActual = ktModifierListMo7452getModifierList != null && PsiUtilsKt.hasActualModifier(ktModifierListMo7452getModifierList);
        this.isExpect = (ktModifierListMo7452getModifierList != null && PsiUtilsKt.hasExpectModifier(ktModifierListMo7452getModifierList)) || ((declarationDescriptor instanceof ClassDescriptor) && ((ClassDescriptor) declarationDescriptor).get$isExpect());
        this.isFun = ktModifierListMo7452getModifierList != null && PsiUtilsKt.hasFunModifier(ktModifierListMo7452getModifierList);
        if (ktModifierListMo7452getModifierList != null && PsiUtilsKt.hasValueModifier(ktModifierListMo7452getModifierList)) {
            z2 = true;
        }
        this.isValue = z2;
        ArrayList arrayList = new ArrayList();
        if (ktClassOrObjectMo7451getCorrespondingClassOrObject != null && (ktClassOrObjectMo7451getCorrespondingClassOrObject.getMyParent() instanceof KtObjectLiteralExpression)) {
            arrayList.addAll(KtPsiUtilKt.getAnnotationEntries((KtObjectLiteralExpression) ktClassOrObjectMo7451getCorrespondingClassOrObject.getMyParent()));
        }
        if (ktModifierListMo7452getModifierList != null) {
            arrayList.addAll(ktModifierListMo7452getModifierList.getAnnotationEntries());
        }
        if (!arrayList.isEmpty()) {
            this.annotations = new LazyAnnotations(new LazyAnnotationsContext(lazyClassContext.getAnnotationResolver(), storageManager, lazyClassContext.getTrace()) { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor.1
                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/resolve/lazy/descriptors/LazyClassDescriptor$1", "getScope"));
                }

                @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.LazyAnnotationsContext
                public LexicalScope getScope() {
                    LexicalScope outerScope = LazyClassDescriptor.this.getOuterScope();
                    if (outerScope == null) {
                        $$$reportNull$$$0(0);
                    }
                    return outerScope;
                }
            }, arrayList);
        } else {
            this.annotations = Annotations.INSTANCE.getEMPTY();
        }
        List<KtAnnotationEntry> danglingAnnotations = ktClassLikeInfo.getDanglingAnnotations();
        if (danglingAnnotations.isEmpty()) {
            this.danglingAnnotations = Annotations.INSTANCE.getEMPTY();
        } else {
            this.danglingAnnotations = new LazyAnnotations(new LazyAnnotationsContext(lazyClassContext.getAnnotationResolver(), storageManager, lazyClassContext.getTrace()) { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor.2
                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/resolve/lazy/descriptors/LazyClassDescriptor$2", "getScope"));
                }

                @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.LazyAnnotationsContext
                public LexicalScope getScope() {
                    LexicalScope scopeForMemberDeclarationResolution = LazyClassDescriptor.this.getScopeForMemberDeclarationResolution();
                    if (scopeForMemberDeclarationResolution == null) {
                        $$$reportNull$$$0(0);
                    }
                    return scopeForMemberDeclarationResolution;
                }
            }, danglingAnnotations);
        }
        this.companionObjectDescriptor = storageManager.createNullableLazyValue(new Function0() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.m7457xae12e42c();
            }
        });
        this.extraCompanionObjectDescriptors = storageManager.createMemoizedFunction(new Function1() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.computeCompanionObjectDescriptor((KtObjectDeclaration) obj);
            }
        });
        this.forceResolveAllContents = storageManager.createRecursionTolerantNullableLazyValue(new Function0() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.m7458x89d45fed();
            }
        }, null);
        this.resolutionScopesSupport = new ClassResolutionScopesSupport(this, storageManager, lazyClassContext.getLanguageVersionSettings(), new Function0() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.getOuterScope();
            }
        });
        this.parameters = lazyClassContext.getStorageManager().createLazyValue(new Function0() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.m7459x6595dbae(lazyClassContext, storageManager);
            }
        });
        this.scopeForInitializerResolution = storageManager.createLazyValue(new Function0() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.m7460x4157576f(ktClassLikeInfo);
            }
        });
        final boolean zSupportsFeature = lazyClassContext.getLanguageVersionSettings().supportsFeature(LanguageFeature.AllowSealedInheritorsInDifferentFilesOfSamePackage);
        this.sealedSubclasses = storageManager.createLazyValue(new Function0() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.m7461x1d18d330(lazyClassContext, zSupportsFeature);
            }
        });
        this.contextReceivers = storageManager.createLazyValue(new Function0() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.m7462xd49bcab2(lazyClassContext);
            }
        });
        if (ktClassOrObjectMo7451getCorrespondingClassOrObject != null) {
            lazyClassContext.getTrace().record(BindingContext.CLASS, ktClassOrObjectMo7451getCorrespondingClassOrObject, this);
        }
        lazyClassContext.getTrace().record(BindingContext.FQNAME_TO_CLASS_DESCRIPTOR, DescriptorUtils.getFqName(this), this);
    }

    /* JADX INFO: renamed from: lambda$new$2$org-jetbrains-kotlin-resolve-lazy-descriptors-LazyClassDescriptor, reason: not valid java name */
    /* synthetic */ Modality m7456xd251686b(Modality modality, LazyClassContext lazyClassContext) {
        return ModifiersChecker.resolveModalityFromModifiers(this.classOrObject, modality, lazyClassContext.getTrace().getContext(), null, true);
    }

    /* JADX INFO: renamed from: lambda$new$3$org-jetbrains-kotlin-resolve-lazy-descriptors-LazyClassDescriptor, reason: not valid java name */
    /* synthetic */ ClassDescriptorWithResolutionScopes m7457xae12e42c() {
        return computeCompanionObjectDescriptor(getCompanionObjectIfAllowed());
    }

    /* JADX INFO: renamed from: lambda$new$4$org-jetbrains-kotlin-resolve-lazy-descriptors-LazyClassDescriptor, reason: not valid java name */
    /* synthetic */ Void m7458x89d45fed() {
        doForceResolveAllContents();
        return null;
    }

    /* JADX INFO: renamed from: lambda$new$5$org-jetbrains-kotlin-resolve-lazy-descriptors-LazyClassDescriptor, reason: not valid java name */
    /* synthetic */ List m7459x6595dbae(LazyClassContext lazyClassContext, StorageManager storageManager) {
        Annotations empty;
        DiagnosticFactory0<KtTypeParameterList> diagnosticFactory0;
        KtClassLikeInfo ownerInfo = this.declarationProvider.getOwnerInfo();
        KtTypeParameterList ktTypeParameterListMo7453getTypeParameterList = ownerInfo.mo7453getTypeParameterList();
        if (ktTypeParameterListMo7453getTypeParameterList == null) {
            return Collections.emptyList();
        }
        boolean z = ownerInfo.getClassKind() == ClassKind.CLASS && (ownerInfo.mo7451getCorrespondingClassOrObject() instanceof KtObjectDeclaration);
        if (ownerInfo.getClassKind() == ClassKind.ENUM_CLASS) {
            lazyClassContext.getTrace().report(Errors.TYPE_PARAMETERS_IN_ENUM.on(ktTypeParameterListMo7453getTypeParameterList));
        }
        if (ownerInfo.getClassKind() == ClassKind.OBJECT) {
            lazyClassContext.getTrace().report(Errors.TYPE_PARAMETERS_IN_OBJECT.on(ktTypeParameterListMo7453getTypeParameterList));
        }
        if (z) {
            if (lazyClassContext.getLanguageVersionSettings().supportsFeature(LanguageFeature.ProhibitTypeParametersInAnonymousObjects)) {
                diagnosticFactory0 = Errors.TYPE_PARAMETERS_IN_OBJECT;
            } else {
                diagnosticFactory0 = Errors.TYPE_PARAMETERS_IN_ANONYMOUS_OBJECT;
            }
            lazyClassContext.getTrace().report(diagnosticFactory0.on(ktTypeParameterListMo7453getTypeParameterList));
        }
        List<KtTypeParameter> parameters = ktTypeParameterListMo7453getTypeParameterList.getParameters();
        if (parameters.isEmpty()) {
            return Collections.emptyList();
        }
        boolean zSupportsFeature = lazyClassContext.getLanguageVersionSettings().supportsFeature(LanguageFeature.ClassTypeParameterAnnotations);
        ArrayList arrayList = new ArrayList(parameters.size());
        for (int i = 0; i < parameters.size(); i++) {
            KtTypeParameter ktTypeParameter = parameters.get(i);
            if (zSupportsFeature) {
                empty = new LazyAnnotations(new LazyAnnotationsContext(lazyClassContext.getAnnotationResolver(), storageManager, lazyClassContext.getTrace()) { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor.3
                    private static /* synthetic */ void $$$reportNull$$$0(int i2) {
                        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/resolve/lazy/descriptors/LazyClassDescriptor$3", "getScope"));
                    }

                    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.LazyAnnotationsContext
                    public LexicalScope getScope() {
                        LexicalScope outerScope = LazyClassDescriptor.this.getOuterScope();
                        if (outerScope == null) {
                            $$$reportNull$$$0(0);
                        }
                        return outerScope;
                    }
                }, ktTypeParameter.getAnnotationEntries());
            } else {
                empty = Annotations.INSTANCE.getEMPTY();
            }
            arrayList.add(new LazyTypeParameterDescriptor(lazyClassContext, this, ktTypeParameter, empty, i));
        }
        return arrayList;
    }

    /* JADX INFO: renamed from: lambda$new$6$org-jetbrains-kotlin-resolve-lazy-descriptors-LazyClassDescriptor, reason: not valid java name */
    /* synthetic */ LexicalScope m7460x4157576f(KtClassLikeInfo ktClassLikeInfo) {
        return ClassResolutionScopesSupportKt.scopeForInitializerResolution(this, createInitializerScopeParent(), ktClassLikeInfo.getPrimaryConstructorParameters());
    }

    /* JADX INFO: renamed from: lambda$new$7$org-jetbrains-kotlin-resolve-lazy-descriptors-LazyClassDescriptor, reason: not valid java name */
    /* synthetic */ Collection m7461x1d18d330(LazyClassContext lazyClassContext, boolean z) {
        if (getModality() == Modality.SEALED) {
            return lazyClassContext.getSealedClassInheritorsProvider().computeSealedSubclasses(this, z);
        }
        return Collections.emptyList();
    }

    /* JADX INFO: renamed from: lambda$new$9$org-jetbrains-kotlin-resolve-lazy-descriptors-LazyClassDescriptor, reason: not valid java name */
    /* synthetic */ List m7462xd49bcab2(final LazyClassContext lazyClassContext) {
        KtClassOrObject ktClassOrObject = this.classOrObject;
        if (ktClassOrObject == null) {
            return CollectionsKt.emptyList();
        }
        final List<KtContextReceiver> contextReceivers = ktClassOrObject.getContextReceivers();
        List list = (List) IntStream.range(0, contextReceivers.size()).mapToObj(new IntFunction() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor$$ExternalSyntheticLambda6
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return this.f$0.m7463xd7594372(contextReceivers, lazyClassContext, i);
            }
        }).filter(new Predicate() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.nonNull((ReceiverParameterDescriptor) obj);
            }
        }).collect(Collectors.toList());
        if (lazyClassContext.getLanguageVersionSettings().supportsFeature(LanguageFeature.ContextReceivers)) {
            HashMultimap hashMultimapCreate = HashMultimap.create();
            for (int i = 0; i < contextReceivers.size(); i++) {
                hashMultimapCreate.put(contextReceivers.get(i).name(), list.get(i));
            }
            lazyClassContext.getTrace().record(BindingContext.DESCRIPTOR_TO_CONTEXT_RECEIVER_MAP, this, hashMultimapCreate);
        }
        return list;
    }

    /* JADX INFO: renamed from: lambda$null$8$org-jetbrains-kotlin-resolve-lazy-descriptors-LazyClassDescriptor, reason: not valid java name */
    /* synthetic */ ReceiverParameterDescriptor m7463xd7594372(List list, LazyClassContext lazyClassContext, int i) {
        KtContextReceiver ktContextReceiver = (KtContextReceiver) list.get(i);
        KtTypeReference ktTypeReferenceTypeReference = ktContextReceiver.typeReference();
        if (ktTypeReferenceTypeReference == null) {
            return null;
        }
        KotlinType kotlinTypeResolveType = lazyClassContext.getTypeResolver().resolveType(getScopeForClassHeaderResolution(), ktTypeReferenceTypeReference, lazyClassContext.getTrace(), true);
        if (ktContextReceiver.labelNameAsName() != null) {
            ktContextReceiver.labelNameAsName();
        } else {
            NameUtils.contextReceiverName(i);
        }
        return DescriptorFactory.createContextReceiverParameterForClass(this, kotlinTypeResolveType, ktContextReceiver.labelNameAsName(), Annotations.INSTANCE.getEMPTY(), i);
    }

    private static boolean isIllegalInner(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(4);
        }
        if (!DescriptorUtils.isClass(declarationDescriptor)) {
            return true;
        }
        DeclarationDescriptor containingDeclaration = declarationDescriptor.getContainingDeclaration();
        return !(containingDeclaration instanceof ClassDescriptor) || DescriptorUtils.isInterface(containingDeclaration) || DescriptorUtils.isObject(containingDeclaration);
    }

    private DeclarationDescriptor createInitializerScopeParent() {
        ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = mo6347getUnsubstitutedPrimaryConstructor();
        if (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor == null) {
            return new FunctionDescriptorImpl(this, null, Annotations.INSTANCE.getEMPTY(), Name.special("<init-blocks>"), CallableMemberDescriptor.Kind.SYNTHESIZED, SourceElement.NO_SOURCE) { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor.4
                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    Object[] objArr = new Object[3];
                    if (i == 1) {
                        objArr[0] = Namer.METADATA_CLASS_KIND;
                    } else if (i == 2) {
                        objArr[0] = "annotations";
                    } else if (i != 3) {
                        objArr[0] = "newOwner";
                    } else {
                        objArr[0] = FirebaseAnalytics.Param.SOURCE;
                    }
                    objArr[1] = "org/jetbrains/kotlin/resolve/lazy/descriptors/LazyClassDescriptor$4";
                    objArr[2] = "createSubstitutedCopy";
                    throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
                }

                {
                    initialize(null, null, CollectionsKt.emptyList(), Collections.emptyList(), Collections.emptyList(), null, Modality.FINAL, DescriptorVisibilities.PRIVATE);
                }

                @Override // org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl
                protected FunctionDescriptorImpl createSubstitutedCopy(DeclarationDescriptor declarationDescriptor, FunctionDescriptor functionDescriptor, CallableMemberDescriptor.Kind kind, Name name2, Annotations annotations, SourceElement sourceElement) {
                    if (declarationDescriptor == null) {
                        $$$reportNull$$$0(0);
                    }
                    if (kind == null) {
                        $$$reportNull$$$0(1);
                    }
                    if (annotations == null) {
                        $$$reportNull$$$0(2);
                    }
                    if (sourceElement == null) {
                        $$$reportNull$$$0(3);
                    }
                    throw new UnsupportedOperationException();
                }
            };
        }
        if (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor == null) {
            $$$reportNull$$$0(5);
        }
        return classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor;
    }

    protected ScopesHolderForClass<LazyClassMemberScope> createScopesHolderForClass(final LazyClassContext lazyClassContext, final ClassMemberDeclarationProvider classMemberDeclarationProvider) {
        if (lazyClassContext == null) {
            $$$reportNull$$$0(6);
        }
        if (classMemberDeclarationProvider == null) {
            $$$reportNull$$$0(7);
        }
        ScopesHolderForClass<LazyClassMemberScope> scopesHolderForClassCreate = ScopesHolderForClass.INSTANCE.create(this, lazyClassContext.getStorageManager(), lazyClassContext.getKotlinTypeCheckerOfOwnerModule().getKotlinTypeRefiner(), new Function1() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m7455x8fc77d6a(lazyClassContext, classMemberDeclarationProvider, (KotlinTypeRefiner) obj);
            }
        });
        if (scopesHolderForClassCreate == null) {
            $$$reportNull$$$0(8);
        }
        return scopesHolderForClassCreate;
    }

    /* JADX INFO: renamed from: lambda$createScopesHolderForClass$10$org-jetbrains-kotlin-resolve-lazy-descriptors-LazyClassDescriptor, reason: not valid java name */
    /* synthetic */ LazyClassMemberScope m7455x8fc77d6a(LazyClassContext lazyClassContext, ClassMemberDeclarationProvider classMemberDeclarationProvider, KotlinTypeRefiner kotlinTypeRefiner) {
        return new LazyClassMemberScope(lazyClassContext, classMemberDeclarationProvider, this, lazyClassContext.getTrace(), kotlinTypeRefiner, !kotlinTypeRefiner.isRefinementNeededForModule(lazyClassContext.getModuleDescriptor()) ? null : (LazyClassMemberScope) this.scopesHolderForClass.getScope(lazyClassContext.getKotlinTypeCheckerOfOwnerModule().getKotlinTypeRefiner()));
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.ModuleAwareClassDescriptor
    public MemberScope getUnsubstitutedMemberScope(KotlinTypeRefiner kotlinTypeRefiner) {
        if (kotlinTypeRefiner == null) {
            $$$reportNull$$$0(9);
        }
        MemberScope scope = this.scopesHolderForClass.getScope(kotlinTypeRefiner);
        if (scope == null) {
            $$$reportNull$$$0(10);
        }
        return scope;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public LexicalScope getOuterScope() {
        LexicalScope resolutionScopeForDeclaration = this.c.getDeclarationScopeProvider().getResolutionScopeForDeclaration(this.declarationProvider.getOwnerInfo().getScript());
        if (resolutionScopeForDeclaration == null) {
            $$$reportNull$$$0(11);
        }
        return resolutionScopeForDeclaration;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes
    public LexicalScope getScopeForClassHeaderResolution() {
        LexicalScope lexicalScopeInvoke = this.resolutionScopesSupport.getScopeForClassHeaderResolution().invoke();
        if (lexicalScopeInvoke == null) {
            $$$reportNull$$$0(12);
        }
        return lexicalScopeInvoke;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes
    public LexicalScope getScopeForConstructorHeaderResolution() {
        LexicalScope lexicalScopeInvoke = this.resolutionScopesSupport.getScopeForConstructorHeaderResolution().invoke();
        if (lexicalScopeInvoke == null) {
            $$$reportNull$$$0(13);
        }
        return lexicalScopeInvoke;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes
    public LexicalScope getScopeForCompanionObjectHeaderResolution() {
        LexicalScope lexicalScopeInvoke = this.resolutionScopesSupport.getScopeForCompanionObjectHeaderResolution().invoke();
        if (lexicalScopeInvoke == null) {
            $$$reportNull$$$0(14);
        }
        return lexicalScopeInvoke;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes
    public LexicalScope getScopeForMemberDeclarationResolution() {
        LexicalScope lexicalScopeInvoke = this.resolutionScopesSupport.getScopeForMemberDeclarationResolution().invoke();
        if (lexicalScopeInvoke == null) {
            $$$reportNull$$$0(15);
        }
        return lexicalScopeInvoke;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes
    public LexicalScope getScopeForStaticMemberDeclarationResolution() {
        LexicalScope lexicalScopeInvoke = this.resolutionScopesSupport.getScopeForStaticMemberDeclarationResolution().invoke();
        if (lexicalScopeInvoke == null) {
            $$$reportNull$$$0(16);
        }
        return lexicalScopeInvoke;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes
    public LexicalScope getScopeForInitializerResolution() {
        LexicalScope lexicalScopeInvoke = this.scopeForInitializerResolution.invoke();
        if (lexicalScopeInvoke == null) {
            $$$reportNull$$$0(17);
        }
        return lexicalScopeInvoke;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes
    public Collection<CallableMemberDescriptor> getDeclaredCallableMembers() {
        List listFilter = CollectionsKt.filter(DescriptorUtils.getAllDescriptors(getUnsubstitutedMemberScope()), new Function1() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) obj;
                return Boolean.valueOf((declarationDescriptor instanceof CallableMemberDescriptor) && ((CallableMemberDescriptor) declarationDescriptor).getKind() != CallableMemberDescriptor.Kind.FAKE_OVERRIDE);
            }
        });
        if (listFilter == null) {
            $$$reportNull$$$0(18);
        }
        return listFilter;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public MemberScope getStaticScope() {
        MemberScope memberScope = this.staticScope;
        if (memberScope == null) {
            $$$reportNull$$$0(19);
        }
        return memberScope;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public Collection<ClassConstructorDescriptor> getConstructors() {
        Collection<ClassConstructorDescriptor> constructors = ((LazyClassMemberScope) getUnsubstitutedMemberScope()).getConstructors();
        if (constructors == null) {
            $$$reportNull$$$0(20);
        }
        return constructors;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractClassDescriptor, org.jetbrains.kotlin.descriptors.ClassDescriptor
    public List<ReceiverParameterDescriptor> getContextReceivers() {
        List<ReceiverParameterDescriptor> listInvoke = this.contextReceivers.invoke();
        if (listInvoke == null) {
            $$$reportNull$$$0(21);
        }
        return listInvoke;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: getUnsubstitutedPrimaryConstructor */
    public ClassConstructorDescriptor mo6347getUnsubstitutedPrimaryConstructor() {
        return ((LazyClassMemberScope) getUnsubstitutedMemberScope()).getPrimaryConstructor();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptor
    public TypeConstructor getTypeConstructor() {
        LazyClassTypeConstructor lazyClassTypeConstructor = this.typeConstructor;
        if (lazyClassTypeConstructor == null) {
            $$$reportNull$$$0(22);
        }
        return lazyClassTypeConstructor;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: getCompanionObjectDescriptor */
    public ClassDescriptorWithResolutionScopes mo6346getCompanionObjectDescriptor() {
        return this.companionObjectDescriptor.invoke();
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractClassDescriptor, org.jetbrains.kotlin.descriptors.ClassDescriptor
    public SimpleType getDefaultFunctionTypeForSamInterface() {
        return this.c.getSamConversionResolver().resolveFunctionTypeIfSamInterface(this);
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractClassDescriptor, org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isDefinitelyNotSamInterface() {
        return !isFun();
    }

    public List<ClassDescriptor> getDescriptorsForExtraCompanionObjects() {
        final KtObjectDeclaration companionObjectIfAllowed = getCompanionObjectIfAllowed();
        List<ClassDescriptor> map = CollectionsKt.map(CollectionsKt.filter(this.declarationProvider.getOwnerInfo().getCompanionObjects(), new Function1() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(((KtObjectDeclaration) obj) != companionObjectIfAllowed);
            }
        }), this.extraCompanionObjectDescriptors);
        if (map == null) {
            $$$reportNull$$$0(23);
        }
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ClassDescriptorWithResolutionScopes computeCompanionObjectDescriptor(KtObjectDeclaration ktObjectDeclaration) {
        if (ktObjectDeclaration == null) {
            return createSyntheticCompanionObjectDescriptor();
        }
        KtClassLikeInfo companionObjectInfo = getCompanionObjectInfo(ktObjectDeclaration);
        if (!(companionObjectInfo instanceof KtClassOrObjectInfo)) {
            return null;
        }
        getUnsubstitutedMemberScope().mo7465getContributedClassifier(((KtClassOrObjectInfo) companionObjectInfo).getName(), NoLookupLocation.WHEN_GET_COMPANION_OBJECT);
        ClassDescriptor classDescriptor = (ClassDescriptor) this.c.getTrace().get(BindingContext.CLASS, ktObjectDeclaration);
        if (classDescriptor instanceof ClassDescriptorWithResolutionScopes) {
            return (ClassDescriptorWithResolutionScopes) classDescriptor;
        }
        return null;
    }

    private ClassDescriptorWithResolutionScopes createSyntheticCompanionObjectDescriptor() {
        Name syntheticCompanionObjectNameIfNeeded = this.c.getSyntheticResolveExtension().getSyntheticCompanionObjectNameIfNeeded(this);
        if (syntheticCompanionObjectNameIfNeeded == null) {
            return null;
        }
        SyntheticClassOrObjectDescriptor syntheticClassOrObjectDescriptor = new SyntheticClassOrObjectDescriptor(this.c, this.classOrObject, this, syntheticCompanionObjectNameIfNeeded, getSourceElement(), getOuterScope(), Modality.FINAL, DescriptorVisibilities.PUBLIC, Annotations.INSTANCE.getEMPTY(), DescriptorVisibilities.PRIVATE, ClassKind.OBJECT, true);
        syntheticClassOrObjectDescriptor.initialize();
        return syntheticClassOrObjectDescriptor;
    }

    private static KtClassLikeInfo getCompanionObjectInfo(KtObjectDeclaration ktObjectDeclaration) {
        if (ktObjectDeclaration != null) {
            return KtClassInfoUtil.createClassOrObjectInfo(ktObjectDeclaration);
        }
        return null;
    }

    private KtObjectDeclaration getCompanionObjectIfAllowed() {
        KtObjectDeclaration ktObjectDeclaration = (KtObjectDeclaration) CollectionsKt.firstOrNull((List) this.declarationProvider.getOwnerInfo().getCompanionObjects());
        if (ktObjectDeclaration == null || !isCompanionObjectAllowed()) {
            return null;
        }
        return ktObjectDeclaration;
    }

    private boolean isCompanionObjectAllowed() {
        return (getKind().isSingleton() || getIsInner() || DescriptorUtils.isLocal(this)) ? false : true;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public ClassKind getKind() {
        ClassKind classKind = this.kind;
        if (classKind == null) {
            $$$reportNull$$$0(24);
        }
        return classKind;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public Modality getModality() {
        Modality modalityInvoke = this.modality.invoke();
        if (modalityInvoke == null) {
            $$$reportNull$$$0(25);
        }
        return modalityInvoke;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
    /* JADX INFO: renamed from: getVisibility */
    public DescriptorVisibility getVisibilityImpl() {
        DescriptorVisibility descriptorVisibility = this.visibility;
        if (descriptorVisibility == null) {
            $$$reportNull$$$0(26);
        }
        return descriptorVisibility;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
    /* JADX INFO: renamed from: isInner */
    public boolean getIsInner() {
        return this.isInner;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isData() {
        return this.isData;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isInline() {
        return this.isInline;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: isCompanionObject */
    public boolean getIsCompanionObject() {
        return this.isCompanionObject;
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    /* JADX INFO: renamed from: isExpect */
    public boolean get$isExpect() {
        return this.isExpect;
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isActual() {
        return this.isActual;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isFun() {
        return this.isFun;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isValue() {
        return this.isValue;
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotated
    public Annotations getAnnotations() {
        Annotations annotations = this.annotations;
        if (annotations == null) {
            $$$reportNull$$$0(27);
        }
        return annotations;
    }

    public Annotations getDanglingAnnotations() {
        Annotations annotations = this.danglingAnnotations;
        if (annotations == null) {
            $$$reportNull$$$0(28);
        }
        return annotations;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public Collection<ClassDescriptor> getSealedSubclasses() {
        Collection<ClassDescriptor> collectionInvoke = this.sealedSubclasses.invoke();
        if (collectionInvoke == null) {
            $$$reportNull$$$0(29);
        }
        return collectionInvoke;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public ValueClassRepresentation<SimpleType> getValueClassRepresentation() {
        if (!this.isValue && !this.isInline) {
            return null;
        }
        ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = mo6347getUnsubstitutedPrimaryConstructor();
        InlineClassRepresentation inlineClassRepresentation = new InlineClassRepresentation(SpecialNames.SAFE_IDENTIFIER_FOR_NO_NAME, this.c.getModuleDescriptor().getBuiltIns().getAnyType());
        if (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor == null) {
            return inlineClassRepresentation;
        }
        List<ValueParameterDescriptor> valueParameters = classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.getValueParameters();
        SimpleClassicTypeSystemContext simpleClassicTypeSystemContext = SimpleClassicTypeSystemContext.INSTANCE;
        if (isRecursiveInlineClass(classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor, new HashSet())) {
            return new InlineClassRepresentation(valueParameters.get(0).getShortName(), (SimpleType) valueParameters.get(0).getType());
        }
        return valueParameters.size() == 0 ? inlineClassRepresentation : ValueClassRepresentationKt.createValueClassRepresentation(simpleClassicTypeSystemContext, (List) valueParameters.stream().map(new Function() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return LazyClassDescriptor.lambda$getValueClassRepresentation$13((ValueParameterDescriptor) obj);
            }
        }).collect(Collectors.toList()));
    }

    static /* synthetic */ Pair lambda$getValueClassRepresentation$13(ValueParameterDescriptor valueParameterDescriptor) {
        return new Pair(valueParameterDescriptor.getShortName(), (SimpleType) valueParameterDescriptor.getType());
    }

    private static boolean isRecursiveInlineClass(ClassConstructorDescriptor classConstructorDescriptor, Set<ClassDescriptor> set) {
        if (set == null) {
            $$$reportNull$$$0(30);
        }
        if (classConstructorDescriptor != null && classConstructorDescriptor.getValueParameters().size() == 1 && (classConstructorDescriptor.getConstructedClass().isValue() || classConstructorDescriptor.getConstructedClass().isInline())) {
            if (!set.add(classConstructorDescriptor.getConstructedClass())) {
                return true;
            }
            ClassifierDescriptor this$0 = ((SimpleType) classConstructorDescriptor.getValueParameters().get(0).getType()).getConstructor().getThis$0();
            if (this$0 instanceof ClassDescriptor) {
                return isRecursiveInlineClass(((ClassDescriptor) this$0).mo6347getUnsubstitutedPrimaryConstructor(), set);
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.ValidateableDescriptor
    public void validate() {
        if (this.parameters == null) {
            throw new IllegalStateException("parameters == null for " + this);
        }
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        if (this.isExpect) {
            str = "expect ";
        } else {
            str = this.isActual ? "actual " : "";
        }
        return sb.append(str).append(ExternalAnnotationProvider.CLASS_PREFIX).append(getShortName().toString()).toString();
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyEntity
    public void forceResolveAllContents() {
        this.forceResolveAllContents.invoke();
    }

    private void doForceResolveAllContents() {
        resolveMemberHeaders();
        ClassDescriptorWithResolutionScopes classDescriptorWithResolutionScopesMo6346getCompanionObjectDescriptor = mo6346getCompanionObjectDescriptor();
        if (classDescriptorWithResolutionScopesMo6346getCompanionObjectDescriptor != null) {
            ForceResolveUtil.forceResolveAllContents(classDescriptorWithResolutionScopesMo6346getCompanionObjectDescriptor);
        }
        ForceResolveUtil.forceResolveAllContents((Iterable<? extends DeclarationDescriptor>) getConstructors());
        ForceResolveUtil.forceResolveAllContents((Iterable<? extends DeclarationDescriptor>) getDescriptorsForExtraCompanionObjects());
        ForceResolveUtil.forceResolveAllContents(getUnsubstitutedMemberScope());
        ForceResolveUtil.forceResolveAllContents(getTypeConstructor());
        ForceResolveUtil.forceResolveAllContents((Iterable<? extends DeclarationDescriptor>) getContextReceivers());
    }

    public void resolveMemberHeaders() {
        ForceResolveUtil.forceResolveAllContents(getAnnotations());
        ForceResolveUtil.forceResolveAllContents(getDanglingAnnotations());
        mo6346getCompanionObjectDescriptor();
        getDescriptorsForExtraCompanionObjects();
        getConstructors();
        getContainingDeclaration();
        getThisAsReceiverParameter();
        getKind();
        getModality();
        getShortName();
        getOriginal();
        getScopeForClassHeaderResolution();
        getScopeForMemberDeclarationResolution();
        DescriptorUtils.getAllDescriptors(getUnsubstitutedMemberScope());
        getScopeForInitializerResolution();
        getUnsubstitutedInnerClassesScope();
        getTypeConstructor().mo7438getSupertypes();
        Iterator<TypeParameterDescriptor> it = getTypeConstructor().getParameters().iterator();
        while (it.hasNext()) {
            it.next().getUpperBounds();
        }
        mo6347getUnsubstitutedPrimaryConstructor();
        getVisibilityImpl();
        getContextReceivers();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
    public List<TypeParameterDescriptor> getDeclaredTypeParameters() {
        List<TypeParameterDescriptor> listInvoke = this.parameters.invoke();
        if (listInvoke == null) {
            $$$reportNull$$$0(31);
        }
        return listInvoke;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class LazyClassTypeConstructor extends AbstractClassTypeConstructor {
        private final NotNullLazyValue<List<TypeParameterDescriptor>> parameters;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 1 || i == 2 || i == 3 || i == 4 || i == 5) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4 || i == 5) ? 3 : 2];
            if (i == 1 || i == 2) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else if (i == 3) {
                objArr[0] = "trace";
            } else if (i == 4) {
                objArr[0] = "classDescriptor";
            } else if (i != 5) {
                objArr[0] = "org/jetbrains/kotlin/resolve/lazy/descriptors/LazyClassDescriptor$LazyClassTypeConstructor";
            } else {
                objArr[0] = "superclass";
            }
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    objArr[1] = "org/jetbrains/kotlin/resolve/lazy/descriptors/LazyClassDescriptor$LazyClassTypeConstructor";
                    break;
                case 6:
                    objArr[1] = "getSupertypeLoopChecker";
                    break;
                case 7:
                    objArr[1] = "getParameters";
                    break;
                case 8:
                    objArr[1] = "getDeclarationDescriptor";
                    break;
                default:
                    objArr[1] = "computeSupertypes";
                    break;
            }
            if (i == 1) {
                objArr[2] = "reportSupertypeLoopError";
            } else if (i == 2) {
                objArr[2] = "reportScopesLoopError";
            } else if (i == 3 || i == 4 || i == 5) {
                objArr[2] = "reportCyclicInheritanceHierarchyError";
            }
            String str2 = String.format(str, objArr);
            if (i != 1 && i != 2 && i != 3 && i != 4 && i != 5) {
                throw new IllegalStateException(str2);
            }
            throw new IllegalArgumentException(str2);
        }

        @Override // org.jetbrains.kotlin.types.TypeConstructor
        public boolean isDenotable() {
            return true;
        }

        /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-resolve-lazy-descriptors-LazyClassDescriptor$LazyClassTypeConstructor, reason: not valid java name */
        /* synthetic */ List m7464x7480acd7() {
            return TypeParameterUtilsKt.computeConstructorTypeParameters(LazyClassDescriptor.this);
        }

        public LazyClassTypeConstructor() {
            super(LazyClassDescriptor.this.c.getStorageManager());
            this.parameters = LazyClassDescriptor.this.c.getStorageManager().createLazyValue(new Function0() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor$LazyClassTypeConstructor$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return this.f$0.m7464x7480acd7();
                }
            });
        }

        @Override // org.jetbrains.kotlin.types.AbstractTypeConstructor
        protected Collection<KotlinType> computeSupertypes() {
            Collection<KotlinType> collectionComputeSupertypes = LazyClassDescriptor.this.computeSupertypes();
            if (collectionComputeSupertypes == null) {
                $$$reportNull$$$0(0);
            }
            return collectionComputeSupertypes;
        }

        @Override // org.jetbrains.kotlin.types.AbstractTypeConstructor
        protected void reportSupertypeLoopError(KotlinType kotlinType) {
            if (kotlinType == null) {
                $$$reportNull$$$0(1);
            }
            ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
            if (this$0 instanceof ClassDescriptor) {
                reportCyclicInheritanceHierarchyError(LazyClassDescriptor.this.c.getTrace(), LazyClassDescriptor.this, (ClassDescriptor) this$0);
            }
        }

        @Override // org.jetbrains.kotlin.types.AbstractTypeConstructor
        protected boolean getShouldReportCyclicScopeWithCompanionWarning() {
            return !LazyClassDescriptor.this.c.getLanguageVersionSettings().supportsFeature(LanguageFeature.ProhibitVisibilityOfNestedClassifiersFromSupertypesOfCompanion);
        }

        @Override // org.jetbrains.kotlin.types.AbstractTypeConstructor
        protected void reportScopesLoopError(KotlinType kotlinType) {
            if (kotlinType == null) {
                $$$reportNull$$$0(2);
            }
            PsiElement sourceFromDescriptor = DescriptorToSourceUtils.getSourceFromDescriptor(kotlinType.getConstructor().getThis$0());
            if (sourceFromDescriptor instanceof KtClass) {
                sourceFromDescriptor = ((KtClass) sourceFromDescriptor).mo7392getNameIdentifier();
            }
            if (sourceFromDescriptor != null) {
                LazyClassDescriptor.this.c.getTrace().report(Errors.CYCLIC_SCOPES_WITH_COMPANION.on(sourceFromDescriptor));
            }
        }

        private void reportCyclicInheritanceHierarchyError(BindingTrace bindingTrace, ClassDescriptor classDescriptor, ClassDescriptor classDescriptor2) {
            PsiElement psiElementMo7392getNameIdentifier;
            KotlinType kotlinType;
            if (bindingTrace == null) {
                $$$reportNull$$$0(3);
            }
            if (classDescriptor == null) {
                $$$reportNull$$$0(4);
            }
            if (classDescriptor2 == null) {
                $$$reportNull$$$0(5);
            }
            PsiElement sourceFromDescriptor = DescriptorToSourceUtils.getSourceFromDescriptor(classDescriptor);
            PsiElement psiElement = null;
            if (sourceFromDescriptor instanceof KtClassOrObject) {
                Iterator<KtSuperTypeListEntry> it = ((KtClassOrObject) sourceFromDescriptor).getSuperTypeListEntries().iterator();
                while (it.hasNext()) {
                    KtTypeReference typeReference = it.next().getTypeReference();
                    if (typeReference != null && (kotlinType = (KotlinType) bindingTrace.get(BindingContext.TYPE, typeReference)) != null && kotlinType.getConstructor() == classDescriptor2.getTypeConstructor()) {
                        psiElement = typeReference;
                    }
                }
            }
            if (psiElement == null && (sourceFromDescriptor instanceof PsiNameIdentifierOwner) && (psiElementMo7392getNameIdentifier = ((PsiNameIdentifierOwner) sourceFromDescriptor).mo7392getNameIdentifier()) != null) {
                psiElement = psiElementMo7392getNameIdentifier;
            }
            if (psiElement != null) {
                bindingTrace.report(Errors.CYCLIC_INHERITANCE_HIERARCHY.on(psiElement));
            }
        }

        @Override // org.jetbrains.kotlin.types.AbstractTypeConstructor
        protected SupertypeLoopChecker getSupertypeLoopChecker() {
            SupertypeLoopChecker supertypeLoopChecker = LazyClassDescriptor.this.c.getSupertypeLoopChecker();
            if (supertypeLoopChecker == null) {
                $$$reportNull$$$0(6);
            }
            return supertypeLoopChecker;
        }

        @Override // org.jetbrains.kotlin.types.TypeConstructor
        public List<TypeParameterDescriptor> getParameters() {
            List<TypeParameterDescriptor> listInvoke = this.parameters.invoke();
            if (listInvoke == null) {
                $$$reportNull$$$0(7);
            }
            return listInvoke;
        }

        @Override // org.jetbrains.kotlin.types.AbstractClassTypeConstructor, org.jetbrains.kotlin.types.ClassifierBasedTypeConstructor, org.jetbrains.kotlin.types.TypeConstructor
        /* JADX INFO: renamed from: getDeclarationDescriptor */
        public ClassDescriptor getThis$0() {
            LazyClassDescriptor lazyClassDescriptor = LazyClassDescriptor.this;
            if (lazyClassDescriptor == null) {
                $$$reportNull$$$0(8);
            }
            return lazyClassDescriptor;
        }

        public String toString() {
            return LazyClassDescriptor.this.getShortName().toString();
        }
    }

    protected Collection<KotlinType> computeSupertypes() {
        if (KotlinBuiltIns.isSpecialClassWithNoSupertypes(this)) {
            List listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(32);
            }
            return listEmptyList;
        }
        KtClassOrObject ktClassOrObjectMo7451getCorrespondingClassOrObject = this.declarationProvider.getOwnerInfo().mo7451getCorrespondingClassOrObject();
        if (ktClassOrObjectMo7451getCorrespondingClassOrObject == null) {
            Set setSingleton = Collections.singleton(this.c.getModuleDescriptor().getBuiltIns().getAnyType());
            if (setSingleton == null) {
                $$$reportNull$$$0(33);
            }
            return setSingleton;
        }
        return new ArrayList(CollectionsKt.filter(this.c.getDescriptorResolver().resolveSupertypes(getScopeForClassHeaderResolution(), this, ktClassOrObjectMo7451getCorrespondingClassOrObject, this.c.getTrace()), VALID_SUPERTYPE));
    }
}
