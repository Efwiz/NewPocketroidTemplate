package org.jetbrains.kotlin.ir.declarations;

import java.util.List;
import kotlin.Metadata;

/* JADX INFO: compiled from: IrTypeParametersContainer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u00012\u00020\u0002R\u001e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u000e¢\u0006\f\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "typeParameters", "", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "getTypeParameters", "()Ljava/util/List;", "setTypeParameters", "(Ljava/util/List;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrTypeParametersContainer extends IrDeclaration, IrDeclarationParent {
    List<IrTypeParameter> getTypeParameters();

    void setTypeParameters(List<? extends IrTypeParameter> list);
}
