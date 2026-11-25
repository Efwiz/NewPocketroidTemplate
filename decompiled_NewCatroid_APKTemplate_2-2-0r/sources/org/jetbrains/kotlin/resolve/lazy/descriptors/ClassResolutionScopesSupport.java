package org.jetbrains.kotlin.resolve.lazy.descriptors;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.DeprecatedLexicalScope;
import org.jetbrains.kotlin.resolve.scopes.DeprecatedMemberScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalChainedScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeImpl;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeKind;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.utils.ErrorLexicalScope;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: ClassResolutionScopesSupport.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 ,2\u00020\u0001:\u0001,B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bJ4\u0010\u0019\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u001d\u001a\u00020\u001e2\b\b\u0002\u0010\u001f\u001a\u00020\u001eH\u0002J\u0010\u0010 \u001a\u00020!2\u0006\u0010\u001a\u001a\u00020\nH\u0002J>\u0010\"\u001a\b\u0012\u0004\u0012\u0002H$0#\"\b\b\u0000\u0010$*\u00020\u0001*\u00020\u00052\u0012\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u0002H$0&2\f\u0010'\u001a\b\u0012\u0004\u0012\u0002H$0\tH\u0002J\u0016\u0010(\u001a\u0004\u0018\u00010)*\u00020\u00032\u0006\u0010*\u001a\u00020\u0003H\u0002J\u001e\u0010+\u001a\u0004\u0018\u00010\n*\u00020\u00032\u0006\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0010¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/descriptors/ClassResolutionScopesSupport;", "", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getOuterScope", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lkotlin/jvm/functions/Function0;)V", "inheritanceScopeWithMe", "inheritanceScopeWithoutMe", "scopeForClassHeaderResolution", "getScopeForClassHeaderResolution", "()Lkotlin/jvm/functions/Function0;", "scopeForCompanionObjectHeaderResolution", "getScopeForCompanionObjectHeaderResolution", "scopeForConstructorHeaderResolution", "getScopeForConstructorHeaderResolution", "scopeForMemberDeclarationResolution", "getScopeForMemberDeclarationResolution", "scopeForStaticMemberDeclarationResolution", "getScopeForStaticMemberDeclarationResolution", "createInheritanceScope", "parent", "ownerDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "withCompanionObject", "", "isDeprecated", "scopeWithGenerics", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeImpl;", "createLazyValue", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", ExifInterface.GPS_DIRECTION_TRUE, "onRecursion", "Lkotlin/Function1;", "compute", "getStaticScopeOfCompanionObject", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "companionOwner", "packScopesOfCompanionSupertypes", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassResolutionScopesSupport {
    private static final Function1<Boolean, LexicalScope> createErrorLexicalScope = new Function1<Boolean, ErrorLexicalScope>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.ClassResolutionScopesSupport$Companion$createErrorLexicalScope$1
        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ ErrorLexicalScope invoke(Boolean bool) {
            return invoke(bool.booleanValue());
        }

        public final ErrorLexicalScope invoke(boolean z) {
            return new ErrorLexicalScope();
        }
    };
    private final ClassDescriptor classDescriptor;
    private final Function0<LexicalScope> getOuterScope;
    private final Function0<LexicalScope> inheritanceScopeWithMe;
    private final Function0<LexicalScope> inheritanceScopeWithoutMe;
    private final LanguageVersionSettings languageVersionSettings;
    private final Function0<LexicalScope> scopeForClassHeaderResolution;
    private final Function0<LexicalScope> scopeForCompanionObjectHeaderResolution;
    private final Function0<LexicalScope> scopeForConstructorHeaderResolution;
    private final Function0<LexicalScope> scopeForMemberDeclarationResolution;
    private final Function0<LexicalScope> scopeForStaticMemberDeclarationResolution;

    /* JADX WARN: Multi-variable type inference failed */
    public ClassResolutionScopesSupport(ClassDescriptor classDescriptor, StorageManager storageManager, LanguageVersionSettings languageVersionSettings, Function0<? extends LexicalScope> getOuterScope) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(getOuterScope, "getOuterScope");
        this.classDescriptor = classDescriptor;
        this.languageVersionSettings = languageVersionSettings;
        this.getOuterScope = getOuterScope;
        Function1<Boolean, LexicalScope> function1 = createErrorLexicalScope;
        this.scopeForClassHeaderResolution = createLazyValue(storageManager, function1, new Function0<LexicalScope>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.ClassResolutionScopesSupport$scopeForClassHeaderResolution$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final LexicalScope invoke() {
                ClassResolutionScopesSupport classResolutionScopesSupport = this.this$0;
                return classResolutionScopesSupport.scopeWithGenerics((LexicalScope) classResolutionScopesSupport.getOuterScope.invoke());
            }
        });
        this.scopeForConstructorHeaderResolution = storageManager.createLazyValue(new Function0<LexicalScopeImpl>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.ClassResolutionScopesSupport$scopeForConstructorHeaderResolution$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final LexicalScopeImpl invoke() {
                ClassResolutionScopesSupport classResolutionScopesSupport = this.this$0;
                return classResolutionScopesSupport.scopeWithGenerics((LexicalScope) classResolutionScopesSupport.inheritanceScopeWithMe.invoke());
            }
        });
        this.inheritanceScopeWithoutMe = createLazyValue(storageManager, function1, new Function0<LexicalScope>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.ClassResolutionScopesSupport$inheritanceScopeWithoutMe$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final LexicalScope invoke() {
                List<ClassDescriptor> listAsReversedMutable = CollectionsKt.asReversedMutable(DescriptorUtilsKt.getAllSuperclassesWithoutAny(this.this$0.classDescriptor));
                Object objInvoke = this.this$0.getOuterScope.invoke();
                ClassResolutionScopesSupport classResolutionScopesSupport = this.this$0;
                for (ClassDescriptor classDescriptor2 : listAsReversedMutable) {
                    ClassDescriptor classDescriptor3 = classResolutionScopesSupport.classDescriptor;
                    Intrinsics.checkNotNull(classDescriptor2);
                    objInvoke = ClassResolutionScopesSupport.createInheritanceScope$default(classResolutionScopesSupport, (LexicalScope) objInvoke, classDescriptor3, classDescriptor2, false, false, 24, null);
                }
                return (LexicalScope) objInvoke;
            }
        });
        this.inheritanceScopeWithMe = createLazyValue(storageManager, function1, new Function0<LexicalScope>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.ClassResolutionScopesSupport$inheritanceScopeWithMe$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final LexicalScope invoke() {
                ClassResolutionScopesSupport classResolutionScopesSupport = this.this$0;
                return ClassResolutionScopesSupport.createInheritanceScope$default(classResolutionScopesSupport, (LexicalScope) classResolutionScopesSupport.inheritanceScopeWithoutMe.invoke(), this.this$0.classDescriptor, this.this$0.classDescriptor, false, false, 24, null);
            }
        });
        this.scopeForCompanionObjectHeaderResolution = createLazyValue(storageManager, function1, new Function0<LexicalScope>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.ClassResolutionScopesSupport$scopeForCompanionObjectHeaderResolution$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final LexicalScope invoke() {
                ClassResolutionScopesSupport classResolutionScopesSupport = this.this$0;
                return ClassResolutionScopesSupport.createInheritanceScope$default(classResolutionScopesSupport, (LexicalScope) classResolutionScopesSupport.inheritanceScopeWithoutMe.invoke(), this.this$0.classDescriptor, this.this$0.classDescriptor, false, false, 16, null);
            }
        });
        this.scopeForMemberDeclarationResolution = createLazyValue(storageManager, function1, new Function0<LexicalScope>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.ClassResolutionScopesSupport$scopeForMemberDeclarationResolution$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final LexicalScope invoke() {
                ClassResolutionScopesSupport classResolutionScopesSupport = this.this$0;
                LexicalScopeImpl lexicalScopeImplScopeWithGenerics = classResolutionScopesSupport.scopeWithGenerics((LexicalScope) classResolutionScopesSupport.inheritanceScopeWithMe.invoke());
                ClassDescriptor classDescriptor2 = this.this$0.classDescriptor;
                ReceiverParameterDescriptor thisAsReceiverParameter = this.this$0.classDescriptor.getThisAsReceiverParameter();
                List<ReceiverParameterDescriptor> contextReceivers = this.this$0.classDescriptor.getContextReceivers();
                Intrinsics.checkNotNullExpressionValue(contextReceivers, "getContextReceivers(...)");
                return new LexicalScopeImpl(lexicalScopeImplScopeWithGenerics, classDescriptor2, true, thisAsReceiverParameter, contextReceivers, LexicalScopeKind.CLASS_MEMBER_SCOPE, null, null, 192, null);
            }
        });
        this.scopeForStaticMemberDeclarationResolution = createLazyValue(storageManager, function1, new Function0<LexicalScope>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.ClassResolutionScopesSupport$scopeForStaticMemberDeclarationResolution$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final LexicalScope invoke() {
                if (!this.this$0.classDescriptor.getKind().isSingleton()) {
                    return (LexicalScope) this.this$0.inheritanceScopeWithMe.invoke();
                }
                return this.this$0.getScopeForMemberDeclarationResolution().invoke();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final LexicalScopeImpl scopeWithGenerics(LexicalScope parent) {
        return new LexicalScopeImpl(parent, this.classDescriptor, false, null, CollectionsKt.emptyList(), LexicalScopeKind.CLASS_HEADER, null, new Function1<LexicalScopeImpl.InitializeHandler, Unit>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.ClassResolutionScopesSupport.scopeWithGenerics.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(LexicalScopeImpl.InitializeHandler initializeHandler) {
                invoke2(initializeHandler);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(LexicalScopeImpl.InitializeHandler $receiver) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                List<TypeParameterDescriptor> declaredTypeParameters = ClassResolutionScopesSupport.this.classDescriptor.getDeclaredTypeParameters();
                Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
                for (TypeParameterDescriptor typeParameterDescriptor : declaredTypeParameters) {
                    Intrinsics.checkNotNull(typeParameterDescriptor);
                    $receiver.addClassifierDescriptor(typeParameterDescriptor);
                }
            }
        }, 64, null);
    }

    public final Function0<LexicalScope> getScopeForClassHeaderResolution() {
        return this.scopeForClassHeaderResolution;
    }

    public final Function0<LexicalScope> getScopeForConstructorHeaderResolution() {
        return this.scopeForConstructorHeaderResolution;
    }

    public final Function0<LexicalScope> getScopeForCompanionObjectHeaderResolution() {
        return this.scopeForCompanionObjectHeaderResolution;
    }

    public final Function0<LexicalScope> getScopeForMemberDeclarationResolution() {
        return this.scopeForMemberDeclarationResolution;
    }

    public final Function0<LexicalScope> getScopeForStaticMemberDeclarationResolution() {
        return this.scopeForStaticMemberDeclarationResolution;
    }

    static /* synthetic */ LexicalScope createInheritanceScope$default(ClassResolutionScopesSupport classResolutionScopesSupport, LexicalScope lexicalScope, DeclarationDescriptor declarationDescriptor, ClassDescriptor classDescriptor, boolean z, boolean z2, int i, Object obj) {
        if ((i & 8) != 0) {
            z = true;
        }
        boolean z3 = z;
        if ((i & 16) != 0) {
            z2 = false;
        }
        return classResolutionScopesSupport.createInheritanceScope(lexicalScope, declarationDescriptor, classDescriptor, z3, z2);
    }

    private final LexicalScope createInheritanceScope(LexicalScope parent, DeclarationDescriptor ownerDescriptor, ClassDescriptor classDescriptor, boolean withCompanionObject, boolean isDeprecated) {
        LexicalScope lexicalScopePackScopesOfCompanionSupertypes;
        ClassDescriptor classDescriptorMo6346getCompanionObjectDescriptor = classDescriptor.mo6346getCompanionObjectDescriptor();
        if (classDescriptorMo6346getCompanionObjectDescriptor == null || !withCompanionObject) {
            classDescriptorMo6346getCompanionObjectDescriptor = null;
        }
        if (classDescriptorMo6346getCompanionObjectDescriptor == null || (lexicalScopePackScopesOfCompanionSupertypes = packScopesOfCompanionSupertypes(classDescriptorMo6346getCompanionObjectDescriptor, parent, ownerDescriptor)) == null) {
            lexicalScopePackScopesOfCompanionSupertypes = parent;
        }
        LexicalChainedScope.Companion companion = LexicalChainedScope.INSTANCE;
        ReceiverParameterDescriptor thisAsReceiverParameter = classDescriptorMo6346getCompanionObjectDescriptor != null ? classDescriptorMo6346getCompanionObjectDescriptor.getThisAsReceiverParameter() : null;
        List<? extends ReceiverParameterDescriptor> listEmptyList = CollectionsKt.emptyList();
        LexicalScopeKind lexicalScopeKind = LexicalScopeKind.CLASS_INHERITANCE;
        MemberScope[] memberScopeArr = new MemberScope[3];
        memberScopeArr[0] = classDescriptor.getStaticScope();
        memberScopeArr[1] = classDescriptor.getUnsubstitutedInnerClassesScope();
        memberScopeArr[2] = classDescriptorMo6346getCompanionObjectDescriptor != null ? getStaticScopeOfCompanionObject(classDescriptorMo6346getCompanionObjectDescriptor, classDescriptor) : null;
        LexicalScope lexicalScopeCreate = companion.create(lexicalScopePackScopesOfCompanionSupertypes, ownerDescriptor, false, thisAsReceiverParameter, listEmptyList, lexicalScopeKind, memberScopeArr, true);
        return isDeprecated ? new DeprecatedLexicalScope(lexicalScopeCreate) : lexicalScopeCreate;
    }

    private final MemberScope getStaticScopeOfCompanionObject(ClassDescriptor classDescriptor, ClassDescriptor classDescriptor2) {
        if (Intrinsics.areEqual(classDescriptor2, this.classDescriptor)) {
            return classDescriptor.getUnsubstitutedInnerClassesScope();
        }
        if (this.languageVersionSettings.supportsFeature(LanguageFeature.ProhibitVisibilityOfNestedClassifiersFromSupertypesOfCompanion)) {
            return null;
        }
        MemberScope unsubstitutedInnerClassesScope = classDescriptor.getUnsubstitutedInnerClassesScope();
        Intrinsics.checkNotNullExpressionValue(unsubstitutedInnerClassesScope, "getUnsubstitutedInnerClassesScope(...)");
        return new DeprecatedMemberScope(unsubstitutedInnerClassesScope);
    }

    private final LexicalScope packScopesOfCompanionSupertypes(ClassDescriptor classDescriptor, LexicalScope lexicalScope, DeclarationDescriptor declarationDescriptor) {
        if (this.languageVersionSettings.supportsFeature(LanguageFeature.ProhibitVisibilityOfNestedClassifiersFromSupertypesOfCompanion)) {
            return null;
        }
        LexicalScope lexicalScopeCreateInheritanceScope = lexicalScope;
        for (ClassDescriptor classDescriptor2 : CollectionsKt.asReversedMutable(DescriptorUtilsKt.getAllSuperclassesWithoutAny(classDescriptor))) {
            Intrinsics.checkNotNull(classDescriptor2);
            lexicalScopeCreateInheritanceScope = createInheritanceScope(lexicalScopeCreateInheritanceScope, declarationDescriptor, classDescriptor2, false, true);
        }
        return lexicalScopeCreateInheritanceScope;
    }

    private final <T> NotNullLazyValue<T> createLazyValue(StorageManager storageManager, Function1<? super Boolean, ? extends T> function1, Function0<? extends T> function0) {
        return storageManager.createLazyValue(function0, function1);
    }
}
