package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrVarargImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;

/* JADX INFO: compiled from: VarargLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a*\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0002¨\u0006\b"}, d2 = {"toArrayLiteral", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "varargElementType", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class VarargLoweringKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression toArrayLiteral(List<? extends IrExpression> list, JsIrBackendContext jsIrBackendContext, IrType irType, IrType irType2) {
        IrSimpleFunctionSymbol arrayLiteral;
        Map<IrClassSymbol, PrimitiveType> primitiveArrays = jsIrBackendContext.getIntrinsics().getPrimitiveArrays();
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(primitiveArrays.size()));
        Iterator<T> it = primitiveArrays.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap.put((IrClassSymbol) entry.getKey(), entry.getValue());
        }
        PrimitiveType primitiveType = (PrimitiveType) linkedHashMap.get(IrTypesKt.getClassifierOrNull(irType));
        if (primitiveType != null) {
            arrayLiteral = (IrSimpleFunctionSymbol) MapsKt.getValue(jsIrBackendContext.getIntrinsics().getPrimitiveToLiteralConstructor(), primitiveType);
        } else {
            arrayLiteral = jsIrBackendContext.getIntrinsics().getArrayLiteral();
        }
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = arrayLiteral;
        IrExpression irExpression = (IrExpression) CollectionsKt.firstOrNull((List) list);
        int startOffset = irExpression != null ? irExpression.getStartOffset() : -1;
        IrExpression irExpression2 = (IrExpression) CollectionsKt.lastOrNull((List) list);
        int endOffset = irExpression2 != null ? irExpression2.getEndOffset() : -1;
        IrVarargImpl irVarargImpl = new IrVarargImpl(startOffset, endOffset, irType, irType2, list);
        IrCallImpl irCallImpl = new IrCallImpl(startOffset, endOffset, irType, irSimpleFunctionSymbol, !irSimpleFunctionSymbol.getOwner().getTypeParameters().isEmpty() ? 1 : 0, 1, null, null, 192, null);
        if (irCallImpl.getTypeArgumentsCount() == 1) {
            irCallImpl.putTypeArgument(0, irType2);
        }
        irCallImpl.putValueArgument(0, irVarargImpl);
        return irCallImpl;
    }
}
