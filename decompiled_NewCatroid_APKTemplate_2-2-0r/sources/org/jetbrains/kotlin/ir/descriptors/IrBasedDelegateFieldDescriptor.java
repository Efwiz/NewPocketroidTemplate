package org.jetbrains.kotlin.ir.descriptors;

import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: IrBasedDescriptors.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrBasedDelegateFieldDescriptor;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedFieldDescriptor;", "Lorg/jetbrains/kotlin/ir/descriptors/IrImplementingDelegateDescriptor;", "owner", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "(Lorg/jetbrains/kotlin/ir/declarations/IrField;)V", "correspondingSuperType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getCorrespondingSuperType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "isDelegated", "", "()Z", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrBasedDelegateFieldDescriptor extends IrBasedFieldDescriptor implements IrImplementingDelegateDescriptor {
    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedFieldDescriptor, org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors
    public boolean isDelegated() {
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrBasedDelegateFieldDescriptor(IrField owner) {
        super(owner);
        Intrinsics.checkNotNullParameter(owner, "owner");
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrImplementingDelegateDescriptor
    public KotlinType getCorrespondingSuperType() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }
}
