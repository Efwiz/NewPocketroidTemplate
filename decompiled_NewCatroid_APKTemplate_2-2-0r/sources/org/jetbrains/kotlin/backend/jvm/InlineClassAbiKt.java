package org.jetbrains.kotlin.backend.jvm;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.descriptors.MultiFieldValueClassRepresentation;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: InlineClassAbi.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u001e\u0010\u0011\u001a\u00020\u0007*\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u00072\b\b\u0002\u0010\u0014\u001a\u00020\u0007\u001a\u001e\u0010\u0015\u001a\u00020\u0007*\u00020\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u00072\b\b\u0002\u0010\u0014\u001a\u00020\u0007\u001a\n\u0010\u0016\u001a\u00020\u0012*\u00020\u0012\"\u001b\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u0015\u0010\u0006\u001a\u00020\u0007*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\b\u0010\t\"\u0015\u0010\n\u001a\u00020\u000b*\u00020\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e\"\u0015\u0010\u000f\u001a\u00020\u0007*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\t\"\u0015\u0010\u0010\u001a\u00020\u0007*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\t¨\u0006\u0017"}, d2 = {"fullValueParameterList", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getFullValueParameterList", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Ljava/util/List;", "hasMangledReturnType", "", "getHasMangledReturnType", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Z", "inlineClassFieldName", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getInlineClassFieldName", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/name/Name;", "isInlineClassFieldGetter", "isMultiFieldValueClassFieldGetter", "getRequiresMangling", "Lorg/jetbrains/kotlin/ir/types/IrType;", "includeInline", "includeMFVC", "hasMangledParameters", "unboxInlineClass", "backend.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InlineClassAbiKt {
    public static final Name getInlineClassFieldName(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        InlineClassRepresentation<IrSimpleType> inlineClassRepresentation = IrDeclarationsKt.getInlineClassRepresentation(irClass);
        if (inlineClassRepresentation != null) {
            return inlineClassRepresentation.getUnderlyingPropertyName();
        }
        throw new IllegalStateException(("Not an inline class: " + RenderIrElementKt.render$default(irClass, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
    }

    public static final IrType unboxInlineClass(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrType irTypeUnboxType = InlineClassAbi.INSTANCE.unboxType(irType);
        return irTypeUnboxType == null ? irType : irTypeUnboxType;
    }

    public static /* synthetic */ boolean getRequiresMangling$default(IrType irType, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        if ((i & 2) != 0) {
            z2 = true;
        }
        return getRequiresMangling(irType, z, z2);
    }

    public static final boolean getRequiresMangling(IrType irType, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClass erasedUpperBound = JvmIrTypeUtilsKt.getErasedUpperBound(irType);
        if (Intrinsics.areEqual(IrUtilsKt.getFqNameWhenAvailable(erasedUpperBound), StandardNames.RESULT_FQ_NAME)) {
            return false;
        }
        if (!IrDeclarationsKt.isSingleFieldValueClass(erasedUpperBound)) {
            z = IrDeclarationsKt.isMultiFieldValueClass(erasedUpperBound) ? z2 : false;
        }
        return z;
    }

    public static final List<IrValueParameter> getFullValueParameterList(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull(irFunction.getExtensionReceiverParameter()), (Iterable) irFunction.getValueParameters());
    }

    public static /* synthetic */ boolean hasMangledParameters$default(IrFunction irFunction, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        if ((i & 2) != 0) {
            z2 = true;
        }
        return hasMangledParameters(irFunction, z, z2);
    }

    public static final boolean hasMangledParameters(IrFunction irFunction, boolean z, boolean z2) {
        boolean z3;
        boolean z4;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        if (irFunction.getDispatchReceiverParameter() != null) {
            IrFunction irFunction2 = irFunction;
            if (IrDeclarationsKt.isSingleFieldValueClass(IrUtilsKt.getParentAsClass(irFunction2))) {
                z4 = z;
            } else {
                z4 = IrDeclarationsKt.isMultiFieldValueClass(IrUtilsKt.getParentAsClass(irFunction2)) ? z2 : false;
            }
            if (z4) {
                return true;
            }
        }
        List<IrValueParameter> fullValueParameterList = getFullValueParameterList(irFunction);
        if (!(fullValueParameterList instanceof Collection) || !fullValueParameterList.isEmpty()) {
            Iterator<T> it = fullValueParameterList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z3 = false;
                    break;
                }
                if (getRequiresMangling(((IrValueParameter) it.next()).getType(), z, z2)) {
                    z3 = true;
                    break;
                }
            }
        } else {
            z3 = false;
            break;
        }
        if (z3) {
            return true;
        }
        if (irFunction instanceof IrConstructor) {
            IrConstructor irConstructor = (IrConstructor) irFunction;
            if (!IrDeclarationsKt.isSingleFieldValueClass(AdditionalIrUtilsKt.getConstructedClass(irConstructor))) {
                z = IrDeclarationsKt.isMultiFieldValueClass(AdditionalIrUtilsKt.getConstructedClass(irConstructor)) ? z2 : false;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    public static final boolean getHasMangledReturnType(IrFunction irFunction) {
        IrClass parentClassOrNull;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return JvmIrTypeUtilsKt.isInlineClassType(irFunction.getReturnType()) && ((parentClassOrNull = IrUtilsKt.getParentClassOrNull(irFunction)) == null || !IrUtilsKt.isFileClass(parentClassOrNull));
    }

    public static final boolean isInlineClassFieldGetter(IrFunction irFunction) {
        IrPropertySymbol correspondingPropertySymbol;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        IrDeclarationParent parent = irFunction.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        return irClass != null && IrDeclarationsKt.isSingleFieldValueClass(irClass) && (irFunction instanceof IrSimpleFunction) && irFunction.getExtensionReceiverParameter() == null && irFunction.getContextReceiverParametersCount() == 0 && !IrUtilsKt.isStatic(irFunction) && (correspondingPropertySymbol = ((IrSimpleFunction) irFunction).getCorrespondingPropertySymbol()) != null && Intrinsics.areEqual(correspondingPropertySymbol.getOwner().getGetter(), irFunction) && Intrinsics.areEqual(correspondingPropertySymbol.getOwner().getName(), getInlineClassFieldName(IrUtilsKt.getParentAsClass(irFunction)));
    }

    public static final boolean isMultiFieldValueClassFieldGetter(IrFunction irFunction) {
        IrPropertySymbol correspondingPropertySymbol;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        IrDeclarationParent parent = irFunction.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        if (irClass != null && IrDeclarationsKt.isMultiFieldValueClass(irClass) && (irFunction instanceof IrSimpleFunction) && irFunction.getExtensionReceiverParameter() == null && irFunction.getContextReceiverParametersCount() == 0 && !IrUtilsKt.isStatic(irFunction) && (correspondingPropertySymbol = ((IrSimpleFunction) irFunction).getCorrespondingPropertySymbol()) != null) {
            IrFunction irFunction2 = irFunction;
            MultiFieldValueClassRepresentation<IrSimpleType> multiFieldValueClassRepresentation = IrDeclarationsKt.getMultiFieldValueClassRepresentation(IrUtilsKt.getParentAsClass(irFunction2));
            if (multiFieldValueClassRepresentation == null) {
                throw new IllegalStateException(("Multi-field value class must have multiFieldValueClassRepresentation: " + RenderIrElementKt.render$default(IrUtilsKt.getParentAsClass(irFunction2), (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
            if (Intrinsics.areEqual(correspondingPropertySymbol.getOwner().getGetter(), irFunction) && multiFieldValueClassRepresentation.containsPropertyWithName(correspondingPropertySymbol.getOwner().getName())) {
                return true;
            }
        }
        return false;
    }
}
