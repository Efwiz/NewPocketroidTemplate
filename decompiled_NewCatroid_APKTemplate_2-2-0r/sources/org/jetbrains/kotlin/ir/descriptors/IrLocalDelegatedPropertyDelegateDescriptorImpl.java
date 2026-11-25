package org.jetbrains.kotlin.ir.descriptors;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.VariableDescriptorImpl;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.NameUtils;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: IrDelegateDescriptor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\bJ5\u0010\r\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u0010\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u0002H\u000f0\u00112\u0006\u0010\u0012\u001a\u0002H\u000fH\u0016¢\u0006\u0002\u0010\u0013J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u000e\u0010\u0016\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u001bH\u0016J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0007\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrLocalDelegatedPropertyDelegateDescriptorImpl;", "Lorg/jetbrains/kotlin/ir/descriptors/IrLocalDelegatedPropertyDelegateDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/VariableDescriptorImpl;", "correspondingLocalProperty", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "delegateType", "Lorg/jetbrains/kotlin/types/KotlinType;", "kPropertyType", "(Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;)V", "getCorrespondingLocalProperty", "()Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "getKPropertyType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "cleanCompileTimeInitializerCache", "", "getCompileTimeInitializer", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "getVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "isLateInit", "", "isVar", "substitute", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrLocalDelegatedPropertyDelegateDescriptorImpl extends VariableDescriptorImpl implements IrLocalDelegatedPropertyDelegateDescriptor {
    private final VariableDescriptorWithAccessors correspondingLocalProperty;
    private final KotlinType kPropertyType;

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public void cleanCompileTimeInitializerCache() {
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    /* JADX INFO: renamed from: getCompileTimeInitializer */
    public ConstantValue<?> mo6754getCompileTimeInitializer() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isLateInit() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isVar() {
        return false;
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrLocalDelegatedPropertyDelegateDescriptor
    public VariableDescriptorWithAccessors getCorrespondingLocalProperty() {
        return this.correspondingLocalProperty;
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrLocalDelegatedPropertyDelegateDescriptor
    public KotlinType getKPropertyType() {
        return this.kPropertyType;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public IrLocalDelegatedPropertyDelegateDescriptorImpl(VariableDescriptorWithAccessors correspondingLocalProperty, KotlinType delegateType, KotlinType kPropertyType) {
        Intrinsics.checkNotNullParameter(correspondingLocalProperty, "correspondingLocalProperty");
        Intrinsics.checkNotNullParameter(delegateType, "delegateType");
        Intrinsics.checkNotNullParameter(kPropertyType, "kPropertyType");
        DeclarationDescriptor containingDeclaration = correspondingLocalProperty.getContainingDeclaration();
        Annotations empty = Annotations.INSTANCE.getEMPTY();
        Name name2 = correspondingLocalProperty.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        super(containingDeclaration, empty, NameUtils.propertyDelegateName(name2), delegateType, SourceElement.NO_SOURCE);
        this.correspondingLocalProperty = correspondingLocalProperty;
        this.kPropertyType = kPropertyType;
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
    public VariableDescriptor substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public DescriptorVisibility getVisibility() {
        DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
        Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
        return LOCAL;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitVariableDescriptor(this, data);
    }
}
