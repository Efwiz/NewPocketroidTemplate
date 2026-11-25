package org.jetbrains.kotlin.ir.descriptors;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.PropertyDescriptorImpl;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: IrDelegateDescriptor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ5\u0010\u000b\u001a\u0002H\f\"\u0004\b\u0000\u0010\f\"\u0004\b\u0001\u0010\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\r0\u000f2\u0006\u0010\u0010\u001a\u0002H\rH\u0016¢\u0006\u0002\u0010\u0011J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u000e\u0010\u0014\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u00132\b\u0010\u001b\u001a\u0004\u0018\u00010\u0007J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrDelegateDescriptorBase;", "Lorg/jetbrains/kotlin/descriptors/impl/PropertyDescriptorImpl;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "delegateType", "Lorg/jetbrains/kotlin/types/KotlinType;", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "cleanCompileTimeInitializerCache", "", "getCompileTimeInitializer", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "getVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "isVar", "", "setOutType", "outType", "substitute", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrDelegateDescriptorBase extends PropertyDescriptorImpl {
    @Override // org.jetbrains.kotlin.descriptors.impl.VariableDescriptorWithInitializerImpl, org.jetbrains.kotlin.descriptors.VariableDescriptor
    public void cleanCompileTimeInitializerCache() {
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.VariableDescriptorWithInitializerImpl, org.jetbrains.kotlin.descriptors.VariableDescriptor
    /* JADX INFO: renamed from: getCompileTimeInitializer */
    public ConstantValue<?> mo6754getCompileTimeInitializer() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.VariableDescriptorWithInitializerImpl, org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isVar() {
        return false;
    }

    public /* synthetic */ IrDelegateDescriptorBase(DeclarationDescriptor declarationDescriptor, Name name2, KotlinType kotlinType, Annotations annotations, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(declarationDescriptor, name2, kotlinType, (i & 8) != 0 ? Annotations.INSTANCE.getEMPTY() : annotations);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrDelegateDescriptorBase(DeclarationDescriptor containingDeclaration, Name name2, KotlinType delegateType, Annotations annotations) {
        super(containingDeclaration, null, annotations, Modality.FINAL, DescriptorVisibilities.PRIVATE, false, name2, CallableMemberDescriptor.Kind.SYNTHESIZED, SourceElement.NO_SOURCE, false, false, false, false, false, true);
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(delegateType, "delegateType");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        List<? extends TypeParameterDescriptor> listEmptyList = CollectionsKt.emptyList();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        setType(delegateType, listEmptyList, classDescriptor != null ? classDescriptor.getThisAsReceiverParameter() : null, null, CollectionsKt.emptyList());
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.VariableDescriptorImpl
    public final void setOutType(KotlinType outType) {
        super.setOutType(outType);
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.PropertyDescriptorImpl, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public DescriptorVisibility getVisibility() {
        DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
        Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
        return PRIVATE;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.PropertyDescriptorImpl, org.jetbrains.kotlin.descriptors.VariableDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
    public PropertyDescriptor substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        throw new UnsupportedOperationException("Property delegate descriptor shouldn't be substituted: " + this);
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.PropertyDescriptorImpl, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitPropertyDescriptor(this, data);
    }
}
