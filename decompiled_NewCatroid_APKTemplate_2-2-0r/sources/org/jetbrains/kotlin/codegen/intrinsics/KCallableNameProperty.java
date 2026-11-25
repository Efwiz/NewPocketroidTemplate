package org.jetbrains.kotlin.codegen.intrinsics;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.JvmCodegenUtil;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitClassReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: KCallableNameProperty.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J0\u0010\u0003\u001a\u0004\u0018\u00010\u00042\f\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0004H\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/KCallableNameProperty;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicPropertyGetter;", "()V", CompilerOptions.GENERATE, "Lorg/jetbrains/kotlin/codegen/StackValue;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "returnType", "Lorg/jetbrains/org/objectweb/asm/Type;", "receiver", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KCallableNameProperty extends IntrinsicPropertyGetter {
    @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicPropertyGetter
    public StackValue generate(ResolvedCall<?> resolvedCall, final ExpressionCodegen codegen, final Type returnType, StackValue receiver) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        Intrinsics.checkNotNull(resolvedCall);
        ReceiverValue dispatchReceiver = resolvedCall.getDispatchReceiver();
        ExpressionReceiver expressionReceiver = dispatchReceiver instanceof ExpressionReceiver ? (ExpressionReceiver) dispatchReceiver : null;
        if (expressionReceiver == null) {
            return null;
        }
        KtExpression expression = expressionReceiver.getExpression();
        KtCallableReferenceExpression ktCallableReferenceExpression = expression instanceof KtCallableReferenceExpression ? (KtCallableReferenceExpression) expression : null;
        if (ktCallableReferenceExpression == null) {
            return null;
        }
        KtSimpleNameExpression callableReference = ktCallableReferenceExpression.getCallableReference();
        BindingContext bindingContext = codegen.getBindingContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
        final ResolvedCall<? extends CallableDescriptor> resolvedCall2 = CallUtilKt.getResolvedCall(callableReference, bindingContext);
        if (resolvedCall2 == null) {
            return null;
        }
        final ReceiverValue boundCallableReferenceReceiver = JvmCodegenUtil.getBoundCallableReferenceReceiver(resolvedCall2);
        return StackValue.operation(returnType, new Function1<InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.codegen.intrinsics.KCallableNameProperty.generate.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(InstructionAdapter instructionAdapter) {
                invoke2(instructionAdapter);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(InstructionAdapter instructionAdapter) {
                ReceiverValue receiverValue = boundCallableReferenceReceiver;
                if (receiverValue != null && !(receiverValue instanceof ImplicitClassReceiver)) {
                    StackValue stackValueGenerateReceiverValue = codegen.generateReceiverValue(receiverValue, false);
                    Intrinsics.checkNotNullExpressionValue(stackValueGenerateReceiverValue, "generateReceiverValue(...)");
                    KotlinType type = boundCallableReferenceReceiver.getType();
                    Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                    StackValue.coercion(stackValueGenerateReceiverValue, codegen.asmType(type), type).put(Type.VOID_TYPE, instructionAdapter);
                }
                instructionAdapter.aconst(resolvedCall2.getResultingDescriptor().getShortName().asString());
                StackValue.coerce(AsmTypes.JAVA_STRING_TYPE, returnType, instructionAdapter);
            }
        });
    }
}
