package org.jetbrains.kotlin.psi2ir.intermediate;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;

/* JADX INFO: compiled from: OnceExpressionValue.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u001c\u0010\u000e\u001a\u00020\u00042\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00040\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0004H\u0016J\u0010\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/intermediate/OnceExpressionValue;", "Lorg/jetbrains/kotlin/psi2ir/intermediate/LValue;", "Lorg/jetbrains/kotlin/psi2ir/intermediate/AssignmentReceiver;", "irExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "instantiated", "", "getIrExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "assign", "withLValue", "Lkotlin/Function1;", "load", "store", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OnceExpressionValue implements AssignmentReceiver, LValue {
    private boolean instantiated;
    private final IrExpression irExpression;

    public OnceExpressionValue(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "irExpression");
        this.irExpression = irExpression;
    }

    public final IrExpression getIrExpression() {
        return this.irExpression;
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue
    public IrExpression load() {
        if (this.instantiated) {
            throw new AssertionError("Single expression value for " + RenderIrElementKt.render$default(this.irExpression, (DumpIrTreeOptions) null, 1, (Object) null) + " is already instantiated");
        }
        this.instantiated = true;
        return this.irExpression;
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue
    public IrType getType() {
        return this.irExpression.getType();
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.LValue
    public IrExpression store(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "irExpression");
        throw new AssertionError("Expression value " + RenderIrElementKt.render$default(irExpression, (DumpIrTreeOptions) null, 1, (Object) null) + " can't be used in store operation");
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.AssignmentReceiver
    public IrExpression assign(Function1<? super LValue, ? extends IrExpression> withLValue) {
        Intrinsics.checkNotNullParameter(withLValue, "withLValue");
        return withLValue.invoke(this);
    }
}
