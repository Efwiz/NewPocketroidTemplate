package org.jetbrains.kotlin.psi2ir.intermediate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImplKt;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.psi2ir.generators.GeneratorContext;

/* JADX INFO: compiled from: PropertyLValue.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\b \u0018\u00002\u00020\u00012\u00020\u0002BI\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011¢\u0006\u0002\u0010\u0012J\u001c\u0010\"\u001a\u00020#2\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020#0%H\u0016J\u0010\u0010\"\u001a\u00020#2\u0006\u0010&\u001a\u00020#H\u0016J*\u0010'\u001a\u00020\u00002\b\u0010(\u001a\u0004\u0018\u00010)2\b\u0010*\u001a\u0004\u0018\u00010)2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020)0,H$R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0003\u001a\u00020\u0004X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\t\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0018R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/intermediate/PropertyLValueBase;", "Lorg/jetbrains/kotlin/psi2ir/intermediate/LValue;", "Lorg/jetbrains/kotlin/psi2ir/intermediate/AssignmentReceiver;", "context", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "scope", "Lorg/jetbrains/kotlin/ir/builders/Scope;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "callReceiver", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallReceiver;", "superQualifier", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "(Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;Lorg/jetbrains/kotlin/ir/builders/Scope;IILorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/psi2ir/intermediate/CallReceiver;Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;)V", "getCallReceiver", "()Lorg/jetbrains/kotlin/psi2ir/intermediate/CallReceiver;", "getContext", "()Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "getEndOffset", "()I", "getOrigin", "()Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "getScope", "()Lorg/jetbrains/kotlin/ir/builders/Scope;", "getStartOffset", "getSuperQualifier", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "assign", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "withLValue", "Lkotlin/Function1;", "value", "withReceiver", "dispatchReceiver", "Lorg/jetbrains/kotlin/psi2ir/intermediate/VariableLValue;", "extensionReceiver", "contextReceivers", "", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class PropertyLValueBase implements AssignmentReceiver, LValue {
    private final CallReceiver callReceiver;
    private final GeneratorContext context;
    private final int endOffset;
    private final IrStatementOrigin origin;
    private final Scope scope;
    private final int startOffset;
    private final IrClassSymbol superQualifier;
    private final IrType type;

    protected abstract PropertyLValueBase withReceiver(VariableLValue dispatchReceiver, VariableLValue extensionReceiver, List<VariableLValue> contextReceivers);

    public PropertyLValueBase(GeneratorContext context, Scope scope, int i, int i2, IrStatementOrigin irStatementOrigin, IrType type, CallReceiver callReceiver, IrClassSymbol irClassSymbol) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(callReceiver, "callReceiver");
        this.context = context;
        this.scope = scope;
        this.startOffset = i;
        this.endOffset = i2;
        this.origin = irStatementOrigin;
        this.type = type;
        this.callReceiver = callReceiver;
        this.superQualifier = irClassSymbol;
    }

    protected final GeneratorContext getContext() {
        return this.context;
    }

    public final Scope getScope() {
        return this.scope;
    }

    public final int getStartOffset() {
        return this.startOffset;
    }

    public final int getEndOffset() {
        return this.endOffset;
    }

    public final IrStatementOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue
    public IrType getType() {
        return this.type;
    }

    public final CallReceiver getCallReceiver() {
        return this.callReceiver;
    }

    public final IrClassSymbol getSuperQualifier() {
        return this.superQualifier;
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.AssignmentReceiver
    public IrExpression assign(final Function1<? super LValue, ? extends IrExpression> withLValue) {
        Intrinsics.checkNotNullParameter(withLValue, "withLValue");
        return this.callReceiver.call(new CallExpressionBuilder() { // from class: org.jetbrains.kotlin.psi2ir.intermediate.PropertyLValueBase$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallExpressionBuilder
            public final IrExpression withReceivers(IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
                return PropertyLValueBase.assign$lambda$7(withLValue, this, intermediateValue, intermediateValue2, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression assign$lambda$7(Function1 withLValue, PropertyLValueBase this$0, IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List contextReceiverValues) {
        IrVariable irVariableCreateTemporaryVariable;
        IrVariable irVariableCreateTemporaryVariable2;
        Intrinsics.checkNotNullParameter(withLValue, "$withLValue");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(contextReceiverValues, "contextReceiverValues");
        if (intermediateValue != null) {
            Scope scope = this$0.scope;
            IrExpression irExpressionLoad = intermediateValue.load();
            irVariableCreateTemporaryVariable = scope.createTemporaryVariable(irExpressionLoad, (108 & 2) != 0 ? null : "this", (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? null : null, (108 & 32) != 0 ? irExpressionLoad.getStartOffset() : 0, (108 & 64) != 0 ? irExpressionLoad.getEndOffset() : 0);
        } else {
            irVariableCreateTemporaryVariable = null;
        }
        VariableLValue variableLValue = irVariableCreateTemporaryVariable != null ? new VariableLValue(this$0.context, irVariableCreateTemporaryVariable, null, 0, 0, 28, null) : null;
        if (intermediateValue2 != null) {
            Scope scope2 = this$0.scope;
            IrExpression irExpressionLoad2 = intermediateValue2.load();
            irVariableCreateTemporaryVariable2 = scope2.createTemporaryVariable(irExpressionLoad2, (108 & 2) != 0 ? null : "receiver", (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? null : null, (108 & 32) != 0 ? irExpressionLoad2.getStartOffset() : 0, (108 & 64) != 0 ? irExpressionLoad2.getEndOffset() : 0);
        } else {
            irVariableCreateTemporaryVariable2 = null;
        }
        VariableLValue variableLValue2 = irVariableCreateTemporaryVariable2 != null ? new VariableLValue(this$0.context, irVariableCreateTemporaryVariable2, null, 0, 0, 28, null) : null;
        List list = contextReceiverValues;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        int i = 0;
        for (Object obj : list) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Scope scope3 = this$0.scope;
            IrExpression irExpressionLoad3 = ((IntermediateValue) obj).load();
            arrayList.add(scope3.createTemporaryVariable(irExpressionLoad3, (108 & 2) != 0 ? null : "contextReceiver" + i, (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? null : null, (108 & 32) != 0 ? irExpressionLoad3.getStartOffset() : 0, (108 & 64) != 0 ? irExpressionLoad3.getEndOffset() : 0));
            i = i2;
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(new VariableLValue(this$0.context, (IrVariable) it.next(), null, 0, 0, 28, null));
        }
        IrExpression irExpression = (IrExpression) withLValue.invoke(this$0.withReceiver(variableLValue, variableLValue2, arrayList3));
        IrBlockImpl irBlockImpl = new IrBlockImpl(this$0.startOffset, this$0.endOffset, irExpression.getType(), this$0.origin);
        IrBlockImplKt.addIfNotNull(irBlockImpl, irVariableCreateTemporaryVariable);
        IrBlockImplKt.addIfNotNull(irBlockImpl, irVariableCreateTemporaryVariable2);
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            IrBlockImplKt.addIfNotNull(irBlockImpl, (IrVariable) it2.next());
        }
        irBlockImpl.getStatements().add(irExpression);
        return irBlockImpl;
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.AssignmentReceiver
    public IrExpression assign(IrExpression value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return store(value);
    }
}
