package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: VarargLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\tH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R \u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/VarargTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "externalVarargs", "", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "getExternalVarargs", "()Ljava/util/Set;", "setExternalVarargs", "(Ljava/util/Set;)V", "getArgumentFromSingleSegment", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "segment", "arrayInfo", "Lorg/jetbrains/kotlin/ir/backend/js/lower/InlineClassArrayInfo;", "visitFunctionAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "visitVararg", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class VarargTransformer extends IrElementTransformerVoid {
    private final JsIrBackendContext context;
    private Set<IrVararg> externalVarargs;

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    public VarargTransformer(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.externalVarargs = new LinkedHashSet();
    }

    public final Set<IrVararg> getExternalVarargs() {
        return this.externalVarargs;
    }

    public final void setExternalVarargs(Set<IrVararg> set) {
        Intrinsics.checkNotNullParameter(set, "<set-?>");
        this.externalVarargs = set;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitVararg(IrVararg expression) {
        IrSimpleFunctionSymbol arrayConcat;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        InlineClassArrayInfo inlineClassArrayInfo = new InlineClassArrayInfo(this.context, expression.getVarargElementType(), expression.getType());
        for (IrVarargElement irVarargElement : expression.getElements()) {
            if (irVarargElement instanceof IrSpreadElement) {
                if (!arrayList.isEmpty()) {
                    arrayList2.add(inlineClassArrayInfo.toPrimitiveArrayLiteral(arrayList));
                    arrayList.clear();
                }
                arrayList2.add(inlineClassArrayInfo.unboxElementIfNeeded(((IrSpreadElement) irVarargElement).getExpression()));
            } else if (irVarargElement instanceof IrExpression) {
                arrayList.add(inlineClassArrayInfo.unboxElementIfNeeded((IrExpression) irVarargElement));
            }
        }
        if (!arrayList.isEmpty()) {
            arrayList2.add(inlineClassArrayInfo.toPrimitiveArrayLiteral(arrayList));
            arrayList.clear();
        }
        if (arrayList2.isEmpty()) {
            return inlineClassArrayInfo.boxArrayIfNeeded(inlineClassArrayInfo.toPrimitiveArrayLiteral(CollectionsKt.emptyList()));
        }
        if (arrayList2.size() != 1) {
            IrExpression arrayLiteral = VarargLoweringKt.toArrayLiteral(arrayList2, this.context, new IrSimpleTypeImpl((IrClassifierSymbol) this.context.getIntrinsics().getArray(), false, CollectionsKt.emptyList(), CollectionsKt.emptyList(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null), this.context.getIrBuiltIns().getAnyType());
            if (CollectionsKt.contains(this.context.getIntrinsics().getPrimitiveArrays().keySet(), IrTypesKt.getClassifierOrNull(inlineClassArrayInfo.getPrimitiveArrayType()))) {
                arrayConcat = this.context.getIntrinsics().getPrimitiveArrayConcat();
            } else {
                arrayConcat = this.context.getIntrinsics().getArrayConcat();
            }
            IrCallImpl irCallImpl = new IrCallImpl(expression.getStartOffset(), expression.getEndOffset(), inlineClassArrayInfo.getPrimitiveArrayType(), arrayConcat, 0, 1, null, null, 192, null);
            irCallImpl.putValueArgument(0, arrayLiteral);
            return inlineClassArrayInfo.boxArrayIfNeeded(irCallImpl);
        }
        return inlineClassArrayInfo.boxArrayIfNeeded(getArgumentFromSingleSegment(expression, (IrExpression) CollectionsKt.first((List) arrayList2), inlineClassArrayInfo));
    }

    private final IrExpression getArgumentFromSingleSegment(IrVararg expression, IrExpression segment, InlineClassArrayInfo arrayInfo) {
        IrSimpleFunctionSymbol taggedArrayCopy;
        if (this.externalVarargs.contains(expression)) {
            this.externalVarargs.remove(expression);
            return segment;
        }
        List<IrVarargElement> elements = expression.getElements();
        if ((elements instanceof Collection) && elements.isEmpty()) {
            return segment;
        }
        Iterator<T> it = elements.iterator();
        while (it.hasNext()) {
            if (((IrVarargElement) it.next()) instanceof IrSpreadElement) {
                IrType primitiveElementType = arrayInfo.getPrimitiveElementType();
                if (IrTypePredicatesKt.isChar(primitiveElementType) || IrTypePredicatesKt.isBoolean(primitiveElementType) || IrTypePredicatesKt.isLong(primitiveElementType)) {
                    taggedArrayCopy = this.context.getIntrinsics().getTaggedArrayCopy();
                } else {
                    taggedArrayCopy = this.context.getIntrinsics().getJsArraySlice();
                }
                IrCallImpl irCallImpl = new IrCallImpl(expression.getStartOffset(), expression.getEndOffset(), arrayInfo.getPrimitiveArrayType(), taggedArrayCopy, 1, 1, null, null, 192, null);
                irCallImpl.putTypeArgument(0, arrayInfo.getPrimitiveArrayType());
                irCallImpl.putValueArgument(0, segment);
                return irCallImpl;
            }
        }
        return segment;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitFunctionAccess(IrFunctionAccessExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        transformChildrenVoid(expression);
        if (expression.getSymbol().getOwner().getIsExternal()) {
            int valueArgumentsCount = expression.getValueArgumentsCount();
            for (int i = 0; i < valueArgumentsCount; i++) {
                if (expression.getSymbol().getOwner().getValueParameters().get(i).getVarargElementType() != null) {
                    IrExpression valueArgument = expression.getValueArgument(i);
                    IrVararg irVararg = valueArgument instanceof IrVararg ? (IrVararg) valueArgument : null;
                    if (irVararg != null) {
                        this.externalVarargs.add(irVararg);
                    }
                }
            }
        }
        int valueArgumentsCount2 = expression.getValueArgumentsCount();
        for (int i2 = 0; i2 < valueArgumentsCount2; i2++) {
            IrExpression valueArgument2 = expression.getValueArgument(i2);
            IrValueParameter irValueParameter = expression.getSymbol().getOwner().getValueParameters().get(i2);
            IrType varargElementType = irValueParameter.getVarargElementType();
            if (valueArgument2 == null && varargElementType != null) {
                InlineClassArrayInfo inlineClassArrayInfo = new InlineClassArrayInfo(this.context, varargElementType, irValueParameter.getType());
                expression.putValueArgument(i2, inlineClassArrayInfo.boxArrayIfNeeded(inlineClassArrayInfo.toPrimitiveArrayLiteral(CollectionsKt.emptyList())));
            }
        }
        return expression;
    }
}
