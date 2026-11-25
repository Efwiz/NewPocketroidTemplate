package org.jetbrains.kotlin.ir.declarations;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;

/* JADX INFO: compiled from: IrDeclarationWithVisibility.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithVisibility;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "setVisibility", "(Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrDeclarationWithVisibility extends IrDeclaration {
    DescriptorVisibility getVisibility();

    void setVisibility(DescriptorVisibility descriptorVisibility);
}
