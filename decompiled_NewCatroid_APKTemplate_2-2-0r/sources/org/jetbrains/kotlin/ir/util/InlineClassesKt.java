package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.types.IrSimpleType;

/* JADX INFO: compiled from: InlineClasses.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0006"}, d2 = {"getInlineClassBackingField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getInlineClassUnderlyingType", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InlineClassesKt {
    public static final IrSimpleType getInlineClassUnderlyingType(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        InlineClassRepresentation<IrSimpleType> inlineClassRepresentation = IrDeclarationsKt.getInlineClassRepresentation(irClass);
        if (inlineClassRepresentation != null) {
            return (IrSimpleType) inlineClassRepresentation.getUnderlyingType();
        }
        throw new IllegalStateException(("Not an inline class: " + RenderIrElementKt.render$default(irClass, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
    }

    public static final IrField getInlineClassBackingField(IrClass irClass) {
        IrField backingField;
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        for (IrDeclaration irDeclaration : irClass.getDeclarations()) {
            if (irDeclaration instanceof IrField) {
                IrField irField = (IrField) irDeclaration;
                if (!irField.getIsStatic()) {
                    return irField;
                }
            }
            if ((irDeclaration instanceof IrProperty) && (backingField = ((IrProperty) irDeclaration).getBackingField()) != null && !backingField.getIsStatic()) {
                return backingField;
            }
        }
        throw new IllegalStateException(("Inline class has no field: " + IrUtilsKt.getFqNameWhenAvailable(irClass)).toString());
    }
}
