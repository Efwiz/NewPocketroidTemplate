package org.jetbrains.kotlin.ir.backend.js;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.InlineClassesUtils;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.backend.js.utils.MiscKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: JsCommonBackendContext.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000bH&J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0011À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/JsCommonInlineClassesUtils;", "Lorg/jetbrains/kotlin/backend/common/InlineClassesUtils;", "boxIntrinsic", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getBoxIntrinsic", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "unboxIntrinsic", "getUnboxIntrinsic", "getInlinedClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "isTypeInlined", "", "shouldValueParameterBeBoxed", "parameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JsCommonInlineClassesUtils extends InlineClassesUtils {
    IrSimpleFunctionSymbol getBoxIntrinsic();

    IrClass getInlinedClass(IrType type);

    IrSimpleFunctionSymbol getUnboxIntrinsic();

    default boolean isTypeInlined(IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return getInlinedClass(type) != null;
    }

    default boolean shouldValueParameterBeBoxed(IrValueParameter parameter) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        IrDeclarationParent parent = parameter.getParent();
        IrSimpleFunction irSimpleFunction = parent instanceof IrSimpleFunction ? (IrSimpleFunction) parent : null;
        if (irSimpleFunction == null) {
            return false;
        }
        IrDeclarationParent parent2 = irSimpleFunction.getParent();
        IrClass irClass = parent2 instanceof IrClass ? (IrClass) parent2 : null;
        return irClass != null && isClassInlineLike(irClass) && MiscKt.isDispatchReceiver(parameter) && IrUtilsKt.isOverridableOrOverrides(irSimpleFunction);
    }
}
