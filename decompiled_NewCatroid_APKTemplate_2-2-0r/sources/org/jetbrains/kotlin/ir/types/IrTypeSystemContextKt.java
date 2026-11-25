package org.jetbrains.kotlin.ir.types;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;

/* JADX INFO: compiled from: IrTypeSystemContext.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"extractTypeParameters", "", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrTypeSystemContextKt {
    public static final List<IrTypeParameter> extractTypeParameters(IrDeclarationParent parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        ArrayList arrayList = new ArrayList();
        while (parent != null) {
            IrTypeParametersContainer irTypeParametersContainer = parent instanceof IrTypeParametersContainer ? (IrTypeParametersContainer) parent : null;
            if (irTypeParametersContainer != null) {
                CollectionsKt.addAll(arrayList, irTypeParametersContainer.getTypeParameters());
            }
            if (parent instanceof IrField) {
                parent = ((IrField) parent).getParent();
            } else if (parent instanceof IrClass) {
                IrClass irClass = (IrClass) parent;
                if (irClass.getIsInner()) {
                    IrDeclarationParent parent2 = irClass.getParent();
                    Intrinsics.checkNotNull(parent2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                    parent = (IrClass) parent2;
                } else {
                    parent = Intrinsics.areEqual(irClass.getVisibility(), DescriptorVisibilities.LOCAL) ? irClass.getParent() : null;
                }
            } else if (parent instanceof IrConstructor) {
                IrDeclarationParent parent3 = ((IrConstructor) parent).getParent();
                Intrinsics.checkNotNull(parent3, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                parent = (IrClass) parent3;
            } else {
                if (parent instanceof IrFunction) {
                    IrFunction irFunction = (IrFunction) parent;
                    if (Intrinsics.areEqual(irFunction.getVisibility(), DescriptorVisibilities.LOCAL) || irFunction.getDispatchReceiverParameter() != null) {
                        parent = irFunction.getParent();
                    }
                }
            }
        }
        return org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList);
    }
}
