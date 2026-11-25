package org.jetbrains.kotlin.ir.declarations;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;

/* JADX INFO: compiled from: IrAnnotationContainer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001R\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrMutableAnnotationContainer;", "Lorg/jetbrains/kotlin/ir/declarations/IrAnnotationContainer;", "annotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getAnnotations", "()Ljava/util/List;", "setAnnotations", "(Ljava/util/List;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrMutableAnnotationContainer extends IrAnnotationContainer {
    @Override // org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer
    List<IrConstructorCall> getAnnotations();

    void setAnnotations(List<? extends IrConstructorCall> list);
}
