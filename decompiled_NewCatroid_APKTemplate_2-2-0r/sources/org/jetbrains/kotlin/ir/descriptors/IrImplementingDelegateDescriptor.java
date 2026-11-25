package org.jetbrains.kotlin.ir.descriptors;

import kotlin.Metadata;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: IrDelegateDescriptor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrImplementingDelegateDescriptor;", "Lorg/jetbrains/kotlin/ir/descriptors/IrDelegateDescriptor;", "correspondingSuperType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getCorrespondingSuperType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrImplementingDelegateDescriptor extends IrDelegateDescriptor {
    KotlinType getCorrespondingSuperType();
}
