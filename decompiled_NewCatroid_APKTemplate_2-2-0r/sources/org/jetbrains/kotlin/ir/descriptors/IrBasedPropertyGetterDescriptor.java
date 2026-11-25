package org.jetbrains.kotlin.ir.descriptors;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: IrBasedDescriptors.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J7\u0010\u0006\u001a\u0002H\u0007\"\u0004\b\u0000\u0010\u0007\"\u0004\b\u0001\u0010\b2\u0014\u0010\t\u001a\u0010\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\b\u0018\u00010\n2\u0006\u0010\u000b\u001a\u0002H\bH\u0016¢\u0006\u0002\u0010\fJ\b\u0010\r\u001a\u00020\u0000H\u0016J\u000e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00020\u000fH\u0016¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrBasedPropertyGetterDescriptor;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedPropertyAccessorDescriptor;", "Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;", "owner", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "getOriginal", "getOverriddenDescriptors", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IrBasedPropertyGetterDescriptor extends IrBasedPropertyAccessorDescriptor implements PropertyGetterDescriptor {
    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedPropertyAccessorDescriptor, org.jetbrains.kotlin.ir.descriptors.IrBasedSimpleFunctionDescriptor, org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public IrBasedPropertyGetterDescriptor getOriginal() {
        return this;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrBasedPropertyGetterDescriptor(IrSimpleFunction owner) {
        super(owner);
        Intrinsics.checkNotNullParameter(owner, "owner");
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedPropertyAccessorDescriptor, org.jetbrains.kotlin.ir.descriptors.IrBasedSimpleFunctionDescriptor, org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<PropertyGetterDescriptor> getOverriddenDescriptors() {
        List<PropertyAccessorDescriptor> overriddenDescriptors = super.getOverriddenDescriptors();
        ArrayList arrayList = new ArrayList(overriddenDescriptors.size());
        for (PropertyAccessorDescriptor propertyAccessorDescriptor : overriddenDescriptors) {
            Intrinsics.checkNotNull(propertyAccessorDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor");
            arrayList.add((PropertyGetterDescriptor) propertyAccessorDescriptor);
        }
        return CollectionsKt.compactIfPossible(arrayList);
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedSimpleFunctionDescriptor, org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNull(visitor);
        return visitor.visitPropertyGetterDescriptor(this, data);
    }
}
