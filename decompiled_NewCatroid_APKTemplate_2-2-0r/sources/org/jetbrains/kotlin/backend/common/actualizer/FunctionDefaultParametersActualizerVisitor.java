package org.jetbrains.kotlin.backend.common.actualizer;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.util.SymbolRemapper;
import org.jetbrains.kotlin.ir.util.TypeRemapper;

/* JADX INFO: compiled from: FunctionDefaultParametersActualizer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/actualizer/FunctionDefaultParametersActualizerVisitor;", "Lorg/jetbrains/kotlin/backend/common/actualizer/ActualizerVisitor;", "symbolRemapper", "Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;", "typeRemapper", "Lorg/jetbrains/kotlin/ir/util/TypeRemapper;", "(Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;Lorg/jetbrains/kotlin/ir/util/TypeRemapper;)V", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "expression", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FunctionDefaultParametersActualizerVisitor extends ActualizerVisitor {
    private final SymbolRemapper symbolRemapper;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FunctionDefaultParametersActualizerVisitor(SymbolRemapper symbolRemapper, TypeRemapper typeRemapper) {
        super(symbolRemapper, typeRemapper);
        Intrinsics.checkNotNullParameter(symbolRemapper, "symbolRemapper");
        Intrinsics.checkNotNullParameter(typeRemapper, "typeRemapper");
        this.symbolRemapper = symbolRemapper;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrGetValue visitGetValue(IrGetValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return (IrGetValue) IrDeclarationsKt.copyAttributes(LowerUtilsKt.actualize(expression, new Function1<IrClass, IrClass>() { // from class: org.jetbrains.kotlin.backend.common.actualizer.FunctionDefaultParametersActualizerVisitor.visitGetValue.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrClass invoke(IrClass it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return FunctionDefaultParametersActualizerVisitor.this.symbolRemapper.getReferencedClass(it.getSymbol()).getOwner();
            }
        }, new Function1<IrFunction, IrFunction>() { // from class: org.jetbrains.kotlin.backend.common.actualizer.FunctionDefaultParametersActualizerVisitor.visitGetValue.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrFunction invoke(IrFunction it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return FunctionDefaultParametersActualizerVisitor.this.symbolRemapper.getReferencedFunction(it.getSymbol()).getOwner();
            }
        }), expression);
    }
}
