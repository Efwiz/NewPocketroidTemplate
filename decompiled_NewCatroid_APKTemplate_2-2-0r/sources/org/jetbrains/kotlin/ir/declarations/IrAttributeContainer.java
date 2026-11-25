package org.jetbrains.kotlin.ir.declarations;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.IrElement;

/* JADX INFO: compiled from: IrAttributeContainer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\bf\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\u00020\u0000X¦\u000e¢\u0006\f\u001a\u0004\b\u0003\u0010\u0004\"\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u0004\u0018\u00010\u0000X¦\u000e¢\u0006\f\u001a\u0004\b\b\u0010\u0004\"\u0004\b\t\u0010\u0006¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "Lorg/jetbrains/kotlin/ir/IrElement;", "attributeOwnerId", "getAttributeOwnerId", "()Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "setAttributeOwnerId", "(Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;)V", "originalBeforeInline", "getOriginalBeforeInline", "setOriginalBeforeInline", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrAttributeContainer extends IrElement {
    IrAttributeContainer getAttributeOwnerId();

    IrAttributeContainer getOriginalBeforeInline();

    void setAttributeOwnerId(IrAttributeContainer irAttributeContainer);

    void setOriginalBeforeInline(IrAttributeContainer irAttributeContainer);
}
