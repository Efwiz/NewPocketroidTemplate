package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetObjectValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: PrimitiveCompanionLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\bH\u0002J\u0012\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0002J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/PrimitiveCompanionLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "getActualPrimitiveCompanion", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "irClass", "getActualPrimitiveCompanionPropertyAccessor", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "function", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PrimitiveCompanionLowering implements BodyLoweringPass {
    private final JsIrBackendContext context;

    public PrimitiveCompanionLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrClass getActualPrimitiveCompanion(IrClass irClass) {
        IrClassSymbol irClassSymbol;
        if (!irClass.getIsCompanion()) {
            return null;
        }
        IrDeclarationParent parent = irClass.getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
        IrClass irClass2 = (IrClass) parent;
        if ((IrTypePredicatesKt.isPrimitiveType$default(IrUtilsKt.getDefaultType(irClass2), false, 1, null) || IrTypePredicatesKt.isString(IrUtilsKt.getDefaultType(irClass2))) && (irClassSymbol = this.context.getPrimitiveCompanionObjects().get(irClass2.getName())) != null) {
            return irClassSymbol.getOwner();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunction getActualPrimitiveCompanionPropertyAccessor(IrSimpleFunction function) {
        IrProperty owner;
        IrClass actualPrimitiveCompanion;
        IrPropertySymbol correspondingPropertySymbol = function.getCorrespondingPropertySymbol();
        Object obj = null;
        if (correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null) {
            return null;
        }
        IrDeclarationParent parent = owner.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        if (irClass == null || (actualPrimitiveCompanion = getActualPrimitiveCompanion(irClass)) == null) {
            return null;
        }
        for (IrProperty irProperty : IrUtilsKt.getProperties(actualPrimitiveCompanion)) {
            IrSimpleFunction getter = irProperty.getGetter();
            if (getter != null && Intrinsics.areEqual(getter.getName(), function.getName())) {
                return getter;
            }
            IrSimpleFunction setter = irProperty.getSetter();
            if (setter != null && Intrinsics.areEqual(setter.getName(), function.getName())) {
                return setter;
            }
        }
        List<IrDeclaration> declarations = actualPrimitiveCompanion.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : declarations) {
            if (obj2 instanceof IrSimpleFunction) {
                arrayList.add(obj2);
            }
        }
        boolean z = false;
        for (Object obj3 : arrayList) {
            if (Intrinsics.areEqual(((IrSimpleFunction) obj3).getName(), function.getName())) {
                if (z) {
                    throw new IllegalArgumentException("Collection contains more than one matching element.");
                }
                z = true;
                obj = obj3;
            }
        }
        if (!z) {
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        return (IrSimpleFunction) obj;
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        IrElementTransformerVoidKt.transformChildrenVoid(irBody, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.PrimitiveCompanionLowering.lower.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitGetObjectValue(IrGetObjectValue expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrClass actualPrimitiveCompanion = PrimitiveCompanionLowering.this.getActualPrimitiveCompanion(expression.getSymbol().getOwner());
                return actualPrimitiveCompanion == null ? expression : new IrGetObjectValueImpl(expression.getStartOffset(), expression.getEndOffset(), IrUtilsKt.getDefaultType(actualPrimitiveCompanion), actualPrimitiveCompanion.getSymbol());
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitCall(IrCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrExpression irExpressionVisitCall = super.visitCall(expression);
                Intrinsics.checkNotNull(irExpressionVisitCall, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrCall");
                IrCall irCall = (IrCall) irExpressionVisitCall;
                IrSimpleFunction actualPrimitiveCompanionPropertyAccessor = PrimitiveCompanionLowering.this.getActualPrimitiveCompanionPropertyAccessor(expression.getSymbol().getOwner());
                if (actualPrimitiveCompanionPropertyAccessor == null) {
                    return irCall;
                }
                return IrUtilsKt.irCall(irCall, actualPrimitiveCompanionPropertyAccessor, (56 & 4) != 0 ? false : false, (56 & 8) != 0 ? false : false, (56 & 16) != 0 ? null : null, (56 & 32) != 0 ? null : null);
            }
        });
    }
}
