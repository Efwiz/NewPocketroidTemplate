package org.jetbrains.kotlin.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: AbstractTypeAliasDescriptor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0089\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000*\u0001\u001d\b&\u0018\u00002\u00020\u00012\u00020\u0002B5\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJ5\u0010\u001f\u001a\u0002H \"\u0004\b\u0000\u0010 \"\u0004\b\u0001\u0010!2\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u0002H \u0012\u0004\u0012\u0002H!0#2\u0006\u0010$\u001a\u0002H!H\u0016¢\u0006\u0002\u0010%J\b\u0010&\u001a\u00020'H\u0004J\u000e\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018H\u0016J\b\u0010)\u001a\u00020*H\u0016J\b\u0010+\u001a\u00020\u0002H\u0016J\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011J\b\u0010-\u001a\u00020.H\u0016J\u000e\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018H$J\b\u00100\u001a\u00020\u000eH\u0016J\u0014\u00101\u001a\u0002022\f\u00103\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018J\b\u00104\u001a\u000205H\u0016J\b\u00106\u001a\u000205H\u0016J\b\u00107\u001a\u000205H\u0016J\b\u00108\u001a\u000205H\u0016J\b\u00109\u001a\u00020:H\u0016R!\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00118VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018X\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\u00020\u0004X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0010\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001eR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006;"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/impl/AbstractTypeAliasDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/DeclarationDescriptorNonRootImpl;", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "name", "Lorg/jetbrains/kotlin/name/Name;", "sourceElement", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "visibilityImpl", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/descriptors/SourceElement;Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;)V", "constructors", "", "Lorg/jetbrains/kotlin/descriptors/impl/TypeAliasConstructorDescriptor;", "getConstructors", "()Ljava/util/Collection;", "constructors$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "declaredTypeParametersImpl", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getStorageManager", "()Lorg/jetbrains/kotlin/storage/StorageManager;", "typeConstructor", "org/jetbrains/kotlin/descriptors/impl/AbstractTypeAliasDescriptor$typeConstructor$1", "Lorg/jetbrains/kotlin/descriptors/impl/AbstractTypeAliasDescriptor$typeConstructor$1;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "computeDefaultType", "Lorg/jetbrains/kotlin/types/SimpleType;", "getDeclaredTypeParameters", "getModality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getOriginal", "getTypeAliasConstructors", "getTypeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "getTypeConstructorTypeParameters", "getVisibility", "initialize", "", "declaredTypeParameters", "isActual", "", "isExpect", "isExternal", "isInner", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractTypeAliasDescriptor extends DeclarationDescriptorNonRootImpl implements TypeAliasDescriptor {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(AbstractTypeAliasDescriptor.class), "constructors", "getConstructors()Ljava/util/Collection;"))};

    /* JADX INFO: renamed from: constructors$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue constructors;
    private List<? extends TypeParameterDescriptor> declaredTypeParametersImpl;
    private final StorageManager storageManager;
    private final AbstractTypeAliasDescriptor$typeConstructor$1 typeConstructor;
    private final DescriptorVisibility visibilityImpl;

    protected abstract List<TypeParameterDescriptor> getTypeConstructorTypeParameters();

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isActual() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    /* JADX INFO: renamed from: isExpect */
    public boolean get$isExpect() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isExternal() {
        return false;
    }

    protected final StorageManager getStorageManager() {
        return this.storageManager;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r2v2, types: [org.jetbrains.kotlin.descriptors.impl.AbstractTypeAliasDescriptor$typeConstructor$1] */
    public AbstractTypeAliasDescriptor(StorageManager storageManager, DeclarationDescriptor containingDeclaration, Annotations annotations, Name name2, SourceElement sourceElement, DescriptorVisibility visibilityImpl) {
        super(containingDeclaration, annotations, name2, sourceElement);
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(sourceElement, "sourceElement");
        Intrinsics.checkNotNullParameter(visibilityImpl, "visibilityImpl");
        this.storageManager = storageManager;
        this.visibilityImpl = visibilityImpl;
        this.constructors = storageManager.createLazyValue(new Function0<Collection<? extends TypeAliasConstructorDescriptor>>() { // from class: org.jetbrains.kotlin.descriptors.impl.AbstractTypeAliasDescriptor$constructors$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Collection<? extends TypeAliasConstructorDescriptor> invoke() {
                return this.this$0.getTypeAliasConstructors();
            }
        });
        this.typeConstructor = new TypeConstructor() { // from class: org.jetbrains.kotlin.descriptors.impl.AbstractTypeAliasDescriptor$typeConstructor$1
            @Override // org.jetbrains.kotlin.types.TypeConstructor
            public boolean isDenotable() {
                return true;
            }

            @Override // org.jetbrains.kotlin.types.TypeConstructor
            /* JADX INFO: renamed from: getDeclarationDescriptor */
            public TypeAliasDescriptor getThis$0() {
                return this.this$0;
            }

            @Override // org.jetbrains.kotlin.types.TypeConstructor
            public List<TypeParameterDescriptor> getParameters() {
                return this.this$0.getTypeConstructorTypeParameters();
            }

            @Override // org.jetbrains.kotlin.types.TypeConstructor
            /* JADX INFO: renamed from: getSupertypes */
            public Collection<KotlinType> mo7438getSupertypes() {
                Collection<KotlinType> collectionMo7438getSupertypes = getThis$0().getUnderlyingType().getConstructor().mo7438getSupertypes();
                Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
                return collectionMo7438getSupertypes;
            }

            @Override // org.jetbrains.kotlin.types.TypeConstructor
            public boolean isFinal() {
                return getThis$0().getUnderlyingType().getConstructor().isFinal();
            }

            @Override // org.jetbrains.kotlin.types.TypeConstructor
            public KotlinBuiltIns getBuiltIns() {
                return DescriptorUtilsKt.getBuiltIns(getThis$0());
            }

            public String toString() {
                return "[typealias " + getThis$0().getShortName().asString() + ']';
            }

            @Override // org.jetbrains.kotlin.types.TypeConstructor
            public TypeConstructor refine(KotlinTypeRefiner kotlinTypeRefiner) {
                Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
                return this;
            }
        };
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeAliasDescriptor
    public Collection<TypeAliasConstructorDescriptor> getConstructors() {
        return (Collection) StorageKt.getValue(this.constructors, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    public final void initialize(List<? extends TypeParameterDescriptor> declaredTypeParameters) {
        Intrinsics.checkNotNullParameter(declaredTypeParameters, "declaredTypeParameters");
        this.declaredTypeParametersImpl = declaredTypeParameters;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitTypeAliasDescriptor(this, data);
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
    /* JADX INFO: renamed from: isInner */
    public boolean getIsInner() {
        return TypeUtils.contains(getUnderlyingType(), new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.descriptors.impl.AbstractTypeAliasDescriptor.isInner.1
            {
                super(1);
            }

            /* JADX WARN: Code duplicated, block: B:9:0x0028  */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType unwrappedType) {
                boolean z;
                Intrinsics.checkNotNull(unwrappedType);
                if (KotlinTypeKt.isError(unwrappedType)) {
                    z = false;
                } else {
                    AbstractTypeAliasDescriptor abstractTypeAliasDescriptor = AbstractTypeAliasDescriptor.this;
                    ClassifierDescriptor this$0 = unwrappedType.getConstructor().getThis$0();
                    if (!(this$0 instanceof TypeParameterDescriptor) || Intrinsics.areEqual(((TypeParameterDescriptor) this$0).getContainingDeclaration(), abstractTypeAliasDescriptor)) {
                        z = false;
                    } else {
                        z = true;
                    }
                }
                return Boolean.valueOf(z);
            }
        });
    }

    public final Collection<TypeAliasConstructorDescriptor> getTypeAliasConstructors() {
        ClassDescriptor classDescriptor = getClassDescriptor();
        if (classDescriptor == null) {
            return CollectionsKt.emptyList();
        }
        Collection<ClassConstructorDescriptor> constructors = classDescriptor.getConstructors();
        Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
        ArrayList arrayList = new ArrayList();
        for (ClassConstructorDescriptor classConstructorDescriptor : constructors) {
            Intrinsics.checkNotNull(classConstructorDescriptor);
            TypeAliasConstructorDescriptor typeAliasConstructorDescriptorCreateIfAvailable = TypeAliasConstructorDescriptorImpl.INSTANCE.createIfAvailable(this.storageManager, this, classConstructorDescriptor);
            if (typeAliasConstructorDescriptorCreateIfAvailable != null) {
                arrayList.add(typeAliasConstructorDescriptorCreateIfAvailable);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
    public List<TypeParameterDescriptor> getDeclaredTypeParameters() {
        List list = this.declaredTypeParametersImpl;
        if (list != null) {
            return list;
        }
        Intrinsics.throwUninitializedPropertyAccessException("declaredTypeParametersImpl");
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public Modality getModality() {
        return Modality.FINAL;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
    /* JADX INFO: renamed from: getVisibility, reason: from getter */
    public DescriptorVisibility getVisibilityImpl() {
        return this.visibilityImpl;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptor
    public TypeConstructor getTypeConstructor() {
        return this.typeConstructor;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorImpl
    /* JADX INFO: renamed from: toString */
    public String getDebugString() {
        return "typealias " + getShortName().asString();
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorNonRootImpl, org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorImpl, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public TypeAliasDescriptor getOriginal() {
        DeclarationDescriptorWithSource original = super.getOriginal();
        Intrinsics.checkNotNull(original, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.TypeAliasDescriptor");
        return (TypeAliasDescriptor) original;
    }

    protected final SimpleType computeDefaultType() {
        MemberScope.Empty unsubstitutedMemberScope;
        AbstractTypeAliasDescriptor abstractTypeAliasDescriptor = this;
        ClassDescriptor classDescriptor = getClassDescriptor();
        if (classDescriptor == null || (unsubstitutedMemberScope = classDescriptor.getUnsubstitutedMemberScope()) == null) {
            unsubstitutedMemberScope = MemberScope.Empty.INSTANCE;
        }
        SimpleType simpleTypeMakeUnsubstitutedType = TypeUtils.makeUnsubstitutedType(abstractTypeAliasDescriptor, unsubstitutedMemberScope, new Function1<KotlinTypeRefiner, SimpleType>() { // from class: org.jetbrains.kotlin.descriptors.impl.AbstractTypeAliasDescriptor.computeDefaultType.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final SimpleType invoke(KotlinTypeRefiner kotlinTypeRefiner) {
                ClassifierDescriptor classifierDescriptorRefineDescriptor = kotlinTypeRefiner.refineDescriptor(AbstractTypeAliasDescriptor.this);
                if (classifierDescriptorRefineDescriptor != null) {
                    return classifierDescriptorRefineDescriptor.getDefaultType();
                }
                return null;
            }
        });
        Intrinsics.checkNotNullExpressionValue(simpleTypeMakeUnsubstitutedType, "makeUnsubstitutedType(...)");
        return simpleTypeMakeUnsubstitutedType;
    }
}
