package org.jetbrains.kotlin.codegen.range.inExpression;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.BranchedValue;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.Invert;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: CallBasedInExpressionGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/inExpression/CallBasedInExpressionGenerator;", "Lorg/jetbrains/kotlin/codegen/range/inExpression/InExpressionGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "operatorReference", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;)V", "getCodegen", "()Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "isInverted", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "gen", "Lorg/jetbrains/kotlin/codegen/BranchedValue;", "argument", "Lorg/jetbrains/kotlin/codegen/StackValue;", CompilerOptions.GENERATE, Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallBasedInExpressionGenerator implements InExpressionGenerator {
    private final ExpressionCodegen codegen;
    private final boolean isInverted;
    private final ResolvedCall<? extends CallableDescriptor> resolvedCall;

    public CallBasedInExpressionGenerator(ExpressionCodegen codegen, KtSimpleNameExpression operatorReference) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(operatorReference, "operatorReference");
        this.codegen = codegen;
        BindingContext bindingContext = codegen.getBindingContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
        this.resolvedCall = CallUtilKt.getResolvedCallWithAssert(operatorReference, bindingContext);
        this.isInverted = Intrinsics.areEqual(operatorReference.getReferencedNameElementType(), KtTokens.NOT_IN);
    }

    public final ExpressionCodegen getCodegen() {
        return this.codegen;
    }

    @Override // org.jetbrains.kotlin.codegen.range.inExpression.InExpressionGenerator
    public BranchedValue generate(StackValue argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        BranchedValue branchedValueGen = gen(argument);
        return this.isInverted ? new Invert(branchedValueGen) : branchedValueGen;
    }

    private final BranchedValue gen(StackValue argument) {
        return new BranchedValue(argument, this, argument.type) { // from class: org.jetbrains.kotlin.codegen.range.inExpression.CallBasedInExpressionGenerator.gen.1
            final /* synthetic */ CallBasedInExpressionGenerator this$0;

            {
                Intrinsics.checkNotNull(type);
            }

            @Override // org.jetbrains.kotlin.codegen.BranchedValue, org.jetbrains.kotlin.codegen.StackValue
            public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter v) {
                Intrinsics.checkNotNullParameter(type, "type");
                Intrinsics.checkNotNullParameter(v, "v");
                invokeFunction(v);
                coerceTo(type, kotlinType, v);
            }

            @Override // org.jetbrains.kotlin.codegen.BranchedValue
            public void condJump(Label jumpLabel, InstructionAdapter v, boolean jumpIfFalse) {
                Intrinsics.checkNotNullParameter(jumpLabel, "jumpLabel");
                Intrinsics.checkNotNullParameter(v, "v");
                invokeFunction(v);
                v.visitJumpInsn(jumpIfFalse ? 153 : 154, jumpLabel);
            }

            private final void invokeFunction(InstructionAdapter v) {
                StackValue stackValueInvokeFunction = this.this$0.getCodegen().invokeFunction(this.this$0.resolvedCall.getCall(), this.this$0.resolvedCall, StackValue.none());
                Intrinsics.checkNotNullExpressionValue(stackValueInvokeFunction, "invokeFunction(...)");
                stackValueInvokeFunction.put(stackValueInvokeFunction.type, stackValueInvokeFunction.kotlinType, v);
            }
        };
    }
}
