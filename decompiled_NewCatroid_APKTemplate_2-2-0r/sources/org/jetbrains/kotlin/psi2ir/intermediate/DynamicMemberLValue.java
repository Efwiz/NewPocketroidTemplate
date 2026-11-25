package org.jetbrains.kotlin.psi2ir.intermediate;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.builders.IrGeneratorContext;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperator;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.impl.IrDynamicMemberExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDynamicOperatorExpressionImpl;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: DynamicMemberLValue.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B5\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u001c\u0010\u0011\u001a\u00020\u00122\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00120\u0014H\u0016J\u001c\u0010\u0015\u001a\u00020\u00122\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0017H\u0002J\b\u0010\u0019\u001a\u00020\u0012H\u0016J\u0010\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u0012H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/intermediate/DynamicMemberLValue;", "Lorg/jetbrains/kotlin/psi2ir/intermediate/LValue;", "Lorg/jetbrains/kotlin/psi2ir/intermediate/AssignmentReceiver;", "context", "Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "memberName", "", "receiver", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallReceiver;", "(Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;IILorg/jetbrains/kotlin/ir/types/IrType;Ljava/lang/String;Lorg/jetbrains/kotlin/psi2ir/intermediate/CallReceiver;)V", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "assign", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "withLValue", "Lkotlin/Function1;", "getDynamicReceiver", "dispatchReceiverValue", "Lorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;", "extensionReceiverValue", "load", "store", "irExpression", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DynamicMemberLValue implements AssignmentReceiver, LValue {
    private final IrGeneratorContext context;
    private final int endOffset;
    private final String memberName;
    private final CallReceiver receiver;
    private final int startOffset;
    private final IrType type;

    public DynamicMemberLValue(IrGeneratorContext context, int i, int i2, IrType type, String memberName, CallReceiver receiver) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(memberName, "memberName");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        this.context = context;
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.memberName = memberName;
        this.receiver = receiver;
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue
    public IrType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue
    public IrExpression load() {
        return this.receiver.call(new CallExpressionBuilder() { // from class: org.jetbrains.kotlin.psi2ir.intermediate.DynamicMemberLValue$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallExpressionBuilder
            public final IrExpression withReceivers(IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
                return DynamicMemberLValue.load$lambda$0(this.f$0, intermediateValue, intermediateValue2, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression load$lambda$0(DynamicMemberLValue this$0, IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(list, "<anonymous parameter 2>");
        return new IrDynamicMemberExpressionImpl(this$0.startOffset, this$0.endOffset, this$0.getType(), this$0.memberName, this$0.getDynamicReceiver(intermediateValue, intermediateValue2));
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.LValue
    public IrExpression store(final IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "irExpression");
        return this.receiver.call(new CallExpressionBuilder() { // from class: org.jetbrains.kotlin.psi2ir.intermediate.DynamicMemberLValue$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallExpressionBuilder
            public final IrExpression withReceivers(IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
                return DynamicMemberLValue.store$lambda$2(this.f$0, irExpression, intermediateValue, intermediateValue2, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression store$lambda$2(DynamicMemberLValue this$0, IrExpression irExpression, IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(irExpression, "$irExpression");
        Intrinsics.checkNotNullParameter(list, "<anonymous parameter 2>");
        IrExpression dynamicReceiver = this$0.getDynamicReceiver(intermediateValue, intermediateValue2);
        IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImpl = new IrDynamicOperatorExpressionImpl(this$0.startOffset, this$0.endOffset, this$0.context.getIrBuiltIns().getUnitType(), IrDynamicOperator.EQ);
        IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImpl2 = irDynamicOperatorExpressionImpl;
        IrExpressionsKt.setLeft(irDynamicOperatorExpressionImpl2, new IrDynamicMemberExpressionImpl(irDynamicOperatorExpressionImpl.getStartOffset(), irDynamicOperatorExpressionImpl.getEndOffset(), irDynamicOperatorExpressionImpl.getType(), this$0.memberName, dynamicReceiver));
        IrExpressionsKt.setRight(irDynamicOperatorExpressionImpl2, irExpression);
        return irDynamicOperatorExpressionImpl;
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.AssignmentReceiver
    public IrExpression assign(final Function1<? super LValue, ? extends IrExpression> withLValue) {
        Intrinsics.checkNotNullParameter(withLValue, "withLValue");
        return this.receiver.call(new CallExpressionBuilder() { // from class: org.jetbrains.kotlin.psi2ir.intermediate.DynamicMemberLValue$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallExpressionBuilder
            public final IrExpression withReceivers(IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
                return DynamicMemberLValue.assign$lambda$3(withLValue, this, intermediateValue, intermediateValue2, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression assign$lambda$3(Function1 withLValue, DynamicMemberLValue this$0, IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List contextReceiverValues) {
        Intrinsics.checkNotNullParameter(withLValue, "$withLValue");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(contextReceiverValues, "contextReceiverValues");
        return (IrExpression) withLValue.invoke(new DynamicMemberLValue(this$0.context, this$0.startOffset, this$0.endOffset, this$0.getType(), this$0.memberName, new SimpleCallReceiver(intermediateValue, intermediateValue2, contextReceiverValues)));
    }

    private final IrExpression getDynamicReceiver(IntermediateValue dispatchReceiverValue, IntermediateValue extensionReceiverValue) {
        if (dispatchReceiverValue == null) {
            throw new AssertionError("Dynamic call " + this.memberName + " should have a dispatch receiver");
        }
        if (extensionReceiverValue != null) {
            throw new AssertionError("Dynamic call " + this.memberName + " should have no extension receiver");
        }
        return dispatchReceiverValue.load();
    }
}
