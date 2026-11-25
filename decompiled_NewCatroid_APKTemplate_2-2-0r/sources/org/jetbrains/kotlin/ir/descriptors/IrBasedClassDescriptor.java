package org.jetbrains.kotlin.ir.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.utils.CollectionsKt;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: IrBasedDescriptors.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000®\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J7\u0010\u0011\u001a\u0002H\u0012\"\u0004\b\u0000\u0010\u0012\"\u0004\b\u0001\u0010\u00132\u0014\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u0002H\u0012\u0012\u0004\u0012\u0002H\u0013\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u0002H\u0013H\u0016¢\u0006\u0002\u0010\u0017J\u001e\u0010\u0018\u001a\u00020\u00192\u0014\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0015H\u0016J\u000e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH\u0002J\n\u0010\u001e\u001a\u0004\u0018\u00010\u0000H\u0016J\u000e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u001cH\u0016J\u000e\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u001cH\u0016J\u000e\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u001cH\u0016J\n\u0010%\u001a\u0004\u0018\u00010\u0007H\u0016J\b\u0010&\u001a\u00020\u0007H\u0016J\b\u0010'\u001a\u00020(H\u0016J\u0018\u0010)\u001a\u00020*2\u000e\u0010+\u001a\n\u0012\u0006\b\u0001\u0012\u00020-0,H\u0016J\u0010\u0010)\u001a\u00020*2\u0006\u0010.\u001a\u00020/H\u0016J\b\u00100\u001a\u000201H\u0016J\b\u00102\u001a\u000203H\u0016J\b\u00104\u001a\u00020\u0000H\u0016J\u000e\u00105\u001a\b\u0012\u0004\u0012\u00020\u000106H\u0016J\b\u00107\u001a\u000208H\u0016J\b\u00109\u001a\u00020*H\u0016J\b\u0010:\u001a\u00020\"H\u0016J\b\u0010;\u001a\u00020\rH\u0016J\b\u0010<\u001a\u00020*H\u0016J\b\u0010=\u001a\u00020*H\u0016J\n\u0010>\u001a\u0004\u0018\u00010 H\u0016J\u0010\u0010?\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010@H\u0016J\b\u0010A\u001a\u00020BH\u0016J\b\u0010C\u001a\u00020DH\u0016J\b\u0010E\u001a\u00020DH\u0016J\b\u0010F\u001a\u00020DH\u0016J\b\u0010G\u001a\u00020DH\u0016J\b\u0010H\u001a\u00020DH\u0016J\b\u0010I\u001a\u00020DH\u0016J\b\u0010J\u001a\u00020DH\u0016J\b\u0010K\u001a\u00020DH\u0016J\b\u0010L\u001a\u00020DH\u0016J\b\u0010M\u001a\u00020DH\u0016J\u0010\u0010N\u001a\u00020O2\u0006\u0010P\u001a\u00020QH\u0016R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u001b\u0010\f\u001a\u00020\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u000e\u0010\u000f¨\u0006R"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrBasedClassDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedDeclarationDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "owner", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)V", "_defaultType", "Lorg/jetbrains/kotlin/types/SimpleType;", "get_defaultType", "()Lorg/jetbrains/kotlin/types/SimpleType;", "_defaultType$delegate", "Lkotlin/Lazy;", "_typeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "get_typeConstructor", "()Lorg/jetbrains/kotlin/types/TypeConstructor;", "_typeConstructor$delegate", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", "collectTypeParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getCompanionObjectDescriptor", "getConstructors", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedClassConstructorDescriptor;", "getContextReceivers", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getDeclaredTypeParameters", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedTypeParameterDescriptor;", "getDefaultFunctionTypeForSamInterface", "getDefaultType", "getKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "getMemberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope$Empty;", "typeArguments", "", "Lorg/jetbrains/kotlin/types/TypeProjection;", "typeSubstitution", "Lorg/jetbrains/kotlin/types/TypeSubstitution;", "getModality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getOriginal", "getSealedSubclasses", "", "getSource", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getStaticScope", "getThisAsReceiverParameter", "getTypeConstructor", "getUnsubstitutedInnerClassesScope", "getUnsubstitutedMemberScope", "getUnsubstitutedPrimaryConstructor", "getValueClassRepresentation", "Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "getVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "isActual", "", "isCompanionObject", "isData", "isDefinitelyNotSamInterface", "isExpect", "isExternal", "isFun", "isInline", "isInner", "isValue", "substitute", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IrBasedClassDescriptor extends IrBasedDeclarationDescriptor<IrClass> implements ClassDescriptor {

    /* JADX INFO: renamed from: _defaultType$delegate, reason: from kotlin metadata */
    private final Lazy _defaultType;

    /* JADX INFO: renamed from: _typeConstructor$delegate, reason: from kotlin metadata */
    private final Lazy _typeConstructor;

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public IrBasedClassDescriptor getOriginal() {
        return this;
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    /* JADX INFO: renamed from: isActual */
    public boolean getIsActual() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrBasedClassDescriptor(final IrClass owner) {
        super(owner);
        Intrinsics.checkNotNullParameter(owner, "owner");
        this._defaultType = LazyKt.lazy(new Function0<SimpleType>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrBasedClassDescriptor$_defaultType$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SimpleType invoke() {
                IrBasedClassDescriptor irBasedClassDescriptor = this.this$0;
                SimpleType simpleTypeMakeUnsubstitutedType = TypeUtils.makeUnsubstitutedType(irBasedClassDescriptor, irBasedClassDescriptor.getUnsubstitutedMemberScope(), KotlinTypeFactory.INSTANCE.getEMPTY_REFINED_TYPE_FACTORY());
                Intrinsics.checkNotNullExpressionValue(simpleTypeMakeUnsubstitutedType, "makeUnsubstitutedType(...)");
                return simpleTypeMakeUnsubstitutedType;
            }
        });
        this._typeConstructor = LazyKt.lazy(new Function0<LazyTypeConstructor>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrBasedClassDescriptor$_typeConstructor$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final LazyTypeConstructor invoke() {
                IrBasedClassDescriptor irBasedClassDescriptor = this.this$0;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0);
                final IrClass irClass = owner;
                final IrBasedClassDescriptor irBasedClassDescriptor2 = this.this$0;
                Function0<List<? extends KotlinType>> function0 = new Function0<List<? extends KotlinType>>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrBasedClassDescriptor$_typeConstructor$2.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final List<? extends KotlinType> invoke() {
                        List<IrType> superTypes = irClass.getSuperTypes();
                        IrBasedClassDescriptor irBasedClassDescriptor3 = irBasedClassDescriptor2;
                        ArrayList arrayList = new ArrayList(superTypes.size());
                        Iterator<T> it = superTypes.iterator();
                        while (it.hasNext()) {
                            arrayList.add(irBasedClassDescriptor3.toIrBasedKotlinType((IrType) it.next()));
                        }
                        return CollectionsKt.compactIfPossible(arrayList);
                    }
                };
                StorageManager NO_LOCKS = LockBasedStorageManager.NO_LOCKS;
                Intrinsics.checkNotNullExpressionValue(NO_LOCKS, "NO_LOCKS");
                return new LazyTypeConstructor(irBasedClassDescriptor, anonymousClass1, function0, NO_LOCKS);
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.descriptors.IrBasedClassDescriptor$_typeConstructor$2$1, reason: invalid class name */
            /* JADX INFO: compiled from: IrBasedDescriptors.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function0<List<? extends TypeParameterDescriptor>> {
                AnonymousClass1(Object obj) {
                    super(0, obj);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "collectTypeParameters()Ljava/util/List;";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "collectTypeParameters";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinClass(IrBasedClassDescriptor.class);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends TypeParameterDescriptor> invoke() {
                    return ((IrBasedClassDescriptor) this.receiver).collectTypeParameters();
                }
            }
        });
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
        return getOwner().getSource();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public List<IrBasedClassConstructorDescriptor> getConstructors() {
        SmartList smartListEmptyList;
        List<IrDeclaration> declarations = getOwner().getDeclarations();
        if (declarations.isEmpty()) {
            smartListEmptyList = kotlin.collections.CollectionsKt.emptyList();
        } else {
            SmartList smartList = new SmartList();
            for (Object obj : declarations) {
                if ((obj instanceof IrConstructor) && !((IrConstructor) obj).getOrigin().getIsSynthetic()) {
                    smartList.add(obj);
                }
            }
            smartListEmptyList = smartList;
        }
        Collection collection = smartListEmptyList;
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(IrBasedDescriptorsKt.toIrBasedDescriptor((IrConstructor) it.next()));
        }
        return CollectionsKt.compactIfPossible(arrayList);
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
        return getOwner().getKind();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public Modality getModality() {
        return getOwner().getModality();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: getCompanionObjectDescriptor */
    public IrBasedClassDescriptor mo6346getCompanionObjectDescriptor() {
        Object next;
        Iterator<T> it = getOwner().getDeclarations().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if ((next instanceof IrClass) && ((IrClass) next).getIsCompanion()) {
                break;
            }
        }
        IrClass irClass = (IrClass) next;
        if (irClass != null) {
            return IrBasedDescriptorsKt.toIrBasedDescriptor(irClass);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
    /* JADX INFO: renamed from: getVisibility */
    public DescriptorVisibility getVisibilityImpl() {
        return getOwner().getVisibility();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: isCompanionObject */
    public boolean getIsCompanionObject() {
        return getOwner().getIsCompanion();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isData() {
        return getOwner().getIsData();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isInline() {
        return IrDeclarationsKt.isSingleFieldValueClass(getOwner());
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isFun() {
        return getOwner().getIsFun();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isValue() {
        return getOwner().getIsValue();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public ReceiverParameterDescriptor getThisAsReceiverParameter() {
        IrValueParameter thisReceiver = getOwner().getThisReceiver();
        CallableDescriptor irBasedDescriptor = thisReceiver != null ? IrBasedDescriptorsKt.toIrBasedDescriptor(thisReceiver) : null;
        Intrinsics.checkNotNull(irBasedDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor");
        return (ReceiverParameterDescriptor) irBasedDescriptor;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public List<ReceiverParameterDescriptor> getContextReceivers() {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: getUnsubstitutedPrimaryConstructor */
    public IrBasedClassConstructorDescriptor mo6347getUnsubstitutedPrimaryConstructor() {
        List<IrDeclaration> declarations = getOwner().getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrConstructor) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        boolean z = false;
        Object obj2 = null;
        while (true) {
            if (!it.hasNext()) {
                if (!z) {
                    break;
                }
                break;
            }
            Object next = it.next();
            if (((IrConstructor) next).getIsPrimary()) {
                if (!z) {
                    z = true;
                    obj2 = next;
                }
            }
            obj2 = null;
            break;
        }
        IrConstructor irConstructor = (IrConstructor) obj2;
        if (irConstructor != null) {
            return IrBasedDescriptorsKt.toIrBasedDescriptor(irConstructor);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
    public List<IrBasedTypeParameterDescriptor> getDeclaredTypeParameters() {
        List<IrTypeParameter> typeParameters = getOwner().getTypeParameters();
        ArrayList arrayList = new ArrayList(typeParameters.size());
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(IrBasedDescriptorsKt.toIrBasedDescriptor((IrTypeParameter) it.next()));
        }
        return CollectionsKt.compactIfPossible(arrayList);
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public Collection<ClassDescriptor> getSealedSubclasses() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public ValueClassRepresentation<SimpleType> getValueClassRepresentation() {
        ValueClassRepresentation<IrSimpleType> valueClassRepresentation = getOwner().getValueClassRepresentation();
        if (valueClassRepresentation != null) {
            return valueClassRepresentation.mapUnderlyingType(new Function1<IrSimpleType, SimpleType>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrBasedClassDescriptor.getValueClassRepresentation.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final SimpleType invoke(IrSimpleType it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    KotlinType irBasedKotlinType = IrBasedClassDescriptor.this.toIrBasedKotlinType(it);
                    Intrinsics.checkNotNull(irBasedKotlinType, "null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
                    return (SimpleType) irBasedKotlinType;
                }
            });
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    /* JADX INFO: renamed from: isExpect */
    public boolean get$isExpect() {
        return getOwner().getIsExpect();
    }

    @Override // org.jetbrains.kotlin.descriptors.Substitutable
    public ClassifierDescriptorWithTypeParameters substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        throw new UnsupportedOperationException("IrBased descriptors SHOULD NOT be substituted");
    }

    private final TypeConstructor get_typeConstructor() {
        return (TypeConstructor) this._typeConstructor.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<TypeParameterDescriptor> collectTypeParameters() {
        return SequencesKt.toList(SequencesKt.map(IrTypesKt.getTypeConstructorParameters(getOwner()), new Function1<IrTypeParameter, IrBasedTypeParameterDescriptor>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrBasedClassDescriptor.collectTypeParameters.1
            @Override // kotlin.jvm.functions.Function1
            public final IrBasedTypeParameterDescriptor invoke(IrTypeParameter it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return IrBasedDescriptorsKt.toIrBasedDescriptor(it);
            }
        }));
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptor
    public TypeConstructor getTypeConstructor() {
        return get_typeConstructor();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
    /* JADX INFO: renamed from: isInner */
    public boolean getIsInner() {
        return getOwner().getIsInner();
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isExternal() {
        return getOwner().getIsExternal();
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

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public SimpleType getDefaultFunctionTypeForSamInterface() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isDefinitelyNotSamInterface() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }
}
