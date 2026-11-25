package org.jetbrains.kotlin.ir.descriptors;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.name.NameUtils;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: IrDelegateDescriptor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B%\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\u0014\u0010\u0007\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrImplementingDelegateDescriptorImpl;", "Lorg/jetbrains/kotlin/ir/descriptors/IrDelegateDescriptorBase;", "Lorg/jetbrains/kotlin/ir/descriptors/IrImplementingDelegateDescriptor;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "delegateType", "Lorg/jetbrains/kotlin/types/KotlinType;", "correspondingSuperType", "number", "", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;I)V", "getCorrespondingSuperType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrImplementingDelegateDescriptorImpl extends IrDelegateDescriptorBase implements IrImplementingDelegateDescriptor {
    private final KotlinType correspondingSuperType;

    @Override // org.jetbrains.kotlin.ir.descriptors.IrImplementingDelegateDescriptor
    public KotlinType getCorrespondingSuperType() {
        return this.correspondingSuperType;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrImplementingDelegateDescriptorImpl(ClassDescriptor containingDeclaration, KotlinType delegateType, KotlinType correspondingSuperType, int i) {
        super(containingDeclaration, NameUtils.delegateFieldName(i), delegateType, null, 8, null);
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(delegateType, "delegateType");
        Intrinsics.checkNotNullParameter(correspondingSuperType, "correspondingSuperType");
        this.correspondingSuperType = correspondingSuperType;
    }
}
