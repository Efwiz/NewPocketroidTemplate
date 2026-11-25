package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.IrArrayBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.IrArrayBuilderKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;

/* JADX INFO: compiled from: VarargLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u001c\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000bH\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0018H\u0016J\u0014\u0010\u0019\u001a\u00020\u000e*\u00020\u001a2\u0006\u0010\u0013\u001a\u00020\u0018H\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/VarargLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "createBuilder", "Lorg/jetbrains/kotlin/backend/jvm/ir/JvmIrBuilder;", "startOffset", "", "endOffset", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "visitFunctionAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "visitVararg", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "addVararg", "Lorg/jetbrains/kotlin/backend/jvm/ir/IrArrayBuilder;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VarargLowering extends IrElementTransformerVoidWithContext implements FileLoweringPass {
    private final JvmBackendContext context;

    public VarargLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        transformChildrenVoid(irFile);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitConstructorCall(IrConstructorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (IrUtilsKt.isAnnotationClass(AdditionalIrUtilsKt.getConstructedClass(expression.getSymbol().getOwner()))) {
            return expression;
        }
        return super.visitConstructorCall(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitFunctionAccess(IrFunctionAccessExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        transformChildrenVoid(expression);
        IrFunctionSymbol symbol = expression.getSymbol();
        int valueArgumentsCount = expression.getValueArgumentsCount();
        for (int i = 0; i < valueArgumentsCount; i++) {
            if (expression.getValueArgument(i) == null) {
                IrValueParameter irValueParameter = symbol.getOwner().getValueParameters().get(i);
                if (irValueParameter.getVarargElementType() != null && !IrUtilsKt.hasDefaultValue(irValueParameter)) {
                    expression.putValueArgument(i, IrArrayBuilderKt.irArrayOf$default(createBuilder$default(this, 0, 0, 3, null), IrTypesKt.makeNotNull(IrTypeUtilsKt.substitute(irValueParameter.getType(), IrUtilsKt.getTypeSubstitutionMap(expression))), null, 2, null));
                }
            }
        }
        return expression;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitVararg(IrVararg expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrArrayBuilder irArrayBuilder = new IrArrayBuilder(createBuilder(expression.getStartOffset(), expression.getEndOffset()), expression.getType());
        addVararg(irArrayBuilder, expression);
        return irArrayBuilder.build();
    }

    private final void addVararg(IrArrayBuilder irArrayBuilder, IrVararg irVararg) {
        for (IrVarargElement irVarargElement : irVararg.getElements()) {
            if (irVarargElement instanceof IrExpression) {
                irArrayBuilder.unaryPlus(((IrExpression) irVarargElement).transform((IrElementTransformer<? super Object>) this, (Object) null));
            } else if (irVarargElement instanceof IrSpreadElement) {
                IrExpression expression = ((IrSpreadElement) irVarargElement).getExpression();
                if (expression instanceof IrFunctionAccessExpression) {
                    IrFunctionAccessExpression irFunctionAccessExpression = (IrFunctionAccessExpression) expression;
                    if (VarargLoweringKt.isArrayOf(irFunctionAccessExpression.getSymbol().getOwner())) {
                        IrExpression valueArgument = irFunctionAccessExpression.getValueArgument(0);
                        if (valueArgument != null) {
                            if (valueArgument instanceof IrVararg) {
                                addVararg(irArrayBuilder, (IrVararg) valueArgument);
                            }
                        }
                    }
                }
                irArrayBuilder.addSpread(expression.transform((IrElementTransformer<? super Object>) this, (Object) null));
            } else {
                throw new IllegalStateException(("Unexpected IrVarargElement subclass: " + irVarargElement).toString());
            }
        }
    }

    static /* synthetic */ JvmIrBuilder createBuilder$default(VarargLowering varargLowering, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = -1;
        }
        if ((i3 & 2) != 0) {
            i2 = -1;
        }
        return varargLowering.createBuilder(i, i2);
    }

    private final JvmIrBuilder createBuilder(int startOffset, int endOffset) {
        JvmBackendContext jvmBackendContext = this.context;
        ScopeWithIr currentScope = getCurrentScope();
        Intrinsics.checkNotNull(currentScope);
        return JvmIrBuilderKt.createJvmIrBuilder(jvmBackendContext, currentScope, startOffset, endOffset);
    }
}
