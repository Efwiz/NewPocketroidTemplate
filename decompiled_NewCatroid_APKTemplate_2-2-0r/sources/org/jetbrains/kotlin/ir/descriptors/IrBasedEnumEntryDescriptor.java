package org.jetbrains.kotlin.ir.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.types.ClassTypeConstructorImpl;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: compiled from: IrBasedDescriptors.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000¸\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J7\u0010\u0011\u001a\u0002H\u0012\"\u0004\b\u0000\u0010\u0012\"\u0004\b\u0001\u0010\u00132\u0014\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u0002H\u0012\u0012\u0004\u0012\u0002H\u0013\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u0002H\u0013H\u0016¢\u0006\u0002\u0010\u0017J\u001e\u0010\u0018\u001a\u00020\u00192\u0014\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0015H\u0016J\n\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\u000e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eH\u0016J\u000e\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u001eH\u0016J\b\u0010\"\u001a\u00020#H\u0002J\u000e\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\u001eH\u0016J\n\u0010&\u001a\u0004\u0018\u00010\u0007H\u0016J\b\u0010'\u001a\u00020\u0007H\u0016J\b\u0010(\u001a\u00020)H\u0016J\u0018\u0010*\u001a\u00020+2\u000e\u0010,\u001a\n\u0012\u0006\b\u0001\u0012\u00020.0-H\u0016J\u0010\u0010*\u001a\u00020+2\u0006\u0010/\u001a\u000200H\u0016J\b\u00101\u001a\u000202H\u0016J\b\u00103\u001a\u000204H\u0016J\b\u00105\u001a\u00020\u0000H\u0016J\u000e\u00106\u001a\b\u0012\u0004\u0012\u00020\u000107H\u0016J\u0010\u00108\u001a\n :*\u0004\u0018\u00010909H\u0016J\b\u0010;\u001a\u00020+H\u0016J\b\u0010<\u001a\u00020!H\u0016J\b\u0010=\u001a\u00020\rH\u0016J\b\u0010>\u001a\u00020+H\u0016J\b\u0010?\u001a\u00020+H\u0016J\n\u0010@\u001a\u0004\u0018\u00010AH\u0016J\u0010\u0010B\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010CH\u0016J\u0010\u0010D\u001a\n :*\u0004\u0018\u00010E0EH\u0016J\b\u0010F\u001a\u00020GH\u0016J\b\u0010H\u001a\u00020GH\u0016J\b\u0010I\u001a\u00020GH\u0016J\b\u0010J\u001a\u00020GH\u0016J\b\u0010K\u001a\u00020GH\u0016J\b\u0010L\u001a\u00020GH\u0016J\b\u0010M\u001a\u00020GH\u0016J\b\u0010N\u001a\u00020GH\u0016J\b\u0010O\u001a\u00020GH\u0016J\b\u0010P\u001a\u00020GH\u0016J\u0010\u0010Q\u001a\u00020R2\u0006\u0010S\u001a\u00020TH\u0016R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u001b\u0010\f\u001a\u00020\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u000e\u0010\u000f¨\u0006U"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrBasedEnumEntryDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedDeclarationDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "owner", "(Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;)V", "_defaultType", "Lorg/jetbrains/kotlin/types/SimpleType;", "get_defaultType", "()Lorg/jetbrains/kotlin/types/SimpleType;", "_defaultType$delegate", "Lkotlin/Lazy;", "_typeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "get_typeConstructor", "()Lorg/jetbrains/kotlin/types/TypeConstructor;", "_typeConstructor$delegate", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", "getCompanionObjectDescriptor", "", "getConstructors", "", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedClassConstructorDescriptor;", "getContextReceivers", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getCorrespondingClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getDeclaredTypeParameters", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getDefaultFunctionTypeForSamInterface", "getDefaultType", "getKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "getMemberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope$Empty;", "typeArguments", "", "Lorg/jetbrains/kotlin/types/TypeProjection;", "typeSubstitution", "Lorg/jetbrains/kotlin/types/TypeSubstitution;", "getModality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getOriginal", "getSealedSubclasses", "", "getSource", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getStaticScope", "getThisAsReceiverParameter", "getTypeConstructor", "getUnsubstitutedInnerClassesScope", "getUnsubstitutedMemberScope", "getUnsubstitutedPrimaryConstructor", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "getValueClassRepresentation", "Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "getVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "isActual", "", "isCompanionObject", "isData", "isDefinitelyNotSamInterface", "isExpect", "isExternal", "isFun", "isInline", "isInner", "isValue", "substitute", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IrBasedEnumEntryDescriptor extends IrBasedDeclarationDescriptor<IrEnumEntry> implements ClassDescriptor {

    /* JADX INFO: renamed from: _defaultType$delegate, reason: from kotlin metadata */
    private final Lazy _defaultType;

    /* JADX INFO: renamed from: _typeConstructor$delegate, reason: from kotlin metadata */
    private final Lazy _typeConstructor;

    public Void getCompanionObjectDescriptor() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public SimpleType getDefaultFunctionTypeForSamInterface() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public IrBasedEnumEntryDescriptor getOriginal() {
        return this;
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    /* JADX INFO: renamed from: isActual */
    public boolean getIsActual() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: isCompanionObject */
    public boolean getIsCompanionObject() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isData() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isDefinitelyNotSamInterface() {
        return true;
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrBasedEnumEntryDescriptor(IrEnumEntry owner) {
        super(owner);
        Intrinsics.checkNotNullParameter(owner, "owner");
        this._defaultType = LazyKt.lazy(new Function0<SimpleType>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrBasedEnumEntryDescriptor$_defaultType$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SimpleType invoke() {
                IrBasedEnumEntryDescriptor irBasedEnumEntryDescriptor = this.this$0;
                SimpleType simpleTypeMakeUnsubstitutedType = TypeUtils.makeUnsubstitutedType(irBasedEnumEntryDescriptor, irBasedEnumEntryDescriptor.getUnsubstitutedMemberScope(), KotlinTypeFactory.INSTANCE.getEMPTY_REFINED_TYPE_FACTORY());
                Intrinsics.checkNotNullExpressionValue(simpleTypeMakeUnsubstitutedType, "makeUnsubstitutedType(...)");
                return simpleTypeMakeUnsubstitutedType;
            }
        });
        this._typeConstructor = LazyKt.lazy(new Function0<ClassTypeConstructorImpl>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrBasedEnumEntryDescriptor$_typeConstructor$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassTypeConstructorImpl invoke() {
                IrBasedEnumEntryDescriptor irBasedEnumEntryDescriptor = this.this$0;
                List listEmptyList = CollectionsKt.emptyList();
                List<IrType> superTypes = this.this$0.getCorrespondingClass().getSuperTypes();
                IrBasedEnumEntryDescriptor irBasedEnumEntryDescriptor2 = this.this$0;
                ArrayList arrayList = new ArrayList(superTypes.size());
                Iterator<T> it = superTypes.iterator();
                while (it.hasNext()) {
                    arrayList.add(irBasedEnumEntryDescriptor2.toIrBasedKotlinType((IrType) it.next()));
                }
                return new ClassTypeConstructorImpl(irBasedEnumEntryDescriptor, listEmptyList, org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList), LockBasedStorageManager.NO_LOCKS);
            }
        });
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: getCompanionObjectDescriptor */
    public /* bridge */ /* synthetic */ ClassDescriptor mo6346getCompanionObjectDescriptor() {
        return (ClassDescriptor) getCompanionObjectDescriptor();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public /* bridge */ /* synthetic */ MemberScope getMemberScope(List list) {
        return getMemberScope((List<? extends TypeProjection>) list);
    }

    @Override // org.jetbrains.kotlin.descriptors.Named
    /* JADX INFO: renamed from: getName */
    public Name getShortName() {
        return getOwner().getName();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public MemberScope.Empty getMemberScope(List<? extends TypeProjection> typeArguments) {
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        return MemberScope.Empty.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public MemberScope.Empty getMemberScope(TypeSubstitution typeSubstitution) {
        Intrinsics.checkNotNullParameter(typeSubstitution, "typeSubstitution");
        return MemberScope.Empty.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public MemberScope.Empty getUnsubstitutedMemberScope() {
        return MemberScope.Empty.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public MemberScope.Empty getUnsubstitutedInnerClassesScope() {
        return MemberScope.Empty.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public MemberScope.Empty getStaticScope() {
        return MemberScope.Empty.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource
    /* JADX INFO: renamed from: getSource */
    public SourceElement getSourceElement() {
        return SourceElement.NO_SOURCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public List<IrBasedClassConstructorDescriptor> getConstructors() {
        Sequence sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(getCorrespondingClass().getDeclarations()), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrBasedEnumEntryDescriptor$getConstructors$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof IrConstructor);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        return SequencesKt.toList(SequencesKt.map(sequenceFilter, new Function1<IrConstructor, IrBasedClassConstructorDescriptor>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrBasedEnumEntryDescriptor.getConstructors.1
            @Override // kotlin.jvm.functions.Function1
            public final IrBasedClassConstructorDescriptor invoke(IrConstructor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return IrBasedDescriptorsKt.toIrBasedDescriptor(it);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrClass getCorrespondingClass() {
        IrClass correspondingClass = getOwner().getCorrespondingClass();
        if (correspondingClass != null) {
            return correspondingClass;
        }
        IrDeclarationParent parent = getOwner().getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
        return (IrClass) parent;
    }

    private final SimpleType get_defaultType() {
        return (SimpleType) this._defaultType.getValue();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.ClassifierDescriptor
    public SimpleType getDefaultType() {
        return get_defaultType();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public ClassKind getKind() {
        return ClassKind.ENUM_ENTRY;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public Modality getModality() {
        return Modality.FINAL;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
    /* JADX INFO: renamed from: getVisibility */
    public DescriptorVisibility getVisibilityImpl() {
        return DescriptorVisibilities.DEFAULT_VISIBILITY;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public ReceiverParameterDescriptor getThisAsReceiverParameter() {
        IrDeclarationParent parent = getOwner().getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
        return IrBasedDescriptorsKt.toIrBasedDescriptor((IrClass) parent).getThisAsReceiverParameter();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public List<ReceiverParameterDescriptor> getContextReceivers() {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: getUnsubstitutedPrimaryConstructor */
    public ClassConstructorDescriptor mo6347getUnsubstitutedPrimaryConstructor() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
    public List<TypeParameterDescriptor> getDeclaredTypeParameters() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public Collection<ClassDescriptor> getSealedSubclasses() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public ValueClassRepresentation<SimpleType> getValueClassRepresentation() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.Substitutable
    public ClassifierDescriptorWithTypeParameters substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        throw new UnsupportedOperationException("IrBased descriptors SHOULD NOT be substituted");
    }

    private final TypeConstructor get_typeConstructor() {
        return (TypeConstructor) this._typeConstructor.getValue();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptor
    public TypeConstructor getTypeConstructor() {
        return get_typeConstructor();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNull(visitor);
        return visitor.visitClassDescriptor(this, data);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: acceptVoid */
    public void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> visitor) {
        Intrinsics.checkNotNull(visitor);
        visitor.visitClassDescriptor(this, null);
    }
}
