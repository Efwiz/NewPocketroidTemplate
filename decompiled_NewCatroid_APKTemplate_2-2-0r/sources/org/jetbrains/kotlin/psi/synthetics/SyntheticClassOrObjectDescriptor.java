package org.jetbrains.kotlin.psi.synthetics;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.SupertypeLoopChecker;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.ClassConstructorDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.ClassDescriptorBase;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.mpp.K1SyntheticClassifierSymbolMarker;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtClassBody;
import org.jetbrains.kotlin.psi.KtContextReceiver;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.psi.KtPureElement;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.resolve.DescriptorFactory;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.lazy.LazyClassContext;
import org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo;
import org.jetbrains.kotlin.resolve.lazy.data.KtClassOrObjectInfo;
import org.jetbrains.kotlin.resolve.lazy.data.KtScriptInfo;
import org.jetbrains.kotlin.resolve.lazy.declarations.ClassMemberDeclarationProvider;
import org.jetbrains.kotlin.resolve.lazy.descriptors.ClassResolutionScopesSupport;
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.AbstractClassTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: SyntheticClassOrObjectDescriptor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Ò\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003:\u0003_`aBe\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\u0006\u0010\u0016\u001a\u00020\u0013\u0012\u0006\u0010\u0017\u001a\u00020\u0018\u0012\u0006\u0010\u0019\u001a\u00020\u001a¢\u0006\u0002\u0010\u001bJ\u0010\u00108\u001a\u00020\u001e2\u0006\u0010\u0016\u001a\u00020\u0013H\u0002J\n\u00109\u001a\u0004\u0018\u00010\u0002H\u0016J\u000e\u0010:\u001a\b\u0012\u0004\u0012\u00020\u001e0$H\u0016J\u000e\u0010;\u001a\b\u0012\u0004\u0012\u00020<0$H\u0016J\u000e\u0010=\u001a\b\u0012\u0004\u0012\u0002050$H\u0016J\b\u0010>\u001a\u00020\u0018H\u0016J\b\u0010?\u001a\u00020\u0011H\u0016J\b\u0010@\u001a\u00020\u000fH\u0016J\b\u0010A\u001a\u00020\u000fH\u0016J\b\u0010B\u001a\u00020\u000fH\u0016J\b\u0010C\u001a\u00020\u000fH\u0016J\b\u0010D\u001a\u00020\u000fH\u0016J\b\u0010E\u001a\u00020\u000fH\u0016J\u000e\u0010F\u001a\b\u0012\u0004\u0012\u00020G0$H\u0016J\b\u0010H\u001a\u00020IH\u0016J\b\u0010J\u001a\u00020KH\u0016J\u0010\u0010L\u001a\u0002072\u0006\u0010M\u001a\u00020NH\u0014J\b\u0010O\u001a\u00020\u001eH\u0016J\u0010\u0010P\u001a\n\u0012\u0004\u0012\u00020R\u0018\u00010QH\u0016J\b\u0010S\u001a\u00020\u0013H\u0016J\u0018\u0010T\u001a\u00020U2\u000e\b\u0002\u00104\u001a\b\u0012\u0004\u0012\u0002050$H\u0007J\b\u0010V\u001a\u00020\u001aH\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010W\u001a\u00020\u001aH\u0016J\b\u0010X\u001a\u00020\u001aH\u0016J\b\u0010Y\u001a\u00020\u001aH\u0016J\b\u0010Z\u001a\u00020\u001aH\u0016J\b\u0010[\u001a\u00020\u001aH\u0016J\b\u0010\\\u001a\u00020\u001aH\u0016J\b\u0010]\u001a\u00020^H\u0016R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u00020\u0015X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010#\u001a\b\u0012\u0004\u0012\u00020\u001e0$X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u0011\u0010)\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0014\u0010,\u001a\b\u0012\u0004\u0012\u00020.0-X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010/\u001a\u00020\u00008BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b0\u00101R\u0012\u00102\u001a\u000603R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00104\u001a\b\u0012\u0004\u0012\u0002050$X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u000207X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006b"}, d2 = {"Lorg/jetbrains/kotlin/psi/synthetics/SyntheticClassOrObjectDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/ClassDescriptorBase;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptorWithResolutionScopes;", "Lorg/jetbrains/kotlin/mpp/K1SyntheticClassifierSymbolMarker;", "c", "Lorg/jetbrains/kotlin/resolve/lazy/LazyClassContext;", "parentClassOrObject", "Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "outerScope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "constructorVisibility", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "isCompanionObject", "", "(Lorg/jetbrains/kotlin/resolve/lazy/LazyClassContext;Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/descriptors/SourceElement;Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;Lorg/jetbrains/kotlin/descriptors/Modality;Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;Lorg/jetbrains/kotlin/descriptors/ClassKind;Z)V", "_unsubstitutedPrimaryConstructor", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "resolutionScopesSupport", "Lorg/jetbrains/kotlin/resolve/lazy/descriptors/ClassResolutionScopesSupport;", "secondaryConstructors", "", "getSecondaryConstructors", "()Ljava/util/List;", "setSecondaryConstructors", "(Ljava/util/List;)V", "syntheticDeclaration", "getSyntheticDeclaration", "()Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "syntheticSupertypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "thisDescriptor", "getThisDescriptor", "()Lorg/jetbrains/kotlin/psi/synthetics/SyntheticClassOrObjectDescriptor;", "typeConstructor", "Lorg/jetbrains/kotlin/psi/synthetics/SyntheticClassOrObjectDescriptor$SyntheticTypeConstructor;", "typeParameters", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "unsubstitutedMemberScope", "Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyClassMemberScope;", "createUnsubstitutedPrimaryConstructor", "getCompanionObjectDescriptor", "getConstructors", "getDeclaredCallableMembers", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "getDeclaredTypeParameters", "getKind", "getModality", "getScopeForClassHeaderResolution", "getScopeForCompanionObjectHeaderResolution", "getScopeForConstructorHeaderResolution", "getScopeForInitializerResolution", "getScopeForMemberDeclarationResolution", "getScopeForStaticMemberDeclarationResolution", "getSealedSubclasses", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getStaticScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope$Empty;", "getTypeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "getUnsubstitutedMemberScope", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "getUnsubstitutedPrimaryConstructor", "getValueClassRepresentation", "Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "Lorg/jetbrains/kotlin/types/SimpleType;", "getVisibility", "initialize", "", "isActual", "isData", "isExpect", "isFun", "isInline", "isInner", "isValue", "toString", "", "SyntheticClassMemberDeclarationProvider", "SyntheticDeclaration", "SyntheticTypeConstructor", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SyntheticClassOrObjectDescriptor extends ClassDescriptorBase implements ClassDescriptorWithResolutionScopes, K1SyntheticClassifierSymbolMarker {
    private final NotNullLazyValue<ClassConstructorDescriptor> _unsubstitutedPrimaryConstructor;
    private final Annotations annotations;
    private final boolean isCompanionObject;
    private final ClassKind kind;
    private final Modality modality;
    private final ClassResolutionScopesSupport resolutionScopesSupport;
    private List<? extends ClassConstructorDescriptor> secondaryConstructors;
    private final KtPureClassOrObject syntheticDeclaration;
    private final List<KotlinType> syntheticSupertypes;
    private final SyntheticTypeConstructor typeConstructor;
    private List<? extends TypeParameterDescriptor> typeParameters;
    private final LazyClassMemberScope unsubstitutedMemberScope;
    private final DescriptorVisibility visibility;

    /* JADX INFO: Access modifiers changed from: private */
    public final SyntheticClassOrObjectDescriptor getThisDescriptor() {
        return this;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: getCompanionObjectDescriptor */
    public ClassDescriptorWithResolutionScopes mo6346getCompanionObjectDescriptor() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public ValueClassRepresentation<SimpleType> getValueClassRepresentation() {
        return null;
    }

    public final void initialize() {
        initialize$default(this, null, 1, null);
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isActual() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isData() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    /* JADX INFO: renamed from: isExpect */
    public boolean get$isExpect() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isFun() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isInline() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
    /* JADX INFO: renamed from: isInner */
    public boolean getIsInner() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isValue() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotated
    public Annotations getAnnotations() {
        return this.annotations;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SyntheticClassOrObjectDescriptor(LazyClassContext c, KtPureClassOrObject parentClassOrObject, DeclarationDescriptor containingDeclaration, Name name2, SourceElement source, final LexicalScope outerScope, Modality modality, DescriptorVisibility visibility, Annotations annotations, final DescriptorVisibility constructorVisibility, ClassKind kind, boolean z) {
        super(c.getStorageManager(), containingDeclaration, name2, source, false);
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(parentClassOrObject, "parentClassOrObject");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(outerScope, "outerScope");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(constructorVisibility, "constructorVisibility");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.modality = modality;
        this.visibility = visibility;
        this.annotations = annotations;
        this.kind = kind;
        this.isCompanionObject = z;
        String strAsString = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        SyntheticDeclaration syntheticDeclaration = new SyntheticDeclaration(this, parentClassOrObject, strAsString);
        this.syntheticDeclaration = syntheticDeclaration;
        this.secondaryConstructors = CollectionsKt.emptyList();
        this.typeConstructor = new SyntheticTypeConstructor(this, c.getStorageManager());
        this.resolutionScopesSupport = new ClassResolutionScopesSupport(getThisDescriptor(), c.getStorageManager(), c.getLanguageVersionSettings(), new Function0<LexicalScope>() { // from class: org.jetbrains.kotlin.psi.synthetics.SyntheticClassOrObjectDescriptor$resolutionScopesSupport$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final LexicalScope invoke() {
                return outerScope;
            }
        });
        ArrayList arrayList = new ArrayList();
        c.getSyntheticResolveExtension().addSyntheticSupertypes(getThisDescriptor(), arrayList);
        this.syntheticSupertypes = arrayList;
        this.unsubstitutedMemberScope = new LazyClassMemberScope(c, new SyntheticClassMemberDeclarationProvider(syntheticDeclaration), this, c.getTrace(), null, null, 48, null);
        this._unsubstitutedPrimaryConstructor = c.getStorageManager().createLazyValue(new Function0<ClassConstructorDescriptor>() { // from class: org.jetbrains.kotlin.psi.synthetics.SyntheticClassOrObjectDescriptor$_unsubstitutedPrimaryConstructor$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassConstructorDescriptor invoke() {
                return this.this$0.createUnsubstitutedPrimaryConstructor(constructorVisibility);
            }
        });
        Modality modality2 = Modality.SEALED;
    }

    public final KtPureClassOrObject getSyntheticDeclaration() {
        return this.syntheticDeclaration;
    }

    public final List<ClassConstructorDescriptor> getSecondaryConstructors() {
        return this.secondaryConstructors;
    }

    public final void setSecondaryConstructors(List<? extends ClassConstructorDescriptor> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.secondaryConstructors = list;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void initialize$default(SyntheticClassOrObjectDescriptor syntheticClassOrObjectDescriptor, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = CollectionsKt.emptyList();
        }
        syntheticClassOrObjectDescriptor.initialize(list);
    }

    public final void initialize(List<? extends TypeParameterDescriptor> typeParameters) {
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        this.typeParameters = typeParameters;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public Modality getModality() {
        return this.modality;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public DescriptorVisibility getVisibility() {
        return this.visibility;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public ClassKind getKind() {
        return this.kind;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: isCompanionObject, reason: from getter */
    public boolean getIsCompanionObject() {
        return this.isCompanionObject;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptor
    public TypeConstructor getTypeConstructor() {
        return this.typeConstructor;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: getUnsubstitutedPrimaryConstructor */
    public ClassConstructorDescriptor mo6347getUnsubstitutedPrimaryConstructor() {
        return this._unsubstitutedPrimaryConstructor.invoke();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public List<ClassConstructorDescriptor> getConstructors() {
        return CollectionsKt.plus((Collection) CollectionsKt.listOf(this._unsubstitutedPrimaryConstructor.invoke()), (Iterable) this.secondaryConstructors);
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
    public List<TypeParameterDescriptor> getDeclaredTypeParameters() {
        List list = this.typeParameters;
        if (list != null) {
            return list;
        }
        Intrinsics.throwUninitializedPropertyAccessException("typeParameters");
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public MemberScope.Empty getStaticScope() {
        return MemberScope.Empty.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.descriptors.impl.ModuleAwareClassDescriptor
    public LazyClassMemberScope getUnsubstitutedMemberScope(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this.unsubstitutedMemberScope;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public List<ClassDescriptor> getSealedSubclasses() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes
    public List<CallableMemberDescriptor> getDeclaredCallableMembers() {
        Collection<DeclarationDescriptor> allDescriptors = DescriptorUtils.getAllDescriptors(this.unsubstitutedMemberScope);
        Intrinsics.checkNotNullExpressionValue(allDescriptors, "getAllDescriptors(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : allDescriptors) {
            if (obj instanceof CallableMemberDescriptor) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            if (((CallableMemberDescriptor) obj2).getKind() != CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
                arrayList2.add(obj2);
            }
        }
        return arrayList2;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes
    public LexicalScope getScopeForClassHeaderResolution() {
        return this.resolutionScopesSupport.getScopeForClassHeaderResolution().invoke();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes
    public LexicalScope getScopeForConstructorHeaderResolution() {
        return this.resolutionScopesSupport.getScopeForConstructorHeaderResolution().invoke();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes
    public LexicalScope getScopeForCompanionObjectHeaderResolution() {
        return this.resolutionScopesSupport.getScopeForCompanionObjectHeaderResolution().invoke();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes
    public LexicalScope getScopeForMemberDeclarationResolution() {
        return this.resolutionScopesSupport.getScopeForMemberDeclarationResolution().invoke();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes
    public LexicalScope getScopeForStaticMemberDeclarationResolution() {
        return this.resolutionScopesSupport.getScopeForStaticMemberDeclarationResolution().invoke();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes
    public LexicalScope getScopeForInitializerResolution() {
        throw new UnsupportedOperationException("Not supported for synthetic class or object");
    }

    public String toString() {
        return "synthetic class " + getShortName() + " in " + getContainingDeclaration();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassConstructorDescriptor createUnsubstitutedPrimaryConstructor(DescriptorVisibility constructorVisibility) {
        ClassConstructorDescriptorImpl classConstructorDescriptorImplCreatePrimaryConstructorForObject = DescriptorFactory.createPrimaryConstructorForObject(getThisDescriptor(), getSource());
        Intrinsics.checkNotNullExpressionValue(classConstructorDescriptorImplCreatePrimaryConstructorForObject, "createPrimaryConstructorForObject(...)");
        classConstructorDescriptorImplCreatePrimaryConstructorForObject.setVisibility(constructorVisibility);
        classConstructorDescriptorImplCreatePrimaryConstructorForObject.setReturnType(getDefaultType());
        return classConstructorDescriptorImplCreatePrimaryConstructorForObject;
    }

    /* JADX INFO: compiled from: SyntheticClassOrObjectDescriptor.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0014J\b\u0010\f\u001a\u00020\rH\u0016J\u000e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/psi/synthetics/SyntheticClassOrObjectDescriptor$SyntheticTypeConstructor;", "Lorg/jetbrains/kotlin/types/AbstractClassTypeConstructor;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "(Lorg/jetbrains/kotlin/psi/synthetics/SyntheticClassOrObjectDescriptor;Lorg/jetbrains/kotlin/storage/StorageManager;)V", "supertypeLoopChecker", "Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;", "getSupertypeLoopChecker", "()Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;", "computeSupertypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "getDeclarationDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "isDenotable", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class SyntheticTypeConstructor extends AbstractClassTypeConstructor {
        private final SupertypeLoopChecker supertypeLoopChecker;
        final /* synthetic */ SyntheticClassOrObjectDescriptor this$0;

        @Override // org.jetbrains.kotlin.types.TypeConstructor
        public boolean isDenotable() {
            return true;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SyntheticTypeConstructor(SyntheticClassOrObjectDescriptor syntheticClassOrObjectDescriptor, StorageManager storageManager) {
            super(storageManager);
            Intrinsics.checkNotNullParameter(storageManager, "storageManager");
            this.this$0 = syntheticClassOrObjectDescriptor;
            this.supertypeLoopChecker = SupertypeLoopChecker.EMPTY.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.types.TypeConstructor
        public List<TypeParameterDescriptor> getParameters() {
            List<TypeParameterDescriptor> list = this.this$0.typeParameters;
            if (list != null) {
                return list;
            }
            Intrinsics.throwUninitializedPropertyAccessException("typeParameters");
            return null;
        }

        @Override // org.jetbrains.kotlin.types.AbstractClassTypeConstructor, org.jetbrains.kotlin.types.ClassifierBasedTypeConstructor, org.jetbrains.kotlin.types.TypeConstructor
        /* JADX INFO: renamed from: getDeclarationDescriptor */
        public ClassDescriptor getThis$0() {
            return this.this$0.getThisDescriptor();
        }

        @Override // org.jetbrains.kotlin.types.AbstractTypeConstructor
        protected Collection<KotlinType> computeSupertypes() {
            return this.this$0.syntheticSupertypes;
        }

        @Override // org.jetbrains.kotlin.types.AbstractTypeConstructor
        protected SupertypeLoopChecker getSupertypeLoopChecker() {
            return this.supertypeLoopChecker;
        }
    }

    /* JADX INFO: compiled from: SyntheticClassOrObjectDescriptor.kt */
    @Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u000b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\r0\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u000e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0011H\u0016J*\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u0015\u001a\u00020\u00162\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00190\u0018H\u0016J\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0016\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0016\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0016\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/psi/synthetics/SyntheticClassOrObjectDescriptor$SyntheticClassMemberDeclarationProvider;", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/ClassMemberDeclarationProvider;", "correspondingClassOrObject", "Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "(Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;)V", "getCorrespondingClassOrObject", "()Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "ownerInfo", "Lorg/jetbrains/kotlin/resolve/lazy/data/KtClassLikeInfo;", "getOwnerInfo", "()Lorg/jetbrains/kotlin/resolve/lazy/data/KtClassLikeInfo;", "getClassOrObjectDeclarations", "", "Lorg/jetbrains/kotlin/resolve/lazy/data/KtClassOrObjectInfo;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getDeclarationNames", "", "getDeclarations", "", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "", "getDestructuringDeclarationsEntries", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclarationEntry;", "getFunctionDeclarations", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "getPropertyDeclarations", "Lorg/jetbrains/kotlin/psi/KtProperty;", "getScriptDeclarations", "Lorg/jetbrains/kotlin/resolve/lazy/data/KtScriptInfo;", "getTypeAliasDeclarations", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class SyntheticClassMemberDeclarationProvider implements ClassMemberDeclarationProvider {
        private final KtPureClassOrObject correspondingClassOrObject;
        private final KtClassLikeInfo ownerInfo;

        public SyntheticClassMemberDeclarationProvider(KtPureClassOrObject correspondingClassOrObject) {
            Intrinsics.checkNotNullParameter(correspondingClassOrObject, "correspondingClassOrObject");
            this.correspondingClassOrObject = correspondingClassOrObject;
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.declarations.ClassMemberDeclarationProvider
        public KtPureClassOrObject getCorrespondingClassOrObject() {
            return this.correspondingClassOrObject;
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.declarations.ClassMemberDeclarationProvider
        public KtClassLikeInfo getOwnerInfo() {
            return this.ownerInfo;
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
        public List<KtDeclaration> getDeclarations(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
            Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
            Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
        public Collection<KtNamedFunction> getFunctionDeclarations(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
        public Collection<KtProperty> getPropertyDeclarations(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
        public Collection<KtDestructuringDeclarationEntry> getDestructuringDeclarationsEntries(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
        public Collection<KtClassOrObjectInfo<?>> getClassOrObjectDeclarations(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
        public Collection<KtScriptInfo> getScriptDeclarations(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
        public Collection<KtTypeAlias> getTypeAliasDeclarations(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
        public Set<Name> getDeclarationNames() {
            return SetsKt.emptySet();
        }
    }

    /* JADX INFO: compiled from: SyntheticClassOrObjectDescriptor.kt */
    @Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0080\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\u0007\u001a\u00020\bJ\n\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u000e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\fH\u0016J\u000e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\fH\u0016J\n\u0010\u0014\u001a\u0004\u0018\u00010\u0005H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\n\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\n\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0016J\u000e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\fH\u0016J\b\u0010\u001d\u001a\u00020\u0016H\u0016J\u000e\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\fH\u0016J\u000e\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\fH\u0016J\b\u0010\"\u001a\u00020#H\u0016J\b\u0010$\u001a\u00020#H\u0016J\b\u0010%\u001a\u00020#H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/psi/synthetics/SyntheticClassOrObjectDescriptor$SyntheticDeclaration;", "Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "_parent", "Lorg/jetbrains/kotlin/psi/KtPureElement;", "_name", "", "(Lorg/jetbrains/kotlin/psi/synthetics/SyntheticClassOrObjectDescriptor;Lorg/jetbrains/kotlin/psi/KtPureElement;Ljava/lang/String;)V", "descriptor", "Lorg/jetbrains/kotlin/psi/synthetics/SyntheticClassOrObjectDescriptor;", "getBody", "Lorg/jetbrains/kotlin/psi/KtClassBody;", "getCompanionObjects", "", "Lorg/jetbrains/kotlin/psi/KtObjectDeclaration;", "getContainingKtFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "getContextReceivers", "Lorg/jetbrains/kotlin/psi/KtContextReceiver;", "getDeclarations", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getName", "getParent", "Lorg/jetbrains/kotlin/psi/KtElement;", "getPrimaryConstructor", "Lorg/jetbrains/kotlin/psi/KtPrimaryConstructor;", "getPrimaryConstructorModifierList", "Lorg/jetbrains/kotlin/psi/KtModifierList;", "getPrimaryConstructorParameters", "Lorg/jetbrains/kotlin/psi/KtParameter;", "getPsiOrParent", "getSecondaryConstructors", "Lorg/jetbrains/kotlin/psi/KtSecondaryConstructor;", "getSuperTypeListEntries", "Lorg/jetbrains/kotlin/psi/KtSuperTypeListEntry;", "hasExplicitPrimaryConstructor", "", "hasPrimaryConstructor", "isLocal", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class SyntheticDeclaration implements KtPureClassOrObject {
        private final String _name;
        private final KtPureElement _parent;
        final /* synthetic */ SyntheticClassOrObjectDescriptor this$0;

        @Override // org.jetbrains.kotlin.psi.KtPureClassOrObject
        public KtClassBody getBody() {
            return null;
        }

        @Override // org.jetbrains.kotlin.psi.KtPureClassOrObject
        public KtPrimaryConstructor getPrimaryConstructor() {
            return null;
        }

        @Override // org.jetbrains.kotlin.psi.KtPureClassOrObject
        public KtModifierList getPrimaryConstructorModifierList() {
            return null;
        }

        @Override // org.jetbrains.kotlin.psi.KtPureClassOrObject
        public boolean hasExplicitPrimaryConstructor() {
            return false;
        }

        @Override // org.jetbrains.kotlin.psi.KtPureClassOrObject
        public boolean hasPrimaryConstructor() {
            return false;
        }

        @Override // org.jetbrains.kotlin.psi.KtPureClassOrObject
        public boolean isLocal() {
            return false;
        }

        public SyntheticDeclaration(SyntheticClassOrObjectDescriptor syntheticClassOrObjectDescriptor, KtPureElement _parent, String _name) {
            Intrinsics.checkNotNullParameter(_parent, "_parent");
            Intrinsics.checkNotNullParameter(_name, "_name");
            this.this$0 = syntheticClassOrObjectDescriptor;
            this._parent = _parent;
            this._name = _name;
        }

        public final SyntheticClassOrObjectDescriptor descriptor() {
            return this.this$0.getThisDescriptor();
        }

        @Override // org.jetbrains.kotlin.psi.KtPureClassOrObject
        /* JADX INFO: renamed from: getName, reason: from getter */
        public String get_name() {
            return this._name;
        }

        @Override // org.jetbrains.kotlin.psi.KtDeclarationContainer
        public List<KtDeclaration> getDeclarations() {
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.psi.KtPureClassOrObject
        public List<KtSuperTypeListEntry> getSuperTypeListEntries() {
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.psi.KtPureClassOrObject
        public List<KtObjectDeclaration> getCompanionObjects() {
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.psi.KtPureClassOrObject
        public List<KtParameter> getPrimaryConstructorParameters() {
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.psi.KtPureClassOrObject
        public List<KtSecondaryConstructor> getSecondaryConstructors() {
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.psi.KtPureClassOrObject
        public List<KtContextReceiver> getContextReceivers() {
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.psi.KtPureElement
        public KtElement getPsiOrParent() {
            KtElement psiOrParent = this._parent.getPsiOrParent();
            Intrinsics.checkNotNullExpressionValue(psiOrParent, "getPsiOrParent(...)");
            return psiOrParent;
        }

        @Override // org.jetbrains.kotlin.psi.KtPureElement
        public KtElement getParent() {
            KtElement psiOrParent = this._parent.getPsiOrParent();
            Intrinsics.checkNotNullExpressionValue(psiOrParent, "getPsiOrParent(...)");
            return psiOrParent;
        }

        @Override // org.jetbrains.kotlin.psi.KtPureElement
        public KtFile getContainingKtFile() {
            KtFile containingKtFile = this._parent.getContainingKtFile();
            if (containingKtFile != null) {
                return containingKtFile;
            }
            throw new IllegalStateException("containingKtFile was null for " + this._parent + " of " + this._parent.getClass());
        }
    }
}
