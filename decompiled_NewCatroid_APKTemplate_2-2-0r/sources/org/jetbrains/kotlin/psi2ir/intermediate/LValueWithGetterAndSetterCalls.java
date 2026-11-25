package org.jetbrains.kotlin.psi2ir.intermediate;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.psi2ir.generators.CallGenerator;

/* JADX INFO: compiled from: LValueWithGetterAndSetterCalls.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0000\u0018\u00002\u00020\u0001B_\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u000e\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0007\u0012\u0014\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\b0\n\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0012¢\u0006\u0002\u0010\u0013J\b\u0010\u0016\u001a\u00020\u000bH\u0016J\u0010\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\u000bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/intermediate/LValueWithGetterAndSetterCalls;", "Lorg/jetbrains/kotlin/psi2ir/intermediate/LValue;", "callGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/CallGenerator;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getterCall", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallBuilder;", "setterCall", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "(Lorg/jetbrains/kotlin/psi2ir/generators/CallGenerator;Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;Lorg/jetbrains/kotlin/ir/types/IrType;IILorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "load", "store", "irExpression", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LValueWithGetterAndSetterCalls implements LValue {
    private final CallGenerator callGenerator;
    private final CallableDescriptor descriptor;
    private final int endOffset;
    private final Function0<CallBuilder> getterCall;
    private final IrStatementOrigin origin;
    private final Function1<IrExpression, CallBuilder> setterCall;
    private final int startOffset;
    private final IrType type;

    /* JADX WARN: Multi-variable type inference failed */
    public LValueWithGetterAndSetterCalls(CallGenerator callGenerator, CallableDescriptor descriptor, Function0<CallBuilder> getterCall, Function1<? super IrExpression, CallBuilder> setterCall, IrType type, int i, int i2, IrStatementOrigin irStatementOrigin) {
        Intrinsics.checkNotNullParameter(callGenerator, "callGenerator");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(getterCall, "getterCall");
        Intrinsics.checkNotNullParameter(setterCall, "setterCall");
        Intrinsics.checkNotNullParameter(type, "type");
        this.callGenerator = callGenerator;
        this.descriptor = descriptor;
        this.getterCall = getterCall;
        this.setterCall = setterCall;
        this.type = type;
        this.startOffset = i;
        this.endOffset = i2;
        this.origin = irStatementOrigin;
    }

    public /* synthetic */ LValueWithGetterAndSetterCalls(CallGenerator callGenerator, CallableDescriptor callableDescriptor, Function0 function0, Function1 function1, IrType irType, int i, int i2, IrStatementOrigin irStatementOrigin, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(callGenerator, callableDescriptor, function0, function1, irType, i, i2, (i3 & 128) != 0 ? null : irStatementOrigin);
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue
    public IrType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue
    public IrExpression load() {
        CallBuilder callBuilderInvoke = this.getterCall.invoke();
        if (callBuilderInvoke == null) {
            throw new AssertionError("No getter call for " + this.descriptor);
        }
        return this.callGenerator.generateCall(this.startOffset, this.endOffset, callBuilderInvoke, this.origin);
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.LValue
    public IrExpression store(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "irExpression");
        CallBuilder callBuilderInvoke = this.setterCall.invoke(irExpression);
        if (callBuilderInvoke == null) {
            throw new AssertionError("No setter call for " + this.descriptor);
        }
        return this.callGenerator.generateCall(this.startOffset, this.endOffset, callBuilderInvoke, this.origin);
    }
}
