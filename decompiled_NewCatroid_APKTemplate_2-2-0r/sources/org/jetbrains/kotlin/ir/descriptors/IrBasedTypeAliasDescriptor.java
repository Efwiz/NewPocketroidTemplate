package org.jetbrains.kotlin.ir.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: IrBasedDescriptors.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0002¢\u0006\u0002\u0010\u0005J5\u0010\u0015\u001a\u0002H\u0016\"\u0004\b\u0000\u0010\u0016\"\u0004\b\u0001\u0010\u00172\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u0002H\u0016\u0012\u0004\u0012\u0002H\u00170\u00192\u0006\u0010\u001a\u001a\u0002H\u0017H\u0016¢\u0006\u0002\u0010\u001bJ\u001c\u0010\u001c\u001a\u00020\u001d2\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u001e0\u0019H\u0016J\u000e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 H\u0016J\b\u0010\"\u001a\u00020\u0010H\u0016J\b\u0010#\u001a\u00020$H\u0016J\b\u0010%\u001a\u00020&H\u0016J\b\u0010'\u001a\u00020\u0003H\u0016J\b\u0010(\u001a\u00020)H\u0016J\b\u0010*\u001a\u00020+H\u0016J\b\u0010,\u001a\u00020-H\u0016J\b\u0010.\u001a\u00020/H\u0016J\b\u00100\u001a\u00020/H\u0016J\b\u00101\u001a\u00020/H\u0016J\b\u00102\u001a\u00020/H\u0016J\u0010\u00103\u001a\u0002042\u0006\u00105\u001a\u000206H\u0016R\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0012¨\u00067"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrBasedTypeAliasDescriptor;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedDeclarationDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "owner", "(Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;)V", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getClassDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "constructors", "", "Lorg/jetbrains/kotlin/descriptors/impl/TypeAliasConstructorDescriptor;", "getConstructors", "()Ljava/util/Collection;", "expandedType", "Lorg/jetbrains/kotlin/types/SimpleType;", "getExpandedType", "()Lorg/jetbrains/kotlin/types/SimpleType;", "underlyingType", "getUnderlyingType", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", "getDeclaredTypeParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getDefaultType", "getModality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getOriginal", "getSource", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getTypeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "getVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "isActual", "", "isExpect", "isExternal", "isInner", "substitute", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IrBasedTypeAliasDescriptor extends IrBasedDeclarationDescriptor<IrTypeAlias> implements TypeAliasDescriptor {
    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    /* JADX INFO: renamed from: isExpect */
    public boolean get$isExpect() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isExternal() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
    /* JADX INFO: renamed from: isInner */
    public boolean getIsInner() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrBasedTypeAliasDescriptor(IrTypeAlias owner) {
        super(owner);
        Intrinsics.checkNotNullParameter(owner, "owner");
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeAliasDescriptor
    public SimpleType getUnderlyingType() {
        throw new UnsupportedOperationException("Unexpected use of IrBasedTypeAliasDescriptor " + this);
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeAliasDescriptor
    public Collection<TypeAliasConstructorDescriptor> getConstructors() {
        throw new UnsupportedOperationException("Unexpected use of IrBasedTypeAliasDescriptor " + this);
    }

    @Override // org.jetbrains.kotlin.descriptors.Substitutable
    public ClassifierDescriptorWithTypeParameters substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        throw new UnsupportedOperationException("IrBased descriptors should not be substituted");
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptor
    public SimpleType getDefaultType() {
        throw new UnsupportedOperationException("Unexpected use of IrBasedTypeAliasDescriptor " + this);
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptor
    public TypeConstructor getTypeConstructor() {
        throw new UnsupportedOperationException("Unexpected use of IrBasedTypeAliasDescriptor " + this);
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeAliasDescriptor
    public SimpleType getExpandedType() {
        KotlinType irBasedKotlinType = toIrBasedKotlinType(getOwner().getExpandedType());
        Intrinsics.checkNotNull(irBasedKotlinType, "null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        return (SimpleType) irBasedKotlinType;
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeAliasDescriptor
    public ClassDescriptor getClassDescriptor() {
        throw new NotImplementedError("An operation is not implemented: catch type alias class descriptor");
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public TypeAliasDescriptor getOriginal() {
        return this;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
    public List<TypeParameterDescriptor> getDeclaredTypeParameters() {
        List<IrTypeParameter> typeParameters = getOwner().getTypeParameters();
        ArrayList arrayList = new ArrayList(typeParameters.size());
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(IrBasedDescriptorsKt.toIrBasedDescriptor((IrTypeParameter) it.next()));
        }
        return CollectionsKt.compactIfPossible(arrayList);
    }

    @Override // org.jetbrains.kotlin.descriptors.Named
    /* JADX INFO: renamed from: getName */
    public Name getShortName() {
        return getOwner().getName();
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public Modality getModality() {
        return Modality.FINAL;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource
    /* JADX INFO: renamed from: getSource */
    public SourceElement getSourceElement() {
        SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
        return NO_SOURCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
    /* JADX INFO: renamed from: getVisibility */
    public DescriptorVisibility getVisibilityImpl() {
        return getOwner().getVisibility();
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isActual() {
        return getOwner().getIsActual();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitTypeAliasDescriptor(this, data);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: acceptVoid */
    public void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        visitor.visitTypeAliasDescriptor(this, null);
    }
}
