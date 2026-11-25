package org.jetbrains.kotlin.codegen.range.forLoop;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.diagnostics.PsiDiagnosticUtils;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.scopes.receivers.TransientReceiver;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: IteratorForLoopGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0011\u001a\u00020\u0012H\u0014J\b\u0010\u0013\u001a\u00020\u0012H\u0016J\u0010\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0016H\u0014J\u0010\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/forLoop/IteratorForLoopGenerator;", "Lorg/jetbrains/kotlin/codegen/range/forLoop/AbstractForLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/psi/KtForExpression;)V", "asmTypeForIterator", "Lorg/jetbrains/org/objectweb/asm/Type;", "iteratorCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "iteratorType", "Lorg/jetbrains/kotlin/types/KotlinType;", "iteratorVarIndex", "", "nextCall", "assignToLoopParameter", "", "beforeLoop", "checkEmptyLoop", "loopExit", "Lorg/jetbrains/org/objectweb/asm/Label;", "checkPostConditionAndIncrement", "checkPreCondition", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IteratorForLoopGenerator extends AbstractForLoopGenerator {
    private final Type asmTypeForIterator;
    private final ResolvedCall<FunctionDescriptor> iteratorCall;
    private final KotlinType iteratorType;
    private int iteratorVarIndex;
    private final ResolvedCall<FunctionDescriptor> nextCall;

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void checkEmptyLoop(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForLoopGenerator
    protected void checkPostConditionAndIncrement(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IteratorForLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression) {
        super(codegen, forExpression);
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        KtExpression loopRange = forExpression.getLoopRange();
        Intrinsics.checkNotNull(loopRange);
        Object notNull = BindingContextUtils.getNotNull(getBindingContext(), BindingContext.LOOP_RANGE_ITERATOR_RESOLVED_CALL, loopRange, "No .iterator() function " + PsiDiagnosticUtils.atLocation(loopRange));
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        ResolvedCall<FunctionDescriptor> resolvedCall = (ResolvedCall) notNull;
        this.iteratorCall = resolvedCall;
        KotlinType returnType = ((FunctionDescriptor) resolvedCall.getResultingDescriptor()).getReturnType();
        Intrinsics.checkNotNull(returnType);
        this.iteratorType = returnType;
        Type typeAsmType = codegen.asmType(returnType);
        Intrinsics.checkNotNullExpressionValue(typeAsmType, "asmType(...)");
        this.asmTypeForIterator = typeAsmType;
        Object notNull2 = BindingContextUtils.getNotNull(getBindingContext(), BindingContext.LOOP_RANGE_NEXT_RESOLVED_CALL, loopRange, "No next() function " + PsiDiagnosticUtils.atLocation(loopRange));
        Intrinsics.checkNotNullExpressionValue(notNull2, "getNotNull(...)");
        this.nextCall = (ResolvedCall) notNull2;
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForLoopGenerator, org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void beforeLoop() {
        super.beforeLoop();
        int iCreateLoopTempVariable = createLoopTempVariable(this.asmTypeForIterator);
        this.iteratorVarIndex = iCreateLoopTempVariable;
        StackValue.local(iCreateLoopTempVariable, this.asmTypeForIterator, this.iteratorType).store(getCodegen().invokeFunction(this.iteratorCall, StackValue.none()), getV());
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void checkPreCondition(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
        KtExpression loopRange = getForExpression().getLoopRange();
        BindingContext bindingContext = getCodegen().getBindingContext();
        WritableSlice<KtExpression, ResolvedCall<FunctionDescriptor>> writableSlice = BindingContext.LOOP_RANGE_HAS_NEXT_RESOLVED_CALL;
        Intrinsics.checkNotNull(loopRange);
        Object notNull = BindingContextUtils.getNotNull(bindingContext, writableSlice, loopRange, "No hasNext() function " + PsiDiagnosticUtils.atLocation(loopRange));
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        ExpressionCodegen codegen = getCodegen();
        KotlinType returnType = ((FunctionDescriptor) this.iteratorCall.getResultingDescriptor()).getReturnType();
        Intrinsics.checkNotNull(returnType);
        StackValue stackValueInvokeFunction = getCodegen().invokeFunction(codegen.makeFakeCall(new TransientReceiver(returnType)), (ResolvedCall) notNull, StackValue.local(this.iteratorVarIndex, this.asmTypeForIterator, this.iteratorType));
        Intrinsics.checkNotNullExpressionValue(stackValueInvokeFunction, "invokeFunction(...)");
        stackValueInvokeFunction.put(Type.BOOLEAN_TYPE, getV());
        getV().ifeq(loopExit);
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForLoopGenerator
    protected void assignToLoopParameter() {
        ExpressionCodegen codegen = getCodegen();
        KotlinType returnType = ((FunctionDescriptor) this.iteratorCall.getResultingDescriptor()).getReturnType();
        Intrinsics.checkNotNull(returnType);
        StackValue stackValueInvokeFunction = getCodegen().invokeFunction(codegen.makeFakeCall(new TransientReceiver(returnType)), this.nextCall, StackValue.local(this.iteratorVarIndex, this.asmTypeForIterator, this.iteratorType));
        Intrinsics.checkNotNullExpressionValue(stackValueInvokeFunction, "invokeFunction(...)");
        StackValue.local(getLoopParameterVar(), getLoopParameterType(), getLoopParameterKotlinType()).store(stackValueInvokeFunction, getV());
    }
}
