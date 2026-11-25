package org.jetbrains.kotlin.ir.descriptors;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: IrBasedDescriptors.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0000\b&\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\u0000H\u0016J\u000e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrBasedPropertyAccessorDescriptor;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedSimpleFunctionDescriptor;", "Lorg/jetbrains/kotlin/descriptors/PropertyAccessorDescriptor;", "owner", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "correspondingVariable", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "getCorrespondingVariable", "()Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "getCorrespondingProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getOriginal", "getOverriddenDescriptors", "", "isDefault", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrBasedPropertyAccessorDescriptor extends IrBasedSimpleFunctionDescriptor implements PropertyAccessorDescriptor {
    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedSimpleFunctionDescriptor, org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public IrBasedPropertyAccessorDescriptor getOriginal() {
        return this;
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor
    public boolean isDefault() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrBasedPropertyAccessorDescriptor(IrSimpleFunction owner) {
        super(owner);
        Intrinsics.checkNotNullParameter(owner, "owner");
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedSimpleFunctionDescriptor, org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public /* bridge */ /* synthetic */ PropertyAccessorDescriptor copy(DeclarationDescriptor declarationDescriptor, Modality modality, DescriptorVisibility descriptorVisibility, CallableMemberDescriptor.Kind kind, boolean z) {
        return (PropertyAccessorDescriptor) copy(declarationDescriptor, modality, descriptorVisibility, kind, z);
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedSimpleFunctionDescriptor, org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<PropertyAccessorDescriptor> getOverriddenDescriptors() {
        List<FunctionDescriptor> overriddenDescriptors = super.getOverriddenDescriptors();
        ArrayList arrayList = new ArrayList(overriddenDescriptors.size());
        for (FunctionDescriptor functionDescriptor : overriddenDescriptors) {
            Intrinsics.checkNotNull(functionDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor");
            arrayList.add((PropertyAccessorDescriptor) functionDescriptor);
        }
        return CollectionsKt.compactIfPossible(arrayList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor
    public PropertyDescriptor getCorrespondingProperty() {
        IrPropertySymbol correspondingPropertySymbol = ((IrSimpleFunction) getOwner()).getCorrespondingPropertySymbol();
        Intrinsics.checkNotNull(correspondingPropertySymbol);
        return IrBasedDescriptorsKt.toIrBasedDescriptor(correspondingPropertySymbol.getOwner());
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableAccessorDescriptor
    public VariableDescriptorWithAccessors getCorrespondingVariable() {
        return getCorrespondingProperty();
    }
}
